package com.github.baby.owspace.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.baby.owspace.R;
import com.github.baby.owspace.app.GlideApp;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.view.activity.DetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/2
 * owspace
 */
public class MainFragment extends Fragment {
    String title;
    @BindView(R.id.image_iv)
    ImageView imageIv;
    @BindView(R.id.type_container)
    LinearLayout typeContainer;
    @BindView(R.id.title_tv)
    TextView titleTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.author_tv)
    TextView authorTv;
    @BindView(R.id.type_tv)
    TextView typeTv;
    @BindView(R.id.home_advertise_iv)
    ImageView homeAdvertiseIv;
    @BindView(R.id.pager_content)
    RelativeLayout pagerContent;

    public static Fragment instance(HomeList homeList) {
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("homeList",homeList);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.item_main_page, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        final HomeList homeList = (HomeList) getArguments().getSerializable("homeList");
        GlideApp.with(this.getContext()).load(homeList.getGameImage()).centerCrop().into(imageIv);
        titleTv.setText(homeList.getGameName());
        contentTv.setText(homeList.getGameIntroduce());
        authorTv.setText(homeList.getUserName());
        typeContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("homeList",homeList);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
