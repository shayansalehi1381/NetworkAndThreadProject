package shared.Request;


import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("LoginReq")
public class LoginRequest implements Request{


    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleLoginRequest(this);
    }
}
