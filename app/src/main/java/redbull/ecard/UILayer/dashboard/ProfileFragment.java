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
import redbull.ecard.UILayer.Profile.ProfileViewModel;

import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProfileFragment extends Fragment{
    private ProfileViewModel profileViewModel;
    public ImageView hImageViewSemafor;
    private ImageView qrCodeIV;
    private JSONObject jsondata;
    private JSONObject address;
    private String result;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        Log.d("CREATFRAG","SD");
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        //----------
        try {
            jsondata = new JSONObject("{\n" +
                    "  \"address\" : {\n" +
                    "    \"city\" : \"Winnipeg\",\n" +
                    "    \"country\" : \"CANADA\",\n" +
                    "    \"houseNumber\" : \"21\",\n" +
                    "    \"postalCode\" : \"Q3E 2R4\",\n" +
                    "    \"province\" : \"MB\",\n" +
                    "    \"roadNumber\" : \"12\"\n" +
                    "  },\n" +
                    "  \"contact\" : {\n" +
                    "    \"cellPhone\" : \"123 456 7891\",\n" +
                    "    \"emailAddress\" : \"redbull.com\",\n" +
                    "    \"homePhone\" : \"987 654 3210\"\n" +
                    "  },\n" +
                    "  \"name\" : {\n" +
                    "    \"firstName\" : \"Abir\",\n" +
                    "    \"lastName\" : \"Sakib\",\n" +
                    "    \"middleName\" : \"Ahammed\"\n" +
                    "  }\n" +
                    "}\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            address = (JSONObject)jsondata.get("address");
            result = "address:\n"+"roadnumber: "+address.get("roadNumber").toString()+"\n"+"province: "+address.get("province").toString()+"\n"+"postalCode: "+address.get("postalCode").toString()+"\n"+"housenumber: "+address.get("houseNumber").toString()+"\n"+"country: "+address.get("country").toString()+"\n"+"city: "+address.get("city").toString();
            JSONObject contact = (JSONObject) jsondata.get("contact");
            String result2 = "contact:\n"+"cellPhone: "+contact.get("homePhone").toString()+"\n"+"emailAddress: "+contact.get("emailAddress")+"\n"+"cellPhone: "+contact.get("cellPhone");

            result = result+"\n"+result2;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //-----------
        qrCodeIV = (ImageView) root.findViewById(R.id.imageView2);// this is where the QR gonna be
        Display display = getActivity().getWindowManager().getDefaultDisplay();// display QR
//        Point point = new Point();
//        display.getSize(point);

        int dimen = 50;// this is QR dimension
        qrgEncoder = new QRGEncoder(result,null,QRGContents.Type.TEXT,dimen);// now we can generate QR code
        bitmap = qrgEncoder.getBitmap();// get bot map
        qrCodeIV.setImageBitmap(bitmap);//put qr image to qrCodeIV
        return root;
    }
}

