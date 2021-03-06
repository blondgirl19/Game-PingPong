package code.ui.components;

import resources.colors;
import resources.constants;
import resources.images;
import resources.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class TitlePanel extends JPanel {
    private JLabel titleLabel;
    private JLabel backLabel;
    private JLabel invisibleLabel;

    public TitlePanel(){
        setLayout(new BorderLayout());
        setBackground(colors.WHITE);

        initTitle();
        initBackButton();

        setupShadow();
    }

    private void initTitle() {
        titleLabel = new JLabel();
        styles.TitleTextStyle(titleLabel);
        add(titleLabel, BorderLayout.CENTER);
    }

    private void initBackButton() {
        backLabel = new JLabel();
        styles.TransparentComponentStyle(backLabel);

        backLabel.setIcon(images.loadImg(getClass(), images.BACK_BUTTON));
        add(backLabel, BorderLayout.WEST);

        backLabel.setVisible(false);

        invisibleLabel = new JLabel();
        styles.setComponentMargins(invisibleLabel, constants.TITLE_TRANSPARENT_MARGINS);
        add(invisibleLabel, BorderLayout.EAST);
    }

    public void setBackButtonClickListener(MouseAdapter mouseListener) {
        backLabel.addMouseListener(mouseListener);
    }

    public void setBackButtonVisible(boolean isVisible) {
        backLabel.setVisible(isVisible);
        if (isVisible) {
            add(invisibleLabel, BorderLayout.EAST);
        } else {
            remove(invisibleLabel);
        }
    }

    public void setTitle(String text) {
        titleLabel.setText(text.toUpperCase());
    }

    private void setupShadow() {
        JLabel shadowLabel = new JLabel();
        styles.setComponentMargins(shadowLabel, constants.SHADOW_MARGINS);
        shadowLabel.setBackground(colors.SHADOW);
        shadowLabel.setOpaque(true);
        add(shadowLabel, BorderLayout.SOUTH);
    }
}
