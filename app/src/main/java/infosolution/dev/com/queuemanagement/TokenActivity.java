package infosolution.dev.com.queuemanagement;

import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    private LinearLayout linearLayout,llupr;
    ImageView ivimg;

    Bitmap bitmap;

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
        linearLayout = findViewById(R.id.scr);
        llupr = findViewById(R.id.llupr);
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

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Do something after 100ms
                takeScreenshot();
            }
        }, 1000);





    }
    @Override
    public void onRestart() {
        super.onRestart();
       finish();

    }
    private void takeScreenshot() {

        llupr.setVisibility(View.GONE);
        btnprint.setVisibility(View.GONE);


        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/PICTURES/Screenshots/" + "now" + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
             bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);
            File imageFile = new File(mPath);
            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();


            MediaScannerConnection.scanFile(this,
                    new String[]{imageFile.toString()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.i("ExternalStorage", "Scanned " + path + ":");
                            Log.i("ExternalStorage", "-> uri=" + uri);
                        }
                    });

            doPhotoPrint();
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }
    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(TokenActivity.this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
       // Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_profiledet);
        photoPrinter.printBitmap("bg_profiledet.png - test print", bitmap);
    }

   /* private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image*//*");
        startActivity(intent);
    }*/

  /*  private void connect() {


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

          *//*  byte[] arrayOfByte1 = { 27, 33, 0 };
            byte[] format = { 27, 33, 0 };
            format[2] = ((byte)(0x8 | arrayOfByte1[2]));
            format[2] = ((byte)(0x10 | arrayOfByte1[2]));
            format[2] = ((byte) (0x20 | arrayOfByte1[2]));*//*

            byte[] PrintHeader = { (byte) 0xAA, 0x55,2,0 };


//            byte[] printformat = {0x1B, 0x21, 0x8};
            btoutputstream.write(PrintHeader);

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

       *//* try {
            if(btsocket!= null){
                btoutputstream.close();
                btsocket.close();
                btsocket = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*//*
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

    }*/


    }
