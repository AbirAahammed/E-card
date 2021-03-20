package redbull.ecard.UILayer.Profile;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.graphics.Bitmap;
import android.view.Display;
import android.widget.EditText;
import android.widget.ImageView;
import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import redbull.ecard.DataLayer.Card;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.LogicLayer.CardDatabaseConnector;
import redbull.ecard.R;

import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class ProfileFragment extends Fragment{
    private ProfileViewModel profileViewModel;
    public ImageView hImageViewSemafor;
    private ImageView qrCodeIV;
    Bitmap bitmap;
    QRGEncoder qrgEncoder;

    private CardDatabaseConnector connector;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        connector = new CardDatabaseConnector();
        ProfileDisplaySetup(root);

        qrCodeIV = (ImageView) root.findViewById(R.id.imageView2);// this is where the QR gonna be
        Display display = getActivity().getWindowManager().getDefaultDisplay();// display QR
//        Point point = new Point();
//        display.getSize(point);



        // This QR code is a bit messy, we can clean it up into methods
        int dimen = 50;// this is QR dimension
        qrgEncoder = new QRGEncoder("IhnuB3O0gUZgkpf2FQ33hcOem022",null,QRGContents.Type.TEXT,dimen);// now we can generate QR code
        bitmap = qrgEncoder.getBitmap();// get bot map
        qrCodeIV.setImageBitmap(bitmap);//put qr image to qrCodeIV
        return root;
    }

    // Setup the display for the users profile
    private void ProfileDisplaySetup(View root)
    {
        // Custom exception needed
        if (root == null)
            return;

        // Setup events attached to the profile
        ProfileEventSetup(root);
        InitializeProfile(root);
    }

    // Initialize the profile to what it should be initially
    private void InitializeProfile(View root)
    {
        Card userInfo = connector.GetActiveUser();

        String currentService = userInfo.getDescription();
        String currentPhone = userInfo.getContact().getCellPhone();
        String currentEmail = userInfo.getContact().getEmailAddress();

        // Set the text for all the preview
        SetViewText((TextView)root.findViewById(R.id.descriptionPreview), currentService);
        SetViewText((TextView)root.findViewById(R.id.namePreview), userInfo.getName().toString());
        SetViewText((TextView)root.findViewById(R.id.phoneNumPreview), currentPhone);
        SetViewText((TextView)root.findViewById(R.id.emailPreview), currentEmail);

        SetViewText((TextView)root.findViewById(R.id.serviceInput), userInfo.getName().toString());
        SetViewText((TextView)root.findViewById(R.id.phoneInput), currentPhone);
        SetViewText((TextView)root.findViewById(R.id.emailInput), currentEmail);
    }

    // Set the text of a specific view object
    private void SetViewText(TextView textObj, String newText)
    {
        // Custom exception needed, this is an error
        if (textObj == null)
            return;

        // Do nothing if the text is null or empty, since this may remove the hint and put irrelevant text on the screen
        if (newText == null || newText == "")
            return;

        textObj.setText(newText);
    }

    // Set the text of a specific view object
    private void SetViewText(EditText textObj, String newText)
    {
        // Custom exception needed, this is an error
        if (textObj == null)
            return;

        textObj.setText(newText);
    }

    // Setup the event listeners for the view
    private void ProfileEventSetup(View root)
    {
        // TODO create custom exceptions for this
        if (connector == null)
            return;

        SetTextEvent(root, (EditText) root.findViewById(R.id.serviceInput), (TextView) root.findViewById(R.id.descriptionPreview), AdjustableViews.SERVICE);
        SetTextEvent(root, (EditText) root.findViewById(R.id.phoneInput), (TextView) root.findViewById(R.id.phoneNumPreview), AdjustableViews.PHONE);
        SetTextEvent(root, (EditText) root.findViewById(R.id.emailInput), (TextView) root.findViewById(R.id.emailPreview), AdjustableViews.EMAIL);
    }

    // Create a text event that will update a preview, then return the View type that was changed and update the database
    private void SetTextEvent(View objView, EditText inputView, TextView targetView, AdjustableViews type)
    {
        if (!inputView.hasOnClickListeners())
            inputView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}


            @Override
            public void afterTextChanged(Editable s) {
                // After they have updated their service information, update the card preview and the information on the database
                String value = s.toString();

                // Update the text of the preview
                targetView.setText(value);

                // Pass the updated information to the database (for logic layer to manage)
                ViewChange(type, value);
            }
        });
    }

    // Pass the view that was changed to the Logic layer so that the database can be updated accordingly
    private void ViewChange(AdjustableViews viewChanged, String value)
    {
        switch (viewChanged)
        {
            case EMAIL:
                connector.ContactUpdate(new Contact(null, null, value));
                break;
            case PHONE:
                connector.ContactUpdate(new Contact(value, null, null));
                break;
            case SERVICE:
                connector.ServiceUpdate(value);
                break;
            case TEMPLATE:

                int actValue = -1;
                try {
                    actValue = Integer.parseInt(value);
                } catch (NumberFormatException e)
                {
                    // We do not have to throw an exception here, rather, do nothing
                    // This is because a template is defined purely on a button press that calls this function
                    // In other words, the integer value passed is NOT defined by the user input.
                    // So, if this fails, we just do nothing.
                    Log.d("dev", "Templates use a numeric value representation. Please do not pass a non-integer value.");
                }

                if (actValue != -1)
                    connector.TemplateUpdate(actValue);

                break;
        }
    }
}

