package code.ui.components;

import resources.colors;
import resources.styles;

import javax.swing.*;
import java.awt.*;

public class ValuableSlider extends JPanel {
    private JSlider slider;
    private JLabel valueLabel;
    private String labelText;

    public ValuableSlider() {
        setOpaque(false);
        setLayout(new GridLayout(1, 2));

        valueLabel = new JLabel();
        styles.LightFontStyle(valueLabel);
        valueLabel.setForeground(colors.DARK_GRAY);

        add(valueLabel);
    }

    public void createSlider(String labelText, int minValue, int maxValue) {
        this.labelText = labelText;
        valueLabel.setText(labelText);

        slider = new JSlider(minValue, maxValue);
        slider.setPaintLabels(true);

        styles.TransparentComponentStyle(slider);

        add(slider, BorderLayout.CENTER);
        slider.addChangeListener(e -> updateValueLabel());
    }

    public void setValue(int value) {
        slider.setValue(value);
        updateValueLabel();
    }

    private void updateValueLabel() {
        valueLabel.setText(labelText + String.valueOf(slider.getValue()));
    }

    public int getValue() {
        return slider.getValue();
    }
}
