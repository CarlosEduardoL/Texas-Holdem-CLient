package controller;

import communication.TCPConnection;
import view.MainScreen;

public class MainScreenController implements TCPConnection.ConnectionEvent {
    
    private MainScreen view;
    
    public MainScreenController(){
        init();
    }

    private void init() {
    }

    @Override
    public void onConnection(String ip) {

    }

    @Override
    public void onMessage(String msj) {

    }
}
