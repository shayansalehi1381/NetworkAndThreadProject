package shared.Response;

import shared.Request.SeeFilesRequest;

public interface ResponseHandler {
    void handleSignUpResponse(SignUpResponse signUpResponse);
    void handleLoginResponse(LoginResponse loginResponse);
    void handleValidUsernameResponse(ValidUsernameResponse validUsernameResponse);
    void handleUsernameExistResponse(UserNameExistResponse userNameExistResponse);
    void handleCheckPasswordResponse(CheckPasswordResponse checkPasswordResponse);
    void  handleSeeFilesResponse(SeeFilesResponse seeFilesResponse);
}
