package com.example.fxg.ggg;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.MediaController;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.Vitamio;

public class VideoActivity extends AppCompatActivity {
    private io.vov.vitamio.widget.VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //检查vitamio框架是否可用
        if (!LibsChecker.checkVitamioLibs(this)) {
            return;
        }
        setContentView(R.layout.activity_video);

        String url=getIntent().getStringExtra("url");
//        //显示缓冲百分比的TextView
//        percentTv = (TextView) findViewById(R.id.buffer_percent);
//        //显示下载网速的TextView
//        netSpeedTv = (TextView) findViewById(R.id.net_speed);
        //初始化加载库文件
        if (Vitamio.isInitialized(this)) {
            videoView = (io.vov.vitamio.widget.VideoView) findViewById(R.id.vitamio);
            videoView.setVideoURI(Uri.parse(url));
            videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);
            MediaController controller = new MediaController(this);
            videoView.setMediaController(controller);
            videoView.setBufferSize(10240); //设置视频缓冲大小。默认1024KB，单位byte
            videoView.requestFocus();
            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    // optional need Vitamio 4.0
                    mediaPlayer.setPlaybackSpeed(1.0f);
                    //mediaPlayer.setLooping(true);
                }
            });
        }
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                switch (what) {
                    //开始缓冲
                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:
//                        percentTv.setVisibility(View.VISIBLE);
//                        netSpeedTv.setVisibility(View.VISIBLE);
                        mp.pause();
                        break;
                    //缓冲结束
                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:
//                        percentTv.setVisibility(View.GONE);
//                        netSpeedTv.setVisibility(View.GONE);
                        mp.start(); //缓冲结束再播放
                        break;
                    //正在缓冲
                    case MediaPlayer.MEDIA_INFO_DOWNLOAD_RATE_CHANGED:
//                        netSpeedTv.setText("当前网速:" + extra + "kb/s");
                        break;
                }
                return true;
            }
        });
    }
}
