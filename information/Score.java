package information;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

public class Score extends Information{

    private static final long serialVersionUID = 2785156638580723947L;

    private long [][] score = new long[6][2]; // i : level - 1, j : correct , total
    private LinkedList<Integer> total = new LinkedList<>();
    private final String owner;

    public Score(String owner){
        this.owner = owner;
    }

    public void setScore(long[][] score) {
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 2; j++){
                this.score[i][j] += score[i][j];
            }
        }
    }
    public void addTotal(int adder){
        // adder : correct num
        if (total.size() != 0)
            adder += total.getLast();
        total.add(adder);
    }

    public long[][] getScore() {
        return score;
    }

    public LinkedList<Integer> getTotal() {
        return total;
    }

    private void loadInf(Score score){
        this.score = score.score;
        this.total = score.total;
    }

    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + owner + "/score.obj"));

            loadInf((Score) ois.readObject());

            ois.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dump(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + owner +"/score.obj"));

            oos.writeObject(this);

            oos.flush();
            oos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
