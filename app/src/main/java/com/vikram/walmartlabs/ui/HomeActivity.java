package com.vikram.walmartlabs.ui;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.vikram.walmartlabs.model.Product;
import com.vikram.walmartlabs.R;

public class HomeActivity extends AppCompatActivity implements ProductListFragment.OnFragmentInteractionListener {

    private static final String PRODUCT_LIST_FRAGMENT = "PRODUCT_LIST_FRAGMENT";
    private static final String PRODUCT_DESCRIPTION_FRAGMENT = "PRODUCT_DESCRIPTION_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            ProductListFragment productListFragment = ProductListFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, productListFragment, PRODUCT_LIST_FRAGMENT).commit();
        }
    }

    @Override
    public void onProductClick(Product product) {
        ProductDescriptionFragment productDescriptionFragment = ProductDescriptionFragment.newInstance(product);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.fragmentContainer, productDescriptionFragment, PRODUCT_DESCRIPTION_FRAGMENT);
        ft.addToBackStack(null);
        ft.commit();
    }
}
