package client;

import java.io.IOException;
import java.net.*;

public class UDPclient {
    private DatagramSocket socket;
    private InetAddress address;
    private final int port;

    public UDPclient(String ipAddress, int port) throws UnknownHostException, SocketException {
        this.address = InetAddress.getByName(ipAddress);
        this.port = port;
        socket = new DatagramSocket();
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

    public byte[] downloadFile(String fileName, String username) throws IOException {
        byte[] sendData = ("DOWNLOAD " + username + " " + fileName).getBytes(); // Include username in the download command
        DatagramPacket packet = new DatagramPacket(sendData, sendData.length, address, port);
        socket.send(packet);

        byte[] buffer = new byte[4096];
        packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        System.out.println("File downloaded: " + fileName);
        return packet.getData();
    }

    public void close() {
        socket.close();
    }
}
