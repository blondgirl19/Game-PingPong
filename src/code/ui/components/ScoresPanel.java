package code.ui.components;

import code.data.pojo.game.Player;
import resources.colors;
import resources.constants;
import resources.strings;
import resources.styles;

import javax.swing.*;
import java.awt.*;

import static resources.strings.*;

public class ScoresPanel extends JPanel {
    private Player leftPlayer, rightPlayer;
    private int leftPlayerScores, rightPlayerScores;
    private JLabel scoresLabel;
    private int scoresToWin;
    private ScoresCallback callback;

    public ScoresPanel(Player leftPlayer, Player rightPlayer, int scoresToWin) {
        this.scoresToWin = scoresToWin;
        this.leftPlayer = leftPlayer;
        this.rightPlayer = rightPlayer;

        initPanel();
        refreshScores();
        setupDivider();
    }

    private void initPanel() {
        setOpaque(true);

        setLayout(new BorderLayout());
        setBackground(colors.WHITE);
        initLabels();
    }

    public void setCallback(ScoresCallback callback) {
        this.callback = callback;
    }

    public void refreshScores() {
        leftPlayerScores = 0;
        rightPlayerScores = 0;
        repaintScores();
    }

    public void incLeftPlayerScores() {
        leftPlayerScores++;
        repaintScores();
        checkGameEnd();
    }

    public void incRightPlayerScore() {
        rightPlayerScores++;
        repaintScores();
        checkGameEnd();
    }

    private void checkGameEnd() {
        if (callback != null) {
            if (leftPlayerScores >= scoresToWin) {
                callback.onPlayerWin(leftPlayer);
            } else if (rightPlayerScores >= scoresToWin) {
                callback.onPlayerWin(rightPlayer);
            }
        }
    }

    private void repaintScores() {
        String scores = String.format(strings.SCORES_FORMAT, leftPlayerScores, rightPlayerScores);
        scoresLabel.setText(scores);
    }

    private void initLabels() {
        JPanel labelsPanel = new JPanel(new GridLayout(1, 3));
        labelsPanel.setOpaque(false);


        JLabel leftPlayerLabel = createBigLabel(leftPlayer.getName());
        JPanel leftPlayerPanel = createPlayerPanel(leftPlayerLabel, leftPlayer.getType());
        labelsPanel.add(leftPlayerPanel);

        scoresLabel = createBigLabel("");
        labelsPanel.add(scoresLabel);

        JLabel rightPlayerLabel = createBigLabel(rightPlayer.getName());
        JPanel rightPlayerPanel = createPlayerPanel(rightPlayerLabel, rightPlayer.getType());
        labelsPanel.add(rightPlayerPanel);

        add(labelsPanel, BorderLayout.CENTER);
    }

    private JPanel createPlayerPanel(JLabel playerLabel, int playerType) {
        JPanel leftPlayerPanel = new JPanel(new BorderLayout());
        leftPlayerPanel.setOpaque(false);

        String typeString;
        switch (playerType) {
            case constants.COMPUTER_HARD:
                typeString = HARD + " " + COMPUTER.toLowerCase();
                break;
            case constants.COMPUTER_MEDIUM:
                typeString = MEDIUM + " " + COMPUTER.toLowerCase();
                break;
            case constants.COMPUTER_EASY:
                typeString = EASY + " " + COMPUTER.toLowerCase();
                break;
            default:
                typeString = HUMAN;
                break;
        }
        JLabel playerTypeLabel = new JLabel(typeString);
        styles.LightTextStyle(playerTypeLabel);
        playerTypeLabel.setForeground(colors.SHADOW);
        leftPlayerPanel.add(playerTypeLabel, BorderLayout.SOUTH);

        leftPlayerPanel.add(playerLabel, BorderLayout.CENTER);

        return leftPlayerPanel;
    }

    private JLabel createBigLabel(String text) {
        JLabel label = new JLabel(text);
        styles.PlayerNameFontStyle(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    private void setupDivider() {
        JLabel shadowLabel = new JLabel();
        styles.setComponentMargins(shadowLabel, constants.SHADOW_MARGINS);
        shadowLabel.setBackground(colors.DARK_GRAY);
        shadowLabel.setOpaque(true);
        add(shadowLabel, BorderLayout.NORTH);
    }

    public interface ScoresCallback {
        void onPlayerWin(Player winner);
    }
}
