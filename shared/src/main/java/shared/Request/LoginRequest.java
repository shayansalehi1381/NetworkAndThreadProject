package shared.Request;


import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("LoginReq")
public class LoginRequest implements Request{

    private String username;

    public LoginRequest(String username) {
        this.username = username;
    }

    public  LoginRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleLoginRequest(this);
    }
}
