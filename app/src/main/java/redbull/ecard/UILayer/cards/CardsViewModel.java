package redbull.ecard.UILayer.cards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CardsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CardsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("View your cards here.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}