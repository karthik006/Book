package com.tgt.karthik.book;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {

    EditText emailText, passText;
    Button loginButton;
    FirebaseAuth Auth;
    FirebaseAuth.AuthStateListener Authlistener;
    DatabaseReference database,f_users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Auth = FirebaseAuth.getInstance();
        emailText = findViewById(R.id.input_email);
        passText = findViewById(R.id.input_password);
        loginButton = findViewById(R.id.btn_login);
        database = FirebaseDatabase.getInstance().getReference();
        f_users = database.child("Users");

        Authlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null)
                {
//                    if(user.isEmailVerified())
//                    {
                        String tokenID = FirebaseInstanceId.getInstance().getToken();
                        String uid = user.getUid();
                        f_users.child(uid).child("tokenID").setValue(tokenID);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
//                    }
//                    else
//                    {
//                        Toast.makeText(getApplicationContext(), "EMAIL NOT VERIFIED", Toast.LENGTH_SHORT).show();
//                        FirebaseAuth.getInstance().signOut();
//                        recreate();
//                    }
                }

            }
        };

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "SIGNING IN. PLEASE WAIT...", Toast.LENGTH_SHORT).show();
                signIn();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        emailText.setText("");
        passText.setText("");
        Auth.addAuthStateListener(Authlistener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Auth!=null)
        {
            Auth.removeAuthStateListener(Authlistener);
        }
    }

    private void signIn() {
        String email = emailText.getText().toString().trim();
        String pass = passText.getText().toString().trim();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pass))
        {
            Toast.makeText(LoginActivity.this, "FIELDS CANNOT BE EMPTY", Toast.LENGTH_LONG).show();
        }
        else {
            Auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful())
                    {
                        Toast.makeText(LoginActivity.this, "CHECK EMAIL AND PASSWORD AGAIN", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}
