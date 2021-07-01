package scene.function;

import appObject.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import scene.menu.MenuController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class InfController {
    @FXML
    private Label name, id, error, per1, per2, per3, per4, per5, per6;
    @FXML
    private PasswordField passwd, newPasswd;
    @FXML
    private TabPane tp;
    @FXML
    private Tab all;
    @FXML
    private LineChart<String, Long> lineChart;
    @FXML
    private PieChart p1, p2, p3, p4, p5, p6;
    @FXML
    private ImageView paint;
    @FXML
    private Button menu;
    @FXML
    private AnchorPane info;


    private Person person;

    public void setPerson(Person person) {
        this.person = person;
        name.setText(person.getUser().getName());
        id.setText(person.getUser().getId());
        drawLine();
        drawPie();
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

    public void setTab(){
        tp.getTabs().remove(0);
        tp.getSelectionModel().select(all);
    }

    public void changePasswd(ActionEvent ae){
        if (passwd.getText().length() != 0 && newPasswd.getText().length() != 0){
            if (passwd.getText().equals(person.getUser().getPasswd())){
                error.setText("");
                person.getUser().setPasswd(newPasswd.getText());
                person.getUser().dump();
                person.getUser().load();
                passwd.setText("");
                newPasswd.setText("");
            }else{
                error.setText("錯誤密碼");
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

    private void drawLine(){
        XYChart.Series<String, Long> series = new XYChart.Series<>();
        for (int i = 0; i < person.getScore().getTotal().size(); i++){
            double value =  ((double)(person.getScore().getTotal().get(i))) / 20 / (i + 1);
            series.getData().add(new XYChart.Data<>(Integer.toString(i + 1), (long)(value * 100)));
        }
        lineChart.getData().add(series);
    }

    private void drawPie(){
        ObservableList<PieChart.Data> pieChartData;
        PieChart[] pies = new PieChart[6];
        Label[] pers = new Label[6];
        pies[0] = p1; pies[1] = p2; pies[2] = p3; pies[3] = p4; pies[4] = p5; pies[5] = p6;
        pers[0] = per1; pers[1] = per2; pers[2] = per3; pers[3] = per4; pers[4] = per5; pers[5] = per6;

        for (int i = 0; i < 6; i++){
            long correct = person.getScore().getScore()[i][0];
            long total = person.getScore().getScore()[i][1];
            pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("答對", correct),
                new PieChart.Data("答錯", total - correct));
            pies[i].setData(pieChartData);
            pies[i].setLabelsVisible(false);
            pers[i].setText(Integer.toString((int)((((double)correct) / total * 100))) + "%");
        }

    }

    public void moveOver(MouseEvent me){
        info.getScene().setCursor(Cursor.HAND);
    }
    public void moveDefault(MouseEvent me){
        info.getScene().setCursor(Cursor.DEFAULT);
    }

}
