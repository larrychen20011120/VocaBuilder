package scene.home;

import appObject.Person;
import file.CheckOpen;
import information.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.Node;
import scene.menu.MenuController;

import java.io.IOException;

public class HomeController {


    @FXML
    private TextField id;
    @FXML
    private PasswordField passwd;
    @FXML
    private Label idWarning, pdWarning;
    @FXML
    private Pane home;


    public void submit(ActionEvent ae){
        if (id.getText().trim().length() == 0 ){
            pdWarning.setText("");
            idWarning.setText("帳號不可為空");
        }else if (passwd.getText().trim().length() == 0){
            pdWarning.setText("密碼不可為空");
            idWarning.setText("");
        }else{
            String path = id.getText();
            if (CheckOpen.hasFile(path)){
                User user = new User(path);
                user.load();
                idWarning.setText("");
                if (user.getPasswd().equals(passwd.getText())){
                    try{
                        Stage stage;
                        Scene scene;
                        Parent root;

                        FXMLLoader fl =  new FXMLLoader(getClass().getResource("../menu/menu.fxml"));
                        root = fl.load();
                        stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
                        Person person = new Person(user);
                        if (CheckOpen.hasFile(path + "/score.obj"))
                            person.getScore().load();
                        ((MenuController) fl.getController()).setPerson(person);

                        scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }else{
                    pdWarning.setText("密碼錯誤");
                    idWarning.setText("");
                }
            }else{
                idWarning.setText("查無此帳號");
                pdWarning.setText("");
            }
        }
    }

    public void switchToSignUp(ActionEvent ae) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveOver(MouseEvent me){
        home.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        home.getScene().setCursor(Cursor.DEFAULT);
    }


}


