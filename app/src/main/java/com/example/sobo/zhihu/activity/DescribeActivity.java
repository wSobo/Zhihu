package com.example.sobo.zhihu.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.bean.ZhihuStorie;
import com.example.sobo.zhihu.presenter.ZhihuStoriePresenter;
import com.example.sobo.zhihu.presenter.implPresenter.ZhihuStoriePresenterImpl;
import com.example.sobo.zhihu.presenter.implView.IZhihuStorie;
import com.example.sobo.zhihu.util.WebUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DescribeActivity extends AppCompatActivity implements IZhihuStorie {

    private Unbinder unbinder;
    private ZhihuStoriePresenter mZhihuStoriePresenter;
    private WebView mWebView;

    boolean isEmpty; // 用来判断body是否为空

    private String id;
    private String title;
    private String image;
    private String url;
    private String mBody;
    private String[] css;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.top_img)
    ImageView mTopImg;
    @BindView(R.id.title_tv)
    TextView mTitileTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.wv_zhihu)
    LinearLayout wvZhihu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_describe);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        initData();
        initView();
        getData();
    }

    public void initData() {
        id = getIntent().getStringExtra("id");
        title = getIntent().getStringExtra("title");
        mZhihuStoriePresenter = new ZhihuStoriePresenterImpl(this);
    }

    public void initView() {
        mTitileTv.setText(title);
        TextPaint tp = mTitileTv.getPaint();
        tp.setFakeBoldText(true);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -mCollapsingToolbarLayout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle(title);
                } else {
                    mCollapsingToolbarLayout.setTitle("");
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // 显示返回按钮

        mWebView = new WebView(this);
        wvZhihu.addView(mWebView);
        WebSettings settings = mWebView.getSettings(); // 获得WebView设置
        settings.setJavaScriptEnabled(true); // 支持js
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); // 设置缓存模式
        settings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        settings.setDomStorageEnabled(true); // 开启DOM storage API功能
        settings.setDatabaseEnabled(true); // 开启Database storage API功能
        settings.setAppCachePath(getCacheDir().getAbsolutePath() + "/webViewCache");// 设置缓存路径
        settings.setAppCacheEnabled(true);// 开启Application Caches功能
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); // 设置页面的布局
        mWebView.setWebChromeClient(new WebChromeClient());
    }

    public void getData() {
        mZhihuStoriePresenter.getZhihuStorie(id);
    }

    @Override
    public void showError(String error) {
        Snackbar.make(wvZhihu, getString(R.string.snack_infor), Snackbar.LENGTH_INDEFINITE).setAction("重试", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        }).show();

    }

    @Override
    public void showZhihuStorie(ZhihuStorie zhihuStory) {

        Glide.with(this)
                .load(zhihuStory.getImage())
                .placeholder(R.drawable.defaultimg)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //设置缓存策略 SOURCE--缓存源资源
                .into(mTopImg);

        isEmpty = TextUtils.isEmpty(zhihuStory.getBody());
        mBody = zhihuStory.getBody();
        css = zhihuStory.getCss();

        // 如果body为空 WebView直接打开条目的分享url
        if (isEmpty) {
            mWebView.setWebViewClient(new WebViewClient());
            mWebView.setWebChromeClient(new WebChromeClient());
            mWebView.loadUrl(url);
        } else {
            String data = WebUtil.buildHtmlWithCss(mBody, css, false);
            mWebView.loadDataWithBaseURL(WebUtil.BASE_URL, data, WebUtil.MIME_TYPE, WebUtil.ENCODING, WebUtil.FAIL_URL);
        }


    }


    @Override
    protected void onDestroy() {
        mZhihuStoriePresenter.unSubcribe();
        unbinder.unbind();
        super.onDestroy();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
