package controller;

import communication.TCPConnection;
import view.MainScreen;
import view.WaitScreen;

import java.net.InetAddress;
import java.net.UnknownHostException;

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
        try {
            connection.connect(InetAddress.getLocalHost().getHostAddress(),5000);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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
