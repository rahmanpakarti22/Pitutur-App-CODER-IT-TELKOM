package com.example.pitutur.Register;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Register extends AppCompatActivity
{
    //deklarasi variabel object
    ImageButton back_btn;
    EditText emailET, nama_lengkapET, nimET, jurusanET, passwordET;
    Button btn_register_proses;
    //firebase
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_act);

        //Deklarasi fungsi edit text padq ui aplikasi
        emailET        = findViewById(R.id.emailET);
        nama_lengkapET = findViewById(R.id.nama_lengkapET);
        nimET          = findViewById(R.id.nimET);
        jurusanET      = findViewById(R.id.jurusanET);
        passwordET     = findViewById(R.id.passwordET);


        //Deklarasi fungsi Button padq ui aplikasi
        btn_register_proses   = findViewById(R.id.btn_register_proses);
        back_btn              = findViewById(R.id.back_btn);

        firebaseAuth = FirebaseAuth.getInstance();

        back_btn.setOnClickListener(v -> {
            startActivity(new Intent(Register.this, Login.class));
            finish();
        });

        btn_register_proses.setOnClickListener(v -> validasi_data_registrasi());

    }

    final Custom_loading_Bar loading_bar = new Custom_loading_Bar(Register.this);

    //validsai strings
    private String email_reg;
    private String nama_lengkap_reg;
    private String nim_reg;
    private String jurusan_reg;
    private String password_reg;

    private void validasi_data_registrasi()
    {
        email_reg           = emailET.getText().toString().trim();
        nama_lengkap_reg    = nama_lengkapET.getText().toString().trim();
        nim_reg             = nimET.getText().toString().trim();
        jurusan_reg         = jurusanET.getText().toString().trim();
        password_reg        = passwordET.getText().toString().trim();

        if (!Patterns.EMAIL_ADDRESS.matcher(email_reg).matches())
        {
            Toast.makeText(this, "Email Harus Benar", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nama_lengkap_reg))
        {
            Toast.makeText(this, "Nama Harus Diisi", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(nim_reg))
        {
            Toast.makeText(this, "NIM Harus Diisi", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(jurusan_reg))
        {
            Toast.makeText(this, "Jurusan Harus Diisi", Toast.LENGTH_SHORT).show();
        }
        else if (password_reg.isEmpty() || password_reg.length() < 4)
        {
            Toast.makeText(this, "Password Harus Diisi", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading_bar.StartDialog();
            membuat_akun();
        }
    }

    private void membuat_akun()
    {
        firebaseAuth.createUserWithEmailAndPassword(email_reg,password_reg)
                .addOnSuccessListener(authResult ->
                {
                    //Membuat Akun
                    add_To_Firebase_database();
                })
                .addOnFailureListener(e ->
                {
                    //Gagal Membuat Akun
                    loading_bar.CloseDialog();
                    Toast.makeText(Register.this, "Yah Maaf Akun Kamu Gagal Dibuat", Toast.LENGTH_SHORT).show();
                });
    }

    private void add_To_Firebase_database()
    {
        Calendar calendar = Calendar.getInstance();
        String currenDate = DateFormat.getDateInstance().format(calendar.getTime());

        //menyimpan data tanpa foto profile
            HashMap<String, Object> hashMap =  new HashMap<>();
            hashMap.put("uid",""             +firebaseAuth.getUid());
            hashMap.put("accountType","user");
            hashMap.put("email",""           +email_reg);
            hashMap.put("jurusan",""         +jurusan_reg);
            hashMap.put("nama",""            +nama_lengkap_reg);
            hashMap.put("nim",""             +nim_reg);
            hashMap.put("password",""        +password_reg);
            hashMap.put("timestamp",""       +currenDate);
            hashMap.put("online","true");
            hashMap.put("profileImage","");

            //Menyimpan informasi akun ke db
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
            databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).setValue(hashMap)
                    .addOnSuccessListener(unused ->
                    {
                        //data terupdate
                        loading_bar.CloseDialog();
                        startActivity(new Intent(Register.this, Login.class));
                        finish();
                    })
                    .addOnFailureListener(e ->
                    {
                        loading_bar.CloseDialog();
                        Toast.makeText(Register.this, "Maaf Akun Kamu Gagal Dibuat", Toast.LENGTH_SHORT).show();
                    });

    }
}