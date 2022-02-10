package com.example.pitutur.Tamplate_Detail;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pitutur.Custom_loading_Bar;
import com.example.pitutur.Dashboard.Home;
import com.example.pitutur.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Tamplate_detail extends AppCompatActivity
{
    ImageButton close_btn, simpan_btn, salin_btn;
    RelativeLayout sdh_waktunya, blm_waktunya;
    LinearLayout bc_salam_one, bc_salam_two, pagi_bc, siang_bc, sore_bc, bc_gender_two, bc_gender_one;
    TextView jenis_tamplate, salam_one, salam_two, tvsore, tvsiang, tvpagi, gender_one_tv, gender_two_tv;
    EditText tamplate_ET, judul_tamplate_ET;
    FirebaseAuth firebaseAuth;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tamplate_detail);

        close_btn  = findViewById(R.id.close_btn);
        simpan_btn = findViewById(R.id.simpan_btn);
        salin_btn  = findViewById(R.id.salin_btn);

        sdh_waktunya  = findViewById(R.id.sdh_waktunya);
        blm_waktunya  = findViewById(R.id.blm_waktunya);

        bc_salam_one  = findViewById(R.id.bc_salam_one);
        bc_salam_two  = findViewById(R.id.bc_salam_two);
        pagi_bc   = findViewById(R.id.pagi_bc);
        siang_bc  = findViewById(R.id.siang_bc);
        sore_bc   = findViewById(R.id.sore_bc);
        bc_gender_two  = findViewById(R.id.bc_gender_two);
        bc_gender_one  = findViewById(R.id.bc_gender_one);

        salam_one  = findViewById(R.id.salam_one);
        salam_two  = findViewById(R.id.salam_two);
        tvsore  = findViewById(R.id.tvsore);
        tvsiang = findViewById(R.id.tvsiang);
        tvpagi  = findViewById(R.id.tvpagi);
        gender_one_tv  = findViewById(R.id.gender_one_tv);
        gender_two_tv  = findViewById(R.id.gender_two_tv);

        jenis_tamplate    = findViewById(R.id.jenis_tamplate);
        tamplate_ET       = findViewById(R.id.tamplate_ET);
        judul_tamplate_ET = findViewById(R.id.judul_tamplate_ET);

        firebaseAuth = FirebaseAuth.getInstance();

        String jenisTamplate = getIntent().getStringExtra("Jenis_Tamplate");
        String isiTamplate = getIntent().getStringExtra("Isi_Tamplate");
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

        Calendar c     = Calendar.getInstance();
        int timeOfDay  = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 8 && timeOfDay < 16)
        {
            blm_waktunya.setVisibility(View.GONE);
            sdh_waktunya.setVisibility(View.VISIBLE);
            salin_btn.setVisibility(View.VISIBLE);
        }
        else if (timeOfDay >= 17 && timeOfDay <23)
        {
            blm_waktunya.setVisibility(View.VISIBLE);
            sdh_waktunya.setVisibility(View.GONE);
            salin_btn.setVisibility(View.GONE);
        }
        else if (timeOfDay >= 7)
        {
            blm_waktunya.setVisibility(View.VISIBLE);
            sdh_waktunya.setVisibility(View.GONE);
            salin_btn.setVisibility(View.GONE);
        }
        else
        {
            blm_waktunya.setVisibility(View.GONE);
            sdh_waktunya.setVisibility(View.VISIBLE);
            salin_btn.setVisibility(View.VISIBLE);
        }


        close_btn.setOnClickListener(v -> {
            startActivity(new Intent(Tamplate_detail.this, Home.class));
            finish();
        });

        bc_salam_one.setOnClickListener(v ->
        {
            bc_salam_one.setBackgroundResource(R.drawable.salam_shape);
            bc_salam_two.setBackgroundResource(R.drawable.button__salam_shape);
            salam_one.setTextColor(Color.parseColor("#F9F9F9"));
            salam_two.setTextColor(Color.parseColor("#1B6CA8"));

            pagi_bc.setOnClickListener(v1 -> {
                pagi_bc.setBackgroundResource(R.drawable.salam_shape_2);
                siang_bc.setBackgroundResource(R.drawable.button__salam_shape);
                sore_bc.setBackgroundResource(R.drawable.button__salam_shape);
                tvsiang.setTextColor(Color.parseColor("#1B6CA8"));
                tvpagi.setTextColor(Color.parseColor("#F9F9F9"));
                tvsiang.setTextColor(Color.parseColor("#1B6CA8"));
                tvsore.setTextColor(Color.parseColor("#1B6CA8"));

                tamplate_ET.setText("Assalamualaikum selamat pagi "+isiTamplate);

                bc_gender_one.setOnClickListener(v11 -> {
                    bc_gender_one.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_two.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_two_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_one_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Assalamualaikum selamat pagi bapak dosen yang terhormat "+isiTamplate);
                });

                bc_gender_two.setOnClickListener(v112 -> {
                    bc_gender_two.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_one.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_one_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_two_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Assalamualaikum selamat pagi ibu dosen yang terhormat "+isiTamplate);
                });
            });

            siang_bc.setOnClickListener(v12 -> {
                pagi_bc.setBackgroundResource(R.drawable.button__salam_shape);
                sore_bc.setBackgroundResource(R.drawable.button__salam_shape);
                siang_bc.setBackgroundResource(R.drawable.salam_shape_2);
                tvpagi.setTextColor(Color.parseColor("#1B6CA8"));
                tvsiang.setTextColor(Color.parseColor("#F9F9F9"));
                tvsore.setTextColor(Color.parseColor("#1B6CA8"));

                tamplate_ET.setText("Assalamualaikum selamat siang "+isiTamplate);

                bc_gender_one.setOnClickListener(v121 -> {
                    bc_gender_one.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_two.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_two_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_one_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Assalamualaikum selamat siang bapak dosen yang terhormat "+isiTamplate);
                });

                bc_gender_two.setOnClickListener(v1212 -> {
                    bc_gender_two.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_one.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_one_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_two_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Assalamualaikum selamat siang ibu dosen yang terhormat "+isiTamplate);
                });
            });

            sore_bc.setOnClickListener(v13 -> {
                pagi_bc.setBackgroundResource(R.drawable.button__salam_shape);
                siang_bc.setBackgroundResource(R.drawable.button__salam_shape);
                sore_bc.setBackgroundResource(R.drawable.salam_shape_2);
                tvpagi.setTextColor(Color.parseColor("#1B6CA8"));
                tvsiang.setTextColor(Color.parseColor("#1B6CA8"));
                tvsore.setTextColor(Color.parseColor("#F9F9F9"));

                tamplate_ET.setText("Assalamualaikum selamat sore "+isiTamplate);

                bc_gender_one.setOnClickListener(v131 -> {
                    bc_gender_one.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_two.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_two_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_one_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Assalamualaikum selamat sore bapak dosen yang terhormat "+isiTamplate);
                });

                bc_gender_two.setOnClickListener(v1312 -> {
                    bc_gender_two.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_one.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_one_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_two_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Assalamualaikum selamat sore ibu dosen yang terhormat "+isiTamplate);
                });
            });
        });

        bc_salam_two.setOnClickListener(v -> {
            bc_salam_one.setBackgroundResource(R.drawable.button__salam_shape);
            bc_salam_two.setBackgroundResource(R.drawable.salam_shape);
            salam_two.setTextColor(Color.parseColor("#F9F9F9"));
            salam_one.setTextColor(Color.parseColor("#1B6CA8"));

            pagi_bc.setOnClickListener(v18 -> {
                pagi_bc.setBackgroundResource(R.drawable.salam_shape_2);
                siang_bc.setBackgroundResource(R.drawable.button__salam_shape);
                sore_bc.setBackgroundResource(R.drawable.button__salam_shape);
                tvsiang.setTextColor(Color.parseColor("#1B6CA8"));
                tvpagi.setTextColor(Color.parseColor("#F9F9F9"));
                tvsiang.setTextColor(Color.parseColor("#1B6CA8"));
                tvsore.setTextColor(Color.parseColor("#1B6CA8"));

                tamplate_ET.setText("Selamat pagi "+isiTamplate);

                bc_gender_one.setOnClickListener(v17 -> {
                    bc_gender_one.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_two.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_two_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_one_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Selamat pagi bapak dosen yang terhormat "+isiTamplate);
                });

                bc_gender_two.setOnClickListener(v16 -> {
                    bc_gender_two.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_one.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_one_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_two_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Selamat pagi ibu dosen yang terhormat "+isiTamplate);
                });
            });

            siang_bc.setOnClickListener(v15 -> {
                pagi_bc.setBackgroundResource(R.drawable.button__salam_shape);
                sore_bc.setBackgroundResource(R.drawable.button__salam_shape);
                siang_bc.setBackgroundResource(R.drawable.salam_shape_2);
                tvpagi.setTextColor(Color.parseColor("#1B6CA8"));
                tvsiang.setTextColor(Color.parseColor("#F9F9F9"));
                tvsore.setTextColor(Color.parseColor("#1B6CA8"));

                tamplate_ET.setText("Selamat siang "+isiTamplate);

                bc_gender_one.setOnClickListener(v1512 -> {
                    bc_gender_one.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_two.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_two_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_one_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Selamat siang bapak dosen yang terhormat "+isiTamplate);
                });

                bc_gender_two.setOnClickListener(v151 -> {
                    bc_gender_two.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_one.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_one_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_two_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Selamat siang ibu dosen yang terhormat "+isiTamplate);
                });
            });

            sore_bc.setOnClickListener(v14 -> {
                pagi_bc.setBackgroundResource(R.drawable.button__salam_shape);
                siang_bc.setBackgroundResource(R.drawable.button__salam_shape);
                sore_bc.setBackgroundResource(R.drawable.salam_shape_2);
                tvpagi.setTextColor(Color.parseColor("#1B6CA8"));
                tvsiang.setTextColor(Color.parseColor("#1B6CA8"));
                tvsore.setTextColor(Color.parseColor("#F9F9F9"));

                tamplate_ET.setText("Selamat sore "+isiTamplate);

                bc_gender_one.setOnClickListener(v141 -> {
                    bc_gender_one.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_two.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_two_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_one_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Selamat sore bapak dosen yang terhormat "+isiTamplate);
                });

                bc_gender_two.setOnClickListener(v1412 -> {
                    bc_gender_two.setBackgroundResource(R.drawable.salam_shape_2);
                    bc_gender_one.setBackgroundResource(R.drawable.button__salam_shape);
                    gender_one_tv.setTextColor(Color.parseColor("#1B6CA8"));
                    gender_two_tv.setTextColor(Color.parseColor("#F9F9F9"));

                    tamplate_ET.setText("Selamat sore ibu dosen yang terhormat "+isiTamplate);
                });
            });
        });

        jenis_tamplate.setText(jenisTamplate);

        simpan_btn.setOnClickListener(v -> input_data_tamplate());

        salin_btn.setOnClickListener(v ->
        {
            String cp = tamplate_ET.getText().toString();
            ClipData clipData = ClipData.newPlainText("text", cp);
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(this, "Tamplate "+jenisTamplate+" tersalin", Toast.LENGTH_SHORT).show();
        });

    }

    final Custom_loading_Bar loading_bar = new Custom_loading_Bar(Tamplate_detail.this);

    private String Judul, Jenis_Tamplate, Isi_Tamplate;
    private void input_data_tamplate()
    {
        //memasukan data
        Judul          = judul_tamplate_ET.getText().toString().trim();
        Jenis_Tamplate = jenis_tamplate.getText().toString().trim();
        Isi_Tamplate   = tamplate_ET.getText().toString().trim();

        if (TextUtils.isEmpty(Judul))
        {
            Toast.makeText(Tamplate_detail.this, "Judul Tamplate Harus Diisi !!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            loading_bar.StartDialog();
            sendData();
        }
    }

    private void sendData()
    {
        Calendar calendar = Calendar.getInstance();
        String currenDate = DateFormat.getDateInstance().format(calendar.getTime());
        String timestap = ""+System.currentTimeMillis();

        HashMap<String, Object> hashMap =  new HashMap<>();
        hashMap.put("uid",""               +firebaseAuth.getUid());
        hashMap.put("ID_Tamplate",""        +timestap);
        hashMap.put("Judul_Tamplate",""    +Judul);
        hashMap.put("Jenis_Tamplate",""    +Jenis_Tamplate);
        hashMap.put("Isi_amplate",""       +Isi_Tamplate);
        hashMap.put("timestamp",""         +currenDate);

        //Menyimpan informasi akun ke db
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Tamplate").child(timestap).setValue(hashMap)
                .addOnSuccessListener(unused ->
                {
                    loading_bar.CloseDialog();
                    Toast.makeText(Tamplate_detail.this, "Tamplate Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                {
                    loading_bar.CloseDialog();
                    Toast.makeText(Tamplate_detail.this, "Yah Datanya Gagal Diupdate", Toast.LENGTH_SHORT).show();
                });
    }
}