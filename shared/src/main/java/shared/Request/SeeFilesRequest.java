package shared.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;
import shared.Response.SeeFilesResponse;

@JsonTypeName("SeeFilesRequest")
public class SeeFilesRequest implements Request{
   private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public SeeFilesRequest(String username){
        this.username = username;
    }

    public SeeFilesRequest(){}

    @Override
    public Response run(RequestHandler requestHandler) {
       return requestHandler.handleSeeFilesRequest(this);
    }
}
