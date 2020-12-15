package adventure;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DisplayRoomImage {
    /**
     * Method that shows a JFrame of the image passed in
     * @param filePath: string of the image's path from repository root
     * @return true if the image was successfully displayed, false otherwise
     */
    public static boolean displayRoomImage(String filePath) {
        try {
            BufferedImage img = ImageIO.read(new File(filePath));
            ImageIcon icon = new ImageIcon(img);
            JFrame frame = new JFrame();
            frame.setLayout(new FlowLayout());
            frame.setSize(900, 700);
            JLabel lbl = new JLabel();
            lbl.setIcon(icon);
            frame.add(lbl);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            return true;
        } catch(Exception e) {
            System.out.println("Image cannot be generated.");
            return false;
        }
    }
}