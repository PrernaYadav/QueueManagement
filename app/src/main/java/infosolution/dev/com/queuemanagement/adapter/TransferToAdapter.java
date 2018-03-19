package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
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

import infosolution.dev.com.queuemanagement.ProfileDetailStaff;
import infosolution.dev.com.queuemanagement.ProfileDetailforVisitor;
import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.TransferToActivity;
import infosolution.dev.com.queuemanagement.model.HairModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by amit on 3/16/2018.
 */

public class TransferToAdapter extends RecyclerView.Adapter<TransferToAdapter.TransferHolder> {
    public TransferToAdapter(ArrayList<HairModel> hairModelArrayList, Context context, Activity activityy) {
        this.hairModelArrayList = hairModelArrayList;
        this.context = context;
        this.activityy = activityy;
    }

    private ArrayList<HairModel> hairModelArrayList;
    Context context;
    private Activity activityy;
    private String WorkingId,Staff_code;
    private ProgressDialog pdLoading;





    @Override
    public TransferToAdapter.TransferHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.hairdresser_row, parent, false);
        return new TransferHolder(itemView, context, hairModelArrayList);
    }

    @Override
    public void onBindViewHolder(TransferToAdapter.TransferHolder holder, int position) {

        final HairModel cart= hairModelArrayList.get(position);
        holder.tvname.setText(hairModelArrayList.get(position).getName());
        holder.tvwaiting.setText(hairModelArrayList.get(position).getWaiting());
        holder.tvid.setText(hairModelArrayList.get(position).getId());
        //  holder.ivprofile.setImageResource(hairModelArrayList.get(position).getImage());

        Glide.with(activityy).load(cart.getImage()).into(holder.ivprofile);

    }

    @Override
    public int getItemCount() {
        if (hairModelArrayList == null)
            return 0;
        return hairModelArrayList.size();
    }

    public class TransferHolder extends RecyclerView.ViewHolder {

        ImageView ivprofile;
        TextView tvname,tvwaiting,tvid;
        public TransferHolder(View itemView, Context context, final ArrayList<HairModel> hairModelArrayList) {
            super(itemView);
            Typeface typeface = Typeface.createFromAsset(activityy.getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

            ivprofile = itemView.findViewById(R.id.profile_imagerow);
            tvname = (TextView)itemView.findViewById(R.id.tv_namerow);
            tvwaiting = itemView.findViewById(R.id.tv_waitingrow);
            tvid = itemView.findViewById(R.id.idrow);

            tvname.setTypeface(typeface);
            tvwaiting.setTypeface(typeface);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Position=getAdapterPosition();
                    Staff_code=hairModelArrayList.get(Position).getId();
                    Log.i("Staff","code"+Staff_code);
                   TransferToken();
                }
            });



        }
    }

    private void TransferToken() {

        final SharedPreferences ct = activityy.getSharedPreferences("WorkingId", MODE_PRIVATE);
        WorkingId = ct.getString("id", null);

        pdLoading = new ProgressDialog(activityy);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/staff/action/update/token?work_id="+WorkingId+"&to_staff="+Staff_code;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                          // Toast.makeText(activityy, "responce"+response.toString(), Toast.LENGTH_SHORT).show();

                        Intent intent= new Intent(activityy, ProfileDetailStaff.class);
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
