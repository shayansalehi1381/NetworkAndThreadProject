package server.Socket;

import server.ClientHandler;
import server.DataBase;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketStarter extends Thread {
    private DataBase dataBase = new DataBase();

    private ServerSocket serverSocket;


    @Override
    public void run() {
        try {
            dataBase.loadUsers(); // Load users when the server starts
            serverSocket = new ServerSocket(4321);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(new SocketResponseSender(socket), dataBase).start();
            }
        } catch (Exception e) {
            System.out.println('d');
        }
    }
}