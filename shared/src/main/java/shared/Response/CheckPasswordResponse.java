package shared.Response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("CheckPasswordResponse")
public class CheckPasswordResponse implements Response{

    public boolean ableToLogin;
    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleCheckPasswordResponse(this);
    }
}
