package code.ui.components;

import resources.colors;
import resources.constants;
import resources.styles;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class TablePanel extends JPanel {
    private PaintCallback callback;

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

    public void setCallback(PaintCallback callback) {
        this.callback = callback;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        callback.onPaintComponent(g);
    }

    public interface PaintCallback {
        void onPaintComponent(Graphics g);
    }
}
