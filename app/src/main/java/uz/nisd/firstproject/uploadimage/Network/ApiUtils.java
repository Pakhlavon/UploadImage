package uz.nisd.firstproject.uploadimage.Network;

public class ApiUtils {
    private ApiUtils(){
    }
    public static final String API_URL="https://logistics-house.uz/";
    public static FileService getFileService(){
        return RetrofitClient.getClient(API_URL).create(FileService.class);
    }
}
