package redbull.ecard.LogicLayer;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.google.firebase.firestore.core.View;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import redbull.ecard.R;

public class QRGenerator {
    private static android.view.View rootView;
    public QRGenerator(android.view.View rootView){
        this.rootView = rootView;
    }
    public QRGenerator(){

    };
    public ImageView getPos(int pos){
        if(rootView!=null) {
            return (ImageView) rootView.findViewById(pos);
        }
        else{
            return null;
        }
    }

    public QRGEncoder getQRGencoder(String a,int dimen){
        return new QRGEncoder(a,null, QRGContents.Type.TEXT,dimen);
    }

    public Bitmap getBitmap(QRGEncoder a){
        return a.getBitmap();
    }

    public void setQr(ImageView a, Bitmap b){
        a.setImageBitmap(b);
    }
}
