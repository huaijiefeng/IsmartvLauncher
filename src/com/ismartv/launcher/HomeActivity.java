package com.ismartv.launcher;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.VideoView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.ismartv.launcher.core.http.HttpClient;
import com.ismartv.launcher.data.ChannelBean;
import com.ismartv.launcher.data.FrontPageBean;
import com.ismartv.launcher.data.TvHomeBean;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.GET;

import java.util.List;

/**
 * Created by <huaijiefeng@gmail.com> on 9/2/14.
 */
public class HomeActivity extends Activity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener {
    private static final String TAG = "HomeActivity";
    private Context context;


    RequestQueue mQueue;


    //VIEW
    private VideoView videoView;
    private NetworkImageView weixinIM;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = this;
        setContentView(R.layout.activity_home);
        initViews();
        mQueue = Volley.newRequestQueue(this);
        getFrontPage();
        getTvHome();
        getChannels();
    }


    private void initViews() {

        videoView = (VideoView) findViewById(R.id.video_view);
        weixinIM = (NetworkImageView) findViewById(R.id.weixin_im);


    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                        HttpClient.getInstance().getImageLoader(context));
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
//                contentAdapter = new ContentAdapter(context, tvHome.getObjects());


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
                Log.d(TAG, "getChannels --> success");

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.e(TAG, "getChannels --> failure " + retrofitError.getMessage());
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
