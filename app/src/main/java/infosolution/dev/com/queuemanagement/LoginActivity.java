package infosolution.dev.com.queuemanagement;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private Button btnsignin;
    private TextView tvstafflogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvstafflogin=findViewById(R.id.tv_stafflogin);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvstafflogin.setTypeface(typeface);

        btnsignin=findViewById(R.id.btn_signin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,ProfileDetailStaff.class);
                startActivity(intent);
            }
        });
    }
}
