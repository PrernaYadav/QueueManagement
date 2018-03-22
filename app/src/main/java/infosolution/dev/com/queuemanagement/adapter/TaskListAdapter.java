package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.TaskListActivity;
import infosolution.dev.com.queuemanagement.model.TaskModel;

/**
 * Created by Shreyansh Srivastava on 3/22/2018.
 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskListHolder> {

    private ArrayList<TaskModel> taskModelArrayList;
    Context context;
    private Activity activityy;
    private ProgressDialog pdLoading;
    private String StaffIdd,TaskIdd;
    Button btncmplt;

    public TaskListAdapter(ArrayList<TaskModel> taskModelArrayList, Context context, Activity activityy) {
        this.taskModelArrayList = taskModelArrayList;
        this.context = context;
        this.activityy = activityy;
    }





    @Override
    public TaskListAdapter.TaskListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.task_row, parent, false);
        return new TaskListHolder(itemView, context, taskModelArrayList);
    }

    @Override
    public void onBindViewHolder(TaskListAdapter.TaskListHolder holder, int position) {

        final TaskModel taskModel= taskModelArrayList.get(position);
        holder.tvname.setText(taskModelArrayList.get(position).getTask());
        holder.tvassignedat.setText(taskModelArrayList.get(position).getAssignAt());
        holder.tvdeadline.setText(taskModelArrayList.get(position).getDeadline());
        holder.tvstaffid.setText(taskModelArrayList.get(position).getStaffId());
        holder.tvtaskid.setText(taskModelArrayList.get(position).getTaskId());
        holder.status.setText(taskModelArrayList.get(position).getStatus());

    }

    @Override
    public int getItemCount() {
        if (taskModelArrayList == null)
            return 0;
        return taskModelArrayList.size();
    }

    public class TaskListHolder extends RecyclerView.ViewHolder {

        TextView tvname,tvassignedat,tvdeadline,tvstaffid,tvtaskid,status;
//        Button btncmplt;
        public TaskListHolder(View itemView, Context context, final ArrayList<TaskModel> taskModelArrayList) {
            super(itemView);



            tvname=itemView.findViewById(R.id.tv_task);
            tvassignedat=itemView.findViewById(R.id.tv_assignat);
            tvdeadline=itemView.findViewById(R.id.tv_deadline);
            btncmplt=itemView.findViewById(R.id.btncomplete);
            tvstaffid=itemView.findViewById(R.id.staffid);
            tvtaskid=itemView.findViewById(R.id.taskid);
            status=itemView.findViewById(R.id.status);

            btncmplt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Pos=getAdapterPosition();
                    StaffIdd=taskModelArrayList.get(Pos).getStaffId();
                    TaskIdd=taskModelArrayList.get(Pos).getTaskId();


Complete();
                }
            });
        }
    }

    private void Complete() {



        pdLoading = new ProgressDialog(activityy);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/staff/action/update/task?staff_id="+StaffIdd+"&task_id="+TaskIdd ;
        Log.i("urlstat",""+URL);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                         //  Toast.makeText(activityy, "responce"+response.toString(), Toast.LENGTH_SHORT).show();

                        try {
                            JSONObject jsono = new JSONObject(response);
                            String Status=jsono.getString("status");
                            if (Status.equals("success")){

                            }

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
