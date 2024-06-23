package client;

import shared.Model.User;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {

        startMenu();
    }


    static void startMenu(){
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();

        System.out.println("Welcome!");
        System.out.println("What do you want to do?");
        System.out.println("1.login");
        System.out.println("2.sign up");



        while (true){
            try {
                if (input == 1){
                    login(scanner);
                }
                else if (input == 2){
                    signUp(scanner);
                }
            }catch (InputMismatchException e){
                System.out.println("wrong input!");
            }
        }
    }


    static void signUp(Scanner scanner){
        System.out.println("Choose your Username:");
        String username = scanner.next();
        System.out.println("Choose your password:");
        String password = scanner.next();

        System.out.println("You signed UP!");
    }



    static  void login(Scanner scanner){
        System.out.println("Type your username:");
        String username = scanner.next();

    }
}