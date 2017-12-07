package code.ui.components;

import code.core.BaseBorderPanel;
import code.data.pojo.game.Player;
import resources.colors;
import resources.constants;
import resources.strings;
import resources.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PlayerSelectionPanel extends BaseBorderPanel {
    private Player previousPlayer;

    private JTextField nameInputField;
    private JComboBox playersCompoBox, difficultComboBox;
    private JLabel difficultLabel;
    private JButton colorPickerButton;

    public PlayerSelectionPanel(String panelTitle, Player previousPlayer) {
        super(colors.ORANGE, panelTitle);
        this.previousPlayer = previousPlayer;

        initScreenContent();
    }

    @Override
    protected void initScreenContent() {
        screenContentPanel.setLayout(new GridLayout(0, 2, 0, constants.LIST_ITEMS_SPACING));

        initNameInput(previousPlayer.getName());
        initColorPicker(previousPlayer.getRacketColor());
        initPlayerTypeSelection(previousPlayer.isHuman());
        initComputerDifficultSelection(previousPlayer.getType());

        onPlayerSelected(!previousPlayer.isHuman());
    }

    private void initNameInput(String previousName) {
        addDataInputLabel(strings.NAME);

        nameInputField = new JTextField(previousName);
        nameInputField.setHorizontalAlignment(SwingConstants.CENTER);
        styles.MediumFontStyle(nameInputField);

        screenContentPanel.add(nameInputField);
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

        screenContentPanel.add(playersCompoBox);

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

    private void initComputerDifficultSelection(int previousPlayerType) {
        difficultLabel = addDataInputLabel(strings.DIFFICULT);

        String[] difficultTypes = {strings.EASY, strings.MEDIUM, strings.HARD};

        difficultComboBox = new JComboBox(difficultTypes);
        styles.MediumFontStyle(difficultComboBox);
        int selectedPosition = 1;
        switch (previousPlayerType) {
            case constants.COMPUTER_EASY:
                selectedPosition = 0;
                break;
            case constants.COMPUTER_HARD:
                selectedPosition = 2;
                break;
        }
        difficultComboBox.setSelectedIndex(selectedPosition);

        screenContentPanel.add(difficultComboBox);
    }

    private void initColorPicker(Color previousColor) {
        addDataInputLabel(strings.PLAYER_COLOR);
        colorPickerButton = new JButton();
        colorPickerButton.setBackground(previousColor);

        colorPickerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Color newColor = JColorChooser.showDialog(
                        PlayerSelectionPanel.this,
                        strings.CHOOSE_COLOR,
                        colors.DARK_GRAY);
                if(newColor != null) {
                    colorPickerButton.setBackground(newColor);
                    repaint();
                }
            }
        });

        screenContentPanel.add(colorPickerButton);
    }

    private JLabel addDataInputLabel(String text) {
        JLabel label = new JLabel(text);
        styles.LightTextStyle(label);
        screenContentPanel.add(label);

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
                    return new Player(playerName, getComputerDifficult(), previousPlayer.getSide(), colorPickerButton.getBackground());
                case strings.HUMAN:
                    return new Player(playerName, constants.HUMAN, previousPlayer.getSide(), colorPickerButton.getBackground());
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