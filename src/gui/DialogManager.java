package gui;

import entidades.Empleado;
import entidades.Tarea;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DialogManager {
    JDialog jDialog;
    JPanel panel;

    public DialogManager()
    {
        mostrar(panel);
    }

    public void mostrar(JPanel panel)
    {
        Dialog jDialog = new JDialog();
        jDialog.setLayout(new GridLayout(2,1));
        jDialog.setModal(true);
        jDialog.setSize(300, 200);
        jDialog.setLocationRelativeTo(null);
    }
}
