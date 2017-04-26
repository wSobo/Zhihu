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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.activity.DescribeActivity;
import com.example.sobo.zhihu.bean.ZhihuDailyItem;
import com.example.sobo.zhihu.bean.ZhihuTopDailyItem;
import com.example.sobo.zhihu.presenter.LoadingMore;
import com.example.sobo.zhihu.util.DBUtils;
import com.example.sobo.zhihu.util.DensityUtil;
import com.example.sobo.zhihu.widget.CycleView;

import java.util.ArrayList;

import static com.example.sobo.zhihu.MyApplication.getContext;

/**
 * Created by sobo on 16/11/8.
 */

public class ZhihuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements LoadingMore {

    private static final int TYPE_LOADING_MORE = -1;
    private static final int TOP_CYCLE = 0;
    private static final int NOMAL_ITEM = 1;
    float width;
    int widthPx;
    int heighPx;
    boolean showLoadingMore;
    private Context mContext;
    private ArrayList<ZhihuDailyItem> zhihuDailyItems = new ArrayList<>();
    private ArrayList<ZhihuTopDailyItem> zhihuTopDailyItems = new ArrayList<>();

    public ZhihuAdapter(Context context) {
        this.mContext = context;
        width = mContext.getResources().getDimension(R.dimen.image_width);
        widthPx = DensityUtil.dip2px(mContext, width);
        heighPx = widthPx * 3 / 4;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TOP_CYCLE:
                return new CycleViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.cycle_view, parent, false));
            case NOMAL_ITEM:
                return new ZhihuViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.card_item_havaimg, parent, false));
            case TYPE_LOADING_MORE:
                return new LoadingMoreHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.infinite_loading, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TOP_CYCLE:
                bindCycleViewHolder((CycleViewHolder) holder, position);
                break;
            case NOMAL_ITEM:
                bindNomalViewHolder((ZhihuViewHolder) holder, position);
                break;
            case TYPE_LOADING_MORE:
                bindLoadingViewHolder((LoadingMoreHolder) holder, position);
                break;
        }

    }

    private void bindCycleViewHolder(CycleViewHolder holder, int position) {
        holder.topStorie.setData(zhihuTopDailyItems, mContext, new CycleView.CycleViewListener() {
            @Override
            public void onItemClick(int position) {
                DBUtils.getDB(mContext).insertHasRead("zhihu", zhihuTopDailyItems.get(position).getId(), 1);
                Intent intent = new Intent(getContext(), DescribeActivity.class);
                intent.putExtra("id", zhihuTopDailyItems.get(position).getId());
                intent.putExtra("title", zhihuTopDailyItems.get(position).getTitle());
                intent.putExtra("image", zhihuTopDailyItems.get(position).getImage());
                mContext.startActivity(intent);
            }
        });

    }


    private void bindNomalViewHolder(final ZhihuViewHolder holder, int position) {

        final ZhihuDailyItem zhihuDailyItem = zhihuDailyItems.get(holder.getAdapterPosition() - 1);

        //ImageView的监听事件
        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDescribeActivity(holder, zhihuDailyItem);
            }
        });
        //整个item的监听
        holder.mFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goDescribeActivity(holder, zhihuDailyItem);
            }
        });

        holder.mTextView.setText(zhihuDailyItem.getTitle());


        Glide.with(mContext)
                .load(zhihuDailyItems.get(position - 1).getImages()[0])
                .placeholder(R.drawable.defaultimg)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE) //设置缓存策略 SOURCE--缓存源资源
                .centerCrop().override(widthPx, heighPx)
                .into(holder.mImageView);


    }

    private void bindLoadingViewHolder(LoadingMoreHolder holder, int position) {
        holder.progressBar.setVisibility(showLoadingMore == true ? View.VISIBLE : View.INVISIBLE);
    }


    public void addItems(ArrayList<ZhihuDailyItem> list) {
        zhihuDailyItems.addAll(list);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<ZhihuDailyItem> list, final ArrayList<ZhihuTopDailyItem> toplist) {
        zhihuTopDailyItems.addAll(toplist);
        addItems(list);
    }

    // 跳转到 ZhihuDescribe
    private void goDescribeActivity(ZhihuViewHolder holder, ZhihuDailyItem item) {
        DBUtils.getDB(mContext).insertHasRead("zhihu", item.getId(), 1);
        holder.mTextView.setTextColor(Color.GRAY);
        Intent intent = new Intent(mContext, DescribeActivity.class);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        intent.putExtra("image", item.getImages());
        mContext.startActivity(intent);
    }


    // 获取 item 条数
    @Override
    public int getItemCount() {
        return zhihuDailyItems.size() + 1;
    }

    // 获取 item 布局类型
    @Override
    public int getItemViewType(int position) {
        if (position < getItemCount() && getItemCount() > 0) {
            if (position == 0)
                return TOP_CYCLE;
            else
                return NOMAL_ITEM;
        } else {
            return TYPE_LOADING_MORE;
        }
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
        zhihuTopDailyItems.clear();
        zhihuDailyItems.clear();
        //刷新
        notifyDataSetChanged();
    }


    // ProgressBar Holder
    public static class LoadingMoreHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;

        public LoadingMoreHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView;
        }
    }

    // 知乎item Holder
    class ZhihuViewHolder extends RecyclerView.ViewHolder {

        final TextView mTextView;
        final FrameLayout mFrameLayout;
        final ImageView mImageView;

        public ZhihuViewHolder(View itemView) {
            super(itemView);
            mFrameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_haveimg);
            mTextView = (TextView) itemView.findViewById(R.id.card_tv_haveimg);
            mImageView = (ImageView) itemView.findViewById(R.id.card_iv_haveimg);
            TextPaint tp = mTextView.getPaint();
            tp.setFakeBoldText(true);

        }
    }

    // Cycleview Holder
    public static class CycleViewHolder extends RecyclerView.ViewHolder {
        CycleView topStorie;

        public CycleViewHolder(View itemView) {
            super(itemView);
            topStorie = (CycleView) itemView.findViewById(R.id.top_storie);
            topStorie.requestDisallowInterceptTouchEvent(false);
        }
    }

}
