package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import infosolution.dev.com.queuemanagement.model.QueueModel;

public class CurrentTokenActivity extends AppCompatActivity {
    private View view;

    private TextView tvtoken,tvcurrdate,tvtransferedby,tvtransferedat;
    private LinearLayout ll;
    private ProgressDialog pdLoading;
    private String StaffCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_token);

        final SharedPreferences prefslogin =getSharedPreferences("l", MODE_PRIVATE);
        StaffCode = prefslogin.getString("staff", null);


        tvtoken=findViewById(R.id.tv_tokencurr);
        tvcurrdate=findViewById(R.id.tv_currdate);
        tvtransferedby=findViewById(R.id.tv_transferbycurr);
        tvtransferedat=findViewById(R.id.tv_transferatcurr);
        ll=findViewById(R.id.llcurrr);

        view = findViewById(R.id.action_curr);
        TextView tv = findViewById(R.id.tv_curr);
        ImageView iv = findViewById(R.id.backcurr);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tv.setTypeface(typeface);
        tvtoken.setTypeface(typeface);
        GetData();
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent= new Intent(CurrentTokenActivity.this,ServiceActivity.class);
                startActivity(intent);

            }
        });



    }

    private void GetData() {
        pdLoading = new ProgressDialog(CurrentTokenActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/staff/action/get/current-token?staff_code=" +StaffCode ;

        Log.i("urll",URL);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                          //    Toast.makeText(CurrentTokenActivity.this, "responce"+response.toString(), Toast.LENGTH_SHORT).show();


                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String WorkingId = object.getString("id");
                                String Token = object.getString("token_no");
                                String Date = object.getString("added_date");
                                String TransferedBy = object.getString("transferred_by");
                                String isTransfered = object.getString("is_transferred");
                                String TransferedAt = object.getString("transferred_at");

                                SharedPreferences sharedPreferencesl = getApplicationContext().getSharedPreferences("WorkingId", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editorl = sharedPreferencesl.edit();
                                editorl.putString("id",WorkingId);
                                editorl.commit();



                                StringTokenizer tkk = new StringTokenizer(Date);
                                String datee = tkk.nextToken();
                                String timee = tkk.nextToken();



                                StringTokenizer tk = new StringTokenizer(TransferedAt);
                                String date = tk.nextToken();
                                String time = tk.nextToken();

                                tvtoken.setText(Token);
                                tvcurrdate.setText(timee);
                                if (isTransfered.equals("0")){
                                    // queueModel.setTransferBy("None");
                                }else {
                                    tvtransferedby.setText("Transfered By : "+TransferedBy);
                                    tvtransferedat.setText("Transfered At : "+time);
                                }



                            }

                        } catch (Exception e) {

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        pdLoading.dismiss();
                        Log.d("error", "" + error.toString());
                        Toast.makeText(getApplicationContext(), "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                // params.put("user_id", userid);


                Log.i("parameters", "" + params);
                return params;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue requestQueue = Volley.newRequestQueue(CurrentTokenActivity.this);
        requestQueue.add(stringRequest);

    }
}
