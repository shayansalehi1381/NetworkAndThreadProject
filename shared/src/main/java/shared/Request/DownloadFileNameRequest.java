package shared.Request;

import com.fasterxml.jackson.annotation.JsonTypeName;
import shared.Response.Response;

@JsonTypeName("downloadFileNameRequest")
public class DownloadFileNameRequest implements Request {
    private String fileName;

    private String username;

    public DownloadFileNameRequest(String fileName, String username) {
        this.username = username;
        this.fileName = fileName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public DownloadFileNameRequest() {
    }

    @Override
    public Response run(RequestHandler requestHandler) {
        return requestHandler.handleDownloadFileNameRequest(this);
    }
}
