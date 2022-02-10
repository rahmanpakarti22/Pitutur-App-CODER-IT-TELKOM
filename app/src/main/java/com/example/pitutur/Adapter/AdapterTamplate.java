package com.example.pitutur.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pitutur.Detail;
import com.example.pitutur.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterTamplate extends RecyclerView.Adapter<AdapterTamplate.HolderTmaplateUser>
{
    private final Context context;
    public ArrayList<ModelTamplate> tamplateList;

    public AdapterTamplate(Context context, ArrayList<ModelTamplate> tamplateList)
    {
        this.context = context;
        this.tamplateList = tamplateList;
    }

    @NonNull
    @Override
    public HolderTmaplateUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.row_tamplate, parent, false);
        return new HolderTmaplateUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderTmaplateUser holder, int position)
    {
        ModelTamplate modelTamplate = tamplateList.get(position);
        String judul_tamplate = modelTamplate.getJudul_Tamplate();
        String jenis_tamplate  = modelTamplate.getJenis_Tamplate();
        String timestamp = modelTamplate.getTimestamp();

        holder.judul_tamplate_TV.setText(judul_tamplate);
        holder.date_tamplate_TV.setText(timestamp);
        holder.jns_tamplate_TV.setText(jenis_tamplate);

        holder.press.setOnClickListener(v -> detailBottomSheet(modelTamplate));
    }

    private void detailBottomSheet(ModelTamplate modelTamplate)
    {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context,R.style.SheetDialog);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(context).inflate(R.layout.bottomdialog, null);
        bottomSheetDialog.setContentView(view);

        ImageButton hapus_btn = view.findViewById(R.id.hapus_btn);
        ImageButton edit_btn  = view.findViewById(R.id.edit_btn);

        String judul_tamplate = modelTamplate.getJudul_Tamplate();
        String id_tamplate = modelTamplate.getID_Tamplate();

        bottomSheetDialog.show();

        hapus_btn.setOnClickListener(view1 ->
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Hapus")
                    .setMessage("Anda Yakin Ingin Mengpaus Tamplate "+judul_tamplate+" ?")
                    .setPositiveButton("HAPUS", (dialogInterface, i) -> delete(id_tamplate))
                    .setNegativeButton("BATAL", (dialogInterface, i) -> dialogInterface.dismiss())
                    .show();

            bottomSheetDialog.dismiss();
        });

        edit_btn.setOnClickListener(v ->
        {
            Intent intent = new Intent(context, Detail.class);
            intent.putExtra("ID_Tamplate", id_tamplate);
            context.startActivity(intent);
        });
    }


    private void delete(String id_tamplate)
    {
        FirebaseAuth firebaseAuth   = FirebaseAuth.getInstance();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(Objects.requireNonNull(firebaseAuth.getUid())).child("Tamplate").child(id_tamplate).removeValue()
                .addOnSuccessListener(unused -> Toast.makeText(context, "Tamplate Dihapus", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(context, "Tamplate Gagal Dihapus", Toast.LENGTH_SHORT).show());
    }

    @Override
    public int getItemCount()
    {
        return tamplateList.size();
    }

    static class HolderTmaplateUser extends RecyclerView.ViewHolder
    {
        private final TextView judul_tamplate_TV;
        private final TextView date_tamplate_TV;
        private final TextView jns_tamplate_TV;
        private final LinearLayout press;

        public HolderTmaplateUser(@NonNull View itemView)
        {
            super(itemView);

            judul_tamplate_TV = itemView.findViewById(R.id.judul_tamplate_TV);
            date_tamplate_TV  = itemView.findViewById(R.id.date_tamplate_TV);
            jns_tamplate_TV   = itemView.findViewById(R.id.jns_tamplate_TV);
            press             = itemView.findViewById(R.id.press);
        }
    }
}
