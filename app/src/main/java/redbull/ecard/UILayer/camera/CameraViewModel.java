package redbull.ecard.UILayer.Profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CameraViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CameraViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Scan a profiles QR code");
    }

    public LiveData<String> getText() {
        return mText;
    }
}