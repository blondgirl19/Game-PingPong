package code.ui.game_settings;

import code.core.BasePanel;
import code.ui.main_menu.MainMenuPanel;
import resources.strings;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameSettingsPanel extends BasePanel {
    @Override
    protected void fillScreenContent() {

    }

    @Override
    protected void fillTitle() {
        titlePanel.setTitle(strings.GAME_PARAMS);
        titlePanel.setBackButtonVisible(true);

        titlePanel.setBackButtonClickListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                gotoPanel(new MainMenuPanel());
            }
        });
    }
}
