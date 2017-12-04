package code.ui.components;

import code.data.pojo.players.BasePlayer;
import resources.colors;
import resources.constants;
import resources.strings;
import resources.styles;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ScoresPanel extends JPanel {
    private BasePlayer leftPlayer, rightPlayer;
    private int leftPlayerScores, rightPlayerScores;
    private JLabel scoresLabel;

    public ScoresPanel(BasePlayer leftPlayer, BasePlayer rightPlayer) {
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;
        setOpaque(true);
        refreshScores();

        setLayout(new BorderLayout());
        setBackground(colors.WHITE);
        initLabels();
        repaintScores();
        setupDivider();
    }

    public void refreshScores() {
        leftPlayerScores = 0;
        rightPlayerScores = 0;
    }

    public void incLeftPlayerScores() {
        leftPlayerScores++;
        repaintScores();
    }

    public void incRightPlayerScore() {
        rightPlayerScores++;
        repaintScores();
    }

    private void repaintScores() {
        String scores = String.format(strings.SCORES_FORMAT, leftPlayerScores, rightPlayerScores);
        scoresLabel.setText(scores);
    }

    private void initLabels() {
        JPanel labelsPanel = new JPanel(new GridLayout(1, 3));
        labelsPanel.setOpaque(false);

        JLabel leftPlayerLabel = new JLabel(leftPlayer.getName());
        setupLabel(leftPlayerLabel);
        labelsPanel.add(leftPlayerLabel);

        scoresLabel = new JLabel();
        setupLabel(scoresLabel);
        styles.BigFontStyle(scoresLabel);
        labelsPanel.add(scoresLabel);

        JLabel rightPlayerLabel = new JLabel(rightPlayer.getName());
        setupLabel(rightPlayerLabel);
        labelsPanel.add(rightPlayerLabel);

        add(labelsPanel, BorderLayout.CENTER);
    }

    private void setupLabel(JLabel label) {
        styles.PlayerNameFontStyle(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);
    }

    private void setupDivider() {
        JLabel shadowLabel = new JLabel();
        styles.setComponentMargins(shadowLabel, constants.SHADOW_MARGINS);
        shadowLabel.setBackground(colors.DARK_GRAY);
        shadowLabel.setOpaque(true);
        add(shadowLabel, BorderLayout.NORTH);
    }
}
