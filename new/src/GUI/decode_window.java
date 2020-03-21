package GUI;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Code.decoding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class decode_window {

    private static String carrierFile = null;
    private static TextArea dataTextArea;
    public static String password;
    private static Label errorLabel = new Label();

    public static void display(Stage stage) {


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);

        errorLabel.setTextFill(Color.RED);

        Label carrierLabel = new Label("Carrier:");
        TextField carrierText = new TextField();
        carrierText.setPrefWidth(600);
        carrierText.setDisable(true);
        Button carrierChoose = new Button("...");
        carrierChoose.setOnAction(e -> {
            if((carrierFile = chooseCarrierFile(stage)) != null) {
                carrierText.setText(carrierFile);
            }
            else if(!carrierText.getText().equals("")) carrierFile = carrierText.getText();
            errorLabel.setText(null);
        });
        Button viewCarrierImage = new Button("View");
        viewCarrierImage.setOnAction(e -> {
            String url = carrierText.getText();
            if(!url.equals(""))
                ImageDisplay.display(url);
        });

        Label passlabel = new Label("Password: ");
        TextField passtext = new TextField();
        passtext.setPrefWidth(300);

        // TextArea:
        Label dataLabel = new Label("Text: ");
        dataTextArea = new TextArea();
        dataTextArea.setPrefRowCount(15);
        dataTextArea.setPrefColumnCount(100);
        dataTextArea.setWrapText(true);
        dataTextArea.setPrefWidth(600);
        dataTextArea.setDisable(true);

        //Constraints
        GridPane.setConstraints(carrierLabel, 0, 0);
        GridPane.setConstraints(carrierText, 1, 0);
        GridPane.setConstraints(carrierChoose, 2, 0);
        GridPane.setConstraints(viewCarrierImage, 3, 0);
        GridPane.setConstraints(dataLabel, 0, 4);
        GridPane.setConstraints(dataTextArea, 1, 4);
        GridPane.setConstraints(errorLabel, 1, 5);
        GridPane.setConstraints(passlabel, 0, 2);
        GridPane.setConstraints(passtext, 1, 2);

        // Add all controls to grid
        grid.getChildren().addAll(carrierText, carrierLabel, carrierChoose, viewCarrierImage,
                dataLabel, dataTextArea, errorLabel,passlabel,passtext);

        Button backButton = new Button("<< Back");
        backButton.setOnAction(e -> {
            steg.display(stage);
        });
        Button decodeButton = new Button("Decode");
        decodeButton.setOnAction(e -> {
            if(!carrierText.getText().equals("") && passtext.getText()!=null) {
                password = (passtext.getText().trim().length()>0)? passtext.getText() : null;
                decodeButton.setDisable(true); // ERROR: not disabling
                decoding temp = new decoding();
                try {
                    String str = temp.decode(carrierFile, password);
                    dataTextArea.setText(str);
                    decodeButton.setDisable(false);
                }
                catch (Exception ex){  errorLabel.setText("Wrong password");  }
            }
            else { errorLabel.setText("Carrier File Required! and password too!"); }
        });

        HBox base = new HBox(15);
        base.setPadding(new Insets(10,20,20,10)); // top, right, bottom, left
        base.setAlignment(Pos.BASELINE_RIGHT);
        base.getChildren().addAll(backButton, decodeButton);

        BorderPane bPane = new BorderPane();
        bPane.setBottom(base);
        bPane.setCenter(grid);

        Scene scene = new Scene(bPane, 800, 400);

        stage.setScene(scene);
        stage.show();


    }

    private static String chooseCarrierFile(Stage window) {
        File file;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File(*.png,*.bmp)", "*.png", "*.bmp");
        fileChooser.getExtensionFilters().add(extFilter);
        fileChooser.setTitle("Select Carrier Image");
        if((file = fileChooser.showOpenDialog(window)) != null) {
            return file.getPath();
        }
        else { return null; }
    }



}
