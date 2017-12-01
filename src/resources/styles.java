package resources;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class styles {
    private static final int BIG_FONT_SIZE = 50;
    private static final int MEDIUM_FONT_SIZE = 25;

    private static void BoldFontStyle(JComponent component) {
        component.setFont(new Font(Font.SANS_SERIF, Font.BOLD, BIG_FONT_SIZE));
    }

    private static void MediumFontStyle(JComponent component) {
        component.setFont(new Font(Font.SANS_SERIF, Font.BOLD, MEDIUM_FONT_SIZE));
    }

    public static void MenuButtonStyle(JLabel button) {
        TransparentComponentStyle(button);
        BoldFontStyle(button);

        button.setHorizontalAlignment(SwingConstants.CENTER);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                button.setForeground(colors.DARK_GRAY);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                button.setForeground(colors.GREYISH_BROWN);
            }
        });
    }

    public static void TitleTextStyle(JLabel title) {
        MediumFontStyle(title);
        TransparentComponentStyle(title);

        title.setForeground(colors.GREYISH_BROWN);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        setComponentMargins(title, dimen.TITLE_MARGINS);
    }

    public static void TransparentComponentStyle(JComponent button) {
        button.setOpaque(false);
        button.setFocusable(false);
        button.setBackground(colors.TRANSPARENT);
    }

    public static void setComponentMargins(JComponent component, int margins) {
        Border current = component.getBorder();
        Border empty = new EmptyBorder(margins, margins, margins, margins);

        if (current == null){
            component.setBorder(empty);
        } else {
            component.setBorder(new CompoundBorder(empty, current));
        }
    }
}
