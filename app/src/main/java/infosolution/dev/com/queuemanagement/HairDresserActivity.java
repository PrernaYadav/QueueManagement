package infosolution.dev.com.queuemanagement;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import infosolution.dev.com.queuemanagement.adapter.HairDresserAdapter;
import infosolution.dev.com.queuemanagement.model.HairModel;

import static java.security.AccessController.getContext;

public class HairDresserActivity extends AppCompatActivity {
    private TextView tvchoose;
    RecyclerView rcview;
    HairDresserAdapter hairDresserAdapter;
    private ArrayList<HairModel> hairModelArrayList;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_dresser);

        view=findViewById(R.id.action);
        TextView tv=findViewById(R.id.tv);
        ImageView iv=findViewById(R.id.back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        tvchoose=findViewById(R.id.tv_choose);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tvchoose.setTypeface(typeface);
        tv.setTypeface(typeface);

        rcview=findViewById(R.id.rc);


        rcview.setLayoutManager(new LinearLayoutManager(this));
        rcview.setHasFixedSize(true);
        rcview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));


      /*  DividerItemDecoration itemDecorator = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.divider));*/

        int numberOfColumns = 3;
        rcview.setLayoutManager(new GridLayoutManager(this, numberOfColumns));

        rcview.setAdapter(hairDresserAdapter);
        hairModelArrayList= new ArrayList<>();
        hairDresserAdapter = new HairDresserAdapter(hairModelArrayList, this, this);

for (int i=0;i<=5;i++){
    HairModel hairModel= new HairModel();
    hairModel.setId("1");
    hairModel.setName("test");
    hairModel.setWaiting("5");
    hairModel.setImage(R.drawable.pic);
    hairModelArrayList.add(hairModel);

    rcview.setAdapter(hairDresserAdapter);
}




    }
}
