package com.example.trippie.Common.LoginSignup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.trippie.R;
import com.example.trippie.User.UserDashboard;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class Login extends AppCompatActivity {
   TextInputLayout phoneno,password;
    ProgressBar progressBar;
    String PhoneNo,Password;
    CountryCodePicker countryCodePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_login);

        phoneno=findViewById(R.id.phoneNumber);
        password=findViewById(R.id.login_Password);
        progressBar=findViewById(R.id.progressBar);
        countryCodePicker = findViewById(R.id.countryCodePicker);
        progressBar.setVisibility(View.GONE);

    }
    public void loggedIn(View view){
        if(!validateEmail() | !validatePassword()){
            return;
        }
        progressBar.setVisibility(View.GONE);
        String    _getUserEnteredPhoneNumber = phoneno.getEditText().getText().toString().trim();
        PhoneNo = "+"+countryCodePicker.getFullNumber()+ _getUserEnteredPhoneNumber;
        Password=password.getEditText().getText().toString().trim();

        // Database
        Query checkUser = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phoneNo").equalTo(PhoneNo);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    phoneno.setError(null);
                    phoneno.setErrorEnabled(false);
                    String systemPassword= snapshot.child(PhoneNo).child("password").getValue(String.class);
                    if(Password==systemPassword){

                        password.setError(null);
                        password.setErrorEnabled(false);
                        Toast.makeText(Login.this, "Login Successfull",Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Login.this, "No such user Exist",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                progressBar.setVisibility(View.GONE);
                Toast.makeText(Login.this, DatabaseError.UNKNOWN_ERROR,Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validateEmail() {
        String val = phoneno.getEditText().getText().toString().trim();


        if (val.isEmpty()) {
            phoneno.setError("Field can not be empty");
            return false;

        } else {
            phoneno.setError(null);
            phoneno.setErrorEnabled(false);
            return true;
        }
    }
    private boolean validatePassword() {
        String val = password.getEditText().getText().toString().trim();
if(val.isEmpty()){
    password.setError("Please Enter Password");
    return false;
}
        password.setError(null);
        password.setErrorEnabled(false);
        return true;

    }
}