package com.leejangyoun.slidecardview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.Random;


public class CardViewAdapter extends ArrayAdapter<Fruit> {

    Context mContext;

    int mCardViewWidth;

    // =======================================================================
    // METHOD : QnaCardAdapter
    // =======================================================================
    public CardViewAdapter(Context context, int width) {
        super(context, R.layout.cardview_cell);
        this.mContext = context;
        mCardViewWidth = width;
    }

    // =======================================================================
    // METHOD : getView
    // =======================================================================
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view.findViewById(R.id.layout_header).getLayoutParams().width = mCardViewWidth;
        view.findViewById(R.id.layout_body)  .getLayoutParams().width = mCardViewWidth;
        view.findViewById(R.id.layout_footer).getLayoutParams().width = mCardViewWidth;

        ((TextView) view.findViewById(R.id.txt_desc)).setText(getItem(position).getDesc());
        ((TextView) view.findViewById(R.id.txt_fruit_name)).setText(getItem(position).getTitle());
        ((TextView) view.findViewById(R.id.txt_fruit_no)).setText("FRUIT NO : " + getItem(position).getNo());

        ((TextView) view.findViewById(R.id.txt_comment_cnt)).setText((100 + new Random().nextInt(99)) + "");
        ((TextView) view.findViewById(R.id.txt_like_cnt)).setText((100 + new Random().nextInt(99)) + "");

        ImageView profile = (ImageView) view.findViewById(R.id.img_profile);
        Glide.with(mContext).load(getItem(position).getThumb()).into(profile);

        ImageView contentThumb = (ImageView) view.findViewById(R.id.content_thumb);
        Glide.with(mContext).load(getItem(position).getThumb()).into(contentThumb);

        return view;
    }
}
