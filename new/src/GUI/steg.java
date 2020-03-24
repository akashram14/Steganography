package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class steg extends Application implements EventHandler<ActionEvent>{

    static Scene main_scene;
    public  static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        display(stage);
    }
    public static void display(Stage stage){
        stage.setTitle("Title");
        Label label =new Label("Image Steganography");
        label.setFont(Font.font ("Verdana", FontWeight.BOLD,20));

        Button button_encode = new Button();
        Button button_decode = new Button();
        button_encode.setText("Encode");
        button_decode.setText("Decode");
        button_encode.setOnAction(e->encode_window.display(stage));
        button_decode.setOnAction(e->decode_window.display(stage));


        VBox layout1 = new VBox(20);
        layout1.setAlignment(Pos.CENTER);
        layout1.getChildren().addAll(label,button_decode,button_encode);
        main_scene=new Scene(layout1,300,250);


        stage.setScene(main_scene);
        stage.setTitle("Image Steganography");
        stage.show();

    }

    @Override
    public void handle(ActionEvent actionEvent) {

    }
}
