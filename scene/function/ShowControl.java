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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import scene.menu.MenuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ShowControl {
    private Person person;
    private String[] page;

    @FXML
    private Button first, second, third, fourth, fifth, sixth, menu;
    @FXML
    private Label num;
    @FXML
    private ImageView paint;
    @FXML
    private Pane showCards;

    public void setPerson(Person person) {
        this.person = person;
        int size = person.cards.cardList.size() / 6 + 1;
        page = new String[size];
        for (int i = 0; i < page.length; i++){
            page[i] = Integer.toString(i + 1);
        }
        setCard(num.getText());

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

    public void next(ActionEvent ae){
        if (Integer.parseInt(num.getText()) < page.length){
            num.setText(Integer.toString(Integer.parseInt(num.getText()) + 1));
            setCard(num.getText());
        }
    }
    public void pre(ActionEvent ae){
        if (Integer.parseInt(num.getText()) > 1){
            num.setText(Integer.toString(Integer.parseInt(num.getText()) - 1));
            setCard(num.getText());
        }
    }

    private void setCard(String page){
        int p = Integer.parseInt(page) - 1;
        String name;
        for (int i = 0; i < 6; i++) {
            if (p * 6 + i < person.cards.cardList.size()) {
                name = person.cards.cardList.get(p * 6 + i).getName();
            } else {
                name = "+";
            }
            switch (i) {
                case 0:
                    first.setText(name);
                    break;
                case 1:
                    second.setText(name);
                    break;
                case 2:
                    third.setText(name);
                    break;
                case 3:
                    fourth.setText(name);
                    break;
                case 4:
                    fifth.setText(name);
                    break;
                default:
                    sixth.setText(name);
            }
        }
    }

    public void switchScene(ActionEvent ae){
        if (((Button)ae.getSource()).getText().equals("+")){
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
        }else{
            int index = switch ((((Button) ae.getSource()).getId())) {
                case "first" -> (Integer.parseInt(num.getText()) - 1) * 6 + 1;
                case "second" -> (Integer.parseInt(num.getText()) - 1) * 6 + 2;
                case "third" -> (Integer.parseInt(num.getText()) - 1) * 6 + 3;
                case "fourth" -> (Integer.parseInt(num.getText()) - 1) * 6 + 4;
                case "fifth" -> (Integer.parseInt(num.getText()) - 1) * 6 + 5;
                default -> (Integer.parseInt(num.getText()) - 1) * 6 + 6;
            };

            try{
                Stage stage;
                Scene scene;
                Parent root;

                FXMLLoader fl =  new FXMLLoader(getClass().getResource("../function/readCard.fxml"));
                root = fl.load();
                stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
                ((ReadCardController) fl.getController()).setPerson(person, index - 1);

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
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
        showCards.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        showCards.getScene().setCursor(Cursor.DEFAULT);
    }

}
