package redbull.ecard.UILayer.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;

import io.grpc.EquivalentAddressGroup;
import redbull.ecard.DataLayer.Contact;
import redbull.ecard.DataLayer.Profile;
import redbull.ecard.DataLayer.Services;
import redbull.ecard.LogicLayer.ProfileLogic;
import redbull.ecard.MainActivity;
import redbull.ecard.R;

import redbull.ecard.DataLayer.Name;
import redbull.ecard.DataLayer.Address;
public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }
    private Profile genarateProfileData() {
        TextInputLayout firstNameTextInputLayout = findViewById(R.id.firstName);
        String firstName = firstNameTextInputLayout.getEditText().getText().toString();

        TextInputLayout middleNameTextInputLayout = findViewById(R.id.middleName);
        String middleName = middleNameTextInputLayout.getEditText().getText().toString();

        TextInputLayout lastNameTextInputLayout = findViewById(R.id.lastName);
        String lastName = lastNameTextInputLayout.getEditText().getText().toString();

        Name name = new Name(firstName, lastName, middleName);

        // Cell no Validation
        TextInputLayout cellPhoneTextInputLayout = findViewById(R.id.cellPhone);
        String cellPhone = cellPhoneTextInputLayout.getEditText().getText().toString();

        TextInputLayout homePhoneTextInputLayout = findViewById(R.id.homePhone);
        String homePhone = homePhoneTextInputLayout.getEditText().getText().toString();

        TextInputLayout emailTextInputLayout = findViewById(R.id.email);
        String emailAddress = emailTextInputLayout.getEditText().getText().toString();

        Contact contact = new Contact(cellPhone, homePhone, emailAddress);
        // Address Validation
        TextInputLayout rdTextInputLayout = findViewById(R.id.rdNo);
        String roadNumber = rdTextInputLayout.getEditText().getText().toString();

        TextInputLayout houseTextInputLayout = findViewById(R.id.houseNo);
        String houseNumber = houseTextInputLayout.getEditText().getText().toString();

        TextInputLayout postalCodeTextInputLayout = findViewById(R.id.postalCode);
        String postalCode = postalCodeTextInputLayout.getEditText().getText().toString();

        TextInputLayout cityInputLayout = findViewById(R.id.city);
        String city = cityInputLayout.getEditText().getText().toString();

        Spinner provinceSpinner = findViewById(R.id.province);
        String province = provinceSpinner.getSelectedItem().toString();

        Address address = new Address(roadNumber, houseNumber, postalCode, city, province, "Canada");

        Spinner serviceSpinner = findViewById(R.id.serviceSpinner);
        String service = serviceSpinner.getSelectedItem().toString();

        Profile profile = new Profile(name, contact, address, "", service);
        return profile;
    }
    public boolean validate() {
        boolean res = true;

        // Name Validation
        TextInputLayout firstNameTextInputLayout = findViewById(R.id.firstName);
        if (firstNameTextInputLayout.getEditText().getText().toString().length() == 0){
            firstNameTextInputLayout.setError("First name required");
            res = res && false;
        }
        TextInputLayout middleNameTextInputLayout = findViewById(R.id.middleName);
        if (middleNameTextInputLayout.getEditText().getText().toString().length() == 0){
            middleNameTextInputLayout.setError("First name required");
            res = res && false;
        }
        TextInputLayout lastNameTextInputLayout = findViewById(R.id.lastName);
        if (lastNameTextInputLayout.getEditText().getText().toString().length() == 0){
            lastNameTextInputLayout.setError("First name required");
            res = res && false;
        }


        // Cell no Validation
        TextInputLayout cellPhoneTextInputLayout = findViewById(R.id.cellPhone);
        if (cellPhoneTextInputLayout.getEditText().getText().toString().length() == 0){
            cellPhoneTextInputLayout.setError("First name required");
            res = res && false;
        }

        // Address Validation
        TextInputLayout rdTextInputLayout = findViewById(R.id.rdNo);
        if (rdTextInputLayout.getEditText().getText().toString().length() == 0){
            rdTextInputLayout.setError("First name required");
            res = res && false;
        }
        TextInputLayout houseTextInputLayout = findViewById(R.id.houseNo);
        if (houseTextInputLayout.getEditText().getText().toString().length() == 0){
            houseTextInputLayout.setError("First name required");
            res = res && false;
        }
        TextInputLayout postalCodeTextInputLayout = findViewById(R.id.postalCode);
        if (postalCodeTextInputLayout.getEditText().getText().toString().length() == 0){
            postalCodeTextInputLayout.setError("First name required");
            res = res && false;
        }
        TextInputLayout cityInputLayout = findViewById(R.id.city);
        if (cityInputLayout.getEditText().getText().toString().length() == 0){
            cityInputLayout.setError("First name required");
            res = res && false;
        }
        Spinner provinceSpinner = findViewById(R.id.province);
        Spinner serviceSpinner = findViewById(R.id.serviceSpinner);


        return true;
    }

    public void onClickDone(View view) {
        if (validate()) {
            Profile profile = genarateProfileData();
            ProfileLogic.getInstance().createProfile(profile);
            startMainActivity();
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}

//TODO
// TextInputLayout til = (TextInputLayout) findViewById(R.id.text_input_layout);
// til.setError("You need to enter a name");