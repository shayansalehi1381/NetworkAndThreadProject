package shared.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("CheckPasswordRequest")
public class CheckPasswordRequest implements Request{

   private String username;
   private String password;

    public CheckPasswordRequest(String username, String password){
        this.username = username;
        this.password = password;
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

    public CheckPasswordRequest(){}


    @Override
    public Response run(RequestHandler requestHandler) {
       return requestHandler.handleCheckPasswordRequest(this);
    }
}
