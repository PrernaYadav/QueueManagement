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
    long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L ;
    Handler handler;
    int Seconds, Minutes, MilliSeconds,hours;
    private ImageView ivprofile;
    private TextView tvcompleted,tvqueue,tvname;
    private ProgressDialog pdLoading;
    private String StaffCode;
    private LinearLayout llnext,llpause,llstart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_staff);
        clearApplicationData();

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




        btnstart=findViewById(R.id.btn_start);
        btnpause=findViewById(R.id.btn_pause);
        btnstop=findViewById(R.id.btn_stop);
        tvtime=findViewById(R.id.tv_timestaff);
        tvtimee=findViewById(R.id.tv_timestafff);


        btnstop.setEnabled(false);
        btnpause.setEnabled(false);


        handler = new Handler() ;
        btnstart.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                Start();
                btnpause.setEnabled(true);
                btnstop.setEnabled(true);
              /*  llstart.setBackgroundColor(getResources().getColor(R.color.start));
                llpause.setBackgroundColor(getResources().getColor(R.color.dis));*/
                llstart.setBackground(getResources().getDrawable(R.drawable.enable));
                llpause.setBackground(getResources().getDrawable(R.drawable.disable));
                btnstart.setEnabled(false);


            }
        });

        btnpause.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                TimeBuff += MillisecondTime;
                handler.removeCallbacks(runnable);
             //   btnstop.setEnabled(true);
                Pause();
                /*llstart.setBackgroundColor(getResources().getColor(R.color.dis));
                llpause.setBackgroundColor(getResources().getColor(R.color.start));*/

                llpause.setBackground(getResources().getDrawable(R.drawable.enable));
                llstart.setBackground(getResources().getDrawable(R.drawable.disable));
                btnstart.setEnabled(true);
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

             //   tvtime.setText("00:00:00");

                Stop();
                tvtime.setText("00:00:00");
                tvtime.setVisibility(View.GONE);
                tvtimee.setVisibility(View.VISIBLE);

                SharedPreferences preferencesl =getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorl = preferencesl.edit();
                editorl.clear();
                editorl.commit();

                Intent intent=new Intent(ProfileDetailStaff.this,LoginActivity.class);
                startActivity(intent);
                finish();
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

            Log.i("ahskjah","" +hours +":" + Minutes + ":"
                    + String.format("%02d", Seconds));

            handler.postDelayed(this, 0);
        }

    };
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


    public void clearApplicationData()
    {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir)
    {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }
}
