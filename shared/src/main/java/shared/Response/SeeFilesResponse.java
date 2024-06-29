package shared.Response;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.awt.*;
import java.io.File;
import java.util.List;

@JsonTypeName("SeeFilesResponse")
public class SeeFilesResponse implements Response {

    private List<File> files;
    public SeeFilesResponse(List<File> files){
        this.files = files;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    @Override
    public void run(ResponseHandler responseHandler) {
        responseHandler.handleSeeFilesResponse(this);
    }
}
