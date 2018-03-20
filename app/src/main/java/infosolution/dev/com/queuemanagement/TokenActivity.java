package infosolution.dev.com.queuemanagement;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Typeface;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;

import infosolution.dev.com.queuemanagement.model.Formatter;

public class TokenActivity extends AppCompatActivity {
    private TextView tvname, tvtoken, tvdate;
    private String Name, Token, Date;
    private TextView tvpdv, tvt;
    private Button btnprint;
    byte FONT_TYPE;
    private static BluetoothSocket btsocket;
    private static OutputStream btoutputstream, btoutputstream1, btoutputstream2;
    private TextView tvsname, tvd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        tvname = findViewById(R.id.namet);
        tvtoken = findViewById(R.id.token);
        tvt = findViewById(R.id.tvt);
        tvdate = findViewById(R.id.date);
        btnprint = findViewById(R.id.btn_print);
        tvsname = findViewById(R.id.tvsname);
        tvd = findViewById(R.id.tvd);


        Typeface typeface = Typeface.createFromAsset(getAssets(), "font/AUDIOWIDE-REGULAR.TTF");

        tvpdv = findViewById(R.id.tv_pdv);
        tvpdv.setTypeface(typeface);

        Intent intent = getIntent();
        Name = intent.getStringExtra("Name");
        Token = intent.getStringExtra("token");
        Date = intent.getStringExtra("date");

        tvname.setText(Name);
        tvtoken.setText(Token);
        tvdate.setText(Date);

        connect();


    }

    private void connect() {


        if (btsocket == null) {
            Intent BTIntent = new Intent(getApplicationContext(), BTDeviceList.class);
            this.startActivityForResult(BTIntent, BTDeviceList.REQUEST_CONNECT_BT);
        } else {

            OutputStream opstream = null;
            try {
                opstream = btsocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            btoutputstream = opstream;
            print_bt();

        }

    }

    private void print_bt() {

        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            btoutputstream = btsocket.getOutputStream();



            String msg = "                 "+tvt.getText().toString() + " " + tvtoken.getText().toString();
            String StaffName = "                  Staff Name";
            String Name = "                 "+tvname.getText().toString();
            String Date = "               "+tvd.getText().toString() + " " + tvdate.getText().toString();
            String Space=" ";

            byte[] arrayOfByte1 = { 27, 33, 0 };
            byte[] format = { 27, 33, 0 };
            format[2] = ((byte)(0x8 | arrayOfByte1[2]));
            format[2] = ((byte)(0x10 | arrayOfByte1[2]));
            format[2] = ((byte) (0x20 | arrayOfByte1[2]));


//            byte[] printformat = {0x1B, 0x21, 0x8};
            btoutputstream.write(format);

            btoutputstream.write(Space.getBytes());
            btoutputstream.write(Space.getBytes());


            btoutputstream.write(msg.getBytes());
            btoutputstream.write(Space.getBytes());

            btoutputstream.write(StaffName.getBytes());
            btoutputstream.write(Name.getBytes());
            btoutputstream.write(Space.getBytes());

            btoutputstream.write(Date.getBytes());
            btoutputstream.write(Space.getBytes());
            btoutputstream.write(Space.getBytes());

            btoutputstream.flush();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
      /*  try {
            if(btsocket!= null){
                btoutputstream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            btsocket = BTDeviceList.getSocket();
            if (btsocket != null) {
                print_bt();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
