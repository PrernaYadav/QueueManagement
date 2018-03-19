package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import infosolution.dev.com.queuemanagement.adapter.HairDresserAdapter;
import infosolution.dev.com.queuemanagement.adapter.TransferToAdapter;
import infosolution.dev.com.queuemanagement.model.HairModel;

public class TransferToActivity extends AppCompatActivity {

    private TextView tvchoose;
    RecyclerView rcview;
    TransferToAdapter hairDresserAdapter;
    private ArrayList<HairModel> hairModelArrayList;
    private View view;
    private ProgressDialog pdLoading;
    private String WorkingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_to);

        final SharedPreferences ct = getSharedPreferences("WorkingId", MODE_PRIVATE);
        WorkingId = ct.getString("id", null);


        view = findViewById(R.id.actionb);
        TextView tv = findViewById(R.id.tv);
        ImageView iv = findViewById(R.id.back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        tvchoose = findViewById(R.id.tv_trasnsss);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tvchoose.setTypeface(typeface);
        tv.setTypeface(typeface);

        rcview = findViewById(R.id.rvlistt);
        rcview.setLayoutManager(new LinearLayoutManager(this));
        rcview.setHasFixedSize(true);
        rcview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        rcview.setNestedScrollingEnabled(false);


      /*  DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));*/

        int numberOfColumns = 3;
        rcview.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        rcview.setAdapter(hairDresserAdapter);
        hairModelArrayList = new ArrayList<>();
        hairDresserAdapter = new TransferToAdapter(hairModelArrayList, this, this);

        GetList();
    }

    private void GetList() {

        pdLoading = new ProgressDialog(TransferToActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/staff/action/get/staff-list?working_id="+WorkingId;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                        //   Toast.makeText(HairDresserActivity.this, "responce"+response.toString(), Toast.LENGTH_SHORT).show();


                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Staffcode = object.getString("staff_code");
                                String Imagee = object.getString("staff_pic");
                                String StaffNameeFirst = object.getString("staff_fname");
                                String StaffNameelast= object.getString("staff_lname");
                             //   String NPQq = object.getString("number of people in queue");

                            //    Log.i("Datalist", "image" + Imagee + "NAme" + StaffNamee + "Ncp" + Staffcode + "Npq" + NPQq);


                                HairModel hairModel = new HairModel();
                                hairModel.setId(Staffcode);
                                hairModel.setName(StaffNameeFirst+" "+StaffNameelast);
                             //   hairModel.setWaiting(NPQq);
                                hairModel.setImage(Imagee);

                                hairModelArrayList.add(hairModel);

                            }
                            rcview.setAdapter(hairDresserAdapter);
                            hairDresserAdapter.notifyDataSetChanged();


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
        RequestQueue requestQueue = Volley.newRequestQueue(TransferToActivity.this);
        requestQueue.add(stringRequest);
    }
}
