package repository.file;

import domain.Friendship;
import domain.Tuple;

import java.util.List;

public class FriendsFileRepository extends AbstractFileRepository<Tuple<Long, Long>, Friendship> {

    public FriendsFileRepository() {
        super("C:\\Users\\Asus\\IdeaProjects\\Lab3\\data\\friends.csv");
    }

    @Override
    public Friendship extractEntity(List<String> attributes) {
        Friendship friendship = new Friendship(Long.parseLong(attributes.get(0)), Long.parseLong(attributes.get(1)));
        return friendship;
    }

    @Override
    public String encapsulateEntity(Friendship friendship) {
        return friendship.getUser1() + ";" + friendship.getUser2() + ";" + friendship.getStringFriendsFrom() + "\n";
    }


}
