package server;

import shared.Model.User;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UDPServer extends Thread {
    private DatagramSocket socket;
    private final int port;
    DataBase dataBase;

    public UDPServer(int port, DataBase dataBase) throws IOException {
        this.port = port;
        socket = new DatagramSocket(port);
        this.dataBase = dataBase;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[4096];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength()).trim();

                if (received.startsWith("UPLOAD")) {
                    String[] parts = received.split(" ");
                    if (parts.length < 3) {
                        System.out.println("Invalid upload request format: " + received);
                        continue;
                    }
                    String username = parts[1];
                    System.out.println(received

                    );
                    String fileName = parts[2];

                    // Create a new DatagramPacket to receive the actual file data
                    DatagramPacket filePacket = new DatagramPacket(buffer, buffer.length);
                    socket.receive(filePacket);

                    handleUpload(filePacket, username, fileName);
                } else if (received.startsWith("DOWNLOAD")) {
                    handleDownload(packet, received.substring(9).trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void handleUpload(DatagramPacket packet, String username, String fileName) {
        byte[] fileData = packet.getData();
        Path filePath = Paths.get("C:\\Users\\shaya\\IdeaProjects\\AP-assignment5\\server\\src\\main\\java\\server\\data\\" + username + "\\" + fileName);

        try {
            // Create directories if they don't exist
            Files.createDirectories(filePath.getParent());

            // Write file data to the specified path
            Files.write(filePath, fileData);

            // Associate file with user in the database
            dataBase.addFileToUser(username, fileName);
            dataBase.saveUsers(); // Save updated user information
            System.out.println("File uploaded and associated with user: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to upload file: " + e.getMessage());
        }
    }

    private void handleDownload(DatagramPacket packet, String fileName) throws IOException {
        File file = new File(fileName);
        if (file.exists()) {
            byte[] fileData = Files.readAllBytes(Paths.get(fileName));
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            DatagramPacket sendPacket = new DatagramPacket(fileData, fileData.length, address, port);
            socket.send(sendPacket);
            System.out.println("File sent: " + fileName);
        } else {
            System.out.println("File not found: " + fileName);
        }
    }

    public void close() {
        socket.close();
    }
}
