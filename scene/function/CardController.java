package scene.function;

import appObject.Person;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CardController {
    private Person person;

    @FXML
    private Pane noCard;
    @FXML
    private ImageView paint;
    @FXML
    private Button button;

    public void setPerson(Person person) {
        this.person = person;
        try {
            paint.setImage(new Image(new FileInputStream("src/outImage/paint.png")));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/outImage/add.png")));
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            button.setGraphic(imageView);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    public void addCard(ActionEvent ae){
        try{
            Stage stage;
            Scene scene;
            Parent root;

            FXMLLoader fl =  new FXMLLoader(getClass().getResource("../function/createCard.fxml"));
            root = fl.load();
            stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
            ((CreateCard) fl.getController()).setPerson(person);

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void moveOver(MouseEvent me){
        noCard.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        noCard.getScene().setCursor(Cursor.DEFAULT);
    }

}
