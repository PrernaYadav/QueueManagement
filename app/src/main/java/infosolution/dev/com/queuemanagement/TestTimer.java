/*
package infosolution.dev.com.queuemanagement;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestTimer extends AppCompatActivity {

    TextView tvt,tvmin,tvhours;
    final Handler handler = new Handler();
    int Count=55;
    int Value=59;
    int Hours=00;
    Button btnpausee,btnstart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_timer);





        tvt=findViewById(R.id.tvt);
        btnpausee=findViewById(R.id.btn_Pauseee);
        tvhours=findViewById(R.id.tvthours);
        tvmin=findViewById(R.id.tvtminut);
        btnstart=findViewById(R.id.btn_starttt);



        btnpausee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeCallbacks(runnable);
            }
        });

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                handler.postDelayed(runnable, 0);


            }
        });
    }

    final   Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Count++;
            tvt.setText(""+Count);

            if (Count == 59){
                Count=0;
                Value++;
                tvmin.setText(""+Value);

                if (Value == 60){

                    Value=00;
                    tvmin.setText(""+Value);
                    Hours++;
                    tvhours.setText(""+Hours);
                }

            }

 if (Count % 60 == 0){

                 Value=Value + Count/60;

                tvmin.setText(""+Value);



                Count=0;
            }

            handler.postDelayed(this, 1000);
        }
    };




}
*/
