package client;

import client.Socket.SocketRequestSender;
import shared.Model.User;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

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