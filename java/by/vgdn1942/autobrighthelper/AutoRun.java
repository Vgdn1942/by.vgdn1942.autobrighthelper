package by.vgdn1942.autobrighthelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AutoRun extends BroadcastReceiver {

    private static final boolean DBG = true;
    private static final String TAG = "autobrightnesshelper_service";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (DBG) Log.i(TAG, " AutoRun - Receive");
        String action = intent.getAction();
        if (action != null) {
            if (action.equals(Intent.ACTION_BOOT_COMPLETED)) {
                if (DBG) Log.i(TAG, " AutoRun - BootComplete");
                context.startService(new Intent(context, BackgroundService.class));
            } else if (action.equals(Intent.ACTION_USER_PRESENT)) {
                if (DBG) Log.i(TAG, " AutoRun - UserPresent");
                context.startService(new Intent(context, BackgroundService.class));
            } else if (action.equals(Intent.ACTION_SCREEN_ON)) {
                if (DBG) Log.i(TAG, " AutoRun - ScreenOn");
                context.startService(new Intent(context, BackgroundService.class));
            }
        }
    }
}
