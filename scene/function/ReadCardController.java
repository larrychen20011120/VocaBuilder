package scene.function;

import appObject.Card;
import appObject.Person;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scene.menu.MenuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ReadCardController {
    private Person person;
    private Card card;
    private int page = 1;

    @FXML
    private Label english, chinese, type;
    @FXML
    private ImageView paint;
    @FXML
    private AnchorPane readCard;
    @FXML
    private Button menu;

    public void setPerson(Person person, int num){
        this.person = person;
        card = person.cards.cardList.get(num);
        english.setText(card.getElement(1).getEnglish());
        chinese.setText(card.getElement(1).getChinese());
        type.setText(card.getElement(1).getType());

        try {
            paint.setImage(new Image(new FileInputStream("src/outImage/paint.png")));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/outImage/menu.png")));
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            menu.setGraphic(imageView);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

    }

    public void next(){
        if (page < card.getSize() ){
            page += 1;
            english.setText(card.getElement(page).getEnglish());
            chinese.setText(card.getElement(page).getChinese());
            type.setText(card.getElement(page).getType());
        }
    }

    public void pre(){
        if (page > 1){
            page -= 1;
            english.setText(card.getElement(page).getEnglish());
            chinese.setText(card.getElement(page).getChinese());
            type.setText(card.getElement(page).getType());
        }
    }

    public void switchToMenu(ActionEvent ae){
        try{
            Stage stage;
            Scene scene;
            Parent root;

            FXMLLoader fl =  new FXMLLoader(getClass().getResource("../menu/menu.fxml"));
            root = fl.load();
            stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
            ((MenuController) fl.getController()).setPerson(person);

            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void moveOver(MouseEvent me){
        readCard.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        readCard.getScene().setCursor(Cursor.DEFAULT);
    }

}
