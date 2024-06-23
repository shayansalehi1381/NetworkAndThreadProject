package shared.Request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import shared.Response.Response;
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "subclassType"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = SignUpRequest.class, name = "signUpRequest"),
})
public interface Request {
    Response run(RequestHandler requestHandler);
}
