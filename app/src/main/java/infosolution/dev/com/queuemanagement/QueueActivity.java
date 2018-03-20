package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import infosolution.dev.com.queuemanagement.adapter.QueueAdapter;
import infosolution.dev.com.queuemanagement.model.HairModel;
import infosolution.dev.com.queuemanagement.model.QueueModel;

public class QueueActivity extends AppCompatActivity {

    private RecyclerView rcview;
  private   QueueAdapter queueAdapter;
    private ArrayList<QueueModel> queueModelArrayList;
    private View view;
    private ProgressDialog pdLoading;
    private String StaffCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        final SharedPreferences prefslogin =getSharedPreferences("l", MODE_PRIVATE);
        StaffCode = prefslogin.getString("staff", null);

        view = findViewById(R.id.actionqueue);
        TextView tv = findViewById(R.id.tvqueuee);
        ImageView iv = findViewById(R.id.backqueue);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tv.setTypeface(typeface);


        rcview = findViewById(R.id.rv_queue);
       /* rcview.setLayoutManager(new LinearLayoutManager(this));
        rcview.setHasFixedSize(true);
        rcview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcview.setNestedScrollingEnabled(false);*/

        int numberOfColumns = 2;
        rcview.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        rcview.setAdapter(queueAdapter);
        queueModelArrayList = new ArrayList<>();
        queueAdapter = new QueueAdapter(queueModelArrayList, this, this);

        GetTokenList();
    }

    private void GetTokenList() {

        pdLoading = new ProgressDialog(QueueActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/staff/action/get/token-list?staff_code=" +StaffCode ;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                     //      Toast.makeText(QueueActivity.this, "responce"+response.toString(), Toast.LENGTH_SHORT).show();


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



                                StringTokenizer tkk = new StringTokenizer(Date);
                                String datee = tkk.nextToken();
                                String timee = tkk.nextToken();



                                StringTokenizer tk = new StringTokenizer(TransferedAt);
                                String date = tk.nextToken();
                                String time = tk.nextToken();



                                QueueModel queueModel= new QueueModel();
                                queueModel.setQueueId(WorkingId);
                                queueModel.setTokenQueue(Token);
                                queueModel.setDate(timee);

                                if (isTransfered.equals("0")){
                                   // queueModel.setTransferBy("None");
                                }else {
                                    queueModel.setTransferBy("By : "+TransferedBy);
                                    queueModel.setTransferedAt("Transfered At :"+time);
                                }
                                queueModelArrayList.add(queueModel);
                            }
                            rcview.setAdapter(queueAdapter);
                            queueAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(QueueActivity.this);
        requestQueue.add(stringRequest);




    }
}
