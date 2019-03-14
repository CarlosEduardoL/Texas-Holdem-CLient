package view;

import controller.MainScreenController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class MainScreen extends JFrame{

    private JPanel privateLabelContainer;
    private JPanel publicLabelContainer;
    private JPanel botonContainer;
    private JButton seguir,salir;
    private MainScreenController controller;

    public MainScreen(){

        privateLabelContainer = new JPanel();
        publicLabelContainer = new JPanel();
        botonContainer = new JPanel();
        privateLabelContainer.setLayout(new GridLayout(1,0));
        publicLabelContainer.setLayout(new GridLayout(1,0));
        botonContainer.setLayout(new GridLayout(0,1));

        controller = new MainScreenController();

        seguir = new JButton("Seguir");
        seguir.setActionCommand("Seguir");
        seguir.addActionListener(controller);

        salir = new JButton("Salir");
        salir.setActionCommand("Salir");
        salir.addActionListener(controller);

        botonContainer.add(salir);
        botonContainer.add(seguir);

        setSize(600,600);
        setLayout(new BorderLayout());
        add(privateLabelContainer,BorderLayout.SOUTH);
        add(publicLabelContainer,BorderLayout.CENTER);
        add(botonContainer);

    }

    public void addPublicCard(String carta){
        publicLabelContainer.add(new JLabel(carta));
        setSize(600,600);
    }

    public void addPrivateCard(String carta){
        privateLabelContainer.add(new JLabel(carta));
        setSize(600,600);
    }

}
