package fr.product.slf.calls;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by faisa on 08/12/2015.
 */
public class CallDetectService extends Service {
    private CallHelper callHelper;

    public CallDetectService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        callHelper = new CallHelper(this);
        int res = super.onStartCommand(intent, flags, startId);

        //Start service
        callHelper.start();
        return res;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        //Stop service
        callHelper.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // not supporting binding
        return null;
    }
}
