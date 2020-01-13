package uz.nisd.firstproject.uploadimage.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FileInfoDetails implements Serializable {

    @SerializedName("url")
    private String url;

    @SerializedName("title")
    private String title;


    public FileInfoDetails(){

    }

    public FileInfoDetails(String url, String title) {
        this.url = url;
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
