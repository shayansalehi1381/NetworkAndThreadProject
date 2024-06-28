package shared.Response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("UsernameExistResponse")
public class UserNameExistResponse implements Response{

    public boolean exists = true;
    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleUsernameExistResponse(this);
    }
}
