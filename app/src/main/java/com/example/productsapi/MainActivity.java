package com.example.productsapi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.add_product);
        menu.add(R.string.close_app);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.toString().equals(getResources().getString(R.string.close_app))) {
            finish();
        }
        if (item.toString().equals(getResources().getString(R.string.add_product))) {
            addProduct();
        }

        return super.onOptionsItemSelected(item);
    }

    private void addProduct() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.add_product);
        alert.setIcon(android.R.drawable.ic_menu_add);

        EditText nameInput = new EditText(this);

        nameInput.setHint(R.string.hint_name);

        alert.setView(nameInput);

        alert.setNeutralButton(R.string.cancel,null);
        alert.setPositiveButton(R.string.submit, (dialog, which) -> {
            String name = nameInput.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(
                        this,
                        R.string.name_is_empty,
                        Toast.LENGTH_LONG
                ).show();
            } else {
                ProductAdd productAdd = new ProductAdd();
                productAdd.execute("nome="+ name  + "&preco=1.5&quantidade=1.0");
            }
        });
        alert.show();
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

    private class ProductAdd extends AsyncTask<String,String, String>{
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
                try {
                    JSONObject formattedResponse = new JSONObject(response);
                     int id = formattedResponse.getInt("id");
                     if (id > 0) {
                         Toast.makeText(
                                 MainActivity.this,
                                 R.string.insert_successful,
                                 Toast.LENGTH_LONG
                         ).show();
                     } else {
                         Toast.makeText(
                                 MainActivity.this,
                                 R.string.insert_error,
                                 Toast.LENGTH_LONG
                         ).show();
                     }
                } catch (JSONException exception) {
                    Toast.makeText(
                            MainActivity.this,
                            exception.toString(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            }

            bar.setActivated(false);
            bar.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... request) {
            return Api.execute("addProdutos.php", request);
        }
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