package com.example.userstats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.example.userstats.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    public int USER_COUNT = 0;
    private EditText otpCode1, otpCode2, otpCode3, otpCode4, otpCode5, otpCode6;
    private String verificationID;
    FirebaseDatabase fdb;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);

        TextView textMobile = findViewById(R.id.textMobile);
        textMobile.setText(String.format(
                "+91-%s", getIntent().getStringExtra("mobile")
        ));

        otpCode1 = findViewById(R.id.otpCode1);
        otpCode2 = findViewById(R.id.otpCode2);
        otpCode3 = findViewById(R.id.otpCode3);
        otpCode4 = findViewById(R.id.otpCode4);
        otpCode5 = findViewById(R.id.otpCode5);
        otpCode6 = findViewById(R.id.otpCode6);

        sendOTPInputs();

        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button verifyBtn = findViewById(R.id.verifyOtpBtn);

        verificationID = getIntent().getStringExtra("verificationID");

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(otpCode1.getText().toString().trim().isEmpty()
                || otpCode2.getText().toString().trim().isEmpty()
                || otpCode3.getText().toString().trim().isEmpty()
                || otpCode4.getText().toString().trim().isEmpty()
                || otpCode5.getText().toString().trim().isEmpty()
                || otpCode6.getText().toString().trim().isEmpty()) {
                    Toast.makeText(VerifyOTPActivity.this, "Please enter Valid OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                String code = otpCode1.getText().toString() +
                        otpCode2.getText().toString() +
                        otpCode3.getText().toString() +
                        otpCode4.getText().toString() +
                        otpCode5.getText().toString() +
                        otpCode6.getText().toString();

                if(verificationID != null) {
                    progressBar.setVisibility(View.VISIBLE);
                    verifyBtn.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                            verificationID,
                            code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            progressBar.setVisibility(View.GONE);
                            verifyBtn.setVisibility(View.VISIBLE);
                            if(task.isSuccessful()) {

                                String phoneNumber = getIntent().getStringExtra("mobile");
                                if(!phoneNumber.isEmpty()) {
//                                    USER_COUNT++;
//                                    int cur = USER_COUNT;
//                                    String cnt = Integer.toString(cur);
                                    Users user = new Users(phoneNumber);
                                    fdb = FirebaseDatabase.getInstance();
                                    reference = fdb.getReference().child("Users");
                                    Query check = reference.equalTo(phoneNumber);

                                    check.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                            if(snapshot.exists()) {
                                                textMobile.setText("User Already Exists!");
                                                return;
                                            }

                                            else {
                                                reference.push().setValue(user);
                                                Toast.makeText(VerifyOTPActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
//                                    String userName = "User" + cnt;
//                                    reference.child(userName).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(Task<Void> task) {
//                                            Toast.makeText(VerifyOTPActivity.this, "Data Added", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });


//

                                }


                                Toast.makeText(VerifyOTPActivity.this, "Successfully Logged In!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(VerifyOTPActivity.this, "The Verification Code entered was Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        findViewById(R.id.resendOTP).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+91" + getIntent().getStringExtra("mobile"), 60,
                        TimeUnit.SECONDS,
                        VerifyOTPActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                            @Override
                            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                            }

                            @Override
                            public void onVerificationFailed(FirebaseException e) {

                                Toast.makeText(VerifyOTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(String newVerificationID, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationID = newVerificationID;
                                Toast.makeText(VerifyOTPActivity.this, "OTP Sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });
    }

    private void sendOTPInputs() {
        otpCode1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    otpCode2.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otpCode2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    otpCode3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otpCode3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    otpCode4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otpCode4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    otpCode5.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        otpCode5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                if(!s.toString().trim().isEmpty()) {
                    otpCode6.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}