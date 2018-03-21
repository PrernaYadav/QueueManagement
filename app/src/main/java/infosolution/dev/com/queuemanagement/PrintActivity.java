package infosolution.dev.com.queuemanagement;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.print.PrintHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PrintActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print);
        doPhotoPrint();
    }

    private void doPhotoPrint() {
        PrintHelper photoPrinter = new PrintHelper(PrintActivity.this);
        photoPrinter.setScaleMode(PrintHelper.SCALE_MODE_FIT);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.bg_profiledet);
        photoPrinter.printBitmap("bg_profiledet.png - test print", bitmap);
    }
}
