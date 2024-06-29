package shared.Response;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.awt.*;
import java.io.File;
import java.util.List;

@JsonTypeName("SeeFilesResponse")
public class SeeFilesResponse implements Response {

    private List<String> files;
    public SeeFilesResponse(List<String> files){
        this.files = files;
    }

    public SeeFilesResponse() {
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleSeeFilesResponse(this);
    }
}
