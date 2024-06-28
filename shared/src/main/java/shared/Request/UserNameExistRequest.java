package shared.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("UsernameExistRequest")
public class UserNameExistRequest implements Request{

    private String username;

    public UserNameExistRequest(String username){
        this.username = username;
    }

    public UserNameExistRequest(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleUsernameExistRequest(this);
    }
}
