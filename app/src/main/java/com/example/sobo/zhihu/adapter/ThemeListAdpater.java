package com.example.sobo.zhihu.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sobo.zhihu.R;
import com.example.sobo.zhihu.bean.ZhihuThemes;
import com.example.sobo.zhihu.bean.ZhihuThemesOther;

import java.util.ArrayList;

/**
 * Created by sobo on 17/3/29.
 */

public class ThemeListAdpater extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {


    private static final int TOP_USER_ITEM = 0;
    private static final int NOMAL_THEME_ITEM = 1;
    private Context mContext;
    private ZhihuThemes mZhihuThemes;
    private ArrayList<ZhihuThemesOther> mZhihuThemesOther;

    public ThemeListAdpater(Context context) {
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType) {
            case TOP_USER_ITEM:
                return new TopUserViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.nav_item_top, parent, false));
            case NOMAL_THEME_ITEM:
                return new ThemeViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.nav_item_theme, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TOP_USER_ITEM:
                bindTopUserViewHolder((TopUserViewHolder) holder, position);
                break;
            case NOMAL_THEME_ITEM:
                bindThemeHolder((ThemeViewHolder) holder, position);
                break;
        }
    }


    private void bindTopUserViewHolder(TopUserViewHolder holder, int position) {
        holder.loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "对不起,该功能还未完善", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void bindThemeHolder(final ThemeViewHolder holder, final int position) {



        holder.mImageView.setImageResource(R.drawable.ic_add);
        holder.mTextView.setText(mZhihuThemesOther.get(position - 1).getName());
//        holder.mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Glide.with(mContext)
//                        .load(R.drawable.ic_clear)
//                        .into(holder.mImageView);
//            }
//        });
    }

    public void setItems(ZhihuThemes zhihuThemes) {
        mZhihuThemes = zhihuThemes;
        mZhihuThemesOther = zhihuThemes.getOthers();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mZhihuThemesOther == null ? 1 : mZhihuThemesOther.size() + 1;
    }

    // 获取 item 布局类型
    @Override
    public int getItemViewType(int position) {
            if (position == 0)
                return TOP_USER_ITEM;
            else
                return NOMAL_THEME_ITEM;

    }

    class TopUserViewHolder extends RecyclerView.ViewHolder {
        final AppCompatButton loginBt;

        public TopUserViewHolder(View itemView) {
            super(itemView);
            loginBt = (AppCompatButton) itemView.findViewById(R.id.login_bt);
        }
    }

    class ThemeViewHolder extends RecyclerView.ViewHolder {
        final TextView mTextView;
        final ImageView mImageView;

        public ThemeViewHolder(View itemView) {
            super(itemView);
            mTextView = (TextView) itemView.findViewById(R.id.theme_name);
            mImageView = (ImageView) itemView.findViewById(R.id.add_bt);
        }
    }
}
