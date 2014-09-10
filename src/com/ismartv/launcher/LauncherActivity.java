package com.ismartv.launcher;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.volley.toolbox.NetworkImageView;
import com.ismartv.launcher.core.http.HttpClient;
import com.ismartv.launcher.data.ChannelBean;
import com.ismartv.launcher.data.FrontPageBean;
import com.ismartv.launcher.data.TvHomeBean;
import com.ismartv.launcher.ui.widget.IsmartvVideoView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;

import java.util.HashMap;
import java.util.List;

/**
 * Created by <huaijiefeng@gmail.com> on 9/9/14.
 */
public class LauncherActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "LauncherActivity";

    private static final int NAME = 1;
    private static final int URL = 2;
    private static final int CHANNEL = 3;

    private IsmartvVideoView videoView;

    private NetworkImageView[] homeImages;

    private NetworkImageView[] channelImages;

    private TextView[] channelTexts;

    private RelativeLayout[] channelImtes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initViews();
        getFrontPage();
        getTvHome();
        getChannels();
    }

    private void initViews() {
        videoView = (IsmartvVideoView) findViewById(R.id.video_view);
        homeImages = new NetworkImageView[]{
                (NetworkImageView) findViewById(R.id.home_1),
                (NetworkImageView) findViewById(R.id.home_2),
                (NetworkImageView) findViewById(R.id.home_3),
                (NetworkImageView) findViewById(R.id.home_4),
                (NetworkImageView) findViewById(R.id.home_5),
                (NetworkImageView) findViewById(R.id.home_6)
        };

        channelImages = new NetworkImageView[]
                {
                        (NetworkImageView) findViewById(R.id.channel_iv_1),
                        (NetworkImageView) findViewById(R.id.channel_iv_2),
                        (NetworkImageView) findViewById(R.id.channel_iv_3),
                        (NetworkImageView) findViewById(R.id.channel_iv_4),
                        (NetworkImageView) findViewById(R.id.channel_iv_5),
                        (NetworkImageView) findViewById(R.id.channel_iv_6),
                        (NetworkImageView) findViewById(R.id.channel_iv_7),
                        (NetworkImageView) findViewById(R.id.channel_iv_8),
                        (NetworkImageView) findViewById(R.id.channel_iv_9),
                        (NetworkImageView) findViewById(R.id.channel_iv_10),
                        (NetworkImageView) findViewById(R.id.channel_iv_11),
                        (NetworkImageView) findViewById(R.id.channel_iv_12)
                };
        channelTexts = new TextView[]{
                (TextView) findViewById(R.id.channel_tv_1),
                (TextView) findViewById(R.id.channel_tv_2),
                (TextView) findViewById(R.id.channel_tv_3),
                (TextView) findViewById(R.id.channel_tv_4),
                (TextView) findViewById(R.id.channel_tv_5),
                (TextView) findViewById(R.id.channel_tv_6),
                (TextView) findViewById(R.id.channel_tv_7),
                (TextView) findViewById(R.id.channel_tv_8),
                (TextView) findViewById(R.id.channel_tv_9),
                (TextView) findViewById(R.id.channel_tv_10),
                (TextView) findViewById(R.id.channel_tv_11),
                (TextView) findViewById(R.id.channel_tv_12)
        };

        channelImtes = new RelativeLayout[]{
                (RelativeLayout) findViewById(R.id.channel_item_1),
                (RelativeLayout) findViewById(R.id.channel_item_2),
                (RelativeLayout) findViewById(R.id.channel_item_3),
                (RelativeLayout) findViewById(R.id.channel_item_4),
                (RelativeLayout) findViewById(R.id.channel_item_5),
                (RelativeLayout) findViewById(R.id.channel_item_6),
                (RelativeLayout) findViewById(R.id.channel_item_7),
                (RelativeLayout) findViewById(R.id.channel_item_8),
                (RelativeLayout) findViewById(R.id.channel_item_9),
                (RelativeLayout) findViewById(R.id.channel_item_10),
                (RelativeLayout) findViewById(R.id.channel_item_11),
                (RelativeLayout) findViewById(R.id.channel_item_12),
        };

    }


    @Override
    public void onClick(View view) {

                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                HashMap<Intent, String> hashMap = (HashMap<Intent, String>) view.getTag();
                intent.putExtra("title", hashMap.get(NAME));
                intent.putExtra("url", hashMap.get(URL));
                intent.putExtra("channel", hashMap.get(CHANNEL));
                intent.setClassName("tv.ismar.daisy", "tv.ismar.daisy.ChannelListActivity");
                this.startActivity(intent);

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
                              videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                                  @Override
                                  public void onPrepared(MediaPlayer mp) {
                                      mp.start();
                                      mp.setLooping(true);

                                  }
                              });

                              videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                  @Override
                                  public void onCompletion(MediaPlayer mp) {
                                      videoView.setVideoURI(uri);
                                      videoView.start();

                                  }
                              });

//                weixinIM.setImageUrl(frontPageBean.getImages().get(1).getImage_url(),
//                        HttpClient.getInstance().getImageLoader(TestActivity.this));
                          }

                          @Override
                          public void failure(RetrofitError retrofitError) {
                              Log.e(TAG, "getFrontPage --> failure " + retrofitError.getMessage());
                          }
                      }

        );

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
                for (int i = 0; i < tvHome.getObjects().size(); i++) {
                    Log.d(TAG, "getTvHome --> " + tvHome.getObjects().get(i).getImage());

                    homeImages[i].setImageUrl(tvHome.getObjects().get(i).getImage(),
                            HttpClient.getInstance().getImageLoader(LauncherActivity.this));
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
                for (int i = 0; i < channelBeans.size(); i++) {
                    channelImages[i].setImageUrl(channelBeans.get(i).getIcon_url(),
                            HttpClient.getInstance().getImageLoader(LauncherActivity.this));
                    channelTexts[i].setText(channelBeans.get(i).getName());
                    channelTexts[i].setTextColor(Color.WHITE);
                    TextPaint tp = channelTexts[i].getPaint();
                    tp.setFakeBoldText(true);
                    HashMap<Integer, String> hashMap = new HashMap<Integer, String>();
                    hashMap.put(1, channelBeans.get(i).getName());
                    hashMap.put(2, channelBeans.get(i).getUrl());
                    hashMap.put(3, channelBeans.get(i).getChannel());
                    channelImtes[i].setTag(hashMap);
                    channelImtes[i].setOnClickListener(LauncherActivity.this);
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "getFrontPage --> failure " + retrofitError.getMessage());
            }
        });
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
