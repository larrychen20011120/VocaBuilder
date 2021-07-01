package appObject;

import java.io.Serializable;

public class Vocabulary implements Serializable {
    private String english, chinese, type;
    private int page, level;
    private static final long serialVersionUID = 2785156638580723947L;

    public Vocabulary(String english, String chinese, String type, int page){
        this.english = english;
        this.chinese = chinese;
        this.type = type;
        this.page = page;
    }
    public Vocabulary(String english, String chinese, String type) {
        this.english = english;
        this.chinese = chinese;
        this.type = type;
    }

    public String getChinese() {
        return chinese;
    }

    public String getEnglish() {
        return english;
    }

    public String getType() {
        return type;
    }

    public int getPage() {
        return page;
    }

    public int getLevel() {
        return level;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
