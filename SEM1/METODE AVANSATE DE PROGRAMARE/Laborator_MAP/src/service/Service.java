package service;

import domain.Friendship;
import domain.Tuple;
import domain.User;
import repository.Repository;
import utils.Graphs;
import validators.FriendshipValidator;
import validators.UserValidator;

import java.util.ArrayList;

import static java.lang.StrictMath.toIntExact;

public class Service {
    private Repository<Long, User> repository;
    private UserValidator userValidator;
    private Repository<Tuple<Long, Long>, Friendship> network;
    private FriendshipValidator friendshipValidator;

    public Service(Repository<Long, User> repository, UserValidator userValidator, Repository<Tuple<Long, Long>, Friendship> network, FriendshipValidator friendshipValidator) {
        this.repository = repository;
        this.userValidator = userValidator;
        this.network = network;
        this.friendshipValidator = friendshipValidator;
    }

    private Friendship validateDataFriendship(Long id1, Long id2) {
        //if (id1 == null || id2 == null) throw new IllegalArgumentException("Id must not be null!");
        if (repository.findOneById(id1) == null || repository.findOneById(id2) == null)
            throw new IllegalArgumentException("One user doesn't exists!");
        Friendship friendship;
        if (id1 < id2) {
            friendship = network.findOneById(new Tuple<>(id1, id2));
        } else {
            friendship = network.findOneById(new Tuple<>(id2, id1));
        }
        if (friendship == null) throw new IllegalArgumentException("Friendship doesn't exists!");
        return friendship;
    }

    public User findOneUser(Long id) {
        return repository.findOneById(id);
    }

    public void addUser(String lastName, String firstName) {
        User user = new User(lastName, firstName);
        //if (user == null) throw new IllegalArgumentException("User must be not null!");
        userValidator.validate(user);
        repository.save(user);
    }

    public void updateUser(Long id, String lastName, String firstName) {
        //if (id == null) throw new IllegalArgumentException("Id must not be null!");
        if (repository.findOneById(id) == null) throw new IllegalArgumentException("User doesn't exists!");
        User user = new User(lastName, firstName);
        user.setId(id);
        userValidator.validate(user);
        repository.update(repository.findOneById(user.getId()), user);
    }

    public void removeUser(Long id) {
        //if (id == null) throw new IllegalArgumentException("Id must not be null!");
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

    public void addFriendship(Long id1, Long id2) {
        //if (id1 == null || id2 == null) throw new IllegalArgumentException("Id must not be null!");
        if (repository.findOneById(id1) == null || repository.findOneById(id2) == null)
            throw new IllegalArgumentException("One user doesn't exists!");
        Friendship friendship = new Friendship(id1, id2);
        if (network.findOneById(friendship.getId()) != null)
            throw new IllegalArgumentException("Friendship already exists!");
        friendshipValidator.validate(friendship);
        network.save(friendship);
    }

    public void updateFriendship(Long id1, Long id2, Long newId1, Long newId2) {
        Friendship friendship = validateDataFriendship(id1, id2);
        Friendship newFriendship = new Friendship(newId1, newId2);
        friendshipValidator.validate(newFriendship);
        network.update(friendship, newFriendship);
    }

    public void removeFriendship(Long id1, Long id2) {
        Friendship friendship = validateDataFriendship(id1, id2);
        network.delete(friendship.getId());
    }

    public Iterable<User> getAllUsers() {
        return repository.findAll();
    }

    public Iterable<Friendship> getAllFriendships() {
        return network.findAll();
    }

    private Graphs generateGraph(int size) {
        Graphs g = new Graphs(size);
        for (Friendship f : network.findAll()) {
            g.addEdge(toIntExact(f.getUser1()), toIntExact(f.getUser2()));
        }
        g.DFS();
        return g;
    }

    public int communitiesNumbers() {
        int size = 0;
        for (User user : repository.findAll()) {
            if (toIntExact(user.getId()) > size) size = toIntExact(user.getId());
        }
        Graphs g = generateGraph(size);
        int nr = g.ConnectedComponents();
        nr = nr - (size - repository.size());
        return nr;
    }

    public ArrayList<Integer> mostSociableCommunity() {
        int size = 0;
        for (User user : repository.findAll()) {
            if (toIntExact(user.getId()) > size) size = toIntExact(user.getId());
        }
        Graphs g = generateGraph(size);
        int max = 1;
        ArrayList<Integer> comp = new ArrayList<Integer>();
        ArrayList<ArrayList<Integer>> lists = g.returnComponents();
        for (ArrayList<Integer> list : lists) {
            if (list.size() >= max) {
                max = list.size();
                comp = list;
            }
        }
        return comp;
    }

}
