package scene.function;

import appObject.Person;
import appObject.Vocabulary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import scene.menu.MenuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;


public class ShowTestScore {

    private Person person;
    private Vocabulary[] vocabularies;
    private int[] select, randInt;
    private int page = 0;

    @FXML
    private Button next;
    @FXML
    private RadioButton ans1, ans2, ans3, ans4;
    @FXML
    private Label qus, num, score;
    @FXML
    private ImageView paint;
    @FXML
    private AnchorPane showTest;

    private final RadioButton[] choose = new RadioButton[4];


    public void setData(Person person, int[] select, int[] randInt, Vocabulary[] vocabularies, int score){
        this.person = person;
        this.select = select;
        this.vocabularies = vocabularies;
        this.randInt = randInt;
        this.score.setText(Integer.toString(5 * score));

        init();
    }

    private void init(){
        try {
            paint.setImage(new Image(new FileInputStream("src/outImage/paint.png")));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        choose[0] = ans1; choose[1] = ans2; choose[2] = ans3; choose[3] = ans4;
        if (select.length == 0){
            qus.setText("恭喜全對!");
            next.setText("結束");
            for (int i = 0; i < 4; i++)
                choose[i].setVisible(false);
        }else if (select.length == 1){
            next.setText("結束");
            qus.setText(vocabularies[0].getEnglish());
            num.setText("(" + Integer.toString(page + 1) + ")");
            choose[randInt[0]].setText(vocabularies[0].getChinese());
            choose[randInt[0]].setTextFill(Color.RED);

            for (int i = 0, j = 1; i < 4; i++) {
                choose[i].setSelected(false);
                if (i != randInt[page]) {
                    choose[i].setText(vocabularies[page * 4 + j].getChinese());
                    j++;
                }
            }
            choose[select[0]].setSelected(true);
        }else{
            qus.setText(vocabularies[0].getEnglish());
            num.setText("(" + Integer.toString(page + 1) + ")");
            choose[randInt[0]].setText(vocabularies[0].getChinese());
            choose[randInt[0]].setTextFill(Color.RED);

            for (int i = 0, j = 1; i < 4; i++) {
                choose[i].setSelected(false);
                if (i != randInt[page]) {
                    choose[i].setText(vocabularies[page * 4 + j].getChinese());
                    j++;
                }
            }
            choose[select[0]].setSelected(true);
        }
    }

    public void nextPage(ActionEvent ae){
        if (((Button)ae.getSource()).getText().equals("下一題")){
            choose[randInt[page]].setTextFill(Color.BLACK);
            page++;
            if (page == select.length - 1)
                next.setText("結束");

            num.setText("(" + Integer.toString(page + 1) + ")");
            qus.setText(vocabularies[page * 4].getEnglish());

            choose[randInt[page]].setText(vocabularies[page * 4].getChinese());
            choose[randInt[page]].setTextFill(Color.RED);

            for (int i = 0, j = 1; i < 4; i++) {
                choose[i].setSelected(false);
                if (i != randInt[page]) {
                    choose[i].setText(vocabularies[page * 4 + j].getChinese());
                    j++;
                }
            }
            choose[select[page]].setSelected(true);
        }else {
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
    }

    public void moveOver(MouseEvent me){
        showTest.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        showTest.getScene().setCursor(Cursor.DEFAULT);
    }

}
