package com.github.baby.owspace.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.github.baby.owspace.R;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerDiscussComponent;
import com.github.baby.owspace.di.components.DaggerPublishComponent;
import com.github.baby.owspace.di.modules.DiscussModule;
import com.github.baby.owspace.di.modules.PublishModule;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.presenter.PublishContract;
import com.github.baby.owspace.presenter.PublishPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublishDiscussActivity extends BaseActivity implements PublishContract.View {
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.publish)
    TextView publish;
    @BindView(R.id.discussTitle)
    EditText discussTitle;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.rg)
    RadioGroup rg;
    @Inject
    PublishPresenter presenter;
    String gameName;
    String type;
    String discussT;
    String discussC;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        ButterKnife.bind(this);
        initPresenter();
        initView();

    }

    private void initView(){
        final Bundle bundle = getIntent().getExtras();
        this.gameName = bundle.getString("gameName");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        title.setText("发布到"+this.gameName);
        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                discussT = String.valueOf(discussTitle.getText());
                discussC = String.valueOf(content.getText());
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.button_discuss:
                                type = "1";
                                break;
                            case R.id.button_qa:
                                type = "2";
                                break;
                        }
                    }
                });
                presenter.publish(discussT,discussC,"1",gameName,"ubiboy");
            }
        });

    }
    private void initPresenter(){
        DaggerPublishComponent.builder()
                .publishModule(new PublishModule(this))
                .netComponent(OwspaceApplication.get(this).getNetComponent())
                .build()
                .inject(this);

    }

    @Override
    public void publishSuccess(String success) {
        Toast.makeText(PublishDiscussActivity.this,success,Toast.LENGTH_SHORT).show();
        this.finish();
    }
}
