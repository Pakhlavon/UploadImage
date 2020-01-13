package uz.nisd.firstproject.uploadimage.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FileInfo implements Serializable {
    @SerializedName("message")
    private String message;

    @SerializedName("files")
    private List<FileInfoDetails> files;

    public FileInfo(String message, List<FileInfoDetails> files) {
        this.message = message;
        this.files = files;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<FileInfoDetails> getFiles() {
        return files;
    }

    public void setFiles(List<FileInfoDetails> files) {
        this.files = files;
    }
}


