package uz.nisd.firstproject.uploadimage.Model;

import com.google.gson.annotations.SerializedName;

public class FileUpload {
    @SerializedName("image")
    private String image;
    @SerializedName("message")
    private String message;

    public FileUpload(String image, String message) {
        this.image = image;
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
