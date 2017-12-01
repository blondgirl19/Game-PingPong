package code.ui.main_menu;

import code.core.BasePanel;
import code.ui.game_settings.GameSettingsPanel;
import resources.strings;
import resources.styles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuPanel extends BasePanel {
    private MouseAdapter clickAdapter;

    @Override
    protected void fillTitle() {
        titlePanel.setTitle(strings.MAIN_MENU);
        titlePanel.setBackButtonVisible(false);
    }

    @Override
    protected void fillScreenContent() {
        screenContentPanel.setLayout(new GridLayout(0, 1));

        initClickListener();
        addLabel(strings.GAME);
        addLabel(strings.STATISTICS);
        //addLabel(SETTINGS);
        addLabel(strings.HELP);
        addLabel(strings.ABOUT);
        addLabel(strings.EXIT);
    }

    private void initClickListener() {
        clickAdapter = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (e.getSource() instanceof  JLabel) {
                    JLabel source = (JLabel) e.getSource();

                    switch (source.getText()) {
                        case strings.GAME:
                            gotoPanel(new GameSettingsPanel());
                            break;
                        case strings.STATISTICS:
                            showNotification("", "Coming soon!");
                            break;
                        case strings.SETTINGS:
                            showNotification("", "Coming soon!");
                            break;
                        case strings.HELP:
                            showNotification(strings.HELP, strings.HELP_TEXT);
                            break;
                        case strings.ABOUT:
                            showNotification(strings.ABOUT, strings.ABOUT_GAME_TEXT);
                            break;
                        case strings.EXIT:
                            showYesNoDialog(strings.WARNING,
                                    strings.ARE_U_SURE_WANT_EXIT,
                                    strings.DO_EXIT,
                                    strings.STAY,
                                    new YesNoClickListener() {
                                        @Override
                                        public void onYesClicked() {
                                            System.exit(0);
                                        }

                                        @Override
                                        public void onNoClicked() {
                                            //ignore
                                        }
                                    });
                            break;
                    }
                }

            }
        };
    }

    private void addLabel(String text) {
        JLabel label = new JLabel(text);
        label.addMouseListener(clickAdapter);
        styles.MenuButtonStyle(label);

        screenContentPanel.add(label);
    }
}
