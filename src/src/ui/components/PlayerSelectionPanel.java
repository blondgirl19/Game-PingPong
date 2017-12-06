package src.ui.components;

import src.data.pojo.game.Player;
import resources.colors;
import resources.constants;
import resources.strings;
import resources.styles;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayerSelectionPanel extends JPanel {
    private JPanel dataInputPanel;

    private JTextField nameInputField;
    private JComboBox playersCompoBox, difficultComboBox;
    private JLabel difficultLabel;
    private int playerSide;

    public PlayerSelectionPanel(String panelTitle, Player previousPlayer) {
        playerSide = previousPlayer.getSide();

        setLayout(new BorderLayout());
        setOpaque(false);
        LineBorder lineBorder = new LineBorder(colors.ORANGE, constants.BORDER_THICKNESS, true);
        setBorder(lineBorder);
        styles.setComponentMargins(this, constants.TITLE_MARGINS);

        initPlayerLabel(panelTitle);
        initDataInputPanel();
        initNameInput(previousPlayer.getName());
        initPlayerTypeSelection(previousPlayer.isHuman());
        initComputerDifficultSelection();

        onPlayerSelected(!previousPlayer.isHuman());
    }

    private void initPlayerLabel(String title) {
        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        styles.LightTextStyle(titleLabel);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(colors.WHITE);
        styles.setComponentMargins(titleLabel, constants.TITLE_MARGINS);


        JLabel divider = new JLabel();
        divider.setBackground(colors.ORANGE);
        divider.setOpaque(true);
        styles.setComponentMargins(divider, constants.SHADOW_MARGINS);

        titlePanel.add(titleLabel, BorderLayout.CENTER);
        titlePanel.add(divider, BorderLayout.SOUTH);
        add(titlePanel, BorderLayout.NORTH);
    }

    private void initDataInputPanel() {
        dataInputPanel = new JPanel(new GridLayout(0, 2, 0, constants.LIST_ITEMS_SPACING));
        dataInputPanel.setBackground(colors.WHITE);
        styles.setComponentMargins(dataInputPanel, constants.TITLE_MARGINS);
        add(dataInputPanel, BorderLayout.CENTER);
    }

    private void initNameInput(String previousName) {
        addDataInputLabel(strings.NAME);

        nameInputField = new JTextField(previousName);
        nameInputField.setHorizontalAlignment(SwingConstants.CENTER);
        styles.MediumFontStyle(nameInputField);

        dataInputPanel.add(nameInputField);
    }

    private void initPlayerTypeSelection(boolean isHumanType) {
        addDataInputLabel(strings.PLAYER_TYPE);

        String[] playerTypes = {strings.HUMAN, strings.COMPUTER};

        playersCompoBox = new JComboBox(playerTypes);
        styles.MediumFontStyle(playersCompoBox);

        int selectedIndex = 1;
        if (isHumanType) {
            selectedIndex = 0;
        }

        playersCompoBox.setSelectedIndex(selectedIndex);

        dataInputPanel.add(playersCompoBox);

        playersCompoBox.addActionListener(e -> {
            JComboBox jComboBox = (JComboBox) e.getSource();
            String playerType = (String) jComboBox.getSelectedItem();
            onPlayerSelected(!playerType.equals(strings.HUMAN));
        });
    }

    private void onPlayerSelected(boolean isHuman) {
        setComputerDifficultVisible(isHuman);
    }

    private void setComputerDifficultVisible(boolean isVisible) {
        difficultComboBox.setVisible(isVisible);
        difficultLabel.setVisible(isVisible);
    }

    private void initComputerDifficultSelection() {
        difficultLabel = addDataInputLabel(strings.DIFFICULT);

        String[] difficultTypes = {strings.EASY, strings.MEDIUM, strings.HARD};

        difficultComboBox = new JComboBox(difficultTypes);
        styles.MediumFontStyle(difficultComboBox);
        difficultComboBox.setSelectedIndex(1);

        dataInputPanel.add(difficultComboBox);
    }

    private JLabel addDataInputLabel(String text) {
        JLabel label = new JLabel(text);
        styles.LightTextStyle(label);
        dataInputPanel.add(label);

        return label;
    }

    public boolean checkIsDataValid() {
        String name = getPlayerName();
        return !name.isEmpty();
    }

    public Player getPlayer() {
        String playerName = getPlayerName();
        String type = (String) playersCompoBox.getSelectedItem();

        if (type != null) {
            switch (type) {
                case strings.COMPUTER:
                    return new Player(playerName, getComputerDifficult(), playerSide);
                case strings.HUMAN:
                    return new Player(playerName, constants.HUMAN, playerSide);
            }
        }

        return null;
    }

    private int getComputerDifficult() {
        String difficultString = (String) difficultComboBox.getSelectedItem();
        if (difficultString != null) {
            switch (difficultString) {
                case strings.EASY:
                    return constants.COMPUTER_EASY;

                case strings.HARD:
                    return constants.COMPUTER_HARD;

                case strings.MEDIUM:
                    return constants.COMPUTER_MEDIUM;
            }
        }

        return constants.COMPUTER_MEDIUM;
    }

    private String getPlayerName() {
        return nameInputField.getText().trim();
    }
}