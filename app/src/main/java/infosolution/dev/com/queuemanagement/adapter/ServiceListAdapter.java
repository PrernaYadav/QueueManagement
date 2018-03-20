package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.model.ServiceListModel;
import infosolution.dev.com.queuemanagement.model.ServiceModel;

/**
 * Created by Shreyansh Srivastava on 3/20/2018.
 */

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ServiceListHOlder> {
    private ArrayList<ServiceListModel> serviceModelArrayList;
    Context context;
    private Activity activityy;

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

        public ServiceListHOlder(View itemView, Context context, ArrayList<ServiceListModel> serviceModelArrayList) {
            super(itemView);
            ivimg=itemView.findViewById(R.id.iv_serimg);
            tvname=itemView.findViewById(R.id.tv_servicename);
            tvid=itemView.findViewById(R.id.tvserid);
            tvprice=itemView.findViewById(R.id.tv_price);

        }
    }}