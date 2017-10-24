package com.mo.jingdong.presenter;

import android.content.Context;

import com.mo.jingdong.comment.API;
import com.mo.jingdong.model.DeleteModel;
import com.mo.jingdong.view.DeleteView;

import java.io.IOException;

import okhttp3.Call;

/**
 * Created by Mo on 2017/10/20.
 */

public class DeletePresenter implements DeleteModel.onDelete {

    private Context context;
    private DeleteModel deleteModel;
    private DeleteView deleteView;

    public DeletePresenter(Context context, DeleteView deleteView) {
        this.context = context;
        this.deleteView = deleteView;
        deleteModel=new DeleteModel();
        deleteModel.setOnDelete(this);
    }

    public void DeleteShop(String uid,String pid){
        deleteModel.Delete(uid,pid);
    }
    @Override
    public void onFailure(Call call, IOException e) {
        deleteView.onFailure("网络有误");
    }

    @Override
    public void onDeleteSuccess(String msg) {
        deleteView.onDeleteSuccess(msg);

    }

    @Override
    public void onDeleteFailure(String msg) {
        deleteView.onDeleteSuccess(msg);

    }
}
