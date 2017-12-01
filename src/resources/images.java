package resources;

import javax.swing.*;
import java.net.URL;

public class images {
    private static String IMAGES_PATH = "resources/img_files/";

    public static String BACK_BUTTON = "back.png";

    public static ImageIcon loadImg(Class cls, String imgName) {
        URL imgUrl = cls.getClassLoader().getResource(IMAGES_PATH + imgName);

        if (imgUrl != null) {
            return new ImageIcon(imgUrl);
        } else {
            return new ImageIcon();
        }
    }
}
