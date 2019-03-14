package controller;

import com.google.gson.Gson;
import communication.TCPConnection;
import model.Carta;
import view.MainScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainScreenController implements TCPConnection.ConnectionEvent, ActionListener {
    
    private MainScreen view;
    private boolean sali = false;
    TCPConnection connection;
    
    public MainScreenController(MainScreen view)
    {
        this.view = view;
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
            String type = msj.split("::")[0];
            String mensaje = msj.split("::")[1];
            if (type.equals("Carta Publica")){
                Carta c = new Gson().fromJson(mensaje,Carta.class);
                view.addPublicCard(c.getNombre());
            }else if (type.equals("Player")){
                view.addIp(mensaje);
            }else if (type.equals("Salio")){
                System.out.println(mensaje);
                view.disconet(mensaje);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getActionCommand().equals("Salir")){
            sali = true;
            connection.sendMessage("Sali");
        }
    }
}
