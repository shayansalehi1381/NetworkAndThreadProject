package shared.Response;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("ValidUsernameResponse")
public class ValidUsernameResponse implements Response{
    public  boolean valid = true;
    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleValidUsernameResponse(this);
    }
}
