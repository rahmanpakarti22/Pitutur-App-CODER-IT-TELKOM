package com.example.pitutur.Dashboard;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pitutur.Adapter.AdapterTamplate;
import com.example.pitutur.Adapter.ModelTamplate;
import com.example.pitutur.R;
import com.example.pitutur.Tamplate_Detail.Tamplate_detail;
import com.example.pitutur.Profile.User_Profile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class Home extends AppCompatActivity
{

    TextView nama_pengguna, nim_pengguna, jurusan_pengguna;
    ImageView profileIv;
    LinearLayout goto_profile;
    RecyclerView riwayat_tamplate;
    ImageButton tamplate_one, tamplate_two, tamplate_three, tamplate_four, tamplate_five;
    FirebaseAuth firebaseAuth;

    private ArrayList<ModelTamplate> tamplateList;
    private AdapterTamplate adapterTamplate;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_act);

        nama_pengguna     = findViewById(R.id.nama_pengguna);
        nim_pengguna      = findViewById(R.id.nim_pengguna);
        jurusan_pengguna  = findViewById(R.id.jurusan_pengguna);

        tamplate_one   = findViewById(R.id.tamplate_one);
        tamplate_two   = findViewById(R.id.tamplate_two);
        tamplate_three = findViewById(R.id.tamplate_three);
        tamplate_four  = findViewById(R.id.tamplate_four);
        tamplate_five  = findViewById(R.id.tamplate_five);

        riwayat_tamplate  = findViewById(R.id.riwayat_tamplate);

        profileIv         = findViewById(R.id.profileIv);
        goto_profile      = findViewById(R.id.goto_profile);

        firebaseAuth = FirebaseAuth.getInstance();
        loadMyInfo();
        Load_riwayat();

        goto_profile.setOnClickListener(v -> {
            startActivity(new Intent(Home.this, User_Profile.class));
            finish();
        });

        tamplate_one.setOnClickListener(v -> Tamplate_One());

        tamplate_two.setOnClickListener(v -> Tamplate_Two());

        tamplate_three.setOnClickListener(v -> Tamplate_Three());

        tamplate_four.setOnClickListener(v -> Tamplate_Four());

        tamplate_five.setOnClickListener(v -> Tamplate_Five());
    }

    private String photo, nama, nim, jurusan;

    private void loadMyInfo()
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            photo       = ""+ds.child("profileImage").getValue();
                            nama        = ""+ds.child("nama").getValue();
                            nim         = ""+ds.child("nim").getValue();
                            jurusan     = ""+ds.child("jurusan").getValue();

                            nama_pengguna.setText(nama);
                            nim_pengguna.setText(nim);
                            jurusan_pengguna.setText(jurusan);

                            try
                            {
                                Picasso.with(Home.this).load(photo).placeholder(R.drawable.empty_foto).fit().centerCrop().into(profileIv);
                            }

                            catch (Exception e)
                            {
                                profileIv.setImageResource(R.drawable.empty_foto);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }

    public void Tamplate_One()
    {
        Intent gototamplateone = new Intent(Home.this, Tamplate_detail.class);
        gototamplateone.putExtra("Jenis_Tamplate","Izin Kehadiran");
        gototamplateone.putExtra("Isi_Tamplate"," Mohon maaf sebelumnya, saya "+nama+" dari jurusan "+jurusan+" dengan NIM "+nim+ " Mohon izin tidak dapat menghadiri perkuliahan pada(isikan sesuai jadwal kuliah) dikarenakan (isikan urgensi dari alasan kalian). Sekiranya itu saja yang bisa saya sampaikan. Terima kasih sebelumnya");
        startActivity(gototamplateone);
        finish();
    }

    public void Tamplate_Two()
    {
        Intent gototamplatetwo = new Intent(Home.this, Tamplate_detail.class);
        gototamplatetwo.putExtra("Jenis_Tamplate","Bimbingan");
        gototamplatetwo.putExtra("Isi_Tamplate"," Mohon maaf mengganggu waktunya, saya "+nama+" dari jurusan "+jurusan+" dengan NIM "+nim+ " Apakah pada (isikan tanggal untuk bimbingan) bapak/ibu dosen pembimbing berkenan untuk melaksanakan bimbingan untuk '(isikan judul skripsi/proposal penelitian)' Skiranya itu saja yang bisa saya sampaikan. Terima kasih");
        startActivity(gototamplatetwo);
        finish();
    }

    public void Tamplate_Three()
    {
        Intent gototamplatethree = new Intent(Home.this, Tamplate_detail.class);
        gototamplatethree.putExtra("Jenis_Tamplate","Sidang");
        gototamplatethree.putExtra("Isi_Tamplate"," Mohon maaf sebelumnya, saya "+nama+" dari jurusan "+jurusan+" dengan NIM "+nim+ " Sekedar memberitahukan kepada bapak/ibu dosen pembimbing, dikarenakan pada (isikan tanggal sidang skripsi), merupakan sidang skripsi saya dengan judul '(isikan judul skripsi)'. Sekiranya itu saja yang ingin saya sampaikan kepada bapak/ibu dosen pembimbing. Terima kasih sebelumnya");
        startActivity(gototamplatethree);
        finish();
    }

    public void Tamplate_Four()
    {
        Intent gototamplatefour = new Intent(Home.this, Tamplate_detail.class);
        gototamplatefour.putExtra("Jenis_Tamplate","Tanda Tangan");
        gototamplatefour.putExtra("Isi_Tamplate"," Mohon maaf sebelumnya, saya "+nama+" dari jurusan "+jurusan+" dengan NIM "+nim+ " Apabila berkenan untuk menandatangani dokumen (isikan jenis dokumen). Terima kasih sebelumnya ");
        startActivity(gototamplatefour);
        finish();
    }

    public void Tamplate_Five()
    {
        Intent gototamplatefive = new Intent(Home.this, Tamplate_detail.class);
        gototamplatefive.putExtra("Jenis_Tamplate","Revisi");
        gototamplatefive.putExtra("Isi_Tamplate"," Mohon maaf mengganggu waktunya, saya "+nama+" dari jurusan "+jurusan+" dengan NIM "+nim+ " Apakah bapak/ibu dosen pembimbing, pada (isikan tanggal revisi) untuk melakukan revisi pada (isikan jenis dokumen) yang berjudul '(isikan judul dokumen)' Sekiranya itu saja yang ingin saya sampaikan. Terima kasih sebelumnya");
        startActivity(gototamplatefive);
        finish();
    }

    public void Load_riwayat()
    {
        tamplateList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Tamplate")
                .addValueEventListener(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        tamplateList.clear();
                        for (DataSnapshot ds: snapshot.getChildren())
                        {
                            ModelTamplate modelTamplate = ds.getValue(ModelTamplate.class);
                            tamplateList.add(modelTamplate);
                        }

                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);

                        adapterTamplate = new AdapterTamplate(Home.this, tamplateList);
                        //productRv.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
                        riwayat_tamplate.setLayoutManager(linearLayoutManager);
                        riwayat_tamplate.setItemAnimator(new DefaultItemAnimator());
                        riwayat_tamplate.setAdapter(adapterTamplate);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });

    }


}