package com.devayanidinda.rekomendasitest;

public class ProdukDAO {
    private String product_code, product_name;

    public ProdukDAO(String product_code, String product_name) {
        this.product_code = product_code;
        this.product_name = product_name;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
