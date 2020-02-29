package Code;


import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javafx.scene.control.Alert;


class AlertBox {

    public static void error(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        System.exit(0);
    }

    public static void information(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}

public class encoding {

    private static BufferedImage img;
    private static String outputFile;
    private static byte[] header;
    private static byte[] message;
    private static int x=0, y=0;

    public static void setImage(String carrier, String output) {
        outputFile = output;
        try {
            img = ImageIO.read(new File(carrier));
        }
        catch(IOException e) { AlertBox.error("Error in reading Carrier image file.", null); }
    }

    private static void saveImage() {
        File f = new File(outputFile);
        String outputFileExt = outputFile.substring(outputFile.lastIndexOf(".")+1);
        try {
            ImageIO.write(img, outputFileExt.toUpperCase(), f);
        }
        catch(IOException e) { AlertBox.error("Error in saving Output image file.", null); }
    }

    public static void setMessage(byte[] data) {
        message = data;
    }








}
