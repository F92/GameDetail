package com.github.baby.owspace.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.baby.owspace.R;
import com.github.baby.owspace.app.GlideApp;
import com.github.baby.owspace.model.entity.DiscussContentList;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.view.activity.DiscussActivity;
import com.github.baby.owspace.view.activity.DiscussContentActivity;
import com.wang.avi.AVLoadingIndicatorView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussContentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int FOOTER_TYPE = 1001;
    private static final int CONTENT_TYPE = 1002;
    private List<DiscussContentList> artList = new ArrayList<>();
    private Context context;
    private boolean hasMore=true;
    private boolean error=false;

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public DiscussContentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_TYPE){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_footer, parent, false);
            return new DiscussContentAdapter.FooterViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discuss_content, parent, false);
            return new DiscussContentAdapter.ArtHolder(view);
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
            DiscussContentAdapter.FooterViewHolder footerHolder = (DiscussContentAdapter.FooterViewHolder)holder;
            if (error){
                error = false;
                footerHolder.avi.setVisibility(View.GONE);
                footerHolder.noMoreTx.setVisibility(View.GONE);
                footerHolder.errorTx.setVisibility(View.VISIBLE);
                footerHolder.errorTx.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO 重新加载
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
            final DiscussContentAdapter.ArtHolder artHolder = (DiscussContentAdapter.ArtHolder) holder;
            final DiscussContentList item = artList.get(position);
            artHolder.userTv.setText(item.getUserName());
            artHolder.contentTv.setText(item.getCommentDetail());
            artHolder.rcomment.setText(item.getRcomment());
            GlideApp.with(context).load(item.getUserImage()).centerCrop().into(artHolder.imageIv);
            artHolder.typeContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DiscussContentActivity activity = (DiscussContentActivity)context;
                    System.out.println(item.getCommentId());
                    activity.change(item.getUserName(),item.getCommentId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return artList.size() + 1;
    }

    public void setArtList(List<DiscussContentList> artList) {
        int position = artList.size() - 1;
        this.artList.addAll(artList);
        notifyItemChanged(position);
    }

    public void replaceAllData(List<DiscussContentList> artList) {
        this.artList.clear();
        this.artList.addAll(artList);
        notifyDataSetChanged();
    }

    public String getLastItemId() {
        if (artList.size() == 0) {
            return "0";
        }
        DiscussContentList item = artList.get(artList.size() - 1);
        return String.valueOf(item.getDiscussId());
    }



    static class ArtHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_iv)
        ImageView imageIv;
        @BindView(R.id.user_tv)
        TextView userTv;
        @BindView(R.id.rcomment)
        TextView rcomment;
        @BindView(R.id.content_tv)
        TextView contentTv;
        @BindView(R.id.discuss_container)
        RelativeLayout typeContainer;

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
