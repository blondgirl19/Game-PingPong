package code.ui;

import resources.strings;
import code.ui.main_menu.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

import static resources.dimen.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        initMainFrame();
        initComponents();
    }

    private void initComponents() {
        add(new MainMenuPanel());
    }

    public void initMainFrame() {
        Dimension dimension = new Dimension(MAIN_FRAME_WIDTH, MAIN_FRAME_HEIGHT);
        setSize(dimension);
        setMinimumSize(dimension);
        setTitle(strings.APP_TITLE);
        //setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setVisible(true);
    }
}