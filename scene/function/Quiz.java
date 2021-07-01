package scene.function;

import appObject.Dictionary;
import appObject.Person;
import appObject.Vocabulary;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class Quiz implements Initializable {

    @FXML
    private Button send, next;
    @FXML
    private RadioButton ans1, ans2, ans3, ans4;
    @FXML
    private Label qus, num, warning;
    @FXML
    private ImageView paint;
    @FXML
    private Pane quiz;


    private final Vocabulary[] vocabularies = new Vocabulary[80];
    private final int[] randInt = new int[20];
    private final int[] select = new int[20];
    private int page = 0;
    private Person person;

    private final RadioButton[] choose = new RadioButton[4];


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // initial :
        int[] questions;
        questions = Dictionary.chooseQuestion(20);
        choose[0] = ans1; choose[1] = ans2; choose[2] = ans3; choose[3] = ans4;
        send.setVisible(false);
        warning.setVisible(false);

        try {
            paint.setImage(new Image(new FileInputStream("src/outImage/paint.png")));
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        for (int i = 0; i < 20; i++)
            select[i] = -1;

        for (int i = 0; i < 80; i++){
            vocabularies[i] = Dictionary.dictList.get(questions[i]);
        }
        qus.setText(vocabularies[0].getEnglish());
        num.setText("(" + Integer.toString(page + 1) + ")");

        Random random = new Random();
        randInt[0] = random.nextInt(4);
        choose[randInt[0]].setText(vocabularies[0].getChinese());

        for (int i = 0, j = 1; i < 4; i++){
            if (i != randInt[page]) {
                choose[i].setText(vocabularies[page * 4 + j].getChinese());
                j++;
            }
        }

    }

    public void setPerson(Person person){
        this.person = person;
    }

    public void nextPage(ActionEvent ae){
        if (select[page] != -1) {
            warning.setVisible(false);
            page++;
            num.setText("(" + Integer.toString(page + 1) + ")");
            qus.setText(vocabularies[page * 4].getEnglish());

            Random random = new Random();
            randInt[page] = random.nextInt(4);
            choose[randInt[page]].setText(vocabularies[page * 4].getChinese());

            for (int i = 0, j = 1; i < 4; i++) {
                choose[i].setSelected(false);
                if (i != randInt[page]) {
                    choose[i].setText(vocabularies[page * 4 + j].getChinese());
                    j++;
                }
            }

            if (page == 19) {
                next.setVisible(false);
                send.setVisible(true);
            }
        }else{
            warning.setVisible(true);
        }
    }


    public void setAnswer(ActionEvent ae){
        switch (((RadioButton)ae.getSource()).getId()) {
            case "ans1" -> select[page] = 0;
            case "ans2" -> select[page] = 1;
            case "ans3" -> select[page] = 2;
            case "ans4" -> select[page] = 3;
        }
    }

    public void submit(ActionEvent ae){
        ArrayList<Integer> testNum = new ArrayList<>();
        long[][] level = new long[6][2];

        int correctTimes = 0;
        if (select[page] != -1){
            for (int i = 0; i < 20; i++){
                level[vocabularies[i].getLevel() - 1][1] += 1L;
                if (select[i] == randInt[i]) {
                    correctTimes++;
                    level[vocabularies[i].getLevel() - 1][0] += 1L;
                }else{
                    testNum.add(i);
                }
            }

            int[] s = new int[testNum.size()];
            int[] r = new int[testNum.size()];
            Vocabulary[] v = new Vocabulary[4 * testNum.size()];

            for (int i = 0; i < testNum.size(); i++){
                s[i] = select[testNum.get(i)];
                r[i] = randInt[testNum.get(i)];
                for (int j = 0; j < 4; j++){
                    v[i * 4 + j] = vocabularies[testNum.get(i) * 4 + j];
                }
            }

            person.getScore().addTotal(correctTimes);
            person.getScore().setScore(level);
            person.getScore().dump();

            try {
                Parent root;
                Stage stage;
                Scene scene;
                FXMLLoader fl = new FXMLLoader();

                fl.setLocation(InfController.class.getResource("../function/showTestScore.fxml"));
                root = fl.load(InfController.class.getResource("../function/showTestScore.fxml").openStream());
                stage = (Stage) ((Node)ae.getSource()).getScene().getWindow();
                ((ShowTestScore)fl.getController()).setData(person, s, r, v, correctTimes);

                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }catch (IOException e){
                e.printStackTrace();
            }
        }else{
            warning.setVisible(true);
        }


    }

    public void moveOver(MouseEvent me){
        quiz.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        quiz.getScene().setCursor(Cursor.DEFAULT);
    }


}
