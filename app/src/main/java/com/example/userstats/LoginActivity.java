package com.example.userstats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText mobileInput = findViewById(R.id.mobileInput);
        Button getOtp = findViewById(R.id.getOtpBtn);

        final ProgressBar progressBar = findViewById(R.id.progressBar);

        getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mobileInput.getText().toString().trim().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                getOtp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + mobileInput.getText().toString(), 60,
                        TimeUnit.SECONDS,
                        LoginActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                getOtp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                getOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(String verificationID, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                getOtp.setVisibility(View.VISIBLE);

                                Intent intent = new Intent(getApplicationContext(), VerifyOTPActivity.class);
                                intent.putExtra("mobile", mobileInput.getText().toString());
                                intent.putExtra("verificationID", verificationID);
                                startActivity(intent);
                            }
                        }
                );

            }
        });
    }
}