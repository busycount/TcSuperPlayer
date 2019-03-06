package com.busycount.tcsuperplayer.sample;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.busycount.tcsuperplayer.SuperPlayerConst;
import com.busycount.tcsuperplayer.SuperPlayerGlobalConfig;
import com.busycount.tcsuperplayer.SuperPlayerModel;
import com.busycount.tcsuperplayer.SuperPlayerView;
import com.tencent.rtmp.TXLiveConstants;

public class MainActivity extends AppCompatActivity {
    SuperPlayerView mSuperPlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSuperPlayerView = findViewById(R.id.mSuperPlayerView);
        // 播放器配置
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = false;
        // 设置悬浮窗的初始位置和宽高
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = 810;
        rect.height = 540;
        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;

        // 通过URL方式的视频信息配置
        SuperPlayerModel model2 = new SuperPlayerModel();
        model2.title = "测试视频-720P";
        model2.videoURL = "http://1252463788.vod2.myqcloud.com/95576ef5vodtransgzp1252463788/68e3febf4564972819220421305/v.f30.mp4";
        // 开始播放
        mSuperPlayerView.playWithMode(model2);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 重新开始播放
        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAY) {
            mSuperPlayerView.onResume();
            if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 停止播放
        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mSuperPlayerView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 释放
        mSuperPlayerView.release();
        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mSuperPlayerView.resetPlayer();
        }
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
