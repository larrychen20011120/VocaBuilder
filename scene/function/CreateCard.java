package scene.function;

import appObject.Card;
import appObject.Person;
import appObject.Vocabulary;
import information.Cards;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CreateCard implements Initializable {
    private Person person;
    private Card card;
    @FXML
    private TextField english, chinese, enterName;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private Label page, name, errorName, errorEnglish, errorChinese, errorType, saveWarning;
    @FXML
    private Pane ground, message;
    @FXML
    private Button bp, bn, bd, bm, bs;
    @FXML
    private ImageView paint;



    private final String[] partOfSpeech = {"", "動詞", "名詞", "形容詞", "副詞", "冠詞", "介係詞", "連接詞"};


    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1){
        type.getItems().addAll(partOfSpeech);
        english.setVisible(false);
        chinese.setVisible(false);
        bp.setVisible(false);
        bd.setVisible(false);
        bs.setVisible(false);
        bn.setVisible(false);
        bm.setVisible(false);
        saveWarning.setVisible(false);

        try {
            paint.setImage(new Image(new FileInputStream("src/outImage/paint.png")));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        try {
            ImageView imageView = new ImageView(new Image(new FileInputStream("src/outImage/save.png")));
            imageView.setFitHeight(40);
            imageView.setPreserveRatio(true);
            bs.setGraphic(imageView);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }
    public void addVocabulary(ActionEvent ae){
        if (!checkNull()) {
            if (checkEnglish(english.getText())){
                errorEnglish.setText("");
                errorChinese.setText("");
                errorType.setText("");
                saveWarning.setVisible(false);
                Vocabulary v = new Vocabulary(english.getText(), chinese.getText(), type.getValue(), Integer.parseInt(page.getText()));
                card.add(v);
                int p = Integer.parseInt(page.getText());
                p += 1;
                page.setText(Integer.toString(p));
                changeShow(p);
            }else{
                errorEnglish.setText("請輸入英文");
                errorChinese.setText("");
                errorType.setText("");
            }
        }else{
            if (english.getText().equals("")){
                errorEnglish.setText("英文欄不能為空");
                errorChinese.setText("");
                errorType.setText("");
            }else if (chinese.getText().equals("")){
                errorChinese.setText("中文不能為空");
                errorEnglish.setText("");
                errorType.setText("");
            }else{
                errorType.setText("請選擇詞性");
                errorEnglish.setText("");
                errorChinese.setText("");
            }
        }

    }
    public void removeVocabulary(ActionEvent ae){
        if (!checkNull()){
            Vocabulary v = new Vocabulary(english.getText(), chinese.getText(), type.getValue(), Integer.parseInt(page.getText()));
            card.remove(v);
            changeShow(Integer.parseInt(page.getText()));
        }
    }

    public void next(ActionEvent ae){
        int p = Integer.parseInt(page.getText());
        if (p <= card.getSize()) {
            p += 1;
            page.setText(Integer.toString(p));
        }
        changeShow(p);
    }
    public void pre(ActionEvent ae){
        int p = Integer.parseInt(page.getText());
        if (p > 1) {
            p -= 1;
            page.setText(Integer.toString(p));
        }
        changeShow(p);
    }
    public void send(ActionEvent ae){
        if (!enterName.getText().equals("")) {
            if (!checkSpecial(enterName.getText())){
                name.setText(enterName.getText());
                ground.getChildren().remove(message);
                english.setVisible(true);
                chinese.setVisible(true);
                bp.setVisible(true);
                bd.setVisible(true);
                bs.setVisible(true);
                bn.setVisible(true);
                bm.setVisible(true);
                if (person.cards == null)
                    person.cards = new Cards(person.getUser().getId());
                card = new Card(name.getText());
            }else{
                errorName.setText("名字勿放入加號或空白");
            }
        }else{
            errorName.setText("名字不可為空");
        }
    }

    private void changeShow(int p){
        if (card.getSize() >= p){
            Vocabulary v = card.getElement(p);

            english.setText(v.getEnglish());
            chinese.setText(v.getChinese());
            type.setValue(v.getType());
        }else{
            english.setText("");
            chinese.setText("");
            type.setValue("");
        }
    }

    private boolean checkNull(){
        if (english.getText().equals("") || chinese.getText().equals("") || type.getValue() == null){
            return true;
        }
        else return false;
    }

    private boolean checkSpecial(String s){
        char[] special = {' ', '+', '\n', '\t'};
        for (int i = 0; i < s.length(); i++){
            for (int j = 0; j < special.length; j++){
                if (s.charAt(i) == special[j])
                    return true;
            }
        }
        return false;
    }

    private boolean checkEnglish(String s){
        for (int i = 0; i < s.length(); i++){
            if (!((s.charAt(i) >= 'A' && s.charAt(i) <= 'Z' ) || (s.charAt(i) >= 'a' && s.charAt(i) <= 'z'))){
                return false;
            }
        }
        return true;
    }

    public void save(ActionEvent ae){
        if (card.getSize() == 0){
            saveWarning.setVisible(true);
        }else{
            try {
                person.cards.cardList.add(card);
                person.cards.dump();
                Parent root;
                Stage stage;
                Scene scene;

                FXMLLoader fl = new FXMLLoader();
                fl.setLocation(InfController.class.getResource("../function/showCard.fxml"));
                root = fl.load(InfController.class.getResource("../function/showCard.fxml").openStream());
                stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
                scene = new Scene(root);
                ((ShowControl)fl.getController()).setPerson(person);

                stage.setScene(scene);
                stage.show();

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void moveOver(MouseEvent me){
        ground.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        ground.getScene().setCursor(Cursor.DEFAULT);
    }

}
