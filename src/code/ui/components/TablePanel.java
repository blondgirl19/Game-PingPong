package code.ui.components;

import code.data.pojo.Dimension;
import resources.colors;
import resources.constants;
import resources.styles;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import code.data.pojo.Point;

public class TablePanel extends JPanel {
    private PaintCallback callback;

    public TablePanel() {
        LineBorder lineBorder = new LineBorder(colors.GREEN, constants.TABLE_BORDER);
        setBorder(lineBorder);

        styles.setComponentMargins(this, constants.TABLE_MARGINS);
        setBackground(colors.LATTE_BACKGROUND);
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

    public int getRacketStartX() {
        return constants.TABLE_MARGINS * 2 + constants.TABLE_BORDER;
    }

    public int getRacketEndX() {
        return getWidth() - 2 *constants.TABLE_MARGINS - constants.TABLE_BORDER;
    }

    public Point getCenterPoint(){
        return new Point(getWidth() / 2, getHeight() / 2);
    }

    public Point getMinBallCoordinates(){
        int minY = constants.TABLE_BORDER + constants.TABLE_MARGINS;
        return new Point(0, minY);
    }

    public Point getMaxBallCoordinates() {
        int maxY = getHeight() - constants.TABLE_BORDER - constants.TABLE_MARGINS;
        return new Point(getWidth(), maxY);
    }

    public Dimension getDimension(){
        return new Dimension(getWidth(), getHeight());
    }
}
