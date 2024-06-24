package shared.Request;


import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("validusernameRequest")
public class ValidUsernameRequest implements Request{

    String username;

    public  ValidUsernameRequest(String username){
        this.username = username;
    }

    public ValidUsernameRequest(){
    }


    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleValidUsernameRequest(this);
    }
}
