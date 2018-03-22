package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.ServiceActivity;
import infosolution.dev.com.queuemanagement.ServiceListActivity;
import infosolution.dev.com.queuemanagement.model.ServiceListModel;
import infosolution.dev.com.queuemanagement.model.ServiceModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Shreyansh Srivastava on 3/20/2018.
 */

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceListHOlder> {
    private ArrayList<ServiceListModel> serviceModelArrayList;
    Context context;
    private Activity activityy;
    private String WorkingID,ServiceID;
    private ProgressDialog pdLoading;

    public ServiceListAdapter(ArrayList<ServiceListModel> serviceModelArrayList, Context context, Activity activityy) {
        this.serviceModelArrayList = serviceModelArrayList;
        this.context = context;
        this.activityy = activityy;
    }

    @Override
    public ServiceListAdapter.ServiceListHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.services_row, parent, false);
        return new ServiceListAdapter.ServiceListHOlder(itemView, context, serviceModelArrayList);
    }

    @Override
    public void onBindViewHolder(ServiceListAdapter.ServiceListHOlder holder, int position) {
        final ServiceListModel serviceListModel= serviceModelArrayList.get(position);

        holder.tvname.setText(serviceModelArrayList.get(position).getName());
        holder.tvid.setText(serviceModelArrayList.get(position).getId());
        holder.tvprice.setText(serviceModelArrayList.get(position).getPrice());
        //    holder.ivimg.setImageResource(serviceModelArrayList.get(position).getImg());
        Glide.with(activityy).load(serviceListModel.getImage()).into(holder.ivimg);

    }

    @Override
    public int getItemCount() {
        if (serviceModelArrayList == null)
            return 0;
        return serviceModelArrayList.size();

    }

    public class ServiceListHOlder extends RecyclerView.ViewHolder {

        ImageView ivimg;
        TextView tvname,tvid,tvprice;

        public ServiceListHOlder(View itemView, Context context, final ArrayList<ServiceListModel> serviceModelArrayList) {
            super(itemView);
            ivimg=itemView.findViewById(R.id.iv_serimg);
            tvname=itemView.findViewById(R.id.tv_servicename);
            tvid=itemView.findViewById(R.id.tvserid);
            tvprice=itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Pos=getAdapterPosition();
                    ServiceID=serviceModelArrayList.get(Pos).getId();
                    SelectService();
                }
            });

        }
    }

    private void SelectService() {
        final SharedPreferences prefslogin =activityy.getSharedPreferences("WorkingId", MODE_PRIVATE);
        WorkingID = prefslogin.getString("id", null);

        pdLoading = new ProgressDialog(activityy);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://devhitech.com/salon-ms/api/staff/action/add/service/?working_id="+WorkingID+"&service_id="+ServiceID;

        Log.i("serviceurl",""+URL);


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                        //  Toast.makeText(activityy, "responce"+response.toString(), Toast.LENGTH_SHORT).show();

                        Toast.makeText(activityy, "Service SuccessFully Added for This Token", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activityy, ServiceActivity.class);
                        activityy.startActivity(intent);
                        activityy.finish();




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //displaying the error in toast if occurrs
                        pdLoading.dismiss();
                        Log.d("error", "" + error.toString());
                        Toast.makeText(activityy, "error" + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(activityy);
        requestQueue.add(stringRequest);


    }
}