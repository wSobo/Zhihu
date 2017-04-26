package com.example.sobo.zhihu.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.bean.LaunchImage;
import com.example.sobo.zhihu.presenter.SplashPresenter;
import com.example.sobo.zhihu.presenter.implPresenter.SplashPresenterImpl;
import com.example.sobo.zhihu.presenter.implView.ISplash;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SplashActivity extends AppCompatActivity implements ISplash {

    private Unbinder unbinder;
    private SplashPresenter mSplashPresenter;
    private String imgUrl;

    @BindView(R.id.splash_iv)
    ImageView SplashIv;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 10000) {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        unbinder = ButterKnife.bind(this);
        mSplashPresenter = new SplashPresenterImpl(this);
        mSplashPresenter.getLaunchImage();

    }

    @Override
    public void setSplashImage(LaunchImage launchImage) {
        imgUrl = launchImage.getCreatives().get(0).getUrl();
        Glide.with(this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(SplashIv);

    }

    @Override
    public void onRequestError() {

    }

    @Override
    public void onRequestEnd() {
        Message message = new Message();
        message.what = 10000;
        mHandler.sendMessageDelayed(message, 3000);
    }


    @Override
    protected void onDestroy() {
        mSplashPresenter.unSubcribe();
        unbinder.unbind();
        super.onDestroy();
    }
}
