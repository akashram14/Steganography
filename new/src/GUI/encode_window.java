package GUI;
import Code.encoding;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class encode_window {

    private static String carrierFileExt = null;
    public static String carrierFile = null;
    public static String outputFile=null;
    public static String password;
    public static String message = null;


    public static void display(Stage stage) {
        //Input Image
        Label carrierLabel = new Label("Carrier: ");
        TextField carrierText = new TextField();
        carrierText.setPrefWidth(600);
        carrierText.setDisable(true);
        Button carrierChoose = new Button("...");
        Button viewCarrierImage = new Button("View");


        Label errorLabel = new Label();
        errorLabel.setTextFill(Color.RED);


        //Output Image
        Label outputLabel = new Label("Output: ");
        TextField outputText = new TextField();
        outputText.setPrefWidth(600);
        outputText.setDisable(true);
        Button outputChoose = new Button("...");

        Button backButton = new Button("<< Back");
        backButton.setOnAction(e -> {
            steg.display(stage);
        });

        Label passlabel = new Label("Password: ");
        TextField passtext = new TextField();
        passtext.setPrefWidth(300);

        //Message
        Label dataLabel = new Label("Text: ");
        TextArea dataTextArea = new TextArea();
        dataTextArea.setPrefRowCount(10);
        dataTextArea.setPrefColumnCount(100);
        dataTextArea.setWrapText(true);
        dataTextArea.setPrefWidth(600);


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 20)); //top, right, bottom, left
        grid.setVgap(8);
        grid.setHgap(10);

        grid.getChildren().addAll(carrierText, carrierLabel, carrierChoose,
                viewCarrierImage, outputLabel, outputText, outputChoose,
                 dataLabel, dataTextArea,passlabel,passtext,errorLabel);


        carrierChoose.setOnAction(e -> {
            if((carrierFile = chooseCarrierFile(stage)) != null) {
                carrierText.setText(carrierFile);
            }
            else if(carrierText.getText() != null) carrierFile = carrierText.getText();
        });

        viewCarrierImage.setOnAction(e -> ImageDisplay.display(carrierText.getText()));

        outputChoose.setOnAction(e -> {
            if ((outputFile = chooseOutputFile(stage)) != null) {
                outputText.setText(outputFile);
            }
            else if(carrierText.getText() != null) carrierFile = carrierText.getText();
        });


        Button nextButton = new Button("encode");
        nextButton.setOnAction(e -> {
            if(dataTextArea.getText() != null && passtext.getText()!=null)
                message = (dataTextArea.getText().trim().length()>0)? dataTextArea.getText() : null;
                password = (passtext.getText().trim().length()>0)? passtext.getText() : null;
            if(!isError()) {
                encoding temp=new encoding();
                temp.encode(message,password,carrierFile,outputFile);
            }
            else errorLabel.setText("All fields required!");
        });


        HBox hBox = new HBox(15);
        hBox.setPadding(new Insets(10,20,20,10)); // top, right, bottom, left
        hBox.setAlignment(Pos.BASELINE_RIGHT);
        hBox.getChildren().addAll(backButton,nextButton);

        BorderPane bPane = new BorderPane();
        bPane.setCenter(grid);
        bPane.setBottom(hBox);
        Scene scene = new Scene(bPane, 800, 400);

        GridPane.setConstraints(carrierLabel, 0, 0);
        GridPane.setConstraints(carrierText, 1, 0);
        GridPane.setConstraints(carrierChoose, 2, 0);
        GridPane.setConstraints(viewCarrierImage, 3, 0);
        GridPane.setConstraints(outputLabel, 0, 1);
        GridPane.setConstraints(outputText, 1, 1);
        GridPane.setConstraints(outputChoose, 2, 1);
        GridPane.setConstraints(dataLabel, 0, 4);
        GridPane.setConstraints(dataTextArea, 1, 4);
        GridPane.setConstraints(carrierLabel, 0, 0);
        GridPane.setConstraints(passlabel, 0, 2);
        GridPane.setConstraints(passtext, 1, 2);
        GridPane.setConstraints(errorLabel, 1, 5);


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
            String path = file.getPath();
            carrierFileExt = path.substring(path.lastIndexOf("."));
            return file.getPath();
        }
        else { return null; }
    }

    private static String chooseOutputFile(Stage window) {
        File file;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("File (*"+carrierFileExt+")", "*"+carrierFileExt);
        fileChooser.getExtensionFilters().addAll(extFilter);

        fileChooser.setTitle("Save As");
        if((file = fileChooser.showSaveDialog(window)) != null)  {
            String path = file.getPath();
            String outputFileExt = path.substring(path.lastIndexOf("."));
            path = path.replace(outputFileExt, carrierFileExt);
            return path;
        }
        else { return null; }
    }

    private static boolean isError() {
        boolean isError = false;
        if(carrierFile == null || outputFile == null || message == null || password==null)
            isError = true;
        return isError;
    }

}
