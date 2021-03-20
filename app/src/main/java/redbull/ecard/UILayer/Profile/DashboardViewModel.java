<<<<<<< HEAD
package redbull.ecard.UILayer.Profile;
=======
<<<<<<< HEAD:app/src/main/java/redbull/ecard/UILayer/camera/CameraViewModel.java
package redbull.ecard.UILayer.camera;
=======
package redbull.ecard.UILayer.Profile;
>>>>>>> Profile Update:app/src/main/java/redbull/ecard/UILayer/Profile/DashboardViewModel.java
>>>>>>> feature/firebase-realtime-db

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

<<<<<<< HEAD
public class DashboardViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DashboardViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
=======
public class CameraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CameraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Scan a profiles QR code");
>>>>>>> feature/firebase-realtime-db
    }

    public LiveData<String> getText() {
        return mText;
    }
}