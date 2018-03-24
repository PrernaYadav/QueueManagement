package infosolution.dev.com.queuemanagement;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import infosolution.dev.com.queuemanagement.adapter.HairDresserAdapter;
import infosolution.dev.com.queuemanagement.model.HairModel;
import infosolution.dev.com.queuemanagement.model.RecyclerItemClickListener;

import static java.security.AccessController.getContext;

public class HairDresserActivity extends AppCompatActivity {
    private TextView tvchoose;
    RecyclerView rcview;
    HairDresserAdapter hairDresserAdapter;
    private ArrayList<HairModel> hairModelArrayList;
    private View view;
    private LinearLayout lltemp;
    private ProgressDialog pdLoading;
    private String Storeid, im;
    final Handler handler = new Handler();
    private ImageView ivad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_dresser);


        final SharedPreferences prefsfb = getSharedPreferences("l", MODE_PRIVATE);
        Storeid = prefsfb.getString("store", null);

        lltemp = findViewById(R.id.temp);
        ivad=findViewById(R.id.iv_ad);

        view = findViewById(R.id.action);
        TextView tv = findViewById(R.id.tv);
        ImageView iv = findViewById(R.id.back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvchoose = findViewById(R.id.tv_choose);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tvchoose.setTypeface(typeface);
        tv.setTypeface(typeface);

        rcview = findViewById(R.id.rc);


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
        hairDresserAdapter = new HairDresserAdapter(hairModelArrayList, this, this);

        GetList();

        Bitmap bmap = BitmapFactory.decodeResource(getResources(), R.drawable.profilebg);
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bmap.compress(Bitmap.CompressFormat.JPEG, 100, bao);
        byte[] ba = bao.toByteArray();
        im = Base64.encodeToString(ba, Base64.DEFAULT);


        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                GetListt();
                handler.postDelayed(this, 5000);
            }
        };

//Start
        handler.postDelayed(runnable, 5000);


    }


   /* @Override
    public void onRestart() {
        super.onRestart();
        // put your code here...

        //    Toast.makeText(this, "restart", Toast.LENGTH_SHORT).show();
        hairModelArrayList.clear();
        GetList();

    }*/

    private void GetList() {
      //  hairModelArrayList.clear();
        // clearApplicationData();

        pdLoading = new ProgressDialog(HairDresserActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/profile_list?store_id=" + Storeid;


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
                                String Imagee = object.getString("pic");
                                String StaffNamee = object.getString("name");
                                String NPQq = object.getString("number of people in queue");

                                Log.i("Datalist", "image" + Imagee + "NAme" + StaffNamee + "Ncp" + Staffcode + "Npq" + NPQq);


                                HairModel hairModel = new HairModel();
                                hairModel.setId(Staffcode);
                                hairModel.setName(StaffNamee);
                                hairModel.setWaiting("("+NPQq+")");
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
        RequestQueue requestQueue = Volley.newRequestQueue(HairDresserActivity.this);
        requestQueue.add(stringRequest);


    }

    private void GetListt() {



        String URL = "http://www.devhitech.com/salon-ms/api/profile_list?store_id=" + Storeid;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        hairModelArrayList.clear();
                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                        //   Toast.makeText(HairDresserActivity.this, "responce"+response.toString(), Toast.LENGTH_SHORT).show();


                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Staffcode = object.getString("staff_code");
                                String Imagee = object.getString("pic");
                                String StaffNamee = object.getString("name");
                                String NPQq = object.getString("number of people in queue");

                                Log.i("Datalist", "image" + Imagee + "NAme" + StaffNamee + "Ncp" + Staffcode + "Npq" + NPQq);


                                HairModel hairModel = new HairModel();
                                hairModel.setId(Staffcode);
                                hairModel.setName(StaffNamee);
                                hairModel.setWaiting("("+NPQq+")");
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
        RequestQueue requestQueue = Volley.newRequestQueue(HairDresserActivity.this);
        requestQueue.add(stringRequest);


    }


}
