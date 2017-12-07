package code.ui.components;

import javax.swing.*;
import java.awt.*;

public class WrapContentPanel extends JPanel {
    private JPanel screenContent;

    public WrapContentPanel(int columnCount) {
        setLayout(new BorderLayout());

        screenContent = new JPanel(new GridLayout(0, columnCount));
        screenContent.setOpaque(false);
        add(screenContent, BorderLayout.NORTH);
    }

    public void addComponent(JComponent component) {
        screenContent.add(component);
    }
}
