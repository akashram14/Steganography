package Code;


import GUI.AlertBox;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;
import java.lang.Math;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.stream.IntStream;

public class encoding {
        private static BufferedImage image;
        public static void encode(String text,String password,String carrier,String output){

            try {
                image = ImageIO.read(new File(carrier));
            }
            catch(IOException e) { AlertBox.error("Error in reading Carrier image file.", null); }

            int bitMask = 0x00000001;	// define the mask bit used to get the digit
            int bit;				// define a integer number to represent the ASCII number of a character
            int x = 0;				// define the starting pixel x
            int y = 0;				// define the starting pixel y
            int len=text.length();
            String binary=Integer.toBinaryString(len);
            while(binary.length()<32){
                binary="0" +binary;
           }
            System.out.println(binary);
            for(int i=0;i<32;i++){
                int flag= binary.charAt(i) -'0';
                if(flag == 1) {
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) | 0x00000001);
                        x++;// store the bit which is 1 into a pixel's last digit
                    }
                }
                else {
                    if(x < image.getWidth()) {
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                        x++;
                    }
                    else {
                        x = 0;
                        y++;
                        image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);
                        x++;// store the bit which is 0 into a pixel's last digit
                    }
                }
            }
            for(int i = 0; i < text.length(); i++) {
                bit = (int) text.charAt(i);		// get the ASCII number of a character
                for(int j = 0; j < 8; j++) {
                    int flag = bit & bitMask;	// get 1 digit from the character
                    if(flag == 1) {
                        if(x < image.getWidth()) {
                            image.setRGB(x, y, image.getRGB(x, y) | 0x00000001); 	// store the bit which is 1 into a pixel's last digit
                            x++;
                        }
                        else {
                            x = 0;
                            y++;
                            image.setRGB(x, y, image.getRGB(x, y) | 0x00000001);
                            x++;// store the bit which is 1 into a pixel's last digit
                        }
                    }
                    else {
                        if(x < image.getWidth()) {
                            image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);	// store the bit which is 0 into a pixel's last digit
                            x++;
                        }
                        else {
                            x = 0;
                            y++;
                            image.setRGB(x, y, image.getRGB(x, y) & 0xFFFFFFFE);
                            x++;// store the bit which is 0 into a pixel's last digit
                        }
                    }
                    bit = bit >> 1;				// get the next digit from the character
                }
            }

            try {
                File outputfile = new File(output);
                ImageIO.write(image, "png", outputfile);
            } catch (IOException e) { AlertBox.error("Error in reading Output image file.", null);}


        }

}
