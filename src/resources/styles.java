package resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class styles {
    public static final int BIG_FONT_SIZE = 50;
    public static final int TITLE_FONT_SIZE = 35;
    public static final int MEDIUM_FONT_SIZE = 25;
    public static final int LIGHT_FONT_SIZE = 20;

    public static Font SansSerifFont(int fontSize) {
        return new Font(Font.SANS_SERIF, Font.BOLD, fontSize);
    }

    public static void BigFontStyle(JComponent component) {
        component.setFont(SansSerifFont(BIG_FONT_SIZE));
    }

    public static void PlayerNameFontStyle(JComponent component) {
        component.setFont(SansSerifFont(TITLE_FONT_SIZE));
    }

    public static void MediumFontStyle(JComponent component) {
        component.setFont(SansSerifFont(MEDIUM_FONT_SIZE));
    }

    public static void LightFontStyle(JComponent component) {
        component.setFont(new Font(Font.SERIF, Font.BOLD, LIGHT_FONT_SIZE));
    }

    public static void MenuButtonStyle(JLabel button) {
        TransparentComponentStyle(button);
        BigFontStyle(button);

        button.setHorizontalAlignment(SwingConstants.CENTER);
        addForegroundClickListener(button, colors.DARK_GRAY, colors.GREYISH_BROWN);
    }

    public static void addForegroundClickListener(JComponent component, Color pressedColor, Color releasedColor) {
        component.setForeground(releasedColor);
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                component.setForeground(pressedColor);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                component.setForeground(releasedColor);
            }
        });
    }

    public static void OrangeButtonStyle(JLabel button) {
        BigFontStyle(button);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setForeground(colors.WHITE);
        button.setBackground(colors.ORANGE);
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                button.setBackground(colors.DARK_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                button.setBackground(colors.ORANGE);
            }
        });
    }

    public static void LightTextStyle(JLabel label) {
        LightFontStyle(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    public static void TitleTextStyle(JLabel title) {
        MediumFontStyle(title);
        TransparentComponentStyle(title);

        title.setForeground(colors.GREYISH_BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        setComponentMargins(title, constants.TITLE_MARGINS);
    }

    public static void TransparentComponentStyle(JComponent button) {
        button.setOpaque(false);
        button.setFocusable(false);
        button.setBackground(colors.TRANSPARENT);
    }

    public static void setComponentMargins(JComponent component, int margins) {
        setComponentMargins(component, margins, margins, margins, margins);
    }

    public static void setComponentMargins(JComponent component, int top, int left, int bottom, int right) {
        Border current = component.getBorder();
        Border empty = new EmptyBorder(top, left, bottom, right);

        if (current == null){
            component.setBorder(empty);
        } else {
            component.setBorder(new CompoundBorder(empty, current));
        }
    }
}
