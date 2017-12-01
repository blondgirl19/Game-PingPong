package code.ui.components;

import code.data.pojo.players.BasePlayer;
import code.data.pojo.players.ComputerPlayer;
import code.data.pojo.players.HumanPlayer;
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

    public PlayerSelectionPanel(String playerNumber, BasePlayer previousPlayer) {
        setLayout(new BorderLayout());
        setOpaque(false);
        LineBorder lineBorder = new LineBorder(colors.ORANGE, constants.BORDER_THICKNESS, true);
        setBorder(lineBorder);
        styles.setComponentMargins(this, constants.TITLE_MARGINS);

        initPlayerLabel(playerNumber);
        initDataInputPanel();
        initNameInput(previousPlayer.getName());
        initPlayerTypeSelection(previousPlayer.getType());
        initComputerDifficultSelection();

        onPlayerSelected(previousPlayer.getType());
    }

    private void initDataInputPanel() {
        dataInputPanel = new JPanel(new GridLayout(0, 2, 0, constants.LIST_ITEMS_SPACING));
        dataInputPanel.setBackground(colors.WHITE);
        styles.setComponentMargins(dataInputPanel, constants.TITLE_MARGINS);
        add(dataInputPanel, BorderLayout.CENTER);
    }

    private void initPlayerLabel(String playerNumber) {
        JPanel titlePanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel(playerNumber);
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

    private void initNameInput(String previousName) {
        addDataInputLabel(strings.NAME);

        nameInputField = new JTextField(previousName);
        nameInputField.setHorizontalAlignment(SwingConstants.CENTER);
        styles.MediumFontStyle(nameInputField);

        dataInputPanel.add(nameInputField);
    }

    private void initPlayerTypeSelection(String previousType) {
        addDataInputLabel(strings.PLAYER_TYPE);

        String[] playerTypes = {strings.HUMAN, strings.COMPUTER};

        playersCompoBox = new JComboBox(playerTypes);
        styles.MediumFontStyle(playersCompoBox);

        int selectedIndex = 0;
        if (previousType.equals(strings.COMPUTER)) {
            selectedIndex = 1;
        }

        playersCompoBox.setSelectedIndex(selectedIndex);

        dataInputPanel.add(playersCompoBox);

        playersCompoBox.addActionListener(e -> {
            JComboBox jComboBox = (JComboBox) e.getSource();
            String playerType = (String) jComboBox.getSelectedItem();
            onPlayerSelected(playerType);
        });
    }

    private void onPlayerSelected(String playerType) {
        setComputerDifficultVisible(playerType.equals(strings.COMPUTER));

        /*switch (playerType) {
            case strings.HUMAN:
                setComputerDifficultVisible(false);
                break;

            case strings.COMPUTER:
                setComputerDifficultVisible(true);
                break;
        }*/
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

    public BasePlayer getPlayer() {
        String playerName = getPlayerName();
        String type = (String) playersCompoBox.getSelectedItem();

        if (type != null) {
            switch (type) {
                case strings.COMPUTER:
                    String difficult = (String) difficultComboBox.getSelectedItem();
                    return new ComputerPlayer(playerName, difficult);
                case strings.HUMAN:
                    return new HumanPlayer(playerName);
            }
        }

        return null;
    }

    private String getPlayerName() {
        return nameInputField.getText().trim();
    }
}