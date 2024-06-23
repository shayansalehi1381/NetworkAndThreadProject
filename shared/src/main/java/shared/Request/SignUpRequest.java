package shared.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;
@JsonTypeName("signUpRequest")
public class SignUpRequest implements Request {

    private String username;
    private String password;

    public SignUpRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignUpRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleSignUpRequest(this);
    }
}
