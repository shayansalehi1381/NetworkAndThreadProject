package client;

import client.Socket.SocketRequestSender;
import shared.Request.*;
import shared.Response.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu implements ResponseHandler {
    UDPclient udPclient;
    String usernameForUDMenu = null;

    public SocketRequestSender socketRequestSender;
    private boolean isUsernameValid = false;
    private boolean isAbleToLogin = false;
    private boolean isUsernameExists = true;//this is for Login

    public Menu(SocketRequestSender socketRequestSender) {
        this.socketRequestSender = socketRequestSender;
    }


    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome!");

        while (true) {
            System.out.println("What do you want to do?");
            System.out.println("1.login");
            System.out.println("2.sign up");

            try {
                int input = scanner.nextInt();
                if (input == 1) {
                    login(scanner);
                    break;
                } else if (input == 2) {
                    signUp(scanner);
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("wrong input! try again.");
                System.out.println("***********************");
                scanner.next();
            }
        }
    }


    private void signUp(Scanner scanner) {
        System.out.println("Choose your Username:");
        String username = scanner.next();
        if (ValidUsername(username)) {
            System.out.println("Choose your password:");
            String password = scanner.next();
            String hashPass = hashPassword(password);
            try {
                socketRequestSender.sendRequest(new SignUpRequest(username, hashPass)).run(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println("Username already exists. Please choose another one.");
            signUp(scanner);
        }
    }

    public String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] passwordBytes = password.getBytes();
            byte[] hashedPasswordBytes = md.digest(passwordBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPasswordBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private void login(Scanner scanner) {
        System.out.println("Type your username:");
        String username = scanner.next();
        if (usernameExists(username)) {
            System.out.println("Type your Password:");
            String password = scanner.next();
            String hashPassword = hashPassword(password);
            if (checkPassword(username, hashPassword)) {
                try {
                    socketRequestSender.sendRequest(new LoginRequest(username)).run(this);
                    System.out.println("Hello " + username);
                    usernameForUDMenu = username;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("Wrong PassWord! try again.");
                login(scanner);
            }
        } else {
            System.out.println("This Username does not Exist in the DataBase! try again.");
            login(scanner);
        }
    }

    private boolean ValidUsername(String username) {
        try {
            ValidUsernameResponse response = (ValidUsernameResponse) socketRequestSender.sendRequest(new ValidUsernameRequest(username));
            response.run(this);
            return isUsernameValid;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean checkPassword(String username, String pass) {
        try {
            socketRequestSender.sendRequest(new CheckPasswordRequest(username, pass)).run(this);
            return isAbleToLogin;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean usernameExists(String username) {
        try {
            socketRequestSender.sendRequest(new UserNameExistRequest(username)).run(this);
            return isUsernameExists;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void handleSignUpResponse(SignUpResponse signUpResponse) {
        System.out.println("**************************************************");
        System.out.println("Your Account is Created!");
        startMenu();
    }

    @Override
    public void handleLoginResponse(LoginResponse loginResponse) {
        System.out.println(loginResponse.getUsername());
        usernameForUDMenu = loginResponse.getUsername();
        System.out.println("**************************************************");
        System.out.println("login Successful!");
        try {
            udPclient = new UDPclient("127.0.0.1", 4322);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
        startUDMenu();
    }

    @Override
    public void handleValidUsernameResponse(ValidUsernameResponse validUsernameResponse) {
        isUsernameValid = validUsernameResponse.valid;
    }

    @Override
    public void handleUsernameExistResponse(UserNameExistResponse userNameExistResponse) {
        isUsernameExists = userNameExistResponse.exists;
    }

    @Override
    public void handleCheckPasswordResponse(CheckPasswordResponse checkPasswordResponse) {
        isAbleToLogin = checkPasswordResponse.ableToLogin;
    }


    //************************************************************************************************************************************
    public void startUDMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. See your files in the Server");
            System.out.println("2. Upload files to the Server");
            System.out.println("3. Download your files from the Server");

            try {
                int input = scanner.nextInt();
                if (input == 1) {
                    seeFiles(scanner);
                } else if (input == 2) {
                    uploadFile(scanner);
                } else if (input == 3) {
                    downloadFile(scanner);
                } else {
                    System.out.println("Wrong input! Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Wrong input! Try again.");
                System.out.println("***********************");
                scanner.next();
            }
        }
    }


    private void seeFiles(Scanner scanner) {
        if (udPclient == null) {
            System.out.println("You need to login first.");
            return;
        }
        try {
            socketRequestSender.sendRequest(new SeeFilesRequest(usernameForUDMenu)).run(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void uploadFile(Scanner scanner) {
        if (udPclient == null) {
            System.out.println("You need to login first.");
            return;
        }
        System.out.println("Enter the file path to upload:");
        String filePath = scanner.next();
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] fileData = new byte[(int) file.length()];
            fis.read(fileData);
            udPclient.uploadFile(fileData, usernameForUDMenu, file.getName());
            System.out.println("File uploaded successfully.");
        } catch (IOException e) {
            System.out.println("File upload failed: " + e.getMessage());
        }
    }


    private void downloadFile(Scanner scanner) {
        if (udPclient == null) {
            System.out.println("You need to login first.");
            return;
        }
        System.out.println("Enter the file name to download:");
        String fileName = scanner.next();
        try {
            byte[] fileData = udPclient.downloadFile(fileName, usernameForUDMenu);
            try (FileOutputStream fos = new FileOutputStream(fileName)) {
                fos.write(fileData);
                System.out.println("File downloaded successfully.");
            }
        } catch (IOException e) {
            System.out.println("File download failed: " + e.getMessage());
        }
    }


    @Override
    public void handleSeeFilesResponse(SeeFilesResponse seeFilesResponse) {
        for (int i = 0; i < seeFilesResponse.getFiles().size(); i++) {
            System.out.println(i+1 + ": " + seeFilesResponse.getFiles().get(i));
        }
        System.out.println("*********************************************************");
    }
}
