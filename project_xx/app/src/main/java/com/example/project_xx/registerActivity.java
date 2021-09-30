package com.example.project_xx;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class registerActivity extends AppCompatActivity {

    TextView alreadyhaveanAccount;
    EditText inputEmail,inputPassword,inputConformpassword;
    Button btnreg;
    String emailPattern="[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    FirebaseAuth mauth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        alreadyhaveanAccount=findViewById(R.id.alreadyhaveanac);
        inputEmail=findViewById(R.id.inputemail);
        inputPassword=findViewById(R.id.inputpassword);
        inputConformpassword=findViewById(R.id.inputpassword2);
        btnreg=findViewById(R.id.btnregister);
        progressDialog=new ProgressDialog(this);
        mauth=FirebaseAuth.getInstance();
        mUser=mauth.getCurrentUser();

        alreadyhaveanAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerActivity.this,MainActivity.class));
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perforAuth();
            }
        });
    }

    private void perforAuth(){
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmpassword=inputConformpassword.getText().toString();

        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter correct Email");
            inputEmail.requestFocus();
        }else if(password.isEmpty()||password.length()<6){
            inputPassword.setError("Enter proper password");
        }else if(!password.equals(confirmpassword)){
            inputConformpassword.setError("Password not matched");
        }else {
            progressDialog.setMessage("Please wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        sendUsertoNextActivity();
                        Toast.makeText(registerActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(registerActivity.this, " " + task.getException(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    private void sendUsertoNextActivity(){
        Intent intent=new Intent(registerActivity.this,tempConvertorActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}