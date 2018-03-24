package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.ServiceListActivity;
import infosolution.dev.com.queuemanagement.model.ServiceModel;

/**
 * Created by Shreyansh Srivastava on 3/20/2018.
 */

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHOlder> {
    private ArrayList<ServiceModel> serviceModelArrayList;
    Context context;
    private Activity activityy;

    public ServiceAdapter(ArrayList<ServiceModel> serviceModelArrayList, Context context, Activity activityy) {
        this.serviceModelArrayList = serviceModelArrayList;
        this.context = context;
        this.activityy = activityy;
    }

    @Override
    public ServiceAdapter.ServiceHOlder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.services_row, parent, false);
        return new ServiceHOlder(itemView, context, serviceModelArrayList);
    }

    @Override
    public void onBindViewHolder(ServiceAdapter.ServiceHOlder holder, int position) {
        final ServiceModel serviceModel= serviceModelArrayList.get(position);

        holder.tvname.setText(serviceModelArrayList.get(position).getName());
        holder.tvid.setText(serviceModelArrayList.get(position).getId());
    //    holder.ivimg.setImageResource(serviceModelArrayList.get(position).getImg());
        Glide.with(activityy).load(serviceModel.getImage()).into(holder.ivimg);

    }

    @Override
    public int getItemCount() {
        if (serviceModelArrayList == null)
            return 0;
        return serviceModelArrayList.size();

    }

    public class ServiceHOlder extends RecyclerView.ViewHolder {

        ImageView ivimg;
        TextView tvname,tvid;

        public ServiceHOlder(View itemView, Context context, final ArrayList<ServiceModel> serviceModelArrayList) {
            super(itemView);
            ivimg=itemView.findViewById(R.id.iv_serimg);
            tvname=itemView.findViewById(R.id.tv_servicename);
            tvid=itemView.findViewById(R.id.tvserid);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int Pos=getAdapterPosition();
                    Intent intent= new Intent(activityy, ServiceListActivity.class);
                    intent.putExtra("CatId",serviceModelArrayList.get(Pos).getId());
                    activityy.startActivity(intent);
                    activityy.finish();
                }
            });

        }
    }
}
