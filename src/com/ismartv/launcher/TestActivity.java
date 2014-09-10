package com.ismartv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.ismartv.launcher.core.http.HttpClient;
import com.ismartv.launcher.data.ChannelBean;
import com.ismartv.launcher.data.FrontPageBean;
import com.ismartv.launcher.data.TvHomeBean;
import com.ismartv.launcher.ui.widget.MyHorizontalView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;

import java.util.HashMap;
import java.util.List;

import static android.view.View.OnClickListener;

public class TestActivity extends Activity implements OnClickListener {
    private static final String TAG = "HomeActivity";
    private static final String TITLE_TAG = "title";

    private static final int NAME = 1;
    private static final int URL = 2;
    private static final int CHANNEL = 3;

    private LinearLayout content;
    private LinearLayout title;

    RequestQueue mQueue;


    //VIEW
    private VideoView videoView;
    private NetworkImageView weixinIM;

    private MyHorizontalView hs_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initViews();
        mQueue = Volley.newRequestQueue(this);
        getFrontPage();
        getTvHome();
        getChannels();
    }


    private void initViews() {

        videoView = (VideoView) findViewById(R.id.video_view);
        content = (LinearLayout) findViewById(R.id.content);
        title = (LinearLayout) findViewById(R.id.title);
        weixinIM = (NetworkImageView) findViewById(R.id.weixin_im);

        hs_content = (MyHorizontalView) findViewById(R.id.hs_content);
        hs_content.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

    }

    public void getFrontPage() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HttpClient.HOST)
                .build();
        Client client = restAdapter.create(Client.class);
        client.excute(new Callback<FrontPageBean>() {
            @Override
            public void success(FrontPageBean frontPageBean, Response response) {
                Log.d(TAG, "getFrontPage --> success");
                final Uri uri = Uri.parse(frontPageBean.getVideos().get(0).getVideo_url());
                videoView.setVideoURI(uri);
                videoView.start();
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer) {
                        videoView.resume();
                    }
                });
                weixinIM.setImageUrl(frontPageBean.getImages().get(1).getImage_url(),
                        HttpClient.getInstance().getImageLoader(TestActivity.this));
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "getFrontPage --> failure " + retrofitError.getMessage());
            }
        });

    }

    public void getTvHome() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HttpClient.HOST)
                .build();
        TvHome client = restAdapter.create(TvHome.class);
        client.excute(new Callback<TvHomeBean>() {
            @Override
            public void success(TvHomeBean tvHome, Response response) {
                Log.d(TAG, "getTvHome --> success");
                for (TvHomeBean.Object object : tvHome.getObjects()) {
                    Log.d(TAG, "getTvHome --> " + object.getImage());
                    NetworkImageView imageView = new NetworkImageView(TestActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    imageView.setImageUrl(object.getImage(), HttpClient.getInstance().getImageLoader(TestActivity.this));
                    imageView.setPadding(10, 10, 10, 0);
                    //TextView
                    TextView title = new TextView(TestActivity.this);
                    title.setText(object.getTitle());
                    title.setGravity(Gravity.CENTER);
                    title.setTextSize(28);

                    title.setLayoutParams(new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    title.setBackgroundColor(Color.WHITE);
                    //
                    LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(TestActivity.this).inflate(R.layout.content, null);
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.addView(imageView);
                    linearLayout.addView(title);
                    linearLayout.setPadding(30, 0, 30, 0);
                    linearLayout.setFocusable(true);
                    linearLayout.setClickable(true);
                    linearLayout.setBackgroundResource(R.drawable.content_selector);
                    linearLayout.setOnClickListener(TestActivity.this);
                    linearLayout.setTag(object.getItem_url());
                    content.addView(linearLayout);
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "getTvHome --> failure " + retrofitError.getMessage());
            }
        });

    }

    public void getChannels() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(HttpClient.HOST)
                .build();
        Channels channels = restAdapter.create(Channels.class);
        channels.excute(new Callback<List<ChannelBean>>() {
            @Override
            public void success(List<ChannelBean> channelBeans, Response response) {
                Log.d(TAG, "getFrontPage --> success");
                for (ChannelBean channel : channelBeans) {
                    NetworkImageView imageView = new NetworkImageView(TestActivity.this);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setImageUrl(channel.getIcon_url(), HttpClient.getInstance().getImageLoader(TestActivity.this));
                    //
                    TextView textView = new TextView(TestActivity.this);
                    textView.setGravity(Gravity.CENTER);
                    textView.setTextSize(32);
                    textView.setText(channel.getName());
                    //
                    LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(TestActivity.this).inflate(R.layout.title, null);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    linearLayout.setGravity(Gravity.CENTER);
                    linearLayout.addView(imageView);
                    linearLayout.addView(textView);
                    linearLayout.setFocusable(true);
                    linearLayout.setClickable(true);
                    linearLayout.setBackgroundResource(R.drawable.content_selector);
                    linearLayout.setPadding(30, 0, 30, 0);
                    linearLayout.setOnClickListener(TestActivity.this);
                    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
                    hashMap.put(1, channel.getName());
                    hashMap.put(2, channel.getUrl());
                    hashMap.put(3, channel.getChannel());
                    linearLayout.setTag(hashMap);

                    title.addView(linearLayout);
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "getFrontPage --> failure " + retrofitError.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title:

                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HashMap<Intent, String> hashMap = (HashMap<Intent, String>)view.getTag();
                intent.putExtra("title", hashMap.get(NAME));
                intent.putExtra("url", hashMap.get(URL));
                intent.putExtra("channel", hashMap.get(CHANNEL));
                intent.setClassName("tv.ismar.daisy", "tv.ismar.daisy.ChannelListActivity");
                this.startActivity(intent);
                break;
            case R.id.content:
                Intent videoIntent = new Intent();
                videoIntent.putExtra("Code", "387068");
                videoIntent.putExtra("ItemUrl", view.getTag().toString());
                videoIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                videoIntent.putExtra("ContentModel", "teleplay");
                videoIntent.putExtra("ModuleName", "4S");
                videoIntent.setClassName("com.lenovo.dll.nebula.vod", "com.lenovo.dll.nebula.vod.player.VODPlayerActivity");
                this.startActivity(videoIntent);
                break;
            default:
                break;

        }

    }

    interface Client {
        @GET("/api/tv/frontpage/")
        void excute(
                Callback<FrontPageBean> callback
        );
    }


    interface TvHome {
        @GET("/api/tv/section/tvhome/")
        void excute(
                Callback<TvHomeBean> callback
        );
    }

    interface Channels {
        @GET("/api/tv/channels/")
        void excute(
                Callback<List<ChannelBean>> callback
        );
    }
}


