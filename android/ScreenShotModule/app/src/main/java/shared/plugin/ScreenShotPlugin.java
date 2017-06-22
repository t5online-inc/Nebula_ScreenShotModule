package shared.plugin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.FrameLayout;

import com.t5online.nebulacore.bridge.NebulaActivity;
import com.t5online.nebulacore.plugin.Plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sungju on 2017. 6. 7..
 */

public class ScreenShotPlugin extends Plugin {

    public void takePicture(){
        NebulaActivity activity = (NebulaActivity) this.bridgeContainer.getActivity();
        if (snap(activity, activity.getWebViewFrameID())) {
            resolve();
        } else {
            reject();
        }
    }

    protected boolean snap(Activity context, int screenId) {
        FrameLayout screen = (FrameLayout) context.findViewById(screenId);
        if (screen!=null) {
            Bitmap bmScreen;
            screen.setDrawingCacheEnabled(true);
            bmScreen = screen.getDrawingCache();
            String filepath = saveImage(bmScreen);
            rescanImage(context, filepath);
            return true;
        }
        return false;
    }

    protected String saveImage(Bitmap bmScreen2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddhhmmss");
        String fname = formatter.format(new Date());
        File filePath = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                        + "/" + fname + ".png");
        if (filePath.exists())
            filePath.delete();
        try {
            FileOutputStream out = new FileOutputStream(filePath);
            bmScreen2.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return filePath.getAbsolutePath();
    }

    protected void rescanImage(Context context, String filePath) {
        if (filePath==null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            File file = new File(filePath);
            if (file!=null) {
                Intent mediaScanIntent = new Intent(
                        Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri contentUri = Uri.fromFile(file);
                mediaScanIntent.setData(contentUri);
                context.sendBroadcast(mediaScanIntent);
            }
        } else {
            context.sendBroadcast(new Intent(
                    Intent.ACTION_MEDIA_MOUNTED,
                    Uri.parse("file://"
                            + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES))));
        }
    }
}
