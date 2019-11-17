package com.destiny.chatapptutorialandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.destiny.chatapptutorialandroid.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    EditText email,password,name;
    ProgressBar progressBar;
    DatabaseReference reference;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email_ed_register);
        password = findViewById(R.id.password_ed_register);
        progressBar = findViewById(R.id.progressBarLogin);
        name = findViewById(R.id.name_ed_register);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    public void goToRegister(View v){
        progressBar.setVisibility(View.VISIBLE);
        if (!email.equals("") && !password.equals("") && password.length()>6){
            auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            User u = new User();
                            u.setName(name.getText().toString());
                            u.setEmail(email.getText().toString());

                            reference.child(firebaseUser.getUid()).setValue(u)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "User Registered", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                                finish();
                                                Intent i = new Intent(RegisterActivity.this,GroupChatActivity.class);
                                                startActivity(i);
                                            }else{
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(RegisterActivity.this, "Failed to make User", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });
        }
    }
    public void goToLogin(View v){
        Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
        startActivity(intent);
    }
}
