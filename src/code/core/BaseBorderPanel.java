package code.core;

import resources.colors;
import resources.constants;
import resources.styles;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public abstract class BaseBorderPanel extends JPanel {
    protected Color borderColor;
    protected JPanel screenContentPanel;

    public BaseBorderPanel(Color borderColor, String title) {
        this.borderColor = borderColor;

        initPanel();
        initTitle(title);

        screenContentPanel = new JPanel();
        styles.setComponentMargins(screenContentPanel, constants.TITLE_MARGINS);
        screenContentPanel.setBackground(colors.WHITE);
        add(screenContentPanel, BorderLayout.CENTER);
    }

    private void initPanel() {
        setLayout(new BorderLayout());
        setOpaque(false);
        LineBorder lineBorder = new LineBorder(borderColor, constants.BORDER_THICKNESS, true);
        setBorder(lineBorder);
        styles.setComponentMargins(this, constants.TITLE_MARGINS);
    }

    private void initTitle(String title) {
        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(title.toUpperCase());
        styles.LightTextStyle(titleLabel);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(colors.WHITE);
        styles.setComponentMargins(titleLabel, constants.TITLE_MARGINS);


        JLabel divider = new JLabel();
        divider.setBackground(borderColor);
        divider.setOpaque(true);
        styles.setComponentMargins(divider, constants.SHADOW_MARGINS);

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(divider, BorderLayout.SOUTH);
        add(titlePanel, BorderLayout.NORTH);
    }

    protected abstract void initScreenContent();
}
