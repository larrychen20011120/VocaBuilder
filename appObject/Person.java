package appObject;

import information.Cards;
import information.Score;
import information.User;


public class Person {
    private User user;
    public Cards cards;
    private Score score;

    public Person(User user){
        this.user = user;
        this.score = new Score(this.getUser().getId());
    }

    public User getUser() {
        return user;
    }

    public Score getScore() {
        return score;
    }

}
