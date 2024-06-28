package shared.Response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public interface Response {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            property = "subclassType"
    )

    @JsonSubTypes({
            @JsonSubTypes.Type(value = SignUpResponse.class, name = "signUpResponse"),
            @JsonSubTypes.Type(value = LoginResponse.class, name = "LoginResponse"),
            @JsonSubTypes.Type(value = ValidUsernameResponse.class, name = "ValidUsernameResponse"),
            @JsonSubTypes.Type(value = CheckPasswordResponse.class, name = "CheckPasswordResponse"),
            @JsonSubTypes.Type(value = CheckPasswordResponse.class, name = "UsernameExistResponse")

    })
    void run(ResponseHandler responseHandler);
}
