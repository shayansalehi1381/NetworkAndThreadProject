package client.Socket;

import client.ClientConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import shared.Response.Response;
import shared.Request.Request;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class SocketRequestSender {
    private final Socket socket;
    private final PrintStream printStream;
    private final Scanner scanner;
    private final ObjectMapper objectMapper;

    public SocketRequestSender() throws IOException {
        this.socket = new Socket("127.0.0.1", 4321);
        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());
        this.objectMapper = ClientConfig.getObjectMapper(); // Use configured ObjectMapper
    }


    public Response sendRequest(Request request) throws IOException {
        try {
            printStream.println(objectMapper.writeValueAsString(request));
            if (scanner.hasNextLine()) {
                String response = scanner.nextLine();
//                System.out.println("Received response: " + response); // Add this line
                return objectMapper.readValue(response, Response.class);
            } else {
                throw new IOException("No response from server");
            }
        } catch (Exception e) {
            System.out.println(e);
            close();
            throw e;
        }
    }



    public void close() {
        try {
            scanner.close();
            printStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
