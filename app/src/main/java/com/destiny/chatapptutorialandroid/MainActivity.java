package com.destiny.chatapptutorialandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    ProgressBar progressBar;

    FirebaseAuth auth;
    DatabaseReference refrence;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            Intent i = new Intent(MainActivity.this,GroupChatActivity.class);
            startActivity(i);
        }else{
            setContentView(R.layout.activity_main);
            email = findViewById(R.id.email_ed_login);
            password = findViewById(R.id.password_ed_login);
            progressBar = findViewById(R.id.progressBarLogin);
        }

    }

    public void goToRegister(View v){
        Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
        startActivity(intent);
    }
    public void goToLogin(View v){
        progressBar.setVisibility(View.VISIBLE);
        if (!email.getText().toString().equals("") && !password.getText().toString().equals("")){
            auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getApplicationContext(),"Logged In",Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(MainActivity.this,GroupChatActivity.class);
                                startActivity(i);
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Wrong Email or Password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    public void goToForgotPassword(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        LinearLayout container = new LinearLayout(MainActivity.this);
        container.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams ip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        ip.setMargins(50,0,0,100);

        final EditText input = new EditText(MainActivity.this);
        input.setLayoutParams(ip);
        input.setGravity(Gravity.TOP|Gravity.START);
        input.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        input.setLines(1);
        input.setMaxLines(1);

        container.addView(input,ip);

        alert.setMessage("Enter Your Registered Email");
        alert.setTitle("Forgot Password");
        alert.setView(container);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                String entered_email = input.getText().toString();
                auth.sendPasswordResetEmail(entered_email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    dialogInterface.dismiss();
                                    Toast.makeText(MainActivity.this, "Email Sent Please Check Your Email", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}
