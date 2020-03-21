package Code;


import GUI.AlertBox;

import javax.imageio.ImageIO;
import javax.print.DocFlavor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class decoding {
    private static BufferedImage image;
    static int x = 0;
    static int y = 0;
    static int bitMask = 0x00000001;

    public static String decode(String carrier,String password) throws Exception {
        try {
            image = ImageIO.read(new File(carrier));
        } catch (IOException e) {
            AlertBox.error("Error in reading Carrier image file.", null);
        }
        String hashtext1;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(password.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            hashtext1 = no.toString(16);
            while (hashtext1.length() < 32) {
                hashtext1 = "0" + hashtext1;
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        String actual_password = actual_decode();
        if(actual_password.equals(hashtext1)) {
            String encoded_string = actual_decode();
            return encrypt.decrypt(encoded_string,hashtext1);
        }
        else throw new Exception();
    }
    public static String actual_decode(){
        int len =0;
        int flag;
        for (int i = 0; i < 32; i++) {
            if (x < image.getWidth()) {
                len |= image.getRGB(x, y) & bitMask;
                len = len << 1;// get the last digit of the pixel
                x++;
            } else {
                x = 0;
                y++;
                len |= image.getRGB(x, y) & bitMask;
                len = len << 1;
                x++;// get the last digit of the pixel
            }
        }
        len=len>>1;
        char[] c = new char[len];
        String s = "";
        for (int i = 0; i < len; i++) {
            int bit = 0;
            // 8 digits form a character
            for (int j = 0; j < 8; j++) {
                if (x < image.getWidth()) {
                    flag = image.getRGB(x, y) & bitMask;    // get the last digit of the pixel
                    x++;
                } else {
                    x = 0;
                    y++;
                    flag = image.getRGB(x, y) & bitMask;
                    x++;// get the last digit of the pixel
                }

                // store the extracted digits into an integer as a ASCII number
                if (flag == 1) {
                    bit = bit >> 1;
                    bit = bit | 0x80;
                } else {
                    bit = bit >> 1;
                }
            }
            c[i] = (char) bit;
            s += c[i];
        }
        return s;
    }

}
