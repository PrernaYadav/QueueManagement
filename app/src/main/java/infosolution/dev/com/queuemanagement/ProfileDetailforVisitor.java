package infosolution.dev.com.queuemanagement;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfileDetailforVisitor extends AppCompatActivity {
    private Button btnback;
    private TextView tvpdvi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detailfor_visitor);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdvi=findViewById(R.id.tv_pdvi);
        tvpdvi.setTypeface(typeface);

        btnback=findViewById(R.id.btn_backvi);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
