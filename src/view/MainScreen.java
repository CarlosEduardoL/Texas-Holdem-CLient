package view;

import controller.MainScreenController;

import javax.swing.*;
import javax.swing.border.Border;
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
    private JTextArea area;

    public MainScreen(){

        privateLabelContainer = new JPanel();
        publicLabelContainer = new JPanel();
        ipsContainer = new JPanel();
        botonContainer = new JPanel();
        ipsContainer.setLayout(new GridLayout(0,1));
        privateLabelContainer.setLayout(new GridLayout(1,0));
        publicLabelContainer.setLayout(new GridLayout(1,0));
        botonContainer.setLayout(new GridLayout(0,1));

        controller = new MainScreenController(this);
        area = new JTextArea();

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
        add(area,BorderLayout.WEST);

    }

    public void disconet(String ip){
        ips.get(ip).setText(ips.get(ip).getText() + ": Salio");
    }

    public void append(String text){
        area.append(text);
    }

    public void addIp(String ip){
        ips.put(ip,new JLabel(ip, SwingConstants.CENTER));
        ips.get(ip).setBorder(BorderFactory.createLineBorder(Color.BLUE));
        ips.get(ip).setText(ips.size() + ":" + ip);
        ipsContainer.add(ips.get(ip));
        pack();
        setSize(600,600);
    }

    public void addPublicCard(String carta){
        JLabel label = new JLabel(carta,SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        publicLabelContainer.add(label);
        pack();
        setSize(600,600);
    }

    public void addPrivateCard(String carta){
        JLabel label = new JLabel(carta,SwingConstants.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        privateLabelContainer.add(label);
        pack();
        setSize(600,600);
    }

}
