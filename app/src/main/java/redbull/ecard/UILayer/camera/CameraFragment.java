package redbull.ecard.UILayer.camera;

import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGEncoder;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.LogicLayer.RunnableCallBack;
import redbull.ecard.LogicLayer.ShareLogic;
import redbull.ecard.R;
import redbull.ecard.UILayer.camera.CameraViewModel;

public class CameraFragment extends Fragment {
    private CameraViewModel cameraViewModel;
    static String scannedUID;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cameraViewModel =
                new ViewModelProvider(this).get(CameraViewModel.class);
        View root = inflater.inflate(R.layout.fragment_camera, container, false);
        final TextView textView = root.findViewById(R.id.camera_view);
        cameraViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        //------------------
        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(CameraFragment.this);

        integrator.setOrientationLocked(false);
        integrator.setPrompt("Scan QR code");
        integrator.setBeepEnabled(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();
        return root;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(getContext(), "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getContext(), "Scanned : " + result.getContents(), Toast.LENGTH_LONG).show();
                ArrayList<RunnableCallBack> callBackSuccesses = new ArrayList<RunnableCallBack>();
                callBackSuccesses.add( () -> scanFetchCallbackSuccess());

                ArrayList<RunnableCallBack> callBackFailures = new ArrayList<RunnableCallBack>();
                callBackFailures.add( () -> scanfetchCallbackfailure());

                scannedUID = result.getContents();
                new CardDatabaseConnector(callBackSuccesses, callBackFailures).fetchScannerProfileInformation (result.getContents());
            }
        }
    }
    private void scanFetchCallbackSuccess(){
        Profile curProfile = CardDatabaseConnector.getCachedUserProfile();
        Profile scannedProfile = CardDatabaseConnector.getScannerProfile();

        if (scannedProfile == null)
            return;

        if (scannedUID != null)
        scannedProfile.setUID (scannedUID);

        // First check if we already have that connection
        // Do not add duplicates
        if (!curProfile.hasConnection(scannedProfile)) {
            curProfile.getConnections().add(scannedProfile);

            ShareLogic.getInstance(curProfile).createConnection(scannedProfile.getUID());
        }
    }
    private void scanfetchCallbackfailure(){}

}