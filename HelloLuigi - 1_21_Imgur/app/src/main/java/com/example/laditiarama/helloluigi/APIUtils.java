package com.example.laditiarama.helloluigi;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.net.URI;

public class APIUtils {

    private void handleAPIRequest(String account, String albumId) {
        HttpResponse httpResponse = null;
        String errorMessage = null;
        try {
            HttpClient httpClient = new CustomHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(AppConstants.IMGUR_API_URL + "account/" + AppConstants.IMGUR_ACCOUNT + "/album/" + AppConstants.IMGUR_ACCOUNT_ALBUM_ID));
            //request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Client-ID " + AppConstants.IMGUR_API_CLIENT_ID);
            httpResponse = httpClient.execute(request);
        }
        catch (Exception ex) {
            errorMessage = ex.getMessage();
            Log.e("APICalloutUtils", errorMessage);
        }
        String jsonString = null;
        JSONObject jsonResponseObject = null;
        try {
            jsonString = EntityUtils.toString(httpResponse.getEntity());
            Log.d("Server JSON response:", jsonString);
            jsonResponseObject = new JSONObject(jsonString);
        }
        catch (Exception ex) {
            errorMessage = ex.getMessage();
            Log.e("APICalloutUtils", errorMessage);
        }
        if (errorMessage != null) {
            Utils.alert(errorMessage, "ERROR");
        }
    }

    /*
    private void sendGETRequestGetOrder(String orderId, String apId) {
        SendGetReqAsyncTask sendGetReqAsyncTask = new SendGetReqAsyncTask();
        sendGetReqAsyncTask.execute(orderId, apId);
    }
    */

}
