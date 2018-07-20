package com.vikram.walmartlabs.model;

import com.google.gson.annotations.SerializedName;
import com.vikram.walmartlabs.model.Product;

import java.util.List;

public class ProductResponse {
    @SerializedName("products")
    private List<Product> products;
    @SerializedName("totalProducts")
    private String totalProducts;
    @SerializedName("pageNumber")
    private int pageNumber;
    @SerializedName("pageSize")
    private int pageSize;
    @SerializedName("statusCode")
    private int statusCode;

    public ProductResponse(List<Product> products, String totalProducts, int pageNumber, int pageSize, int statusCode) {
        this.products = products;
        this.totalProducts = totalProducts;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.statusCode = statusCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(String totalProducts) {
        this.totalProducts = totalProducts;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
