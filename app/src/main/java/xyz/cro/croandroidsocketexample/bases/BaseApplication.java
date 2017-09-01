package xyz.cro.croandroidsocketexample.bases;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by juyounglee on 2017. 9. 1..
 */

public class BaseApplication extends Application {
    private static BaseApplication INSTANCE;
    public static boolean DEBUG;


    public static BaseApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        DEBUG = isDebuggable(this);
    }

    private boolean isDebuggable(Context context) {
        boolean debuggable = false;

        PackageManager pm = context.getPackageManager();
        try {
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return debuggable;
    }
}
