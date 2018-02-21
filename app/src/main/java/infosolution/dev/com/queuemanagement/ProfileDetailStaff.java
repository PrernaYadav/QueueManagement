package infosolution.dev.com.queuemanagement;

import android.graphics.Typeface;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileDetailStaff extends AppCompatActivity {

    private TextView btnstart,btnstop,btnpause;
    private TextView tvtime,tvpdst;
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds,hours;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_staff);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdst=findViewById(R.id.pdst);
        tvpdst.setTypeface(typeface);


        btnstart=findViewById(R.id.btn_start);
        btnpause=findViewById(R.id.btn_pause);
        btnstop=findViewById(R.id.btn_stop);
        tvtime=findViewById(R.id.tv_timestaff);

        handler = new Handler() ;
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                btnstop.setEnabled(false);
            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
                btnstop.setEnabled(true);
            }
        });

        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String time=tvtime.getText().toString();
                Log.i("time",""+time);

                MillisecondTime = 0L ;
                StartTime = 0L ;
                TimeBuff = 0L ;
                UpdateTime = 0L ;
                Seconds = 0 ;
                Minutes = 0 ;
                hours = 0 ;
                MilliSeconds = 0 ;

                tvtime.setText("00:00:00");
            }
        });
    }
    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = SystemClock.uptimeMillis() - StartTime;

            UpdateTime = TimeBuff + MillisecondTime;

            Seconds = (int) (UpdateTime / 1000);

            Minutes = Seconds / 60;

            hours = Minutes / 60;

            Seconds = Seconds % 60;

            MilliSeconds = (int) (UpdateTime % 1000);

            tvtime.setText("" +hours +":" + Minutes + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };
}
