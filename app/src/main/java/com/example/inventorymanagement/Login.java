package com.example.inventorymanagement;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Login extends AppCompatActivity {

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

    Animation fadein, rotate;
    ImageView itext, ilogo;
    EditText txtUsername, txtPassword;
    Button btnLogin;
    TextView txtUserType, register, forgotPassword, r;
    String userLevel;


    private MyDbHelper dbHelper;
    ArrayList<UserModel> list;

    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        actionBar = getSupportActionBar();
        actionBar.hide();


        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        r = findViewById(R.id.r);
        forgotPassword = findViewById(R.id.forgotPassword);
        txtUserType = findViewById(R.id.userType);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        list = new ArrayList<>();
        dbHelper = new MyDbHelper(this);


        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        userLevel = bundle.getString("userLevel");

        assert userLevel != null;
        txtUserType.setText(userLevel.concat(" Login"));

        if (userLevel.toLowerCase().equals("admin")) {
            register.setVisibility(View.GONE);
            r.setVisibility(View.GONE);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String level = userLevel.toLowerCase();
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                list = dbHelper.searchUser(username, password, level);

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Login.this, "Fill in All Details", Toast.LENGTH_SHORT).show();
                } else {
                    if (list.size() < 1) {
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Login.this, "Login successful", Toast.LENGTH_SHORT).show();

                        LoggedModel m = dbHelper.getLogged("1");
                        if (m.getLevel() == null) {
                            dbHelper.addLogged(level, list.get(0).getId());
                        } else {
                            dbHelper.updateLogged("1", level, list.get(0).getId());
                        }


                        Intent intent = new Intent(Login.this, MainActivity.class);
                        intent.putExtra("userLevel", level);
                        intent.putExtra("id", list.get(0).getId());
                        intent.putExtra("userName", username);

                        Clear();
                        startActivity(intent);
                        finish();

                    }
                }
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, PasswordReset.class);
                intent.putExtra("userLevel", "user");
                Clear();
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Newuser.class);
                intent.putExtra("userLevel", "user");
                intent.putExtra("task", "register");

                Clear();
                startActivity(intent);

            }
        });


    }

    private void Clear() {
        txtPassword.setText(null);
        txtUsername.setText(null);
    }

    @Override
    public void onStart() {
        super.onStart();
        itext = (ImageView) findViewById(R.id.imageView3);
        ilogo = (ImageView) findViewById(R.id.imageView2);
        fadein = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
        rotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        itext.startAnimation(fadein);
        ilogo.startAnimation(rotate);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        Runtime.getRuntime().gc();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // System.exit(0);

        startActivity(new Intent(Login.this, ChooseUserType.class));
        finish();
    }


}
