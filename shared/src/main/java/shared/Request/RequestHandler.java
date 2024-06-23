package shared.Request;

import shared.Response.Response;

public interface RequestHandler {
    Response handleSignUpRequest(SignUpRequest signUpRequest);
}
