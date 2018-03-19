package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class ProfileDetailStaff extends AppCompatActivity {

    private TextView btnstart,btnstop,btnpause;
    private TextView tvtime,tvpdst,tvtimee;
    long MillisecondTime, StartTime,  UpdateTime = 0L ;
   // Handler handler;
    int Seconds, Minutes, hours;
    private ImageView ivprofile;
    private TextView tvcompleted,tvqueue,tvname;
    private ProgressDialog pdLoading;
    private String StaffCode;
    private LinearLayout llnext,llpause,llstart,llqueue;
    long TimeBuff;
    int MilliSeconds;


 private    TextView tvt,tvmin,tvhours;
    final Handler handler = new Handler();
    int Count=00;
    int Value=00;
    int Hours=00;

    private TextView tvqueuee;

    private String Check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_staff);
    //    clearApplicationData();

       /* Intent intent=getIntent();
        StaffCode=intent.getStringExtra("staffcode");*/

        final SharedPreferences prefslogin =getSharedPreferences("l", MODE_PRIVATE);
        StaffCode = prefslogin.getString("staff", null);



        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdst=findViewById(R.id.pdst);
        tvpdst.setTypeface(typeface);

        ivprofile=findViewById(R.id.profile_imagestaff);
        tvcompleted=findViewById(R.id.tv_peoplestaff);
        tvqueue=findViewById(R.id.tv_peoplequeuestaff);
        tvname=findViewById(R.id.tv_namestaff);
        llnext=findViewById(R.id.llnext);
        llstart=findViewById(R.id.llstart);
        llpause=findViewById(R.id.llpause);
        llqueue=findViewById(R.id.llqueue);

        llqueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfileDetailStaff.this,QueueActivity.class);
                startActivity(intent);

            }
        });
        tvt=findViewById(R.id.tvt);
        tvhours=findViewById(R.id.tvthours);
        tvmin=findViewById(R.id.tvtminut);
        btnstart=findViewById(R.id.btn_start);
        btnpause=findViewById(R.id.btn_pause);
        btnstop=findViewById(R.id.btn_stop);
       /* tvtime=findViewById(R.id.tv_timestaff);
        tvtimee=findViewById(R.id.tv_timestafff);*/


        btnstop.setEnabled(false);
        btnpause.setEnabled(false);


        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Startt();

                SharedPreferences sharedPreferencesl = getApplicationContext().getSharedPreferences("check", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorl = sharedPreferencesl.edit();
                editorl.putString("ch","A");
                editorl.commit();

            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausee();
            }
        });

      btnstop.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              stopp();
          }
      });

        getData();

        llnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Next();
            }
        });
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub

       Intent in= new Intent(ProfileDetailStaff.this,MainActivity.class);
       startActivity(in);
       finish();

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

            handler.postDelayed(this, 1000);
        }
    };

    public void Startt(){

        handler.postDelayed(runnable, 0);

        Start();

        btnpause.setEnabled(true);
        btnstop.setEnabled(true);
     //   llpause.setBackgroundColor(getResources().getColor(R.color.dis));
        llstart.setBackground(getResources().getDrawable(R.drawable.enable));
        llpause.setBackground(getResources().getDrawable(R.drawable.disable));
        btnstart.setEnabled(false);

    }

    public void pausee(){

        handler.removeCallbacks(runnable);
        Pause();

        llpause.setBackground(getResources().getDrawable(R.drawable.enable));
        llstart.setBackground(getResources().getDrawable(R.drawable.disable));
        btnstart.setEnabled(true);

    }

    public void stopp(){
        Count=0;
        Hours=0;
        Value=0;
        Stop();

        SharedPreferences preferencesl =getSharedPreferences("Login", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorl = preferencesl.edit();
        editorl.clear();
        editorl.commit();

        Intent intent=new Intent(ProfileDetailStaff.this,LoginActivity.class);
        startActivity(intent);
        finish();


    }

    private void Stop() {


      //  tvtime.setText("00:00:00");

        String URL="http://devhitech.com/salon-ms/api/stop?staff_code="+StaffCode;


        Log.i("urll",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pppppppppp", response);
                      //  tvtime.setText("00:00:00");


                    //     Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //     Toast.makeText(LoginActivity.this, ""+error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put("staff_code",StaffId);
                params.put("password",Password);
                Log.i("params",""+params);*/
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailStaff.this);
        requestQueue.add(stringRequest);
    }

    private void Pause() {


        String URL="http://devhitech.com/salon-ms/api/pause?staff_code="+StaffCode;


        Log.i("urll",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pppppppppp", response);

                    //     Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //     Toast.makeText(LoginActivity.this, ""+error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put("staff_code",StaffId);
                params.put("password",Password);
                Log.i("params",""+params);*/
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailStaff.this);
        requestQueue.add(stringRequest);

    }

    private void Start() {

        String URL="http://devhitech.com/salon-ms/api/start?staff_code="+StaffCode;


        Log.i("urll",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pppppppppp", response);

                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //     Toast.makeText(LoginActivity.this, ""+error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailStaff.this);
        requestQueue.add(stringRequest);

    }

    private void Next() {


        pdLoading = new ProgressDialog(ProfileDetailStaff.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL="http://devhitech.com/salon-ms/api/status_update?staff_code="+StaffCode;
        Log.i("urlnext",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ppppppppppnext", response);
                        pdLoading.dismiss();
                        getData();
                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray=jsono.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Staffcode=object.getString("staff_code");
                                String Imagee=object.getString("pic");
                                String StaffNamee=object.getString("name");
                                String NCPp=object.getString("number of completed person");
                                String NPQq=object.getString("number of people in queue");

                                String LoginStatus=object.getString("login_status");
                                int Sec=object.getInt("time_sec");
                                int Min=object.getInt("time_min");
                                int Hourss=object.getInt("time_hour");


                                tvhours.setText(""+Hourss);
                                tvmin.setText(""+Min);
                                tvt.setText(""+Sec);


                                Log.i("TotalTime","hr:"+Hourss+"  Minuts:"+Min+"  Sec:"+Sec);

                                if (LoginStatus.equals("1")){
                                    Count=Sec;
                                    Value=Min;
                                    Hours=Hourss;



                                    handler.postDelayed(runnable, 0);
                                    btnpause.setEnabled(true);
                                    btnstop.setEnabled(true);
                                    llstart.setBackground(getResources().getDrawable(R.drawable.enable));
                                    llpause.setBackground(getResources().getDrawable(R.drawable.disable));
                                    btnstart.setEnabled(false);
                                }else if (LoginStatus.equals("0")){

                                    Count=Sec;
                                    Value=Min;
                                    Hours=Hourss;


                                    handler.removeCallbacks(runnable);

                                    if (TextUtils.isEmpty(Check)){
                                        llpause.setBackground(getResources().getDrawable(R.drawable.disable));
                                    }else {
                                        llpause.setBackground(getResources().getDrawable(R.drawable.enable));
                                    }

                                    llstart.setBackground(getResources().getDrawable(R.drawable.disable));
                                    btnstart.setEnabled(true);
                                }

                                tvname.setText(StaffNamee);
                                tvcompleted.setText(NCPp);
                                tvqueue.setText(NPQq);
                                Glide.with(ProfileDetailStaff.this).load(Imagee).into(ivprofile);

                                Log.i("Datapro","image"+Imagee+"NAme"+StaffNamee+"Ncp"+NCPp+"Npq"+NPQq);

                            }


                        }catch (Exception e){

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pdLoading.dismiss();
                        //     Toast.makeText(LoginActivity.this, ""+error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put("staff_code",StaffId);
                params.put("password",Password);
                Log.i("params",""+params);*/
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailStaff.this);
        requestQueue.add(stringRequest);


    }

    private void getData() {

        final SharedPreferences prefslogin =getSharedPreferences("check", MODE_PRIVATE);
        Check = prefslogin.getString("ch", null);


        pdLoading = new ProgressDialog(ProfileDetailStaff.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL="http://devhitech.com/salon-ms/api/profile_details?staff_code="+StaffCode;


        Log.i("urll",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pppppppppp", response);
                        pdLoading.dismiss();
                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray=jsono.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Staffcode=object.getString("staff_code");
                                String Image=object.getString("pic");
                                String StaffName=object.getString("name");
                                String NCP=object.getString("number of completed person");
                                String NPQ=object.getString("number of people in queue");

                                String LoginStatus=object.getString("login_status");
                                int Sec=object.getInt("time_sec");
                                int Min=object.getInt("time_min");
                                int Hourss=object.getInt("time_hour");


                                tvhours.setText(""+Hourss);
                                tvmin.setText(""+Min);
                                tvt.setText(""+Sec);


                                Log.i("TotalTime","hr:"+Hourss+"  Minuts:"+Min+"  Sec:"+Sec);

                                if (LoginStatus.equals("1")){
                                    Count=Sec;
                                    Value=Min;
                                    Hours=Hourss;



                                    handler.postDelayed(runnable, 0);
                                    btnpause.setEnabled(true);
                                    btnstop.setEnabled(true);
                                    llstart.setBackground(getResources().getDrawable(R.drawable.enable));
                                    llpause.setBackground(getResources().getDrawable(R.drawable.disable));
                                    btnstart.setEnabled(false);
                                }else if (LoginStatus.equals("0")){

                                    Count=Sec;
                                    Value=Min;
                                    Hours=Hourss;


                                    handler.removeCallbacks(runnable);

                                    if (TextUtils.isEmpty(Check)){
                                        llpause.setBackground(getResources().getDrawable(R.drawable.disable));
                                    }else {
                                        llpause.setBackground(getResources().getDrawable(R.drawable.enable));
                                    }

                                    llstart.setBackground(getResources().getDrawable(R.drawable.disable));
                                    btnstart.setEnabled(true);
                                }




                                tvname.setText(StaffName);
                                tvcompleted.setText(NCP);
                                tvqueue.setText(NPQ);
                                Glide.with(ProfileDetailStaff.this).load(Image).into(ivprofile);


                                Log.i("Datapro","image"+Image+"NAme"+StaffName+"Ncp"+NCP+"Npq"+NPQ);



                            }
                        }catch (Exception e){
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //     Toast.makeText(LoginActivity.this, ""+error.toString(), Toast.LENGTH_LONG).show();
                        pdLoading.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
               /* params.put("staff_code",StaffId);
                params.put("password",Password);
                Log.i("params",""+params);*/
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                80000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailStaff.this);
        requestQueue.add(stringRequest);

    }



}
