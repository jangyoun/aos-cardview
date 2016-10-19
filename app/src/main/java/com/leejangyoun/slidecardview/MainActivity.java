package com.leejangyoun.slidecardview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wenchao.cardstack.CardStack;

public class MainActivity extends AppCompatActivity {

    private Context mContext;

    private int mPage = 1;
    private int mStackSize = 0;
    private boolean mIsLastItem = false;

    private CardViewAdapter mCardViewAdapter;
    private RequestQueue mQueue;

    View mLoadingLayout;

    // =======================================================================
    // METHOD : onCreate
    // =======================================================================
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();

        mLoadingLayout  = findViewById(R.id.layout_loading);

        CardStack cardStack = (CardStack) findViewById(R.id.cardstack);
        cardStack.setListener(new CustomCardEventListener());
        cardStack.setContentResource(R.layout.cardview_cell);
        cardStack.setStackMargin(0);
        mCardViewAdapter = new CardViewAdapter(mContext, getCardViewWidth());
        cardStack.setAdapter(mCardViewAdapter);
        //mCardStack.setVisibleCardNum(3);

        //set http queue
        mQueue = Volley.newRequestQueue(mContext);
        //String url = "http://leejangyoun.com/android/dummy/recyclerViewWithHeader_" + mPage + ".json";
        String url = "http://leejangyoun.com/android/dummy/SlideCardView_1.json";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new CustomSuccessListener(), new CustomErrorListener());
        mQueue.add(stringRequest);
    }


    // =======================================================================
    // METHOD : getCardViewWidth
    // =======================================================================
    public int getCardViewWidth() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int marginDp = 23*2/*card margin*/;

        Resources resources = mContext.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float MarginPixel = marginDp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);

        return (int) (size.x - MarginPixel);
    }

    //=======================================================================
    // METHOD : CustomCardEventListener
    //=======================================================================
    class CustomCardEventListener implements CardStack.CardEventListener {

        @Override
        public boolean swipeEnd(int section, float distance) {
            return distance > 200;
        }

        @Override
        public boolean swipeStart(int section, float distance) {
            return true;
        }

        @Override
        public boolean swipeContinue(int section, float distanceX, float distanceY) {
            return true;
        }

        @Override
        public void discarded(int index, int direction) {

            mStackSize--;

            if(mStackSize == 0) {

                // last page
                ((TextView)findViewById(R.id.txt_loading)).setText(mIsLastItem ? "No card, Sorry" : "Searching fruit cards...");
                (findViewById(R.id.progress_loading)).setVisibility(mIsLastItem ? View.GONE : View.VISIBLE);

                //if sending all card, must get more card by http protocol
                mLoadingLayout.setVisibility(View.VISIBLE);

                //set http queue
                String url = "http://leejangyoun.com/android/dummy/SlideCardView_" + (++mPage) + ".json";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new CustomSuccessListener(), new CustomErrorListener());
                mQueue.add(stringRequest);
            }
        }

        @Override
        public void topCardTapped(int index) {
            Toast.makeText(mContext, mCardViewAdapter.getItem(index).getTitle(), Toast.LENGTH_LONG).show();
        }
    }

    // =======================================================================
    // METHOD : CustomSuccessListener
    // =======================================================================
    private  class CustomSuccessListener implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {

            mLoadingLayout.setVisibility(View.GONE);

            Gson gson = new Gson();

            JsonObject root = new JsonParser().parse(response).getAsJsonObject();

            mIsLastItem = root.get("last").getAsBoolean();

            JsonArray node = root.get("arrList").getAsJsonArray();
            mStackSize = node.size();
            Log.i("TEST", "mStackSize : " + mStackSize);
            for (JsonElement jEle : node) {
                Fruit fruit = gson.fromJson(jEle.getAsJsonObject(), Fruit.class);
                mCardViewAdapter.add(fruit);
            }
            mCardViewAdapter.notifyDataSetChanged();
        }
    }

    // =======================================================================
    // METHOD : CustomErrorListener
    // =======================================================================
    private  class CustomErrorListener implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("TEST", "error : " + error.getMessage());
        }
    }
}
