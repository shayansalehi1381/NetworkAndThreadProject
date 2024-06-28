package shared.Response;

public interface ResponseHandler {
    void handleSignUpResponse(SignUpResponse signUpResponse);
    void handleLoginResponse(LoginResponse loginResponse);
    void handleValidUsernameResponse(ValidUsernameResponse validUsernameResponse);
}
