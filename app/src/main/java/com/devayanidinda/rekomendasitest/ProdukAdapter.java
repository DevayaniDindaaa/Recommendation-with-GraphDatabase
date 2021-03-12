package com.devayanidinda.rekomendasitest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder> {
    private List<ProdukDAO> produkList;
    private Context context;

    public ProdukAdapter(List<ProdukDAO> produkList, Context context) {
        this.produkList = produkList;
        this.context = context;
    }

    @NonNull
    @Override
    public ProdukAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycle_adapter_reco, parent, false);
        final ProdukAdapter.MyViewHolder holder = new ProdukAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.MyViewHolder holder, int position) {
        final ProdukDAO row = produkList.get(position);
        holder.nama_produk.setText(row.getProduct_name());
        holder.kode_produk.setText(row.getProduct_code());
        holder.harga_produk.setText("Rp 000000,00");
    }

    @Override
    public int getItemCount() {
        return produkList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout recycler_produk;
        TextView nama_produk, kode_produk, harga_produk;

        public MyViewHolder(@NonNull  View itemView) {
            super(itemView);
            recycler_produk = itemView.findViewById(R.id.recycle_produk);
            nama_produk = itemView.findViewById(R.id.nama_produk);
            kode_produk = itemView.findViewById(R.id.kode_produk);
            harga_produk = itemView.findViewById(R.id.harga_produk);
        }
    }
}