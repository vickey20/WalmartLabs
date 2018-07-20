package com.vikram.walmartlabs.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.vikram.walmartlabs.network.GetProductsInterface;
import com.vikram.walmartlabs.model.Product;
import com.vikram.walmartlabs.R;
import com.vikram.walmartlabs.adapter.RecyclerViewAdapter;
import com.vikram.walmartlabs.model.ProductResponse;
import com.vikram.walmartlabs.network.RetrofitClientInstance;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Fragment to show a list of products.
 */
public class ProductListFragment extends Fragment {

    private static final String TAG = "ProductListFragment";

    private static final String STATE_PRODUCT = "PRODUCTS";
    private static final String STATE_RECYCLE_LAYOUT = "RECYCLE_LAYOUT";

    private static final int DEFAULT_PAGE_NUM = 1;
    private static final int DEFAULT_PAGE_COUNT = 30;

    private int pageNum = DEFAULT_PAGE_NUM;
    private int pageCount = DEFAULT_PAGE_COUNT;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;

    private Context context;

    private ArrayList<Product> products;
    private int totalItems;

    public interface OnFragmentInteractionListener {
        void onProductClick(Product product);
    }

    public ProductListFragment() {}

    public static ProductListFragment newInstance() {
        ProductListFragment fragment = new ProductListFragment();
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(STATE_RECYCLE_LAYOUT, recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(STATE_PRODUCT, products);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null) {
            products = savedInstanceState.getParcelableArrayList(STATE_PRODUCT);
            setRecyclerView();
            Parcelable savedRecyclerViewState = savedInstanceState.getParcelable(STATE_RECYCLE_LAYOUT);
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        products = new ArrayList<>();
        fetchProducts(DEFAULT_PAGE_NUM, DEFAULT_PAGE_COUNT);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRecyclerView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void setRecyclerView() {
        adapter = new RecyclerViewAdapter(context, products);
        adapter.setOnLoadMoreListener(new RecyclerViewAdapter.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemCount) {
                if (itemCount < totalItems) {
                    pageNum++;
                    fetchProducts(pageNum, pageCount);
                } else {
                    Toast.makeText(context, "You've reached the end", Toast.LENGTH_SHORT).show();
                }
            }
        });

        adapter.setOnItemClickListener(new RecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                mListener.onProductClick(product);
            }
        });

        RecyclerView.LayoutManager lm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    private void fetchProducts(int pageNum, int pageCount) {
        GetProductsInterface getProductsInterface = RetrofitClientInstance.getRetrofitInstance().create(GetProductsInterface.class);
        Call<ProductResponse> call = getProductsInterface.getProducts(pageNum, pageCount);
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                products.addAll(response.body().getProducts());
                adapter.notifyDataSetChanged();
                totalItems = Integer.parseInt(response.body().getTotalProducts());
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d(TAG, "Failed to fetch products: " + t.getMessage());
                Log.e(TAG, t.toString());
            }
        });
    }
}