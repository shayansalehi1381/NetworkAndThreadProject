package client.Socket;

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
        this.socket = new Socket("127.0.0.1", 8080);
        printStream = new PrintStream(socket.getOutputStream());
        scanner = new Scanner(socket.getInputStream());
        objectMapper = new ObjectMapper();
        objectMapper.activateDefaultTyping(
                BasicPolymorphicTypeValidator.builder().allowIfBaseType(Response.class).build(),
                ObjectMapper.DefaultTyping.EVERYTHING);
    }


    public Response sendRequest(Request request) throws IOException {
        try {
            String s = null;
            printStream.println(objectMapper.writeValueAsString(request));
            if (scanner.hasNextLine()){
             s = scanner.nextLine();
            }
            return objectMapper.readValue(s, Response.class);
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
