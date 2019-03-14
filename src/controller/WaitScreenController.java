package controller;

import communication.TCPConnection;
import view.MainScreen;
import view.WaitScreen;

public class WaitScreenController implements TCPConnection.ConnectionEvent {

    TCPConnection connection;
    private WaitScreen view;

    public WaitScreenController(WaitScreen waitScreen){
        view = waitScreen;
        init();
    }

    private void init(){
        connection = TCPConnection.getInstance(5000);
        connection.addConnectionEvent(this);
        connection.connect("172.30.0.1",5000);
    }

    @Override
    public void onConnection() {
        view.setVisible(false);
        new MainScreen().setVisible(true);
    }

    @Override
    public void onMessage(String msj) {

    }
}
