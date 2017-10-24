package com.mo.jingdong;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mo.jingdong.Fragment.FaXianFragment;
import com.mo.jingdong.Fragment.FenleiFragment;
import com.mo.jingdong.Fragment.GouWuCheFragment;
import com.mo.jingdong.Fragment.ShouyeFragment;
import com.mo.jingdong.Fragment.WoDeFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView shouye;
    private ImageView fenlei;
    private ImageView faxian;
    private ImageView gouwuche;
    private ImageView wode;
    private ShouyeFragment shouyeFragment;
    private FenleiFragment fenleiFragment;
    private FaXianFragment faXianFragment;
    private GouWuCheFragment gouWuCheFragment;
    private WoDeFragment woDeFragment;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window =getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        initView();
    }


    private void initView() {

        shouyeFragment = new ShouyeFragment();
        fenleiFragment = new FenleiFragment();
        faXianFragment = new FaXianFragment();
        gouWuCheFragment = new GouWuCheFragment();
        woDeFragment = new WoDeFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fl, shouyeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl, fenleiFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl, faXianFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl, gouWuCheFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.fl, woDeFragment).commit();
        getSupportFragmentManager().beginTransaction().show(shouyeFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(fenleiFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(faXianFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(gouWuCheFragment).commit();
        getSupportFragmentManager().beginTransaction().hide(woDeFragment).commit();

        shouye = (ImageView) findViewById(R.id.shouye);
        fenlei = (ImageView) findViewById(R.id.fenlei);
        faxian = (ImageView) findViewById(R.id.faxian);
        gouwuche = (ImageView) findViewById(R.id.gouwuche);
        wode = (ImageView) findViewById(R.id.wode);
        shouye.setImageResource(R.drawable.ac1);
        shouye.setOnClickListener(this);
        fenlei.setOnClickListener(this);
        faxian.setOnClickListener(this);
        gouwuche.setOnClickListener(this);
        wode.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shouye:
                getSupportFragmentManager().beginTransaction().show(shouyeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(fenleiFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(faXianFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(gouWuCheFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(woDeFragment).commit();
                shouye.setImageResource(R.drawable.ac1);
                fenlei.setImageResource(R.drawable.abw);
                faxian.setImageResource(R.drawable.aby);
                gouwuche.setImageResource(R.drawable.abu);
                wode.setImageResource(R.drawable.ac2);
                break;
            case R.id.fenlei:
                getSupportFragmentManager().beginTransaction().hide(shouyeFragment).commit();
                getSupportFragmentManager().beginTransaction().show(fenleiFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(faXianFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(gouWuCheFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(woDeFragment).commit();
                shouye.setImageResource(R.drawable.ac0);
                fenlei.setImageResource(R.drawable.abx);
                faxian.setImageResource(R.drawable.aby);
                gouwuche.setImageResource(R.drawable.abu);
                wode.setImageResource(R.drawable.ac2);

                break;
            case R.id.faxian:
                getSupportFragmentManager().beginTransaction().hide(shouyeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(fenleiFragment).commit();
                getSupportFragmentManager().beginTransaction().show(faXianFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(gouWuCheFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(woDeFragment).commit();
                shouye.setImageResource(R.drawable.ac0);
                fenlei.setImageResource(R.drawable.abw);
                faxian.setImageResource(R.drawable.bc5);
                gouwuche.setImageResource(R.drawable.abu);
                wode.setImageResource(R.drawable.ac2);

                break;
            case R.id.gouwuche:
                getSupportFragmentManager().beginTransaction().hide(shouyeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(fenleiFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(faXianFragment).commit();
                getSupportFragmentManager().beginTransaction().show(gouWuCheFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(woDeFragment).commit();
                shouye.setImageResource(R.drawable.ac0);
                fenlei.setImageResource(R.drawable.abw);
                faxian.setImageResource(R.drawable.aby);
                gouwuche.setImageResource(R.drawable.abv);
                wode.setImageResource(R.drawable.ac2);
                break;
            case R.id.wode:
                getSupportFragmentManager().beginTransaction().hide(shouyeFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(fenleiFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(faXianFragment).commit();
                getSupportFragmentManager().beginTransaction().hide(gouWuCheFragment).commit();
                getSupportFragmentManager().beginTransaction().show(woDeFragment).commit();
                shouye.setImageResource(R.drawable.ac0);
                fenlei.setImageResource(R.drawable.abw);
                faxian.setImageResource(R.drawable.aby);
                gouwuche.setImageResource(R.drawable.abu);
                wode.setImageResource(R.drawable.ac3);
                break;
        }
    }
}
