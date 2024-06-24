package server;

import server.Socket.SocketStarter;

public class Main {
    public static void main(String[] args) {
        System.out.println("Here is the Server!");
        SocketStarter socketStarter = new SocketStarter();
        socketStarter.start();
    }
}