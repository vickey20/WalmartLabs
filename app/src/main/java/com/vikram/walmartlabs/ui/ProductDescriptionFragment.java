package com.vikram.walmartlabs.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Fragment to show product details.
 */
public class ProductDescriptionFragment extends Fragment {
    private static final String ARG_PRODUCT = "ARG_PRODUCT";

    private Product product;

    @BindView(R.id.productId) TextView productId;
    @BindView(R.id.productName) TextView productName;
    @BindView(R.id.shortDescription) TextView shortDescription;
    @BindView(R.id.longDescription) TextView longDescription;
    @BindView(R.id.price) TextView price;
    @BindView(R.id.ratingCount) TextView reviewCount;
    @BindView(R.id.ratingBar) RatingBar ratingBar;
    @BindView(R.id.image) ImageView image;

    public ProductDescriptionFragment() {}

    public static ProductDescriptionFragment newInstance(Product product) {
        ProductDescriptionFragment fragment = new ProductDescriptionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_description, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        productId.setText("Product ID: " + product.getProductId());
        productName.setText(product.getProductName());
        if (product.getShortDescription() != null) {
            shortDescription.setText(Html.fromHtml(product.getShortDescription()));
        }
        if (product.getLongDescription() != null) {
            longDescription.setText(Html.fromHtml(product.getLongDescription()));
        }
        reviewCount.setText(String.valueOf(product.getReviewCount()));
        price.setText(product.getPrice());
        ratingBar.setRating(product.getReviewRating());
        Picasso.get().load(RetrofitClientInstance.BASE_URL + product.getProductImage()).into(image);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
