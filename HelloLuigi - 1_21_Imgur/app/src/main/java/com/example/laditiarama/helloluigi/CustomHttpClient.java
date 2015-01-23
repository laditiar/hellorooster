package com.example.laditiarama.helloluigi;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import android.content.Context;

public class CustomHttpClient extends DefaultHttpClient {

    final Context mContext;

    public CustomHttpClient() {
        super();
        this.mContext = null;
    }

    public CustomHttpClient(Context context) {
        super();
        this.mContext = context;
    }

    @Override
    protected ClientConnectionManager createClientConnectionManager() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
        return new SingleClientConnManager(getParams(), registry);
    }
}