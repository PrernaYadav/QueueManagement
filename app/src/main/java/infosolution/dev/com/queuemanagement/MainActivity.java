package infosolution.dev.com.queuemanagement;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private Button btnvisitor, btnstaff;
    private TextView tvgetapp, tvlogout;
    private ImageView ivconnect;




    Button mainBtn;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket socket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
     /*   android.support.v7.app.ActionBar AB=getSupportActionBar();
        AB.hide();*/


        tvgetapp = findViewById(R.id.tv_getapp);
        tvlogout = findViewById(R.id.tv_logout);
        btnstaff = findViewById(R.id.btn_stafflogin);
        btnvisitor = findViewById(R.id.btn_visitor);

        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");
        tvgetapp.setTypeface(typeface);

        btnvisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HairDresserActivity.class);
                startActivity(intent);
            }
        });

        btnstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        tvlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferencesl = getSharedPreferences("Login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editorl = preferencesl.edit();
                editorl.clear();
                editorl.commit();


                SharedPreferences preferences = getSharedPreferences("LoginStore", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();

                Intent intent = new Intent(MainActivity.this, StoreLogin.class);
                startActivity(intent);
                finish();
            }
        });

        ivconnect = findViewById(R.id.connect);
        ivconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

}