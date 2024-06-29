package client;

import client.Socket.SocketRequestSender;

import java.io.IOException;

public class Main {
    public static void main(String[] args)  {
        try {
          Menu menu = new Menu(new SocketRequestSender());
          menu.startMenu();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}