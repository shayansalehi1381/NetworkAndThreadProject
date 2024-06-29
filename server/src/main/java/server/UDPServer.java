package server;

import shared.Model.User;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;

public class UDPServer extends Thread {
    private DatagramSocket socket;
    private final int port;
    DataBase dataBase;

    public UDPServer(int port,DataBase dataBase) throws IOException {
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


    private void handleUpload(DatagramPacket packet, String username, String fileName) throws IOException {
        byte[] fileData = packet.getData();
        Files.write(Paths.get(fileName), fileData);

        // Associate file with user in the database
        dataBase.addFileToUser(username, fileName);
        dataBase.saveUsers(); // Save updated user information
        System.out.println("File uploaded and associated with user: " + fileName);
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
