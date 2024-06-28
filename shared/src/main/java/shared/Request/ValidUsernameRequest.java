package shared.Request;


import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("ValidUsernameRequest")
public class ValidUsernameRequest implements Request{

    String username;


    public  ValidUsernameRequest(String username){
        this.username = username;
    }

    public ValidUsernameRequest(){
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleValidUsernameRequest(this);
    }
}
