package com.example.laditiarama.helloluigi;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.net.URI;

class SendGetReqAsyncTask extends AsyncTask<String, Void, HttpResponse> {

    @Override
    protected HttpResponse doInBackground(String... params) {
        String account = params[0];
        String album = params[1];
        long start = System.currentTimeMillis();
        HttpResponse httpResponse = null;
        String errorMessage = null;
        try {
            HttpClient httpClient = new CustomHttpClient();
            HttpGet request = new HttpGet();
            String endpoint = AppConstants.IMGUR_API_ENDPOINT + "account/" + account + "/album/" + album;
            Log.i(SendGetReqAsyncTask.class.toString(), "API Endpoint: " + endpoint);
            request.setURI(new URI(endpoint));
            request.setHeader("Content-type", "application/json");
            request.setHeader("Authorization", "Client-ID " + AppConstants.IMGUR_API_CLIENT_ID);
            httpResponse = httpClient.execute(request);
            long end = System.currentTimeMillis();
            Log.i(SendGetReqAsyncTask.class.toString(), "API processing time: " + (end-start) + "ms");
        }
        catch (Exception ex) {
            errorMessage = ex.getMessage();
            Log.e(SendGetReqAsyncTask.class.toString(), Log.getStackTraceString(ex));
        }
        String jsonString = null;
        JSONObject jsonResponseObject = null;
        try {
            jsonString = EntityUtils.toString(httpResponse.getEntity());
            Log.d(SendGetReqAsyncTask.class.toString(), "JSON Response: " + jsonString);
            jsonResponseObject = new JSONObject(jsonString);
        }
        catch (Exception ex) {
            errorMessage = ex.getMessage();
            Log.e(SendGetReqAsyncTask.class.toString(), Log.getStackTraceString(ex));
        }
        /*
        if (errorMessage != null) {
            Utils.alert(errorMessage, "ERROR");
        }
        */
        return httpResponse;
    }

    @Override
    protected void onPreExecute() {
        Log.i(SendGetReqAsyncTask.class.toString(), "API REQUEST PRE-EXECUTE");
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        Log.i(SendGetReqAsyncTask.class.toString(), "API REQUEST POST-EXECUTE");
    }

