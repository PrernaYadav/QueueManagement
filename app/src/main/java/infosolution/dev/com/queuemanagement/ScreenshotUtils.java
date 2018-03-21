package infosolution.dev.com.queuemanagement;

import android.graphics.Bitmap;
import android.view.View;

/**
 * Created by amit on 3/21/2018.
 */

public class ScreenshotUtils {
    public static Bitmap getScreenShot(View view) {
        View screenView = view.getRootView();
        screenView.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(screenView.getDrawingCache());
        screenView.setDrawingCacheEnabled(false);
        return bitmap;
    }
}
