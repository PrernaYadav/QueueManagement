package infosolution.dev.com.queuemanagement.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import infosolution.dev.com.queuemanagement.ProfileDetailforVisitor;
import infosolution.dev.com.queuemanagement.R;
import infosolution.dev.com.queuemanagement.model.HairModel;

/**
 * Created by Shreyansh Srivastava on 2/21/2018.
 */

public class HairDresserAdapter extends RecyclerView.Adapter<HairDresserAdapter.HairDresserHolder> {
    private ArrayList<HairModel> hairModelArrayList;
    Context context;
    private Activity activityy;

    public HairDresserAdapter(ArrayList<HairModel> hairModelArrayList, Context context, Activity activityy) {
        this.hairModelArrayList = hairModelArrayList;
        this.context = context;
        this.activityy = activityy;
    }

    @Override
    public HairDresserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.hairdresser_row, parent, false);
        return new HairDresserHolder(itemView, context, hairModelArrayList);
    }

    @Override
    public void onBindViewHolder(HairDresserHolder holder, int position) {

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


    public class HairDresserHolder extends RecyclerView.ViewHolder {

        ImageView ivprofile;
        TextView tvname,tvwaiting,tvid;
        public HairDresserHolder(View itemView, Context context, final ArrayList<HairModel> hairModelArrayList) {
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

                    SharedPreferences preferencesl =activityy.getSharedPreferences("C", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editorl = preferencesl.edit();
                    editorl.clear();
                    editorl.commit();

                    int Pos=getAdapterPosition();
                    Intent intent= new Intent(activityy, ProfileDetailforVisitor.class);
                    intent.putExtra("id",hairModelArrayList.get(Pos).getId());
                    activityy.startActivity(intent);
                }
            });

        }
    }
}
