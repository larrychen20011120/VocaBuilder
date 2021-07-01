package information;


import java.io.*;

public class User extends Information{
    private String id, name, passwd;
    private static final long serialVersionUID = 2037681803932504388L;

    // constructor
    // load
    public User(String id){
        this.id = id;
    }
    // dump
    public User(String id, String name, String passwd){
        this.id = id;
        this.passwd = passwd;
        this.name = name;
    }

    // load for other information
    private void loadInf(User user){
        this.name = user.name;
        this.passwd = user.passwd;
    }

    public void load(){
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + id + "/inf.obj"));

            loadInf((User) ois.readObject());

            ois.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void dump(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + id + "/inf.obj"));

            oos.writeObject(this);

            oos.flush();
            oos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String getPasswd(){
        return passwd;
    }
    public String getName(){
        return name;
    }

    public String getId() {
        return id;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
}
