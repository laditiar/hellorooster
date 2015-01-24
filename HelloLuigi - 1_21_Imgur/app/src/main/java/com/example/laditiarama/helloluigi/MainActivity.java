package com.example.laditiarama.helloluigi;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends ActionBarActivity {

    public static Context context;

    // To store current background id
    // App starts off with no background
    public static int currentQuoteIndex = -1;
    public static int currentBgId = 0;
    public static ArrayList<Integer> images = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this.getApplication().getApplicationContext();

        //////
        //SendGetReqAsyncTask sendGetReqAsyncTask = new SendGetReqAsyncTask();
        //sendGetReqAsyncTask.execute(AppConstants.IMGUR_ACCOUNT, AppConstants.IMGUR_ACCOUNT_ALBUM_ID);
        //////

        // Loads all drawable resource ids in local memory
        this.loadImages();

        setContentView(R.layout.activity_main);

        randomizeBackgroundImage();
        regenerateQuote();

        Button moreButton = (Button)findViewById(R.id.more_quote);
        moreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomizeBackgroundImage();
                regenerateQuote();
            }
        });
    }

    private void loadImages() {
        if (images != null) images.clear();
        int resID = 0;
        int imgnum = 1;
        do {
            resID = getResources().getIdentifier("bg"+imgnum, "drawable", "com.example.laditiarama.helloluigi");
            if (resID!=0) {
                images.add(resID);
                System.out.println("Resource Id: " + resID);
            }
            imgnum++;
        }
        while (resID!=0);
    }

    /*
    Selects a random drawable and sets it in the background
     */
    private void randomizeBackgroundImage(){
        if (images == null || images.isEmpty()) {
            loadImages();
        }
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int imageId = images.get(random.nextInt(images.size()));
        while (imageId == currentBgId) {
            imageId = images.get(random.nextInt(images.size()));
        }
        currentBgId = imageId;
        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        if (imageView != null) {
            Drawable d = getResources().getDrawable(currentBgId);
            imageView.setImageDrawable(d);
        }
    }

    private void regenerateQuote() {
        TextView quote_view = (TextView)findViewById(R.id.container);
        String currentQuote = quote_view.getText().toString();
        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.motivation_quote);
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int index = random.nextInt(quotes.length);
        String final_quote = quotes[index];
        while(currentQuoteIndex == index) {
            index = random.nextInt(quotes.length);
            final_quote = quotes[index];
        }
        currentQuoteIndex = index;
        quote_view.setText("\"" + final_quote + "\"");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
