package redbull.ecard.UILayer.dashboard;

import android.os.Bundle;
import android.os.health.UidHealthStats;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.view.Display;
import android.widget.ImageView;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import redbull.ecard.R;

import redbull.ecard.R;

import android.widget.ImageView;

public class ProfileFragment extends Fragment{
    private ProfileViewModel profileViewModel;
    public ImageView hImageViewSemafor;
    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("CREATFRAG","SD");
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        qrCodeIV = (ImageView) root.findViewById(R.id.imageView2);// this is where the QR gonna be
        Display display = getActivity().getWindowManager().getDefaultDisplay();// display QR
//        Point point = new Point();
//        display.getSize(point);

        int dimen = 50;// this is QR dimension
        qrgEncoder = new QRGEncoder("IhnuB3O0gUZgkpf2FQ33hcOem022",null,QRGContents.Type.TEXT,dimen);// now we can generate QR code
        bitmap = qrgEncoder.getBitmap();// get bot map
        qrCodeIV.setImageBitmap(bitmap);//put qr image to qrCodeIV
        return root;
    }
}

