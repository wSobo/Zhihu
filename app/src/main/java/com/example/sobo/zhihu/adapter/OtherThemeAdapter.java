package com.example.sobo.zhihu.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.activity.DesNotImgActivity;
import com.example.sobo.zhihu.bean.ZhihuThemeItem;
import com.example.sobo.zhihu.presenter.LoadingMore;
import com.example.sobo.zhihu.util.DBUtils;
import com.example.sobo.zhihu.util.DensityUtil;

import java.util.ArrayList;

/**
 * Created by sobo on 17/3/19.
 */

public class OtherThemeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements LoadingMore {

    private static final int HAVE_IMAGE = 0;
    private static final int NOT_IMAGE = 1;
    float width;
    int widthPx;
    int heighPx;
    boolean showLoadingMore;
    private Context mContext;
    private ArrayList<ZhihuThemeItem> zhihuThemeItems = new ArrayList<>();

    public OtherThemeAdapter(Context context) {
        mContext = context;
        width = mContext.getResources().getDimension(R.dimen.image_width);
        widthPx = DensityUtil.dip2px(mContext, width);
        heighPx = widthPx * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case HAVE_IMAGE:
                return new HaveImgViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.card_item_havaimg, parent, false));
            case NOT_IMAGE:
                return new NotImgViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.card_item_notimg, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case HAVE_IMAGE:
                bindHaveImgViewHolder((HaveImgViewHolder) holder, position);
                break;
            case NOT_IMAGE:
                bindNotImgViewHolder((NotImgViewHolder) holder, position);
                break;
        }

    }

    @Override
    public int getItemCount() {
        return zhihuThemeItems.size();
    }

    // 获取 在 加载更多时 的位置
    private int getLoadingMoreItemPosition() {
        return showLoadingMore ? getItemCount() - 1 : RecyclerView.NO_POSITION;
    }

    @Override
    public void loadingStart() {
        if (showLoadingMore) {
            return;
        }
        showLoadingMore = true;
        notifyItemInserted(getLoadingMoreItemPosition());
    }

    @Override
    public void loadingFinish() {
        if (!showLoadingMore) {
            return;
        }
        showLoadingMore = false;
        notifyItemRemoved(getLoadingMoreItemPosition());
    }

    public void clearData() {
        //清除数据
        zhihuThemeItems.clear();
        //刷新
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<ZhihuThemeItem> list) {
        zhihuThemeItems.addAll(list);
        notifyDataSetChanged();
    }

    // 跳转到 ZhihuDescribe
    private void goDesNotImgActivity(HaveImgViewHolder holder, ZhihuThemeItem item) {
        DBUtils.getDB(mContext).insertHasRead("zhihu", item.getId(), 1);
        holder.mTextView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, DesNotImgActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        mContext.startActivity(intent);
    }

    private void goDesNotImgActivity(NotImgViewHolder holder, ZhihuThemeItem item) {
        DBUtils.getDB(mContext).insertHasRead("zhihu", item.getId(), 1);
        holder.mTextView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, DesNotImgActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        mContext.startActivity(intent);
    }


    @Override
    public int getItemViewType(int position) {
        if (zhihuThemeItems.get(position).getImages() == null) {
            return NOT_IMAGE;
        } else {
            return HAVE_IMAGE;
        }
    }

    private void bindHaveImgViewHolder(final HaveImgViewHolder holder, int position) {

        final ZhihuThemeItem zhihuThemeItem = zhihuThemeItems.get(holder.getAdapterPosition());

        //ImageView的监听事件
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDesNotImgActivity(holder, zhihuThemeItem);
            }
        });
        //整个item的监听
        holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDesNotImgActivity(holder, zhihuThemeItem);
            }
        });

        holder.mTextView.setText(zhihuThemeItems.get(position).getTitle());

        Glide.with(mContext)
                .load(zhihuThemeItems.get(position).getImages().get(0))
                .placeholder(R.drawable.defaultimg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //设置缓存策略 SOURCE--缓存源资源
                .centerCrop().override(widthPx, heighPx)
                .into(holder.mImageView);

    }

    private void bindNotImgViewHolder(final NotImgViewHolder holder, int position) {

        final ZhihuThemeItem zhihuThemeItem = zhihuThemeItems.get(holder.getAdapterPosition());

        holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDesNotImgActivity(holder, zhihuThemeItem);
            }
        });
        holder.mTextView.setText(zhihuThemeItems.get(position).getTitle());
    }

    class HaveImgViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;
        final FrameLayout mFrameLayout;
        final ImageView mImageView;

        public HaveImgViewHolder(View itemView) {
            super(itemView);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_haveimg);
            mTextView = (TextView) itemView.findViewById(R.id.card_tv_haveimg);
            mImageView = (ImageView) itemView.findViewById(R.id.card_iv_haveimg);
            TextPaint tp = mTextView.getPaint();
            tp.setFakeBoldText(true);
        }
    }

    class NotImgViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;
        final FrameLayout mFrameLayout;

        public NotImgViewHolder(View itemView) {
            super(itemView);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_notimg);
            mTextView = (TextView) itemView.findViewById(R.id.card_tv_notimg);
            TextPaint tp = mTextView.getPaint();
            tp.setFakeBoldText(true);
        }
    }
}
