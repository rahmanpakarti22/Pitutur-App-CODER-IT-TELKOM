package com.example.pitutur.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pitutur.Custom_loading_Bar;
import com.example.pitutur.Dashboard.Home;
import com.example.pitutur.Lupa_Password.Lupa_Password;
import com.example.pitutur.R;
import com.example.pitutur.Register.Register;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class Login extends AppCompatActivity
{
    //deklarasi variabel object
    EditText email_ET, password_Et;
    TextView txt_register, lupa_password;
    Button login__button;
    //firebase
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_act);

        txt_register  = findViewById(R.id.txt_register);
        lupa_password = findViewById(R.id.lupa_password);
        email_ET      = findViewById(R.id.email_ET);
        password_Et   = findViewById(R.id.password_Et);

        login__button = findViewById(R.id.login__button);

        firebaseAuth = FirebaseAuth.getInstance();

        txt_register.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Register.class));
            finish();
        });

        lupa_password.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, Lupa_Password.class));
            finish();
        });

        login__button.setOnClickListener(v -> validasi_data_login());
    }

    final Custom_loading_Bar loading_bar = new Custom_loading_Bar(Login.this);

    private void validasi_data_login()
    {
        String email = email_ET.getText().toString();
        String password = password_Et.getText().toString();


        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password) || password.length() < 4)
        {
            Toast.makeText(this, "Password Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(this, "Email Salah", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading_bar.StartDialog();
            login_Pengguna();
        }
    }

    private void login_Pengguna()
    {
        String email = email_ET.getText().toString().trim();
        String password = password_Et.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult ->
                {
                    //login berhasil
                    makeMeOnline();
                })
                .addOnFailureListener(e ->
                {
                    //gagal login
                    loading_bar.CloseDialog();
                    Toast.makeText(Login.this, "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                });
    }

    private void makeMeOnline()
    {
        //setelah login buat status menjadi online
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("online","true");

        //update data ke db
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).updateChildren(hashMap)
                .addOnSuccessListener(unused ->
                {
                    //berhasil update
                    loading_bar.CloseDialog();
                    startActivity(new Intent(Login.this, Home.class));
                    finish();
                })
                .addOnFailureListener(e ->
                {
                    //gagal update
                    loading_bar.CloseDialog();
                    Toast.makeText(Login.this, "Database Error", Toast.LENGTH_SHORT).show();
                });

    }
}