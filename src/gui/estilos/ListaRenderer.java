package gui.estilos;

import javax.swing.*;
import java.awt.*;

public abstract class ListaRenderer<T> extends JPanel implements ListCellRenderer<T>{
    JPanel jPanel;
    JLabel jLabel;
    public ListaRenderer() {

        setLayout(new BorderLayout());

        jLabel = new JLabel();
        jPanel = new JPanel(new BorderLayout());
        jPanel.add(jLabel, BorderLayout.CENTER);

        add(jPanel, BorderLayout.CENTER);

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends T> list, T value, int index, boolean isSelected, boolean cellHasFocus) {
        jLabel.setText(ponerTexto(value));
        if(isSelected)
        {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        }else
        {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());

        setFont(new Font("Arial", Font.PLAIN, 16));
        setPreferredSize(new Dimension(200, 100));
        setOpaque(true);

        return this;
    }

    protected String stringEstado(int estado) {
        switch (estado) {
            case -1:
                return "No empezada";
            case 0:
                return "En progreso";
            case 1:
                return "Pausado";
            case 2:
                return "Terminado";
            default:
                return " ";
        }
    }

    protected abstract String ponerTexto(T value);
}