    /*
    private ProgressDialog mProgressDialog = new ProgressDialog(MainActivity.this);
    private boolean displayTitle = false;
    private String mFinalMessage = null;

    @Override
    protected HttpResponse doInBackground(String... params) {
        String paramOrderId = params[0];
        String paramApId = params[1];
        HttpResponse httpResponse = null;
        try {
            HttpClient httpClient = null;
            if (Utils.HTTPS_SECURED) {
                httpClient = new YouOrderitHttpClient(MainActivity.this);
            }
            else {
                httpClient = new DefaultHttpClient();
            }
            HttpGet request = new HttpGet();
            request.setURI(new URI(Utils.SERVER_URL+"order?id=" + paramOrderId + "&apid=" + paramApId));
            httpResponse = httpClient.execute(request);
        } catch (URISyntaxException use) {
            mFinalMessage = getString(R.string.generic_error_message);
            System.out.println("URISyntaxException :" + use);
            use.printStackTrace();
        } catch (ClientProtocolException cpe) {
            mFinalMessage = getString(R.string.generic_error_message);
            System.out.println("ClientProtocolException :" + cpe);
            cpe.printStackTrace();
        } catch (IOException ioe) {
            mFinalMessage = getString(R.string.generic_error_message);
            System.out.println("IOException :" + ioe);
            ioe.printStackTrace();
        }
        String jsonString = null;
        JSONObject jsonResponseObject = null;
        try {
            jsonString = EntityUtils.toString(httpResponse.getEntity());
            Log.d("Server JSON response:", jsonString);
            jsonResponseObject = new JSONObject(jsonString);
        } catch (ParseException e) {
            mFinalMessage = getString(R.string.generic_error_message);
            System.out.println("ParseException :" + e);
            e.printStackTrace();
        } catch (IOException e) {
            mFinalMessage = getString(R.string.generic_error_message);
            System.out.println("IOException :" + e);
            e.printStackTrace();
        } catch (JSONException e) {
            mFinalMessage = getString(R.string.generic_error_message);
            System.out.println("JSONException :" + e);
            e.printStackTrace();
        }
        if (jsonResponseObject != null) {

            boolean error = false;
            try {
                jsonResponseObject.get("response");
            }
            catch (JSONException e) {
                error = true;
            }
            if (!error) {
                try {
                    int errorCode = (Integer)jsonResponseObject.get("error_code");
                    mFinalMessage = Utils.getErrorCodeMessage(getApplicationContext(), errorCode);
                }
                catch (JSONException e) {
                    mFinalMessage = getString(R.string.generic_error_message);
                    System.out.println("JSONException :" + e);
                    e.printStackTrace();
                }
            }
            else {
                OrderItemContainer item = new OrderItemContainer();
                try {
                    item.orderId = (String)jsonResponseObject.get("order_id");
                }
                catch (JSONException ex) {
                    item.orderId = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.date = (String)jsonResponseObject.get("created_at");
                }
                catch (JSONException ex) {
                    item.date = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.address = (String)jsonResponseObject.get("address");
                }
                catch (JSONException ex) {
                    item.address = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.phone = (String)jsonResponseObject.get("telephone");
                }
                catch (JSONException ex) {
                    item.phone = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.name = (String)jsonResponseObject.get("name");
                }
                catch (JSONException ex) {
                    item.name = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.paymentType = (String)jsonResponseObject.get("payment_type");
                }
                catch (JSONException ex) {
                    item.paymentType = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    String products = (String)jsonResponseObject.get("products");
                    JSONArray productList = new JSONArray(products);
//JSONArray productList = (JSONArray)jsonResponseObject.getJSONArray("products");
                    item.products = "";
                    for (int i = 0; i < productList.length(); i++) {
                        item.products += (String)productList.get(i);
                        if (i < (productList.length()-1)) {
                            item.products += "\n";
                        }
                    }
                }
                catch (JSONException ex) {
                    item.products = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.modifiers = (String)jsonResponseObject.get("modifiers");
                }
                catch (JSONException ex) {
                    item.modifiers = "";
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                try {
                    item.first = (Boolean)jsonResponseObject.get("first");
                }
                catch (JSONException ex) {
                    item.first = false;
                    System.out.println("JSONException :" + ex);
                    ex.printStackTrace();
                }
                SharedPreferences settings = getSharedPreferences(Utils.USER_PROFILE_KEYNAME, 0);
                Editor editor = settings.edit();
                String key = "order_" + item.orderId;
                editor.putInt(key + "_id", Integer.parseInt(item.orderId));
                editor.putString(key + "_d", item.date);
                editor.putString(key + "_a", item.address);
                editor.putString(key + "_p", item.phone);
                editor.putString(key + "_n", item.name);
                editor.putString(key + "_pt", item.paymentType);
                editor.putString(key + "_pr", item.products);
                editor.putString(key + "_m", item.modifiers);
                editor.putBoolean(key + "_req", true);
                editor.putBoolean(key + "_f", item.first);
                editor.commit();
                displayTitle = true;
                mFinalMessage = getDisplayOrderString(item);
            }
        }
        return httpResponse;
    }

    @Override
    protected void onPreExecute() {
        mProgressDialog.setMessage(getString(R.string.retrieve_order_progress_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        if (mFinalMessage != null && !"".equals(mFinalMessage)) {
            if (displayTitle) {
                Utils.alert(MainActivity.this, mFinalMessage, getString(R.string.order_dialog_title));
            }
            else {
                Utils.alert(MainActivity.this, mFinalMessage);
            }
        }
    }
    */
}
