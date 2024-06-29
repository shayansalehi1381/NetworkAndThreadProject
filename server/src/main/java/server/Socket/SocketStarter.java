package server.Socket;

import server.ClientHandler;
import server.DataBase;
import server.UDPServer;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketStarter extends Thread {
    private DataBase dataBase = new DataBase();
    private ServerSocket serverSocket;
    private UDPServer udpServer;

    @Override
    public void run() {
        try {
            dataBase.loadUsers(); // Load users when the server starts
            serverSocket = new ServerSocket(4321);
            udpServer = new UDPServer(4322,dataBase); // Initialize UDPServer on port 4322
            udpServer.start(); // Start UDPServer thread

            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(new SocketResponseSender(socket), dataBase, udpServer).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (udpServer != null) {
                udpServer.close();
            }
        }
    }
}
