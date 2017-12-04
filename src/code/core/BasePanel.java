package code.core;

import code.ui.MainFrame;
import code.ui.components.TitlePanel;

import javax.swing.*;

import java.awt.*;

import static resources.colors.LATTE_BACKGROUND;

public abstract class BasePanel extends JPanel {
    protected TitlePanel titlePanel;
    protected JPanel screenContentPanel;

    public BasePanel(){
        setLayout(new BorderLayout());
        setBackground(LATTE_BACKGROUND);

        initComponents();
        providePresenter();
        fillTitle();
        fillScreenContent();
    }

    private void initComponents() {
        titlePanel = new TitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        screenContentPanel = new JPanel();
        screenContentPanel.setOpaque(false);

        add(screenContentPanel, BorderLayout.CENTER);
    }

    protected abstract void providePresenter();
    protected abstract void fillTitle();
    protected abstract void fillScreenContent();

    protected void setBackButtonVisible(boolean isVisible) {
        titlePanel.setBackButtonVisible(isVisible);
    }

    public void gotoPanel(JPanel panel) {
        setVisible(false);
        getParent().add(panel);
        repaint();
        getParent().remove(this);
    }

    protected void showNotification(String title, String text) {
        JOptionPane.showMessageDialog(this, text, title, JOptionPane.INFORMATION_MESSAGE);
    }

    protected void showYesNoDialog(String title, String text, String yesText, String noText, YesNoClickListener clickListener) {
        Object[] options = {yesText, noText};

        int result = JOptionPane.showOptionDialog(
                this,
                text,
                title,
                JOptionPane.OK_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (result == JOptionPane.OK_OPTION){
            clickListener.onYesClicked();
        } else {
            clickListener.onNoClicked();
        }
    }

    public interface YesNoClickListener {
        void onYesClicked();
        void onNoClicked();
    }
}
