package infosolution.dev.com.queuemanagement;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button btnvisitor,btnstaff;
    private TextView tvgetapp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /*   android.support.v7.app.ActionBar AB=getSupportActionBar();
        AB.hide();*/



     tvgetapp=findViewById(R.id.tv_getapp);
     btnstaff=findViewById(R.id.btn_stafflogin);
     btnvisitor=findViewById(R.id.btn_visitor);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tvgetapp.setTypeface(typeface);

        btnvisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,HairDresserActivity.class);
                startActivity(intent);
            }
        });

        btnstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
