package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class ProfileDetailforVisitor extends AppCompatActivity {
    private Button btnback,btnselect;
    private TextView tvpdvi;
    private ImageView imageView;
    private TextView tvname,tvqueue,tvtime;
    private ProgressDialog pdLoading;
    private String Staffcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detailfor_visitor);
        imageView=findViewById(R.id.profile_imagevisi);
        tvname=findViewById(R.id.tv_namevi);
        tvqueue=findViewById(R.id.tv_peoplevi);
        tvtime=findViewById(R.id.tv_timevi);
        btnselect=findViewById(R.id.btn_joinnow);

        Intent intent=getIntent();
        Staffcode=intent.getStringExtra("id");




        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdvi=findViewById(R.id.tv_pdvi);
        tvpdvi.setTypeface(typeface);

        btnback=findViewById(R.id.btn_backvi);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetTokan();
            }
        });

        GetData();
    }

    private void GetTokan() {

        pdLoading = new ProgressDialog(ProfileDetailforVisitor.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL="http://devhitech.com/salon-ms/api/token_generate?staff_code="+Staffcode;


        Log.i("urlltoken",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ppppppt", response);
                        pdLoading.dismiss();
                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray=jsono.getJSONArray("data");
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Staffcode=object.getString("staff_code");
                                String TokenNO=object.getString("token_no");
                                String Date=object.getString("date");
                                String Name=object.getString("staff_name");


                                Intent intent=new Intent(ProfileDetailforVisitor.this,TokenActivity.class);
                                intent.putExtra("token",TokenNO);
                                intent.putExtra("date",Date);
                                intent.putExtra("Name",Name);
                                startActivity(intent);


                                Log.i("token",""+Staffcode+""+TokenNO+""+Date);


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
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailforVisitor.this);
        requestQueue.add(stringRequest);



    }

    private void GetData() {

        pdLoading = new ProgressDialog(ProfileDetailforVisitor.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL="http://devhitech.com/salon-ms/api/profile_details?staff_code="+Staffcode;


        Log.i("urlltoken",URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pppppp", response);
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
                                String Time=object.getString("waiting_time");

                                tvname.setText(StaffName);
                                tvqueue.setText(NPQ);
                                tvtime.setText(Time);
                                Glide.with(ProfileDetailforVisitor.this).load(Image).into(imageView);
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
        RequestQueue requestQueue = Volley.newRequestQueue(ProfileDetailforVisitor.this);
        requestQueue.add(stringRequest);


    }
}
