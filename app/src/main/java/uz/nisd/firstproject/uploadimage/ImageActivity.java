package uz.nisd.firstproject.uploadimage;

import android.app.Notification;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uz.nisd.firstproject.uploadimage.Adapter.GetAdapter;
import uz.nisd.firstproject.uploadimage.Model.FileInfo;
import uz.nisd.firstproject.uploadimage.Model.FileInfoDetails;
import uz.nisd.firstproject.uploadimage.Network.ApiUtils;

import static uz.nisd.firstproject.uploadimage.App.CHANNEL_ID;
import static uz.nisd.firstproject.uploadimage.App.CHANNEL_ID_2;


public class ImageActivity<trustAllCerts> extends AppCompatActivity {

    private NotificationManagerCompat notificationManagerCompat;
    private RecyclerView recyclerView;
    private GridView gridView;
    LinearLayoutManager layoutInflater;
    private GetAdapter getAdapter;
    List<FileInfoDetails> list = new ArrayList<>();
    FileInfoDetails fileInfoDetails;

    public ImageActivity() throws NoSuchAlgorithmException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(false);
        layoutInflater = new LinearLayoutManager(getApplicationContext());
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutInflater);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        notificationManagerCompat = NotificationManagerCompat.from(this);

        // make network call
        Call<FileInfo> call = ApiUtils.getFileService().getFIle();

        call.enqueue(new Callback<FileInfo>() {
            @Override
            public void onResponse(Call<FileInfo> call, Response<FileInfo> response) {

                if (response.isSuccessful()) {

                    String title = "Download";
                    String description = "Uploaded is Succesfully";

                    Notification notification = new NotificationCompat.Builder(ImageActivity.this, CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_cloud_download_black_24dp)
                            .setContentTitle(title)
                            .setContentText(description)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .build();

                    notificationManagerCompat.notify(1, notification);


                    list.addAll(response.body().getFiles());
//                    System.out.println(list.size());
                    recyclerView.setAdapter(new GetAdapter(ImageActivity.this, list));


                }
            }

            @Override
            public void onFailure(Call<FileInfo> call, Throwable t) {

                String title = "Download";
                String description = "An error Occured";

                Notification notification = new NotificationCompat.Builder(ImageActivity.this, CHANNEL_ID_2)
                        .setSmallIcon(R.drawable.ic_highlight_off_black_24dp)
                        .setContentTitle(title)
                        .setContentText(description)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();

                notificationManagerCompat.notify(1, notification);
                Toast.makeText(ImageActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(t.getMessage());
            }
        });
    }

    }
