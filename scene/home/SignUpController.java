package scene.home;

import file.CheckOpen;
import information.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUpController {
    @FXML
    private TextField id, name;
    @FXML
    private PasswordField passwd;
    @FXML
    private Label idWarning;
    @FXML
    private AnchorPane signUp;


    public void switchToHome(ActionEvent ae) throws IOException {
        Stage stage;
        Scene scene;
        Parent root;
        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void submit(ActionEvent ae){
        String path = id.getText();
        if (CheckOpen.hasFile(path)){
            idWarning.setText("帳號已存在");
        }else{
            CheckOpen.createDir(path);
            CheckOpen.createFile(path + "/inf.obj");
            User user = new User(id.getText(), name.getText(), passwd.getText());
            user.dump();
            try {
                switchToSignIn(ae);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void reset(ActionEvent ae){
        id.setText("");
        name.setText("");
        passwd.setText("");
        idWarning.setText("");
    }
    private void switchToSignIn(ActionEvent ae) throws IOException {
        Parent root;
        Stage stage;
        Scene scene;

        root = FXMLLoader.load(getClass().getResource("home.fxml"));
        stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveOver(MouseEvent me){
        signUp.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        signUp.getScene().setCursor(Cursor.DEFAULT);
    }
}
