package uz.nisd.firstproject.uploadimage;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_ID="channel 1";
    public static final String CHANNEL_ID_2="channel 2";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChanels();

    }

    private void createNotificationChanels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel=new NotificationChannel(
                    CHANNEL_ID,
                    "channe l",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("this is channel 1");


            NotificationChannel channel2=new NotificationChannel(
                    CHANNEL_ID_2,
                    "channe 2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("this is channel 2");

            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            manager.createNotificationChannel(channel2);
        }
    }

}
