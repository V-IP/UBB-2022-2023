package repository.file;

import domain.User;

import java.util.List;

public class UserFileRepository extends AbstractFileRepository<Long, User> {

    public UserFileRepository() {
        super("C:\\Users\\Asus\\IdeaProjects\\Lab3\\data\\users.csv");
    }

    @Override
    public User extractEntity(List<String> attributes) {
        User user = new User(attributes.get(1), attributes.get(2));
        user.setId(Long.parseLong(attributes.get(0)));
        return user;
    }

    @Override
    public String encapsulateEntity(User user) {
        return user.getId() + ";" + user.getFirstName() + ";" + user.getLastName() + "\n";
    }
}
