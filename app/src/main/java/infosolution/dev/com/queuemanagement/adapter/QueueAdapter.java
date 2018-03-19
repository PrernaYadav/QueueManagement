package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.TokenDetailsActivity;
import infosolution.dev.com.queuemanagement.model.QueueModel;

/**
 * Created by amit on 3/16/2018.
 */

public class QueueAdapter extends RecyclerView.Adapter<QueueAdapter.QueueHolder> {
    private ArrayList<QueueModel> queueModelArrayList;
    Context context;
    private Activity activityy;

    public QueueAdapter(ArrayList<QueueModel> queueModelArrayList, Context context, Activity activityy) {
        this.queueModelArrayList = queueModelArrayList;
        this.context = context;
        this.activityy = activityy;
    }


    @Override
    public QueueAdapter.QueueHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.queue_row, parent, false);
        return new QueueHolder(itemView, context, queueModelArrayList);
    }

    @Override
    public void onBindViewHolder(QueueAdapter.QueueHolder holder, int position) {

        final QueueModel queue= queueModelArrayList.get(position);
        holder.tvqueue.setText(queueModelArrayList.get(position).getTokenQueue());
        holder.queueid.setText(queueModelArrayList.get(position).getQueueId());
        holder.tvtransferby.setText(queueModelArrayList.get(position).getTransferBy());
        holder.tvdate.setText(queueModelArrayList.get(position).getDate());
        holder.tv_transferat.setText(queueModelArrayList.get(position).getTransferedAt());

    }

    @Override
    public int getItemCount() {
        if (queueModelArrayList == null)
            return 0;
        return queueModelArrayList.size();
    }

    public class QueueHolder extends RecyclerView.ViewHolder {


        TextView tvqueue,queueid,tvtransferby,tvdate,tv_transferat;
        public QueueHolder(View itemView, Context context, final ArrayList<QueueModel> queueModelArrayList) {
            super(itemView);

            Typeface typeface = Typeface.createFromAsset(activityy.getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

            tvqueue=itemView.findViewById(R.id.tv_tokenqueue);
            queueid=itemView.findViewById(R.id.queueid);
            tvtransferby=itemView.findViewById(R.id.tv_transferby);
            tvdate=itemView.findViewById(R.id.tv_queuedate);
            tv_transferat=itemView.findViewById(R.id.tv_transferat);
            tvqueue.setTypeface(typeface);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int Position=getAdapterPosition();
                    Intent intent= new Intent(activityy, TokenDetailsActivity.class);
                    intent.putExtra("id",queueModelArrayList.get(Position).getQueueId());
                    intent.putExtra("token",queueModelArrayList.get(Position).getTokenQueue());
                    activityy.startActivity(intent);

                }
            });
        }
    }
}
