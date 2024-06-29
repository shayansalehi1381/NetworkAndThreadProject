package client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UDPclient extends Thread {
    private DatagramSocket socket;
    private DatagramSocket socket2;
    private InetAddress address;
    private final int port;

    public UDPclient(String ipAddress, int port) throws UnknownHostException, SocketException {
        this.address = InetAddress.getByName(ipAddress);
        this.port = port;
        socket = new DatagramSocket();
        socket2 = new DatagramSocket(8080);
    }

    @Override
    public void run() {
        byte[] buffer = new byte[4096];
        while (true) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket2.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength()).trim();
                System.out.println(received);

                if (received.startsWith("DOWNLOAD")) {
                    String[] parts = received.split(" ");
                    if (parts.length < 3) {
                        System.out.println("Invalid upload request format: " + received);
                        continue;
                    }
                    String username = parts[1];
                    String fileName = parts[2];

                    DatagramPacket filePacket = new DatagramPacket(buffer, buffer.length);
                    socket2.receive(filePacket);

                    downloadFile(filePacket, username, fileName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void uploadFile(byte[] fileData, String username, String fileName) throws IOException {
        String uploadCommand = "UPLOAD " + username + " " + fileName;
        byte[] sendData = uploadCommand.getBytes();
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(packet);

        // Adding some delay to ensure the server has received the command part
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        packet = new DatagramPacket(fileData, fileData.length, address, port);
        socket.send(packet);
        System.out.println("File uploaded: " + fileName);
    }


    public void downloadFile(DatagramPacket packet, String username, String fileName) throws IOException {
        byte[] fileData = packet.getData();
        Path filePath = Paths.get("C:\\Users\\shaya\\IdeaProjects\\AP-assignment5\\client\\src\\main\\java\\client\\downloadFolder\\"+fileName);
        System.out.println((filePath));
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, fileData);
            System.out.println("File uploaded and associated with user: " + fileName);
        } catch (IOException e) {
            System.err.println("Failed to upload file: " + e.getMessage());
        }
    }

    public void close() {
        socket.close();
    }
}
