package com.example.trippie.Common.LoginSignup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trippie.Databases.UserHelperClass;
import com.example.trippie.R;
import com.example.trippie.User.UserDashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import org.w3c.dom.Text;

public class SignUp extends AppCompatActivity {
    //variables
    ImageView backBtn;
    Button next,login;
    TextView titleText;
    TextInputLayout fullname, username,email,password,phoneNo;
    CountryCodePicker countryCodePicker;
    String Fullname, Username,Email,Password,PhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_sign_up);

        //hooks
        backBtn = findViewById(R.id.signup_back_button);
        next = findViewById(R.id.signup_next_button);
        login = findViewById(R.id.signup_login_button);
        titleText = findViewById(R.id.signup_title_text);

        //hooksfor getting dara
        fullname=findViewById(R.id.signup_full_name);
        email=findViewById(R.id.signup_email);
        username=findViewById(R.id.signup_username);
        password=findViewById(R.id.signup_password);
        phoneNo = findViewById(R.id.phoneNumber);
        countryCodePicker = findViewById(R.id.countryCodePicker);



    }
    public void callNextSignupPage(View view){
        storeNewUsersData();

        if(!validateFullName()|!validateEmail()|!validatePassword()|!validateUsername()){
            return;
        }




        Intent intent = new Intent(SignUp.this, Login.class);
        startActivity(intent);

    }
//validation functions
    private boolean validateFullName(){
        String val = fullname.getEditText().getText().toString().trim();
        if (val.isEmpty()) {
            fullname.setError("Field can not be empty");
            return false;
        } else {
            fullname.setError(null);
            fullname.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateUsername() {
        String val = username.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            username.setError("Field can not be empty");
            return false;
        } else if (val.length() > 20) {
            username.setError("Username is too large!");
            return false;
        }
        else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validateEmail() {
        String val = email.getEditText().getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if (val.isEmpty()) {
            email.setError("Field can not be empty");
            return false;
        } else if (!val.matches(checkEmail)) {
            email.setError("Invalid Email!");
            return false;
        } else {
            email.setError(null);
            email.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();

            password.setError(null);
            password.setErrorEnabled(false);
            return true;

    }
    // database
    private void storeNewUsersData() {
        FirebaseDatabase rootnode = FirebaseDatabase.getInstance();
        DatabaseReference reference = rootnode.getReference("Users");
        Fullname=fullname.getEditText().getText().toString().trim();
        Username=username.getEditText().getText().toString().trim();
        Email=email.getEditText().getText().toString().trim();
        Password=password.getEditText().getText().toString().trim();
        String    _getUserEnteredPhoneNumber = phoneNo.getEditText().getText().toString().trim();
        PhoneNo = "+"+countryCodePicker.getFullNumber()+ _getUserEnteredPhoneNumber;
        UserHelperClass addNewUser= new UserHelperClass(Fullname,Username,Email,Password,PhoneNo);
        reference.child(PhoneNo).setValue(addNewUser);
    }
}