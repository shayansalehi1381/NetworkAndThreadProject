package shared.Response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("LoginResponse")
public class LoginResponse implements Response{
    private String username;

    public LoginResponse(String username) {
        this.username = username;
    }

    public  LoginResponse(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleLoginResponse(this);
    }
}
