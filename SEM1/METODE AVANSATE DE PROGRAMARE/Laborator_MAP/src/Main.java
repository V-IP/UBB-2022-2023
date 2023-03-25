import repository.database.FriendshipDatabaseRepository;
import repository.database.UserDatabaseRepository;
import repository.file.FriendsFileRepository;
import repository.file.UserFileRepository;
import service.Service;
import ui.UI;
import validators.FriendshipValidator;
import validators.UserValidator;

import java.util.*;

public class Main {
    public static void ClearScreen() {
        for (int i = 0; i < 100; i++)
            System.out.println("\n");
    }

    public static void printMenu() {
        String s = "";
        s = s + "\n ------------------------------------ \n";
        s = s + "Please choose one of the options below: \n";
        s = s + "\t \n  0. Get all users and friendships";
        s = s + "\t \n  1. Add user";
        s = s + "\t \n  2. Update user";
        s = s + "\t \n  3. Remove user";
        s = s + "\t \n  4. Add friend";
        s = s + "\t \n  5. Update friend";
        s = s + "\t \n  6. Remove friend";
        s = s + "\t \n  7. Display number of communities";
        s = s + "\t \n  8. Display the most sociable community";
        s = s + "\n";
        s = s + "\t \n -1. Exit";
        s = s + "\n ------------------------------------ \n";
        System.out.println(s);
    }

    public static void runMenu(UI ui) {
        int opt = 0;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            printMenu();

            opt = 0;
            while (true) {
                System.out.println("Please enter your option: ");
                opt = scanner.nextInt();
                if (opt >= -1 && opt <= 8) break;
                else {
                    System.out.println("Please choose one of the options from above. Try again!");
                }
            }

            ClearScreen();

            if (opt == -1) {
                System.out.println("Exiting...");
                break;
            } else if (opt == 0) {
                ui.printUsers();
                ui.printFriendships();
                System.out.println("Printed successfully.");
            } else if (opt == 1) {
                System.out.println("Provide last name:");
                String lastName = scanner.next();
                System.out.println("Provide first name:");
                String firstName = scanner.next();
                ui.addUser(lastName, firstName);
            } else if (opt == 2) {
                System.out.println("Provide id of the existing user:");
                Long id = scanner.nextLong();
                System.out.println("Provide new last name:");
                String lastName = scanner.next();
                System.out.println("Provide new first name:");
                String firstName = scanner.next();
                ui.updateUser(id, lastName, firstName);
            } else if (opt == 3) {
                System.out.println("Provide id:");
                Long id = scanner.nextLong();
                ui.removeUser(id);
            } else if (opt == 4) {
                System.out.println("Provide the id of the first user:");
                Long id1 = scanner.nextLong();
                System.out.println("Provide the id of the second user:");
                Long id2 = scanner.nextLong();
                ui.addFriendship(id1, id2);
            } else if (opt == 5) {
                System.out.println("Provide the id of the first existing user:");
                Long id1 = scanner.nextLong();
                System.out.println("Provide the id of the second existing user:");
                Long id2 = scanner.nextLong();
                System.out.println("Provide the new id of the first user:");
                Long newId1 = scanner.nextLong();
                System.out.println("Provide the new id of the second user:");
                Long newId2 = scanner.nextLong();
                ui.updateFriendship(id1, id2, newId1, newId2);
            } else if (opt == 6) {
                System.out.println("Provide the id of the first user:");
                Long id1 = scanner.nextLong();
                System.out.println("Provide the id of the second user:");
                Long id2 = scanner.nextLong();
                ui.removeFriendship(id1, id2);
            } else if (opt == 7) {
                ui.communitiesNumbers();
            } else if (opt == 8) {
                ui.mostSociableCommunity();
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        UserValidator userValidator = new UserValidator();
        FriendshipValidator friendshipValidator = new FriendshipValidator();
        //UserFileRepository userFileRepository = new UserFileRepository();
        //FriendsFileRepository friendsFileRepository = new FriendsFileRepository();
        UserDatabaseRepository userDatabaseRepository = new UserDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", userValidator);
        FriendshipDatabaseRepository friendshipDatabaseRepository = new FriendshipDatabaseRepository("jdbc:postgresql://localhost:5432/Network", "postgres", "postgres", friendshipValidator);
        Service service = new Service(userDatabaseRepository, userValidator, friendshipDatabaseRepository, friendshipValidator);
        UI ui = new UI(service);
        runMenu(ui);
    }
}