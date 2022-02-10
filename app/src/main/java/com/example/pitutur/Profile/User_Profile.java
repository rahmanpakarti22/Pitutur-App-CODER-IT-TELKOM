package com.example.pitutur.Profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pitutur.Custom_loading_Bar;
import com.example.pitutur.Dashboard.Home;
import com.example.pitutur.Login.Login;
import com.example.pitutur.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;
import java.util.Objects;

public class User_Profile extends AppCompatActivity
{

    EditText prof_nama_lengkapET, prof_nimET, prof_jurusanET;
    Button simpan_button;
    ImageButton logout_btn, close_btn;
    LinearLayout pilih_foto;
    ImageView profile_userIv;
    FirebaseAuth firebaseAuth;

    private final int REQUEST_CODE_GALLERY = 1;
    private Uri Image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        prof_nama_lengkapET = findViewById(R.id.prof_nama_lengkapET);
        prof_nimET          = findViewById(R.id.prof_nimET);
        prof_jurusanET      = findViewById(R.id.prof_jurusanET);

        simpan_button      = findViewById(R.id.simpan_button);
        logout_btn         = findViewById(R.id.logout_btn);
        close_btn          = findViewById(R.id.close_btn);

        profile_userIv     = findViewById(R.id.profile_userIv);
        pilih_foto         = findViewById(R.id.pilih_foto);

        firebaseAuth = FirebaseAuth.getInstance();
        loadMyInfo();

        close_btn.setOnClickListener(v -> {
            startActivity(new Intent(User_Profile.this, Home.class));
            finish();
        });

        simpan_button.setOnClickListener(v -> save_data());

        pilih_foto.setOnClickListener(v -> addImage());

        logout_btn.setOnClickListener(v -> makeMeOffline());
    }

    final Custom_loading_Bar loading_bar = new Custom_loading_Bar(User_Profile.this);

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
                            String photo       = ""+ds.child("profileImage").getValue();
                            String nama        = ""+ds.child("nama").getValue();
                            String nim         = ""+ds.child("nim").getValue();
                            String jurusan     = ""+ds.child("jurusan").getValue();

                            prof_nama_lengkapET.setText(nama);
                            prof_nimET.setText(nim);
                            prof_jurusanET.setText(jurusan);

                            try
                            {
                                Picasso.with(User_Profile.this).load(photo).placeholder(R.drawable.empty_foto).fit().centerCrop().into(profile_userIv);
                            }

                            catch (Exception e)
                            {
                                profile_userIv.setImageResource(R.drawable.empty_foto);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {

                    }
                });
    }

    private String nama_lengkap;
    private String nim_pengguna;
    private String jurusan_pengguna;

    private void save_data()
    {
        nama_lengkap           = prof_nama_lengkapET.getText().toString().trim();
        nim_pengguna           = prof_nimET.getText().toString().trim();
        jurusan_pengguna       = prof_jurusanET.getText().toString().trim();

        loading_bar.StartDialog();
        updateprofile();
    }

    private void updateprofile()
    {
        //update data pengguna tanpa upload foto
        if (Image_uri == null)
        {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("nama",""          +nama_lengkap);
            hashMap.put("nim",""           +nim_pengguna);
            hashMap.put("jurusan",""       +jurusan_pengguna);

            //update data
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
            ref.child(Objects.requireNonNull(firebaseAuth.getUid())).updateChildren(hashMap)
                    .addOnSuccessListener(unused ->
                    {
                        loading_bar.CloseDialog();
                        Toast.makeText(User_Profile.this, "Data Terupdate", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                    {
                        loading_bar.CloseDialog();
                        Toast.makeText(User_Profile.this, "Yah Datanya Gagal Diupdate", Toast.LENGTH_SHORT).show();
                    });
        }

        //update data dengan upload foto
        else
        {
            //upload gambar terlebih dahulu
            String filePathAndName = "profile_image/" + ""+firebaseAuth.getUid();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
            storageReference.putFile(Image_uri)
                    .addOnSuccessListener(taskSnapshot ->
                    {
                        //upload gambar, mendapatkan url dari gambar yg terupload
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        Uri downloadImageUri = uriTask.getResult();

                        if (uriTask.isSuccessful())
                        {
                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("nama",""          +nama_lengkap);
                            hashMap.put("nim",""           +nim_pengguna);
                            hashMap.put("jurusan",""       +jurusan_pengguna);
                            hashMap.put("profileImage",""  +downloadImageUri);

                            //update data
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                            ref.child(Objects.requireNonNull(firebaseAuth.getUid())).updateChildren(hashMap)
                                    .addOnSuccessListener(unused ->
                                    {
                                        loading_bar.CloseDialog();
                                        Toast.makeText(User_Profile.this, "Data Terupdate", Toast.LENGTH_SHORT).show();
                                    })
                                    .addOnFailureListener(e ->
                                    {
                                        loading_bar.CloseDialog();
                                        Toast.makeText(User_Profile.this, "Yah Datanya Gagal Diupdate", Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(e ->
                    {

                    });
        }
    }

    public void addImage()
    {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE_GALLERY);
    }

    private void makeMeOffline()
    {
        //setelah login buat status menjadi online
        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("online","false");

        //update data ke db
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.child(Objects.requireNonNull(firebaseAuth.getUid())).updateChildren(hashMap)
                .addOnSuccessListener(unused ->
                {
                    //berhasil update
                    firebaseAuth.signOut();
                    checkuser();
                })
                .addOnFailureListener(e ->
                {
                    //gagal update
                    Toast.makeText(User_Profile.this, "Database Error", Toast.LENGTH_SHORT).show();
                });

    }

    private void checkuser()
    {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null)
        {
            Toast.makeText(User_Profile.this, "Berhasil Keluar", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(User_Profile.this, Login.class));
            finish();
        }
        else
        {
            loadMyInfo();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        //Mengarahkan user pada halaman Cropping Image
        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK)
        {
            assert data != null;
            Image_uri = data.getData();
            CropImage.activity(Image_uri)
                    .setAspectRatio(1,1)
                    .start(this);
        }

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK)
            {
                assert result != null;
                Image_uri = result.getUri();
                Picasso.with(this)
                        .load(Image_uri)
                        .into(profile_userIv);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}