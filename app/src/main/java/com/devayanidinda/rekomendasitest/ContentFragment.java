package com.devayanidinda.rekomendasitest;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ContentFragment extends Fragment {
    Activity context;
    private List<ProdukDAO> ProdukListCategory, ProdukListPrincipal;
    private RecyclerView recyclerView, recycleView;
    private ProdukAdapter recycleAdapterCat, recycleAdapterPrin;
    private SwipeRefreshLayout refreshLayout, refresh;
    private TextInputEditText kodeProduk;
    private Button showReco;
    private String URLCategory = "https://5255ioe5u6.execute-api.ap-southeast-1.amazonaws.com/Prod/categoryReco/";
    private String URLPrincipal = "https://5255ioe5u6.execute-api.ap-southeast-1.amazonaws.com/Prod/principalReco/";
    private String kode_produk;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_content, container, false);
        kodeProduk = view.findViewById(R.id.kode_produk);
        showReco = view.findViewById(R.id.buttonReco);

        showReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecoCategory();
                getRecoPrincipal();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProdukListCategory = new ArrayList<>();
        ProdukListPrincipal = new ArrayList<>();
        context = getActivity();

        refreshLayout = view.findViewById(R.id.swipe_refresh);
        refresh = view.findViewById(R.id.swipe);

        recyclerView = view.findViewById(R.id.recycle_tampil_reco);
        recycleView = view.findViewById(R.id.recycle_reco);

        recycleAdapterCat = new ProdukAdapter(ProdukListCategory, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapterCat);

        recycleAdapterPrin = new ProdukAdapter(ProdukListPrincipal, getActivity());
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(recycleAdapterPrin);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProdukListCategory.clear();
                getRecoCategory();
                refreshLayout.setRefreshing(false);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProdukListCategory.clear();
                getRecoPrincipal();
                refreshLayout.setRefreshing(false);
            }
        });
    }

    public void getRecoCategory(){
        kode_produk = kodeProduk.getText().toString();
        System.out.println(kode_produk);
        final String url = URLCategory + kode_produk;
        System.out.println(url);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                ProdukDAO r = new ProdukDAO(objectReview.getString("product_code"),
                                        objectReview.getString("product_name"));

                                System.out.println(objectReview);
                                ProdukListCategory.add(r);
                            }
                            recycleAdapterCat.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Kesalahan Koneksi!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }

    public void getRecoPrincipal(){
        kode_produk = kodeProduk.getText().toString();
        System.out.println(kode_produk);
        final String url = URLPrincipal + kode_produk;
        System.out.println(url);
        RequestQueue queue = Volley.newRequestQueue(getActivity());

        StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String produk = jsonObject.getString("message");
                            JSONArray jsonArray = new JSONArray(produk);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject objectReview = jsonArray.getJSONObject(i);
                                ProdukDAO r = new ProdukDAO(objectReview.getString("product_code"),
                                        objectReview.getString("product_name"));

                                System.out.println(objectReview);
                                ProdukListPrincipal.add(r);
                            }
                            recycleAdapterPrin.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Kesalahan Koneksi!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(getRequest);
    }
}