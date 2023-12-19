package gui.estilos;

import javax.swing.*;
import java.awt.*;

public class UtilidadEstilo {

    public Font LABEL_FONT = new Font("Arial", Font.BOLD, 18);
    public Color LABEL_COLOR = new Color(114, 115, 117); // gris oscuro
    public Font TEXT_FIELD_FONT = new Font("Arial", Font.PLAIN, 14);
    public Color TEXT_FIELD_COLOR = new Color(235, 236, 237); // gris claro
    public Font COMBO_BOX_FONT = new Font("Arial", Font.PLAIN, 14);
    public Color COMBO_BOX_COLOR = new Color(235, 236, 237); // gris claro

    public void estiloJLabel(JLabel label) {
        label.setFont(LABEL_FONT);
        label.setForeground(LABEL_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));
    }

    public void estiloJTextField(JTextField textField) {
        textField.setFont(TEXT_FIELD_FONT);
        textField.setBackground(TEXT_FIELD_COLOR);
        textField.setMargin(new Insets(0, 10, 0, 0));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                BorderFactory.createEmptyBorder(0, 10, 0, 0) // Add a 10-pixel padding to the left
        ));
    }

    public void estiloJButton(JButton button) {
        button.setMaximumSize(button.getPreferredSize());
    }

    public void estiloJComboBox(JComboBox comboBox) {
            comboBox.setFont(COMBO_BOX_FONT);
        comboBox.setBackground(COMBO_BOX_COLOR);
    }
}
