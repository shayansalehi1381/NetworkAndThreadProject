package shared.Request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.CheckPasswordResponse;
import shared.Response.Response;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "subclassType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SignUpRequest.class, name = "signUpRequest"),
        @JsonSubTypes.Type(value = LoginRequest.class, name = "LoginReq"),
        @JsonSubTypes.Type(value = ValidUsernameRequest.class, name = "ValidUsernameRequest"),
        @JsonSubTypes.Type(value = UserNameExistRequest.class, name = "UsernameExistRequest"),
        @JsonSubTypes.Type(value = CheckPasswordRequest.class, name = "CheckPasswordRequest"),
        @JsonSubTypes.Type(value = SeeFilesRequest.class, name = "SeeFilesRequest")
})
public interface Request {
    Response run(RequestHandler requestHandler);
}
