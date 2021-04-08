package redbull.ecard.LogicLayer;

import android.app.Activity;
import android.widget.ImageView;

import com.google.firebase.firestore.core.View;

import redbull.ecard.R;

public class QRGenerator {
    private static android.view.View rootView;
    public QRGenerator(android.view.View rootView){
        this.rootView = rootView;
    }

    public ImageView getPos(View a){
        return (ImageView) rootView.findViewById(R.id.imageView2);
    }


}
