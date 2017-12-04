package code.ui.components;

import resources.colors;
import resources.constants;
import resources.styles;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class TablePanel extends JPanel {
    public TablePanel() {
        LineBorder lineBorder = new LineBorder(colors.GREEN, constants.TABLE_BORDER);
        setBorder(lineBorder);

        styles.setComponentMargins(this, constants.TABLE_MARGINS);
        setBackground(colors.LATTE_BACKGROUND);
    }

    public int getTableWidth() {
        return getWidth() - 2 * constants.TABLE_MARGINS;
    }

    public int getTableHeight() {
        return getHeight() - 2 * constants.TABLE_MARGINS;
    }
}
