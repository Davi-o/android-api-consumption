package com.example.productsapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MainActivity extends AppCompatActivity {

    private ListView productsView;
    private ProgressBar bar;
    private List<ProductEntity> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productsView = findViewById(R.id.listProducts);
        bar = findViewById(R.id.progressBar);

        bar.setVisibility(View.INVISIBLE);
        bar.setActivated(false);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ProductSearch search = new ProductSearch();
        search.execute();
    }

    @org.jetbrains.annotations.NotNull
    private List<ProductEntity> formatResponse(String response) {
        List<ProductEntity> products = new ArrayList<>();
        try {
            JSONArray productsArray = new JSONObject(response).getJSONArray("produtos");
            if (productsArray.length() > 0) {
                for (int index = 0; index < productsArray.length(); index++) {
                    JSONObject product = productsArray.getJSONObject(index);

                    ProductEntity responseProduct = new ProductEntity();

                    responseProduct.setId(product.getInt("id"));
                    responseProduct.setName(product.getString("nome"));
                    responseProduct.setValue(product.getDouble("preco"));
                    responseProduct.setQuantity(product.getDouble("quantidade"));

                    products.add(responseProduct);
                }
            }
        } catch (JSONException ignored) {

        }

        return products;
    }

    private class ProductSearch extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setActivated(true);
            bar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (response != null) {
               productList = formatResponse(response);
                ArrayAdapter adapter = new ArrayAdapter(
                        MainActivity.this,
                        android.R.layout.simple_list_item_1,
                        productList
                );
                productsView.setAdapter(adapter);
            }

            bar.setActivated(false);
            bar.setVisibility(View.INVISIBLE);

        }

        @Override
        protected String doInBackground(String... request) {
            return Api.execute("produtos.php", request);
        }
    }

}