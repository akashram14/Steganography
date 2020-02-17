package GUI;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class encode_window {


    public static void display(Stage stage) {

        Button backButton = new Button("<< Back");
        backButton.setOnAction(e -> {
            steg.display(stage);
        });


        StackPane layout2=new StackPane();
        layout2.getChildren().add(backButton);
        Scene scene_encode=new Scene(layout2,300,250);

        stage.setScene(scene_encode);
        stage.show();

    }



}
