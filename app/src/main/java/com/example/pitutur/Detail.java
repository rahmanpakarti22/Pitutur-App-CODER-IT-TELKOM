package com.example.pitutur;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pitutur.Dashboard.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class Detail extends AppCompatActivity
{
    private String ID_tamplate;
    ImageButton close_btn, simpan_btn, salin_btn;
    RelativeLayout sdh_waktunya, blm_waktunya;
    EditText tamplate_ET, judul_tamplate_ET;
    TextView jenis_tamplate;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        close_btn  = findViewById(R.id.close_btn);
        simpan_btn = findViewById(R.id.simpan_btn);
        salin_btn  = findViewById(R.id.salin_btn);

        sdh_waktunya  = findViewById(R.id.sdh_waktunya);
        blm_waktunya  = findViewById(R.id.blm_waktunya);

        jenis_tamplate  = findViewById(R.id.jenis_tamplate);

        judul_tamplate_ET = findViewById(R.id.judul_tamplate_ET);
        tamplate_ET       = findViewById(R.id.tamplate_ET);

        firebaseAuth = FirebaseAuth.getInstance();
        ID_tamplate  = getIntent().getStringExtra("ID_Tamplate");
        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        loadTamplateUser();

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
            startActivity(new Intent(Detail.this, Home.class));
            finish();
        });

        simpan_btn.setOnClickListener(v -> input_data_tamplate());

        salin_btn.setOnClickListener(v ->
        {
            String cp = tamplate_ET.getText().toString();
            ClipData clipData = ClipData.newPlainText("text", cp);
            clipboardManager.setPrimaryClip(clipData);

            Toast.makeText(this, "Tamplate tersalin", Toast.LENGTH_SHORT).show();
        });
    }

    final Custom_loading_Bar loading_bar = new Custom_loading_Bar(Detail.this);

    private String Judul, Jenis_Tamplate, Isi_Tamplate;
    private void input_data_tamplate()
    {
        //memasukan data
        Judul          = judul_tamplate_ET.getText().toString().trim();
        Jenis_Tamplate = jenis_tamplate.getText().toString().trim();
        Isi_Tamplate   = tamplate_ET.getText().toString().trim();

        loading_bar.StartDialog();
        updateTamplate();
    }

    private void updateTamplate()
    {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Judul_Tamplate",""          +Judul);
        hashMap.put("Jenis_Tamplate",""          +Jenis_Tamplate);
        hashMap.put("Isi_amplate",""             +Isi_Tamplate);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Tamplate").child(ID_tamplate).updateChildren(hashMap)
                .addOnSuccessListener(unused ->
                {
                    loading_bar.CloseDialog();
                    Toast.makeText(Detail.this, "Datanya Berhasil Diupdate", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(e ->
                {
                    loading_bar.CloseDialog();
                    Toast.makeText(Detail.this, "Yah Datanya Gagal Diupdate", Toast.LENGTH_SHORT).show();
                });
    }

    private void loadTamplateUser()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Tamplate").child(ID_tamplate)
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String judul          = ""+snapshot.child("Judul_Tamplate").getValue();
                        String isi            = ""+snapshot.child("Isi_amplate").getValue();
                        String jenis          = ""+snapshot.child("Jenis_Tamplate").getValue();

                        judul_tamplate_ET.setText(judul);
                        tamplate_ET.setText(isi);
                        jenis_tamplate.setText(jenis);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }
}