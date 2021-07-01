package scene.menu;

import appObject.Person;
import file.CheckOpen;
import information.Cards;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scene.function.CardController;
import scene.function.InfController;
import scene.function.Quiz;
import scene.function.ShowControl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MenuController {

    private Person person;
    @FXML
    private ImageView paint;
    @FXML
    private Button out;
    @FXML
    private AnchorPane menu;

    public void setPerson(Person person) {
        this.person = person;
        try {
            paint.setImage(new Image(new FileInputStream("src/outImage/paint.png")));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/outImage/out.png")));
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            out.setGraphic(imageView);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        createCard();
    }
    private void createCard(){
        if (CheckOpen.hasFile(person.getUser().getId() + "/cards.obj")){
            person.cards = new Cards(person.getUser().getId());
            person.cards.load();
        }else{
            person.cards = null;
        }
    }

    public void switchToCard(ActionEvent ae) throws IOException {
        try {
            // switch to Menu and send the User message
            Parent root;
            Stage stage;
            Scene scene;
            FXMLLoader fl = new FXMLLoader();

            if (person.cards != null){
                fl.setLocation(InfController.class.getResource("../function/showCard.fxml"));
                root = fl.load(InfController.class.getResource("../function/showCard.fxml").openStream());
                stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
                ((ShowControl)fl.getController()).setPerson(person);
            }else{
                fl.setLocation(InfController.class.getResource("../function/noCard.fxml"));
                root = fl.load(InfController.class.getResource("../function/noCard.fxml").openStream());
                stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
                ((CardController)fl.getController()).setPerson(person);
            }

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void switchToInf(ActionEvent ae) throws IOException {
        try {
            // switch to Menu and send the User message
            Parent root;
            Stage stage;
            Scene scene;
            FXMLLoader fl = new FXMLLoader();

            fl.setLocation(InfController.class.getResource("../function/Inf.fxml"));
            root = fl.load(InfController.class.getResource("../function/Inf.fxml").openStream());
            stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
            ((InfController)fl.getController()).setPerson(person);

            scene = new Scene(root);

            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void switchToScore(ActionEvent ae) throws IOException {
        try {
            // switch to Menu and send the User message
            Parent root;
            Stage stage;
            Scene scene;
            FXMLLoader fl = new FXMLLoader();

            fl.setLocation(InfController.class.getResource("../function/Inf.fxml"));
            root = fl.load(InfController.class.getResource("../function/Inf.fxml").openStream());
            stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
            ((InfController)fl.getController()).setPerson(person);
            ((InfController)fl.getController()).setTab();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void switchToQuiz(ActionEvent ae) throws IOException {
        try {
            // switch to Menu and send the User message
            Parent root;
            Stage stage;
            Scene scene;
            FXMLLoader fl = new FXMLLoader();

            fl.setLocation(InfController.class.getResource("../function/quiz.fxml"));
            root = fl.load(InfController.class.getResource("../function/quiz.fxml").openStream());
            stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
            ((Quiz)fl.getController()).setPerson(person);


            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void logOut(ActionEvent ae){
        try{
            Stage stage;
            Scene scene;
            Parent root;

            FXMLLoader fl =  new FXMLLoader(getClass().getResource("../home/home.fxml"));
            root = fl.load();
            stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void moveOver(MouseEvent me){
        menu.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        menu.getScene().setCursor(Cursor.DEFAULT);
    }

}
