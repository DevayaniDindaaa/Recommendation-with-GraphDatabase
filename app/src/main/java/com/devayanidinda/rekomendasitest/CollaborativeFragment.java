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

public class CollaborativeFragment extends Fragment {
    Activity context;
    private List<ProdukDAO> ProdukListHistory, ProdukListLocation;
    private RecyclerView recyclerView, recycleView;
    private ProdukAdapter recycleAdapterHis, recycleAdapterLoc;
    private SwipeRefreshLayout refreshLayout, refresh;
    private TextInputEditText namaApotek;
    private Button showReco;
    private String URLHistory = "https://5255ioe5u6.execute-api.ap-southeast-1.amazonaws.com/Prod/historyReco/";
    private String URLLocation = "https://5255ioe5u6.execute-api.ap-southeast-1.amazonaws.com/Prod/locationReco/";
    private String nama_apotek;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collaborative, container, false);
        namaApotek = view.findViewById(R.id.apotek);
        showReco = view.findViewById(R.id.buttonReco);

        showReco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRecoHistory();
                getRecoLocation();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProdukListHistory = new ArrayList<>();
        ProdukListLocation = new ArrayList<>();
        context = getActivity();
        refreshLayout = view.findViewById(R.id.swipe_refresh);
        refresh = view.findViewById(R.id.swipe);
        recyclerView = view.findViewById(R.id.recycle_tampil_reco);
        recycleView = view.findViewById(R.id.recycle_reco);

        recycleAdapterHis = new ProdukAdapter(ProdukListHistory, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(recycleAdapterHis);

        recycleAdapterLoc = new ProdukAdapter(ProdukListLocation, getActivity());
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        recycleView.setItemAnimator(new DefaultItemAnimator());
        recycleView.setAdapter(recycleAdapterLoc);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProdukListHistory.clear();
                getRecoHistory();
                refreshLayout.setRefreshing(false);
            }
        });

        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ProdukListLocation.clear();
                getRecoLocation();
                refresh.setRefreshing(false);
            }
        });
    }

    public void getRecoHistory(){
        nama_apotek = namaApotek.getText().toString();
        System.out.println(nama_apotek);
        final String url = URLHistory + nama_apotek;
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
                                ProdukListHistory.add(r);
                            }
                            recycleAdapterHis.notifyDataSetChanged();
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

    public void getRecoLocation(){
        nama_apotek = namaApotek.getText().toString();
        System.out.println(nama_apotek);
        final String url = URLLocation + nama_apotek;
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
                                ProdukListLocation.add(r);
                            }
                            recycleAdapterLoc.notifyDataSetChanged();
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