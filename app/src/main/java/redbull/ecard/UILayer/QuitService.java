package redbull.ecard.UILayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

public class QuitService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onTaskRemoved(Intent rootIntent){
        Log.i("QuitService","Quit invoked");
      
        super.onTaskRemoved(rootIntent);
        FirebaseAuth.getInstance().signOut();
    }
}
