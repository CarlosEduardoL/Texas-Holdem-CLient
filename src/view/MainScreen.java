package view;

import controller.MainScreenController;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;

public class MainScreen extends JFrame{

    private JPanel privateLabelContainer;
    private JPanel publicLabelContainer;
    private JPanel ipsContainer;
    private JPanel botonContainer;
    private JButton seguir,salir;
    private MainScreenController controller;
    private HashMap<String,JLabel> ips = new HashMap<>();

    public MainScreen(){

        privateLabelContainer = new JPanel();
        publicLabelContainer = new JPanel();
        ipsContainer = new JPanel();
        botonContainer = new JPanel();
        ipsContainer.setLayout(new GridLayout(1,0));
        privateLabelContainer.setLayout(new GridLayout(1,0));
        publicLabelContainer.setLayout(new GridLayout(1,0));
        botonContainer.setLayout(new GridLayout(0,1));

        controller = new MainScreenController(this);

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
        add(ipsContainer,BorderLayout.NORTH);
        add(privateLabelContainer,BorderLayout.SOUTH);
        add(publicLabelContainer,BorderLayout.CENTER);
        add(botonContainer,BorderLayout.EAST);

    }

    public void disconet(String ip){
        ips.get(ip).setText(ips.get(ip).getText() + ": Salio");
    }

    public void addIp(String ip){
        ips.put(ip,new JLabel(ip));
        ipsContainer.add(ips.get(ip));
        pack();
        setSize(600,600);
    }

    public void addPublicCard(String carta){
        publicLabelContainer.add(new JLabel(carta));
        pack();
        setSize(600,600);
    }

    public void addPrivateCard(String carta){
        privateLabelContainer.add(new JLabel(carta));
        pack();
        setSize(600,600);
    }

}
