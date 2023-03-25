package ui;

import domain.Friendship;
import domain.User;
import service.Service;

import java.util.ArrayList;

public class UI {
    private Service service;

    public UI(Service service) {
        this.service = service;
    }

    public void printUsers() {
        service.getAllUsers().forEach(System.out::println);
    }

    public void printFriendships() {
        service.getAllFriendships().forEach(System.out::println);
    }

    public void addUser(String lastName, String firstName) {
        try {
            service.addUser(lastName, firstName);
            System.out.println("User added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void updateUser(Long id, String lastName, String firstName) {
        try {
            service.updateUser(id, lastName, firstName);
            System.out.println("User updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void removeUser(Long id) {
        try {
            service.removeUser(id);
            System.out.println("User removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void addFriendship(Long id1, Long id2) {
        try {
            service.addFriendship(id1, id2);
            System.out.println("Friendship added successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void updateFriendship(Long id1, Long id2, Long newId1, Long newId2) {
        try {
            service.updateFriendship(id1, id2, newId1, newId2);
            System.out.println("Friendship updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void removeFriendship(Long id1, Long id2) {
        try {
            service.removeFriendship(id1, id2);
            System.out.println("Friendship removed successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
    }

    public void communitiesNumbers() {
        int nr = service.communitiesNumbers();
        System.out.println("Communities number=" + nr);
    }

    public void mostSociableCommunity() {
        ArrayList<Integer> list = service.mostSociableCommunity();
        if (list.size() > 0) {
            System.out.println("Most sociable community is formed by:");
            for (int id : list) {
                System.out.print(service.findOneUser((long) id) + "\n");
            }
        } else System.out.println("There are no cummunities.");
    }
}
