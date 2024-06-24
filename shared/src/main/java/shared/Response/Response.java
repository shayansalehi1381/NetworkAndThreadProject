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

    })
    void run(ResponseHandler responseHandler);
}
