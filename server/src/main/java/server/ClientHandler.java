package server;

import server.Socket.SocketResponseSender;
import shared.Request.RequestHandler;
import shared.Request.SignUpRequest;
import shared.Response.Response;
import shared.Response.ResponseHandler;

public class ClientHandler extends Thread implements RequestHandler {

    private SocketResponseSender socketResponseSender;
    private DataBase dataBase;

    public ClientHandler(SocketResponseSender socketResponseSender, DataBase dataBase) {
        this.dataBase = dataBase;
        this.socketResponseSender = socketResponseSender;
    }



    @Override
    public void run() {
        try {
            while (true) {
                Response response = socketResponseSender.getRequest().run(this);
                socketResponseSender.sendResponse(response);
            }
        } catch (Exception e) {
            socketResponseSender.close();
        }
    }

    @Override
    public Response handleSignUpRequest(SignUpRequest signUpRequest) {
        return null;
    }
}
