package infosolution.dev.com.queuemanagement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import infosolution.dev.com.queuemanagement.printing.DeviceListActivity;
import infosolution.dev.com.queuemanagement.printing.UnicodeFormatter;

public class ProfileDetailforVisitor extends Activity  {
    private Button btnback, btnselect;
    private TextView tvpdvi;
    private ImageView imageView;
    private TextView tvname, tvqueue, tvtime;
    private ProgressDialog pdLoading;
    private String Staffcode;
    private View view;
    private ImageView ivconnect;

    private String Staffcodee, TokenNO, Date, Name;


    //============================================================
    //for  printer
    protected static final String TAG = "TAG";
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;
    Button mScan, mPrint, mDisc;
    BluetoothAdapter mBluetoothAdapter;
    private UUID applicationUUID = UUID
            .fromString("00001101-0000-1000-8000-00805F9B34FB");
    private ProgressDialog mBluetoothConnectProgressDialog;
    private BluetoothSocket mBluetoothSocket;
    BluetoothDevice mBluetoothDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detailfor_visitor);
        imageView = findViewById(R.id.profile_imagevisi);
        tvname = findViewById(R.id.tv_namevi);
        tvqueue = findViewById(R.id.tv_peoplevi);
        tvtime = findViewById(R.id.tv_timevi);
        btnselect = findViewById(R.id.btn_joinnow);

        Intent intent = getIntent();
        Staffcode = intent.getStringExtra("id");


        view = findViewById(R.id.actionprint);
        TextView tv = findViewById(R.id.tvT);
        ImageView iv = findViewById(R.id.backT);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });

      /*  ivconnect = findViewById(R.id.ivprintt);
        ivconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (mBluetoothAdapter == null) {
                    Toast.makeText(ProfileDetailforVisitor.this, "Message1", Toast.LENGTH_SHORT).show();
                } else {
                    if (!mBluetoothAdapter.isEnabled()) {
                        Intent enableBtIntent = new Intent(
                                BluetoothAdapter.ACTION_REQUEST_ENABLE);
                        startActivityForResult(enableBtIntent,
                                REQUEST_ENABLE_BT);
                    } else {
                        ListPairedDevices();
                        Intent connectIntent = new Intent(ProfileDetailforVisitor.this,
                                DeviceListActivity.class);
                        startActivityForResult(connectIntent,
                                REQUEST_CONNECT_DEVICE);
                    }
                }
            }
        });*/


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdvi = findViewById(R.id.tv_pdvi);
        tvpdvi.setTypeface(typeface);
        tv.setTypeface(typeface);

        btnback = findViewById(R.id.btn_backvi);
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


    @Override
    public void onRestart() {
        super.onRestart();
        // put your code here...

        GetData();

    }

    private void GetTokan() {

        pdLoading = new ProgressDialog(ProfileDetailforVisitor.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://devhitech.com/salon-ms/api/token_generate?staff_code=" + Staffcode;


        Log.i("urlltoken", URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("ppppppt", response);
                        pdLoading.dismiss();
                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Staffcodee = object.getString("staff_code");
                                TokenNO = object.getString("token_no");
                                Date = object.getString("date");
                                Name = object.getString("staff_name");


                                Intent intent=new Intent(ProfileDetailforVisitor.this,TokenActivity.class);
                                intent.putExtra("token",TokenNO);
                                intent.putExtra("date",Date);
                                intent.putExtra("Name",Name);
                                startActivity(intent);
                                finish();


                                Log.i("token", "" + Staffcode + "" + TokenNO + "" + Date + " " + Name);


                            }
                        } catch (Exception e) {
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

        String URL = "http://devhitech.com/salon-ms/api/profile_details?staff_code=" + Staffcode;


        Log.i("urlltoken", URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("pppppp", response);
                        pdLoading.dismiss();
                        // Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Staffcode = object.getString("staff_code");
                                String Image = object.getString("pic");
                                String StaffName = object.getString("name");
                                String NCP = object.getString("number of completed person");
                                String NPQ = object.getString("number of people in queue");
                                String Time = object.getString("waiting_time");

                                tvname.setText(StaffName);
                                tvqueue.setText(NPQ);
                                tvtime.setText(Time);
                                Glide.with(ProfileDetailforVisitor.this).load(Image).into(imageView);
                                Log.i("Datapro", "image" + Image + "NAme" + StaffName + "Ncp" + NCP + "Npq" + NPQ);
                            }
                        } catch (Exception e) {
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


    }}





