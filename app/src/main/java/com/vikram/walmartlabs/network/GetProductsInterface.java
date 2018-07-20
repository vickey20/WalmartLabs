package com.vikram.walmartlabs.network;

import com.vikram.walmartlabs.model.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetProductsInterface {

    @GET("/walmartproducts/{page}/{count}")
    Call<ProductResponse> getProducts(@Path("page") int page, @Path("count") int count);
}
