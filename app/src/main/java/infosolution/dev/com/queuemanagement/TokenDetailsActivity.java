package infosolution.dev.com.queuemanagement;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class TokenDetailsActivity extends AppCompatActivity {

    private View view;
    private TextView text,tvvtoken;
    private String WorkingId,token;
    private Button btntrans,btnservices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokendetail);
        final Intent intent=getIntent();
        WorkingId=intent.getStringExtra("id");
        token=intent.getStringExtra("token");

        SharedPreferences sharedPreferencesl = getApplicationContext().getSharedPreferences("WorkingId", Context.MODE_PRIVATE);
        SharedPreferences.Editor editorl = sharedPreferencesl.edit();
        editorl.putString("id",WorkingId);
        editorl.commit();

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");


        text=findViewById(R.id.tv_transfer);
        tvvtoken=findViewById(R.id.tvvtoken);
        btntrans=findViewById(R.id.btn_transf);
        btnservices=findViewById(R.id.btn_services);
        text.setTypeface(typeface);
        tvvtoken.setTypeface(typeface);

        tvvtoken.setText(token);
        btntrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent1= new Intent(TokenDetailsActivity.this,TransferToActivity.class);
                startActivity(intent1);
            }
        });

btnservices.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent intent1 =new Intent(TokenDetailsActivity.this,ServiceActivity.class);
        startActivity(intent1);

    }
});


    }


}
