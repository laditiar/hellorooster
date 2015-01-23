package com.example.laditiarama.helloluigi;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.net.URI;

public class APIUtils {

    public static void handleAPIRequest(String account, String albumId) {
        HttpResponse httpResponse = null;
        String errorMessage = null;
        try {
            HttpClient httpClient = new CustomHttpClient();
            HttpGet request = new HttpGet();
            String endpoint = AppConstants.IMGUR_API_ENDPOINT + "account/" + AppConstants.IMGUR_ACCOUNT + "/album/" + AppConstants.IMGUR_ACCOUNT_ALBUM_ID;
            Log.i(APIUtils.class.toString(), "API Endpoint: " + endpoint);
            request.setURI(new URI(endpoint));
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Client-ID " + AppConstants.IMGUR_API_CLIENT_ID);
            httpResponse = httpClient.execute(request);
        }
        catch (Exception ex) {
            errorMessage = ex.getMessage();
            Log.e(APIUtils.class.toString(), Log.getStackTraceString(ex));
        }
        String jsonString = null;
        JSONObject jsonResponseObject = null;
        try {
            jsonString = EntityUtils.toString(httpResponse.getEntity());
            Log.d(APIUtils.class.toString(), "JSON Response: " + jsonString);
            jsonResponseObject = new JSONObject(jsonString);
        }
        catch (Exception ex) {
            errorMessage = ex.getMessage();
            Log.e(APIUtils.class.toString(), Log.getStackTraceString(ex));
        }
        /*
        if (errorMessage != null) {
            Utils.alert(errorMessage, "ERROR");
        }
        */
    }

    /*
    private void sendGETRequestGetOrder(String orderId, String apId) {
        SendGetReqAsyncTask sendGetReqAsyncTask = new SendGetReqAsyncTask();
        sendGetReqAsyncTask.execute(orderId, apId);
    }
    */

}
