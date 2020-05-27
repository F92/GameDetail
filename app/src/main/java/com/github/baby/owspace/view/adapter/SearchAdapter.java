package com.github.baby.owspace.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.baby.owspace.R;
import com.github.baby.owspace.app.GlideApp;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.view.activity.DiscussActivity;
import com.github.baby.owspace.view.activity.DiscussContentActivity;
import com.github.baby.owspace.view.activity.SearchActivity;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
    private static final int FOOTER_TYPE = 1001;
    private static final int CONTENT_TYPE = 1002;
    private List<HomeList> artList = new ArrayList<>();
    private Context context;
    private boolean hasMore=true;
    private boolean error=false;

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public SearchAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_footer, parent, false);
            return new SearchAdapter.FooterViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
            return new SearchAdapter.ArtHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return FOOTER_TYPE;
        }
        return CONTENT_TYPE;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position + 1 == getItemCount()) {
            if (artList.size()==0){
                return;
            }
            SearchAdapter.FooterViewHolder footerHolder = (SearchAdapter.FooterViewHolder)holder;
            if (error){
                error = false;
                footerHolder.avi.setVisibility(View.GONE);
                footerHolder.noMoreTx.setVisibility(View.GONE);
                footerHolder.errorTx.setVisibility(View.VISIBLE);
                footerHolder.errorTx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
            if (hasMore){
                footerHolder.avi.setVisibility(View.VISIBLE);
                footerHolder.noMoreTx.setVisibility(View.GONE);
                footerHolder.errorTx.setVisibility(View.GONE);
            }else {
                footerHolder.avi.setVisibility(View.GONE);
                footerHolder.noMoreTx.setVisibility(View.VISIBLE);
                footerHolder.errorTx.setVisibility(View.GONE);
            }
        } else {
            SearchAdapter.ArtHolder artHolder = (SearchAdapter.ArtHolder) holder;
            final HomeList item = artList.get(position);
            artHolder.gameTv.setText(item.getGameName());
            GlideApp.with(context).load(item.getGameImage()).centerCrop().into(artHolder.imageIv);
            artHolder.type_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;
                    intent = new Intent(context,DiscussActivity.class);
                    DiscussList discussList = new DiscussList();
                    discussList.setGameName(item.getGameName());
                    intent.putExtra("discussList",discussList);
                    context.startActivity(intent);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return artList.size() + 1;
    }

    public void setArtList(List<HomeList> artList) {
        int position = artList.size() - 1;
        this.artList.addAll(artList);
        notifyItemChanged(position);
    }

    public void replaceAllData(List<HomeList> artList) {
        this.artList.clear();
        this.artList.addAll(artList);
        notifyDataSetChanged();
    }

    public String getLastItemId() {
        if (artList.size() == 0) {
            return "0";
        }
        HomeList item = artList.get(artList.size() - 1);
        return String.valueOf(item.getGameId());
    }



    static class ArtHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_iv)
        ImageView imageIv;
        @BindView(R.id.arrow_iv)
        ImageView arrowIv;
        @BindView(R.id.game_tv)
        TextView gameTv;
        @BindView(R.id.type_container)
        RelativeLayout type_container;

        public ArtHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avi)
        AVLoadingIndicatorView avi;
        @BindView(R.id.nomore_tx)
        TextView noMoreTx;
        @BindView(R.id.error_tx)
        TextView errorTx;
        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
