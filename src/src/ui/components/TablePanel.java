package src.ui.components;

import src.data.pojo.Dimension;
import src.data.pojo.Point;
import resources.colors;
import resources.styles;

import javax.swing.*;
import java.awt.*;

import static resources.constants.*;

public class TablePanel extends JPanel {
    private PaintCallback callback;
    private String textToDraw;

    public TablePanel() {
        styles.setComponentMargins(this, TABLE_MARGINS);
        setBackground(colors.LATTE_BACKGROUND);
    }

    public void setCallback(PaintCallback callback) {
        this.callback = callback;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //left and right borders
        g.setColor(colors.RED);

        int leftRightWidth = TABLE_BORDER + TABLE_MARGINS;
        int leftRightHeight = getHeight() - TABLE_MARGINS*2;
        int rightStartX = getWidth() - TABLE_BORDER - TABLE_MARGINS;

        g.fillRect(0, TABLE_MARGINS, leftRightWidth, leftRightHeight);
        g.fillRect(rightStartX, TABLE_MARGINS, leftRightWidth, leftRightHeight);

        //top bottom borders
        g.setColor(colors.GREEN);
        int bottomBorderY = getHeight() - TABLE_MARGINS - TABLE_BORDER;

        g.fillRect(0, TABLE_MARGINS, getWidth(), TABLE_BORDER);
        g.fillRect(0, bottomBorderY, getWidth(), TABLE_BORDER);

        drawText(g);
        g.setColor(colors.DARK_GRAY);

        callback.onPaintComponent(g);
    }

    public void setTextToDraw(String textToDraw) {
        this.textToDraw = textToDraw.toUpperCase();
        repaint();
    }

    public void clearTextToDraw() {
        textToDraw = "";
    }

    private void drawText(Graphics g) {
        if (textToDraw != null && !textToDraw.isEmpty()) {
            if (textToDraw.length() < 2) { // timer
                g.setColor(colors.ORANGE);
                g.setFont(styles.SansSerifFont(styles.BIG_FONT_SIZE));
            } else {
                g.setColor(colors.SHADOW);
                g.setFont(styles.SansSerifFont(styles.MEDIUM_FONT_SIZE));
            }


            int textWidth = g.getFontMetrics().stringWidth(textToDraw);
            g.drawString(textToDraw, getWidth() / 2 - textWidth / 2, getHeight() * 3 / 4);
        }
    }

    public interface PaintCallback {
        void onPaintComponent(Graphics g);
    }

    public int getRacketStartX() {
        return TABLE_MARGINS * 2 + TABLE_BORDER;
    }

    public int getRacketEndX() {
        return getWidth() - 2 *TABLE_MARGINS - TABLE_BORDER;
    }

    public Point getCenterPoint(){
        return new Point(getWidth() / 2, getHeight() / 2);
    }

    public Point getMinBallCoordinates(){
        int minY = TABLE_BORDER + TABLE_MARGINS;
        return new Point(0, minY);
    }

    public Point getMaxBallCoordinates() {
        int maxY = getHeight() - TABLE_BORDER - TABLE_MARGINS;
        return new Point(getWidth(), maxY);
    }

    public Dimension getDimension(){
        return new Dimension(getWidth(), getHeight());
    }

    @Override
    public int getHeight() {
        int superHeight = super.getHeight();
        return superHeight == 0 ? MIN_TABLE_PANEL_HEIGHT : superHeight;
    }

    @Override
    public int getWidth() {
        int superWidth = super.getWidth();
        return superWidth == 0 ? MIN_FRAME_WIDTH : superWidth;
    }
}
