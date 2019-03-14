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
    private boolean esperar;
    
    public MainScreenController(MainScreen view)
    {
        this.view = view;
        init();
    }

    private void init() {
        esperar = true;
        connection = TCPConnection.getInstance();
        connection.addConnectionEvent(this);
    }

    @Override
    public void onConnection() {

    }

    @Override
    public void onMessage(String msj) {

        String type = msj.split("::")[0];
        String mensaje = msj.split("::")[1];
        if (type.equals("Carta Publica")){
            Carta c = new Gson().fromJson(mensaje,Carta.class);
            view.addPublicCard(c.getNombre());
            view.append("El servido agrego una carta publica\n");
        }else if (type.equals("Player")){
            view.addIp(mensaje);
            view.append(mensaje+" Se ha unido a la parida \n");
        }else if (type.equals("Salio")){
            System.out.println(mensaje);
            view.disconet(mensaje);
            view.append(mensaje+" ha salido a la parida \n");
        }else if(type.equals("Carta Privada")){
            Carta c = new Gson().fromJson(mensaje,Carta.class);
            view.addPrivateCard(c.getNombre());
        }else if (type.equals("Permiso")){
            esperar = false;
            view.append("Es tu Turno\n");
        }else if (type.equals("Siguio")){
            view.append(mensaje+" sigue en la parida \n");
        }
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (!esperar){
            if (actionEvent.getActionCommand().equals("Salir") && !sali){
                sali = true;
                connection.sendMessage("Sali");
            }else{
                connection.sendMessage("Sigo");
            }

            esperar = true;
        }else {
            view.errorMessage("Usted ya salio de la partida");
        }
    }
}
