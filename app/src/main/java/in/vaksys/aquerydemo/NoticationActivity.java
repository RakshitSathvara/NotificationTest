package in.vaksys.aquerydemo;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NoticationActivity extends Activity {

    String value;
    String formattedDate;
    SimpleDateFormat df;


    private static final String TAG = "RAKSHIT";
    public AlarmManager alarmManager;
    Intent alarmIntent;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notication);

        Calendar c = Calendar.getInstance();
        df = new SimpleDateFormat("HH:mm:ss");
        formattedDate = df.format(c.getTime());
        Log.e("Rakshit", "Notifivation Open time : " + formattedDate);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("comeDate");
            Log.e("Rakshit", "Date in Notification: " + value);
        }

        Date d1, d2;

        try {
            d1 = df.parse(formattedDate);
            d2 = df.parse(value);

            long diff = d1.getTime() - d2.getTime();
            Log.e("Rakshit", "Time Difference : " + diff);


            if (diff > 10000) {
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                alarmIntent = new Intent(NoticationActivity.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(NoticationActivity.this, 0, alarmIntent, 0);

                Calendar alarmStartTime = Calendar.getInstance();
                alarmStartTime.add(Calendar.MINUTE, 1);
                alarmManager.setRepeating(AlarmManager.RTC, alarmStartTime.getTimeInMillis(), getInterval(), pendingIntent);
                Log.i(TAG, "Alarms set every two minutes.");
            } else {
                if (alarmManager != null) {
                    alarmManager.cancel(pendingIntent);
                }

                ComponentName receiver = new ComponentName(this, AlarmService.class);
                PackageManager pm = getPackageManager();

                pm.setComponentEnabledSetting(receiver,
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void viewTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        Log.e("Rakshit", "Notifivation Open time : " + formattedDate);
        Toast.makeText(this, "Notifivation Open time : " + formattedDate, Toast.LENGTH_SHORT).show();
    }

    private int getInterval() {
        int seconds = 60;
        int milliseconds = 1000;
        int repeatMS = seconds * 1 * milliseconds;
        return repeatMS;
    }
}
