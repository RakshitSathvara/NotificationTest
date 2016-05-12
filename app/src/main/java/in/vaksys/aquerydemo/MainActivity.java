package in.vaksys.aquerydemo;

import java.text.SimpleDateFormat;
import java.util.Calendar;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String TAG = "RAKSHIT";
    public AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;
    Button bananaButton, cancel;
    TextView notificationCount;
    TextView notificationCountLabel;
    TextView dateValue;
    int mNotificationCount;
    static final String NOTIFICATION_COUNT = "notificationCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            mNotificationCount = savedInstanceState.getInt(NOTIFICATION_COUNT);
        }

        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();
        System.out.println("Onclick Notofivarion =&gt; " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate1 = df.format(c.getTime());
        //Toast.makeText(this, formattedDate1, Toast.LENGTH_SHORT).show();

        Log.e(TAG, "Notification Open Time : " + formattedDate1);

        bananaButton = (Button) findViewById(R.id.bananaButton);
        cancel = (Button) findViewById(R.id.cancel);
        dateValue = (TextView) findViewById(R.id.dateValue);
        notificationCount = (TextView) findViewById(R.id.notificationCount);
        notificationCountLabel = (TextView) findViewById(R.id.notificationCountLabel);

    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putInt(NOTIFICATION_COUNT, mNotificationCount);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        /*Log.i(TAG, "onNewIntent(), intent = " + intent);

        //dateValue.setText(intent.getExtras().getShort("test"));

        if (intent.getExtras() != null) {
            Log.i(TAG, "in onNewIntent = " + intent.getExtras().getString("test"));
        }*/
        super.onNewIntent(intent);
        setIntent(intent);
    }


    public void triggerAlarm(View v) {
        setAlarm();
        bananaButton.setVisibility(View.GONE);
        notificationCountLabel.setVisibility(View.VISIBLE);
        notificationCount.setVisibility(View.VISIBLE);
        notificationCount.setText("0");
    }

    public void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

        Calendar alarmStartTime = Calendar.getInstance();
        alarmStartTime.add(Calendar.MINUTE, 1);
        alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);
        Log.i(TAG, "Alarms set every two minutes.");

    }

    private int getInterval() {
        int seconds = 60;
        int milliseconds = 1000;
        int repeatMS = seconds * 1 * milliseconds;
        return repeatMS;
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI();
    }

    public void cancelNotifications(View v) {

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        ComponentName receiver = new ComponentName(this, AlarmService.class);
        PackageManager pm = getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);

        bananaButton.setVisibility(View.GONE);

        Log.i(TAG, "All notifications cancelled.");
    }

    public void updateUI() {
        MyAlarm app = (MyAlarm) getApplicationContext();
        mNotificationCount = app.getNotificationCount();
        notificationCount.setText(Integer.toString(mNotificationCount));
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.getIntent().getExtras() != null) {
            Log.i(TAG, "extras: " + this.getIntent().getExtras());
            updateUI();

        }
    }

}
