package com.github.baby.owspace.presenter;

public interface PublishContract {
    interface presenter{
        void publish(String title,String content,String type,String gameName,String userName);
    }
    interface View{
        void publishSuccess(String success);
    }
}
