package appObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable {
    private ArrayList<Vocabulary> vocabularyList = new ArrayList<>();
    private String name;
    private static final long serialVersionUID = 2785156638580723947L;

    public void add(Vocabulary v){
        if (v.getPage() <= vocabularyList.size()){
            vocabularyList.get(v.getPage() - 1).setEnglish(v.getEnglish());
            vocabularyList.get(v.getPage() - 1).setChinese(v.getChinese());
            vocabularyList.get(v.getPage() - 1).setType(v.getType());
        }else {
            vocabularyList.add(v.getPage() - 1, v);
        }
    }
    public void remove(Vocabulary v){
        vocabularyList.remove(v.getPage() - 1);
        for (int i = v.getPage() - 1; i < vocabularyList.size(); i++){
            vocabularyList.get(i).setPage(i + 1);
        }
    }

    public Vocabulary getElement(int i){
        return vocabularyList.get(i - 1);
    }

    public int getSize(){
        return vocabularyList.size();
    }

    public String getName() {
        return name;
    }

    public Card(String name){
        this.name = name;
    }

}
