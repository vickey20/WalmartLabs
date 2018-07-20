package com.vikram.walmartlabs.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;

import com.vikram.walmartlabs.model.Product;
import com.vikram.walmartlabs.R;

public class HomeActivity extends AppCompatActivity implements ProductListFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ProductListFragment productListFragment = ProductListFragment.newInstance(1, 30);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, productListFragment, "PRODUCT_LIST").commit();
    }


    @Override
    public void onProductClick(Product product) {
        ProductDescriptionFragment productDescriptionFragment = ProductDescriptionFragment.newInstance(product);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right);
        ft.replace(R.id.fragmentContainer, productDescriptionFragment, "PRODUCT_DESCRIPTION");
        ft.addToBackStack(null);
        ft.commit();
    }
}
