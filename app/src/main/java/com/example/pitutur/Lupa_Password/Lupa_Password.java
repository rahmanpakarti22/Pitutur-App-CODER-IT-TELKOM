package com.example.pitutur.Lupa_Password;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pitutur.Custom_loading_Bar;
import com.example.pitutur.Login.Login;
import com.example.pitutur.R;
import com.google.firebase.auth.FirebaseAuth;

public class Lupa_Password extends AppCompatActivity
{
    //deklarasi variabel object
    ImageButton close_btn;
    EditText emai_pemulihan_akun;
    Button proses_btn;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lupa_password_act);

        emai_pemulihan_akun  = findViewById(R.id.emai_pemulihan_akun);

        proses_btn           = findViewById(R.id.proses_btn);
        close_btn            = findViewById(R.id.close_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        close_btn.setOnClickListener(v -> {
            startActivity(new Intent(Lupa_Password.this, Login.class));
            finish();
        });

        proses_btn.setOnClickListener(v -> {
            loading_bar.StartDialog();
            recoverPassword();
        });
    }

    final Custom_loading_Bar loading_bar = new Custom_loading_Bar(Lupa_Password.this);

    private void recoverPassword()
    {
        String email = emai_pemulihan_akun.getText().toString();

        if (TextUtils.isEmpty(email))
        {
            Toast.makeText(Lupa_Password.this, "Email Harus Diisi", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Toast.makeText(Lupa_Password.this, "Email Salah", Toast.LENGTH_SHORT).show();
        }
        else
        {
            firebaseAuth.sendPasswordResetEmail(email)
                    .addOnSuccessListener(unused ->
                    {
                        //mengirim permintaan ganti password
                        loading_bar.CloseDialog();
                        Toast.makeText(Lupa_Password.this, "Email Pemulihan Sudah Dikirim Melalui Email Kamu", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                    {
                        //gagal mengirim permintaan
                        loading_bar.CloseDialog();
                        Toast.makeText(Lupa_Password.this, "Yah Gagal Silahkan Coba Kembali", Toast.LENGTH_SHORT).show();
                    });
        }
    }
}