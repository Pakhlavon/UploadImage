package uz.nisd.firstproject.uploadimage;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import uz.nisd.firstproject.uploadimage.Model.FileUpload;
import uz.nisd.firstproject.uploadimage.Network.ApiUtils;
import uz.nisd.firstproject.uploadimage.Network.FileService;


import static uz.nisd.firstproject.uploadimage.App.CHANNEL_ID;
import static uz.nisd.firstproject.uploadimage.App.CHANNEL_ID_2;

public class MainActivity extends AppCompatActivity {
    ImageView image;
    private NotificationManagerCompat notificationManagerCompat;
    public static final int MULTIPLE_PERMISSIONS = 3;
    private LinearLayout choose_image,show_image,create_image;
    FileService fileService;
    File file;
    private Bitmap bitmap;
    String imagePath;
    String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        choose_image=(LinearLayout)findViewById(R.id.choose_image);
        create_image=(LinearLayout)findViewById(R.id.create_image);
        show_image=(LinearLayout)findViewById(R.id.show_image);
        image=(ImageView)findViewById(R.id.image);
        fileService= ApiUtils.getFileService();

        notificationManagerCompat=NotificationManagerCompat.from(this);


        show_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation=AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation);
                show_image.startAnimation(animation);

                Intent intent=new Intent(MainActivity.this,ImageActivity.class);
                startActivity(intent);
            }
        });

        create_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation);
                create_image.startAnimation(animation);

                Intent intent=new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,0);


            }
        });
        choose_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Animation animation= AnimationUtils.loadAnimation(MainActivity.this,R.anim.animation);
                choose_image.startAnimation(animation);


                    file =new File(imagePath!=null?imagePath:"");

                RequestBody requestBody=RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part body =MultipartBody.Part.createFormData("image",file.getName(),requestBody);

                Call<FileUpload> call =fileService.upload(body);
                call.enqueue(new Callback<FileUpload>() {
                    @Override
                    public void onResponse(Call<FileUpload> call, Response<FileUpload> response) {
                        if (response.isSuccessful())
                        {

//                            if (TextUtils.isEmpty(response.body().getMessage())){
                                String title="Upload";
                                String description = "Uploaded is Succesfully";

                                Notification notification =new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID)
                                        .setSmallIcon(R.drawable.ic_cloud_upload_black_24dp)
                                        .setContentTitle(title)
                                        .setContentText(description)
                                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManagerCompat.notify(1,notification);

                                Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_LONG).show();
                                image.setVisibility(View.INVISIBLE);
                            }

//                        }
                    }
                    @Override
                    public void onFailure(Call<FileUpload> call, Throwable t) {

                        String title="Upload";
                        String description = "Xatolik mavjud";

                        Notification notification =new NotificationCompat.Builder(MainActivity.this,CHANNEL_ID_2)
                                .setSmallIcon(R.drawable.ic_highlight_off_black_24dp)
                                .setContentTitle(title)
                                .setContentText(description)
                                .setPriority(NotificationCompat.PRIORITY_LOW)
                                .build();

                        notificationManagerCompat.notify(1,notification);



                        Toast.makeText(MainActivity.this, "ERROR"+ t.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println(t.getMessage());
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==RESULT_OK)
        {
            if (data==null){

                Toast.makeText(this, "Unable to choose image", Toast.LENGTH_SHORT).show();
            return;
            }
            Uri imageUri =data.getData();
//            imagePath=getRealPathFromUri(imageUri);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");

                imagePath = getRealPathFromUri(imageUri);
                file = new File(imagePath.toString());
                image.setImageBitmap(bitmap);
                image.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
            }



        }
    }
    private String getRealPathFromUri(Uri uri)
    {
        String[] projection ={MediaStore.Images.Media.DATA};
        CursorLoader loader=new CursorLoader(getApplicationContext(),uri,projection,null,null,null);
        Cursor cursor =loader.loadInBackground();
        int column_idx=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result=cursor.getString(column_idx);
        cursor.close();
        return result;
    }

    private  boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p:permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this,p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),MULTIPLE_PERMISSIONS );
            return false;
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissionsList[], int[] grantResults) {
        switch (requestCode) {
            case MULTIPLE_PERMISSIONS:{
                if (grantResults.length > 0) {
                    String permissionsDenied = "";
                    for (String per : permissionsList) {
                        if(grantResults[0] == PackageManager.PERMISSION_DENIED){
                            permissionsDenied += "\n" + per;
                        }
                    }
                }
                return;
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        checkPermissions();
    }
}
