package shared.Request;

import shared.Response.Response;

public interface RequestHandler {
    Response handleSignUpRequest(SignUpRequest signUpRequest);
    Response handleValidUsernameRequest(ValidUsernameRequest validUsernameRequest);

    Response handleLoginRequest(LoginRequest loginRequest);

    Response handleUsernameExistRequest(UserNameExistRequest userNameExistRequest);

   Response handleCheckPasswordRequest(CheckPasswordRequest checkPasswordRequest);
   Response handleSeeFilesRequest(SeeFilesRequest seeFilesRequest);
   Response handleDownloadFileNameRequest(DownloadFileNameRequest downloadFileNameRequest);
}
