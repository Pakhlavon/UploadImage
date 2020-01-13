package uz.nisd.firstproject.uploadimage.Network;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import uz.nisd.firstproject.uploadimage.Model.FileInfo;
import uz.nisd.firstproject.uploadimage.Model.FileUpload;

public interface FileService {

    @Multipart
    @POST("/index.php")
    Call<FileUpload> upload(@Part MultipartBody.Part file);


    @GET("/index.php")
    Call<FileInfo> getFIle();


}
