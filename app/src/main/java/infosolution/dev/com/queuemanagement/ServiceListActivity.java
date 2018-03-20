package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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

import infosolution.dev.com.queuemanagement.adapter.ServiceAdapter;
import infosolution.dev.com.queuemanagement.adapter.ServiceListAdapter;
import infosolution.dev.com.queuemanagement.model.ServiceListModel;
import infosolution.dev.com.queuemanagement.model.ServiceModel;

public class ServiceListActivity extends AppCompatActivity {
    private View view;
    private ProgressDialog pdLoading;
    private RecyclerView rcview;
    private ServiceListAdapter serviceListAdapter;
    private ArrayList<ServiceListModel> serviceModelArrayList;
    private String StoreID,CategoryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_list);
        final SharedPreferences prefslogin =getSharedPreferences("l", MODE_PRIVATE);
        StoreID = prefslogin.getString("storeid", null);

        Intent intent=getIntent();
        CategoryID=intent.getStringExtra("CatId");

        view = findViewById(R.id.actionser);
        TextView tv = findViewById(R.id.tvser);
        ImageView iv = findViewById(R.id.backser);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tv.setTypeface(typeface);

        rcview = findViewById(R.id.rv_serviceList);
        int numberOfColumns = 2;
        rcview.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        rcview.setAdapter(serviceListAdapter);
        serviceModelArrayList = new ArrayList<>();
        serviceListAdapter = new ServiceListAdapter(serviceModelArrayList, this, this);
        GetCategoryList();
    }

    private void GetCategoryList() {

        pdLoading = new ProgressDialog(ServiceListActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://devhitech.com/salon-ms/api/staff/action/get/service-list?store_id="+StoreID+"&category_id="+CategoryID ;

        Log.i("storeid",""+URL);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                        //  Toast.makeText(ServiceListActivity.this, "responce"+response.toString(), Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String CatID = object.getString("id");
                                String SerName = object.getString("service_name");
                                String SerImage = object.getString("service_img_add");
                                String SerPrice= object.getString("service_price");



                                ServiceListModel serviceModel= new ServiceListModel();
                                serviceModel.setName(SerName);
                                serviceModel.setId(CatID);
                                serviceModel.setImage(SerImage);
                                serviceModel.setPrice("Price :"+SerPrice);
                                serviceModelArrayList.add(serviceModel);

                            }
                            rcview.setAdapter(serviceListAdapter);
                            serviceListAdapter.notifyDataSetChanged();

                        }catch (Exception e){


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
        RequestQueue requestQueue = Volley.newRequestQueue(ServiceListActivity.this);
        requestQueue.add(stringRequest);
    }
}
