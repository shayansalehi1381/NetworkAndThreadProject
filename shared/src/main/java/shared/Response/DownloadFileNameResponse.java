package shared.Response;


import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("downloadFileNameResponse")
public class DownloadFileNameResponse implements Response {
    private String res;

    public DownloadFileNameResponse(String res) {
        this.res = res;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }

    public DownloadFileNameResponse() {
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleDownloadFileNameResponse(this);
    }
}
