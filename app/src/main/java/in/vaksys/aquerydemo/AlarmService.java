package in.vaksys.aquerydemo;


import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;


public class AlarmService extends IntentService {

    private static final int NOTIFICATION_ID = 1;
    private static final String TAG = "Rakshit";
    private NotificationManager notificationManager;
    private PendingIntent pendingIntent;
    String formattedDate, formattedDate1;
    SimpleDateFormat df;
    Calendar c;

    public AlarmService() {
        super("AlarmService");
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // don't notify if they've played in last 24 hr

        Handler mHandler = new Handler(getMainLooper());
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Alarm Service has started.");
                Context context = getApplicationContext();

                viewTime();

                Resources res = getApplicationContext().getResources();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());


                Intent mIntent = new Intent(getApplicationContext(), NoticationActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("comeDate", df.format(c.getTime()));
                mIntent.putExtras(bundle);
                //communicator = (Communicator) getApplicationContext();
                //mIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                builder.setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setTicker(res.getString(R.string.notification_title))
                        .setAutoCancel(true)
                        .setContentTitle(res.getString(R.string.notification_title))
                        .setContentText(res.getString(R.string.notification_subject));
                notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFICATION_ID, builder.build());
                //viewTime1();
                Log.i(TAG, "Notifications sent.");
                MyAlarm app = (MyAlarm) getApplicationContext();
                app.incrementCount();
            }
        });

        /*Log.i(TAG, "Alarm Service has started.");
        Context context = this.getApplicationContext();
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Intent mIntent = new Intent(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("test", formattedDate);
        mIntent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        viewTime();

        Resources res = this.getResources();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setTicker(res.getString(R.string.notification_title))
                .setAutoCancel(true)
                .setContentTitle(res.getString(R.string.notification_title))
                .setContentText(res.getString(R.string.notification_subject));
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.build());
        viewTime1();
        Log.i(TAG, "Notifications sent.");
        MyAlarm app = (MyAlarm) getApplicationContext();
        app.incrementCount();*/
    }

    public void viewTime() {
        c = Calendar.getInstance();

        df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Log.e("Rakshit", "Notification Come: " + formattedDate);
        Toast.makeText(this, "Notification Come: " + formattedDate, Toast.LENGTH_SHORT).show();
    }


}