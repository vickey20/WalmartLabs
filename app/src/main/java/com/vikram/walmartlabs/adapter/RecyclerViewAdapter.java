package com.vikram.walmartlabs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.vikram.walmartlabs.R;
import com.vikram.walmartlabs.model.Product;
import com.vikram.walmartlabs.network.RetrofitClientInstance;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private ArrayList<Product> products;

    private OnLoadMoreListener onLoadMoreListener;
    private OnItemClickListener onItemClickListener;

    public interface OnLoadMoreListener {
        void loadMore(int itemCount);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public RecyclerViewAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }

    class ProductHolder extends RecyclerView.ViewHolder {
        TextView name, reviewCount, price;
        ImageView image;
        RatingBar ratingBar;

        View view;

        public ProductHolder(View view) {
            super(view);
            this.view = view;
            name = view.findViewById(R.id.productName);
            reviewCount  = view.findViewById(R.id.ratingCount);
            price = view.findViewById(R.id.price);
            image = view.findViewById(R.id.image);
            ratingBar = view.findViewById(R.id.ratingBar);
        }

        public void bind(final Product product, final OnItemClickListener onItemClickListener) {
            name.setText(Html.fromHtml(product.getProductName()));
            reviewCount.setText(String.valueOf(product.getReviewRating()));
            price.setText(product.getPrice());

            Picasso.get().load(RetrofitClientInstance.BASE_URL + product.getProductImage()).into(image);

            ratingBar.setRating(product.getReviewRating());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onItemClick(product);
                }
            });
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProductHolder(LayoutInflater.from(context).inflate(R.layout.list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (position == products.size() - 5) {
            onLoadMoreListener.loadMore(products.size());
        }

        ((ProductHolder) holder).bind(products.get(position), onItemClickListener);
    }
}
