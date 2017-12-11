package code.ui;

import resources.strings;
import code.ui.main_menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

import static resources.constants.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        initMainFrame();
        initComponents();
    }

    public void initMainFrame() {
        Dimension dimension = new Dimension(MIN_FRAME_WIDTH, MIN_FRAME_HEIGHT);
        setSize(dimension);
        setMinimumSize(dimension);
        setTitle(strings.APP_TITLE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        add(new MainMenuPanel());
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}