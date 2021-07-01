package information;

import appObject.Card;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class Cards extends Information{
    public LinkedList<Card> cardList;
    private final String owner;
    private static final long serialVersionUID = 2037681803932504388L;

    public Cards(String owner){
        this.owner = owner;
        this.cardList = new LinkedList<>();
    }

    protected void loadInf(Cards cards){
        this.cardList = cards.cardList;
    }

    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + owner + "/cards.obj"));

            loadInf((Cards) ois.readObject());

            ois.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dump(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + owner + "/cards.obj"));

            oos.writeObject(this);

            oos.flush();
            oos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
