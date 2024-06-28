package shared.Response;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("signUpResponse")
public class SignUpResponse implements Response {
    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleSignUpResponse(this);
    }
}
