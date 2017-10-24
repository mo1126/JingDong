package com.mo.jingdong;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mo.jingdong.comment.API;
import com.mo.jingdong.entity.GetUserInfoBean;
import com.mo.jingdong.presenter.GetInfoPresenter;
import com.mo.jingdong.view.GetInfoView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GerenActivity extends AppCompatActivity implements View.OnClickListener,GetInfoView {

    private TextView username;
    private TextView nickname;
    private ImageView iv;
    private RelativeLayout huantouxiang;
    private RelativeLayout huannicheng;
    private GetInfoPresenter presenter;
    private int uid;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    protected static Uri tempUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geren);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences("uid", MODE_PRIVATE);
        uid = sharedPreferences.getInt("uid", 0);
        presenter = new GetInfoPresenter(this,this);
        presenter.getData(uid);
    }
    private void initView() {
        username = (TextView) findViewById(R.id.gr_username);
        nickname = (TextView) findViewById(R.id.gr_nickname);
        iv = (ImageView) findViewById(R.id.img_geren);
        huantouxiang = (RelativeLayout) findViewById(R.id.huantouxiang);
        huannicheng = (RelativeLayout) findViewById(R.id.huannicheng);
        huantouxiang.setOnClickListener(this);
        huannicheng.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.huantouxiang:
                head();
                break;
            case R.id.huannicheng:
                Intent intent=new Intent(this,HuanNCActivity.class);
                startActivity(intent);
                break;
        }

    }
    public void head(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置头像");
        String[] items = { "选择本地照片", "拍照" };
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent openAlbumIntent = new Intent(
                                Intent.ACTION_GET_CONTENT);
                        openAlbumIntent.setType("image/*");
                        startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "image.jpg"));
                        // 指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }

            }
        });
        builder.create().show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回码是可以用的
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(tempUri); // 开始对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    startPhotoZoom(data.getData()); // 开始对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
            }
        }
    }
    protected void startPhotoZoom(Uri uri) {
        if (uri == null) {
            Log.i("tag", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            final Bitmap photo = extras.getParcelable("data");
            setFile(photo);


            OkHttpClient okHttpClient1 = new OkHttpClient();
            MultipartBody.Builder builder1 = new MultipartBody.Builder();
            builder1.setType(MultipartBody.FORM);
            File file=new File("mnt/sdcard/mo.jpg");
            builder1.addFormDataPart("uid",uid+"");

            System.out.println(file.getName()+file.toString()+"=================================================");
             builder1.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("application/octet-stream"), file));
             Request request1 = new Request.Builder().post(builder1.build()).url(API.UPLOAD).build();
            okHttpClient1.newCall(request1).enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(GerenActivity.this, "网络上传失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(okhttp3.Call call, final Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccessful()){
                                try {
                                    iv.setImageBitmap(photo);
                                    String string = response.body().string();
                                    System.out.println("fileuploadsuccess：" + string);
                                    Toast.makeText(GerenActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    });

                }
            });

        }
    }
    private void setFile(Bitmap photo) {
        File file=new File("mnt/sdcard/mo.jpg");
        try {
            BufferedOutputStream bout=new BufferedOutputStream(new FileOutputStream(file));
            photo.compress(Bitmap.CompressFormat.JPEG,100,bout);
            bout.flush();
            bout.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onFailure(Call call, IOException e) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GerenActivity.this, "网络有误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onGetInfoSuccess(final GetUserInfoBean getUserInfoBean) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                 if(getUserInfoBean.data.icon!=null){
                     Glide.with(GerenActivity.this).load(getUserInfoBean.data.icon).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE) .into(iv);
                 }
                if(getUserInfoBean.data.nickname!=null){
                    nickname.setText(getUserInfoBean.data.nickname.toString());
                }else{
                    nickname.setText(getUserInfoBean.data.username.toString());
                }
                username.setText(getUserInfoBean.data.username.toString());
            }
        });
    }

    @Override
    public void onGetInfoFailure(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(GerenActivity.this, msg ,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
