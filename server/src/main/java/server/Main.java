package server;

import server.Socket.SocketStarter;

public class Main {
    public static void main(String[] args) {
        SocketStarter socketStarter = new SocketStarter();
        socketStarter.start();
    }
}