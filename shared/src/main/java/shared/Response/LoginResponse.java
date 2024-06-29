package shared.Response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("LoginResponse")
public class LoginResponse implements Response{


    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleLoginResponse(this);
    }
}
