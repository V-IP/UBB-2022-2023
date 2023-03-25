package com.example.lab_map_javafx.service;

import com.example.lab_map_javafx.domain.*;
import com.example.lab_map_javafx.repository.Repository;
import com.example.lab_map_javafx.validators.FriendshipValidator;
import com.example.lab_map_javafx.validators.UserValidator;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class Service {
    private Repository<Long, User> repository;
    private UserValidator userValidator;
    private Repository<Tuple<Long, Long>, Friendship> network;
    private FriendshipValidator friendshipValidator;
    private Repository<Struct<Long, Long, LocalDateTime>, Message> chat;

    public Service(Repository<Long, User> repository, UserValidator userValidator, Repository<Tuple<Long, Long>, Friendship> network, FriendshipValidator friendshipValidator, Repository<Struct<Long, Long, LocalDateTime>, Message> chat) {
        this.repository = repository;
        this.userValidator = userValidator;
        this.network = network;
        this.friendshipValidator = friendshipValidator;
        this.chat = chat;
    }

    private Friendship validateDataFriendship(Long id1, Long id2) {
        if (repository.findOneById(id1) == null || repository.findOneById(id2) == null)
            throw new IllegalArgumentException("One user doesn't exists!");
        Friendship friendship = network.findOneById(new Tuple<>(Math.min(id1, id2), Math.max(id1, id2)));
        if (friendship == null) throw new IllegalArgumentException("Friendship doesn't exists!");
        return friendship;
    }

    public User findOneUser(Long id) {
        return repository.findOneById(id);
    }

    public Friendship findOneFriendship(Long id1, Long id2, Long from) {
        return network.findOneById(new Friendship(id1, id2, from).getId());
    }

    public Message findOneMessage(Long id1, Long id2, LocalDateTime date) {
        Map<String, Message> messages = new HashMap<>();
        for (Message message : chat.findAll()) {
            String key = message.getId().getFrom() + "-" + message.getId().getTo() + "-" + message.getId().getDate();
            messages.put(key, message);
        }
        String messageKey = id1 + "-" + id2 + "-" + date;
        return messages.get(messageKey);
    }

    public Iterable<Message> findConversation(Long id1, Long id2) {
        return chat.findAll().stream()
                .filter(m -> (Objects.equals(m.getId().getFrom(), id1) && Objects.equals(m.getId().getTo(), id2)) || (Objects.equals(m.getId().getFrom(), id2) && Objects.equals(m.getId().getTo(), id1)))
                .collect(Collectors.toSet());
    }

    public Set<User> getFriends(Long id) {
        return network.findAll().stream()
                .filter(f -> f.getStatus() == 1)
                .filter(f -> f.getId().getE1().equals(id) || f.getId().getE2().equals(id))
                .map(f -> repository.findOneById(f.getId().getE1().equals(id) ? f.getId().getE2() : f.getId().getE1()))
                .collect(Collectors.toSet());
    }

    public User findUserByName(String lastName, String firstName) {
        return repository.findAll().stream()
                .filter(u -> Objects.equals(u.getLastName(), lastName) && Objects.equals(u.getFirstName(), firstName))
                .findFirst()
                .orElse(null);
    }

    public User findUserByUsername(String username) {
        Map<String, User> users = new HashMap<>();
        for (User user : repository.findAll()) {
            users.put(user.getUsername(), user);
        }
        return users.get(username);
    }

    public void addUser(String lastName, String firstName, String username, String password) {
        User user = new User(lastName, firstName, username, password);
        userValidator.validate(user);
        repository.save(user);
    }

    public void updateUser(Long id, String lastName, String firstName, String username, String password) {
        if (repository.findOneById(id) == null) throw new IllegalArgumentException("User doesn't exists!");
        User user = new User(lastName, firstName, username, password);
        user.setId(id);
        userValidator.validate(user);
        repository.update(repository.findOneById(user.getId()), user);
    }

    public void removeUser(Long id) {
        User user = repository.findOneById(id);
        if (user == null) throw new IllegalArgumentException("User doesn't exists!");
        Iterable<User> list = repository.findAll();
        for (User u : list) {
            if ((network.findOneById(new Tuple<>(u.getId(), user.getId())) != null) || (network.findOneById(new Tuple<>(user.getId(), u.getId())) != null)) {
                removeFriendship(u.getId(), user.getId());
            }
        }
        repository.delete(user.getId());
    }

    public void addFriendship(Long id1, Long id2, Long from) {
        if (repository.findOneById(id1) == null || repository.findOneById(id2) == null)
            throw new IllegalArgumentException("One user doesn't exists!");
        Friendship friendship = new Friendship(id1, id2, from);
        if (network.findOneById(friendship.getId()) != null)
            throw new IllegalArgumentException("Friendship already exists!");
        friendshipValidator.validate(friendship);
        network.save(friendship);
    }

    public void updateFriendship(Long id1, Long id2, Long newId1, Long newId2) {
        Friendship friendship = validateDataFriendship(id1, id2);
        Friendship newFriendship = new Friendship(newId1, newId2, friendship.getFrom());
        newFriendship.setStatus(1);
        friendshipValidator.validate(newFriendship);
        network.update(friendship, newFriendship);
    }

    public void removeFriendship(Long id1, Long id2) {
        Friendship friendship = validateDataFriendship(id1, id2);
        network.delete(friendship.getId());
    }

    public void addMessage(Long id1, Long id2, LocalDateTime date, String info) {
        validateDataFriendship(id1, id2);
        Message message = new Message(id1, id2, date, info);
        chat.save(message);
    }

    public void updateMessage(Long id1, Long id2, LocalDateTime date, String newInfo) {
        validateDataFriendship(id1, id2);
        Message message = chat.findOneById(new Struct<>(id1, id2, date));
        if (message == null) throw new IllegalArgumentException("Message doesn't exists!");
        Message newMessage = new Message(id1, id2, date, newInfo);
        chat.update(message, newMessage);
    }

    public void removeMessage(Long id1, Long id2, LocalDateTime date) {
        validateDataFriendship(id1, id2);
        Message message = chat.findOneById(new Struct<>(id1, id2, date));
        if (message == null) throw new IllegalArgumentException("Message doesn't exists!");
        chat.delete(message.getId());
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public Iterable<Friendship> getAllFriendships() {
        return network.findAll();
    }

//    public Iterable<Message> getAllMessages() {
//        return chat.findAll();
//    }

//    private Graphs generateGraph(int size) {
//        Graphs g = new Graphs(size);
//        for (Friendship f : network.findAll()) {
//            g.addEdge(toIntExact(f.getUser1()), toIntExact(f.getUser2()));
//        }
//        g.DFS();
//        return g;
//    }
//
//    public int communitiesNumbers() {
//        int size = 0;
//        for (User user : repository.findAll()) {
//            if (toIntExact(user.getId()) > size) size = toIntExact(user.getId());
//        }
//        Graphs g = generateGraph(size);
//        int nr = g.ConnectedComponents();
//        nr = nr - (size - repository.size());
//        return nr;
//    }
//
//    public ArrayList<Integer> mostSociableCommunity() {
//        int size = 0;
//        for (User user : repository.findAll()) {
//            if (toIntExact(user.getId()) > size) size = toIntExact(user.getId());
//        }
//        Graphs g = generateGraph(size);
//        int max = 1;
//        ArrayList<Integer> comp = new ArrayList<Integer>();
//        ArrayList<ArrayList<Integer>> lists = g.returnComponents();
//        for (ArrayList<Integer> list : lists) {
//            if (list.size() >= max) {
//                max = list.size();
//                comp = list;
//            }
//        }
//        return comp;
//    }
}
