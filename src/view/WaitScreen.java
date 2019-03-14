package view;

import controller.WaitScreenController;

import javax.swing.*;

public class WaitScreen extends JFrame {

    private WaitScreenController controller;

    public WaitScreen(){
        setSize(600,600);
        add(new JLabel("Wait a minut please... Searching an server to you"), SwingConstants.CENTER);
        new Thread(
                () -> controller = new WaitScreenController(this)
        ).start();
    }
}
