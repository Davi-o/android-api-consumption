package com.example.productsapi;

import android.content.Context;
import android.net.ConnectivityManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

abstract class Api {
    public static final String URL = "https://adalto.pro.br/aulas/api/";

    public static boolean verifyConnection(Context context) {
        ConnectivityManager connection = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connection.getActiveNetworkInfo().isConnected();
    }

    public static String execute(String method, String... request){
        HttpURLConnection connection;

        StringBuilder parameters = new StringBuilder();
        for(String param: request){
            parameters.append(param);
        }
        try {
            URL url = new URL(URL + method);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content=Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content=Type","application/x-www-form-urlencoded");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("charset","utf-8");

            DataOutputStream output = new DataOutputStream(connection.getOutputStream());
            output.writeBytes(Arrays.toString(request));
            output.flush();
            output.close();

            InputStream input = connection.getInputStream();
            BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

            String row;
            StringBuilder response = new StringBuilder();

            while ( (row=bufferedReader.readLine()) != null){
                response.append(row);
                response.append("...");
            }

            bufferedReader.close();
            return response.toString();


        } catch (Exception ignored) {

        }

        return "{}";
    }
}
