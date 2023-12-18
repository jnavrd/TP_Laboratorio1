package gui.estilos;

import javax.swing.*;
import java.awt.*;

public class UtilidadEstilo {

    public static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
    public static final Color LABEL_COLOR = new Color(100, 149, 237); // Cornflower blue
    public static final Font TEXT_FIELD_FONT = new Font("Arial", Font.PLAIN, 14);
    public static final Color TEXT_FIELD_COLOR = new Color(255, 255, 255); // White

    public static void estiloJLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
    }

    public static void estiloJTextField(JTextField textField) {
        textField.setFont(TEXT_FIELD_FONT);
        textField.setBackground(TEXT_FIELD_COLOR);
    }

    public static void estiloJButton(JButton button) {
        button.setFont(LABEL_FONT);
        button.setBackground(LABEL_COLOR);
    }
}
