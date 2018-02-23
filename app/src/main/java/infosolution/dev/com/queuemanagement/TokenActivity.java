package infosolution.dev.com.queuemanagement;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class TokenActivity extends AppCompatActivity {
    private TextView tvname,tvtoken,tvdate;
    private  String Name,Token,Date;
    private TextView tvpdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        tvname=findViewById(R.id.namet);
        tvtoken=findViewById(R.id.token);
        tvdate=findViewById(R.id.date);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdv=findViewById(R.id.tv_pdv);
        tvpdv.setTypeface(typeface);

        Intent intent=getIntent();
        Name=intent.getStringExtra("Name");
        Token=intent.getStringExtra("token");
        Date=intent.getStringExtra("date");

        tvname.setText(Name);
        tvtoken.setText(Token);
        tvdate.setText(Date);
    }
}
