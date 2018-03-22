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

import infosolution.dev.com.queuemanagement.adapter.TaskListAdapter;
import infosolution.dev.com.queuemanagement.model.QueueModel;
import infosolution.dev.com.queuemanagement.model.TaskModel;

public class TaskListActivity extends AppCompatActivity {
    private RecyclerView rcview;
    private View view;
    private ProgressDialog pdLoading;
    private TaskListAdapter taskListAdapter;
    private ArrayList<TaskModel> taskModelArrayList;
    private String StaffCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        final SharedPreferences prefslogin =getSharedPreferences("l", MODE_PRIVATE);
        StaffCode = prefslogin.getString("staff", null);

        view = findViewById(R.id.actionlist);
        TextView tv = findViewById(R.id.tvlist);
        ImageView iv = findViewById(R.id.backlist);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tv.setTypeface(typeface);

        rcview = findViewById(R.id.rv_task);

        rcview.setLayoutManager(new LinearLayoutManager(this));
        rcview.setHasFixedSize(true);
        rcview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


        rcview.setAdapter(taskListAdapter);
        taskModelArrayList = new ArrayList<>();
        taskListAdapter = new TaskListAdapter(taskModelArrayList, this, this);

        GetData();


    }

    private void GetData() {
        pdLoading = new ProgressDialog(TaskListActivity.this);
        //this method will be running on UI thread
        pdLoading.setMessage("\tLoading...");
        pdLoading.setCancelable(false);
        pdLoading.show();

        String URL = "http://www.devhitech.com/salon-ms/api/staff/action/get/task-list/?staff_code=" +StaffCode ;


        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        pdLoading.dismiss();

                        //hiding the progressbar after completion
                        Log.d("Response", response.toString());
                           //   Toast.makeText(TaskListActivity.this, "responce"+response.toString(), Toast.LENGTH_SHORT).show();


                        try {

                            JSONObject jsono = new JSONObject(response);
                            JSONArray jsonArray = jsono.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                String Task = object.getString("task_name");
                                String StaffId = object.getString("task_staff_code");
                                String AssignedAt = object.getString("task_assigned_at");
                                String DeadLine = object.getString("task_deadline");
                                String Status = object.getString("task_status");
                                String TaskId= object.getString("id");

                                TaskModel taskModel=new TaskModel();
                                taskModel.setTask(Task);
                                taskModel.setAssignAt("Assigned At : "+AssignedAt);
                                taskModel.setDeadline("DeadLine : "+DeadLine);
                                taskModel.setTaskId(TaskId);
                                taskModel.setStaffId(StaffId);
                                taskModel.setStatus(Status);

                                taskModelArrayList.add(taskModel);
                            }
                            rcview.setAdapter(taskListAdapter);
                            taskListAdapter.notifyDataSetChanged();
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
        RequestQueue requestQueue = Volley.newRequestQueue(TaskListActivity.this);
        requestQueue.add(stringRequest);

    }
}
