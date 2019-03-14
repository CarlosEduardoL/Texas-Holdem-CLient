package controller;

import communication.TCPConnection;
import view.MainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenController implements TCPConnection.ConnectionEvent, ActionListener {
    
    private MainScreen view;
    private boolean sali = false;
    TCPConnection connection;
    
    public MainScreenController(){
        init();
    }

    private void init() {
        connection = TCPConnection.getInstance();
        connection.addConnectionEvent(this);
    }

    @Override
    public void onConnection() {

    }

    @Override
    public void onMessage(String msj) {
        if (!sali){

        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("Salirse")){
            sali = true;
        }
    }
}
