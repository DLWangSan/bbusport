package com.dlws.bbusport;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlws.bbusport.activity.first_page.FirstPageActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.zackratos.ultimatebar.UltimateBar;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private Button loginButton;
    private EditText idEditText;
    private EditText pwdEditText;
    private CheckBox remPwdCheckBox;

    private View progress;
    private View mInputLayout;
    private float mWidth, mHeight;

    private String id;
    private String pwd;
    static int flag;    //判断账号密码匹配
    public static Users usersNow;      //当前用户

    private boolean isHideFirst = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //状态栏透明
        UltimateBar ultimateBar = new UltimateBar(this);
        ultimateBar.setImmersionBar();
        //获取实例初始化
        initView();
        //加载保存的密码（如果勾选）
        loadPwd();
        //密码可见
        pwdVisible();
        //登录按钮点击事件
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remembPwd();
                loadActions();
                login();
            }
        });

    }


    /*延时让动画加载完毕，暂定2s。
    * 数据库信息增多之后，可以去掉延时
    * 直接在完成后进入*/
    private void login(){
        new Handler().postDelayed(new Runnable(){
            public void run() {
                //               通过验证则跳转
                try {
                    if(checkUsers()==1){
                        Intent intent=new Intent(MainActivity.this,FirstPageActivity.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, usersNow.getName()+",欢迎！", Toast.LENGTH_SHORT).show();
                        recovery();
                    }else if(checkUsers()==-1){
                        Toast.makeText(MainActivity.this, "账号或密码错误，登录失败！", Toast.LENGTH_SHORT).show();
                        recovery();
                    }else{
                        Toast.makeText(MainActivity.this, "网络连接错误，登录失败！", Toast.LENGTH_SHORT).show();
                        recovery();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 2000);
    }

    /*加载动画*/
    private void loadActions(){
        // 计算出控件的高与宽
        mWidth = loginButton.getMeasuredWidth();
        mHeight = loginButton.getMeasuredHeight();
        // 隐藏输入框
        idEditText.setVisibility(View.INVISIBLE);
        pwdEditText.setVisibility(View.INVISIBLE);

        inputAnimator(mInputLayout, mWidth, mHeight);
    }
    /*密码小眼睛，余童提供代码，已完成*/
    @SuppressLint("ClickableViewAccessibility")
    private void pwdVisible(){
        final Drawable[] drawables = pwdEditText.getCompoundDrawables();
        final int eyeWidth = drawables[2].getBounds().width();
        final Drawable drawableEyeOpen = getResources().getDrawable(R.drawable.zhengyan);
        drawableEyeOpen.setBounds(drawables[2].getBounds());
        pwdEditText.setOnTouchListener(new View.OnTouchListener() {
                                           @Override
                                           public boolean onTouch(View v, MotionEvent event) {
                                               if (event.getAction() == MotionEvent.ACTION_UP) {
                                                   float et_pwdMinX = v.getWidth() - eyeWidth - pwdEditText.getPaddingRight();
                                                   float et_pwdMaxX = v.getWidth();
                                                   float et_pwdMinY = 0;
                                                   float et_pwdMaxY = v.getHeight();
                                                   float x = event.getX();
                                                   float y = event.getY();
                                                   if (x < et_pwdMaxX && x > et_pwdMinX && y > et_pwdMinY && y < et_pwdMaxY) {
                                                       isHideFirst = !isHideFirst;
                                                       if (isHideFirst) {
                                                           pwdEditText.setCompoundDrawables(null,
                                                                   null,
                                                                   drawables[2], null);

                                                           pwdEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                                                       } else {
                                                           pwdEditText.setCompoundDrawables(null, null, drawableEyeOpen, null);
                                                           pwdEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                                                       }
                                                   }
                                               }
                                               return false;
                                           }

                                       }
        );
    }

    private void remembPwd(){
        id=idEditText.getText().toString();
        pwd=pwdEditText.getText().toString();

        if(remPwdCheckBox.isChecked())
        {
            writePwd(id,pwd);
        }else{
            deletePwd();
        }
    }

    //加载保存的密码
    private void loadPwd(){

        id=idEditText.getText().toString();
        pwd=pwdEditText.getText().toString();
        remPwdCheckBox.setChecked(true);

        SharedPreferences sPreferences=getSharedPreferences("users", MODE_PRIVATE);
        String idSaved=sPreferences.getString("id","");
        String pwdSaved = sPreferences.getString("pwd","");
        idEditText.setText(idSaved);
        pwdEditText.setText(pwdSaved);
    }

    private void initView(){
        loginButton = findViewById(R.id.ok);
        idEditText=findViewById(R.id.id);
        pwdEditText=findViewById(R.id.pwd);
        remPwdCheckBox=findViewById(R.id.remembPwd);
        progress = findViewById(R.id.layout_progress);
        mInputLayout = findViewById(R.id.input_layout);


    }

    //恢复动画状态
    private void recovery() {
        progress.setVisibility(View.GONE);
        mInputLayout.setVisibility(View.VISIBLE);
        idEditText.setVisibility(View.VISIBLE);
        pwdEditText.setVisibility(View.VISIBLE);

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mInputLayout.getLayoutParams();
        params.leftMargin = 0;
        params.rightMargin = 0;
        mInputLayout.setLayoutParams(params);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout, "scaleX", 0.5f,1f );
        animator2.setDuration(500);
        animator2.setInterpolator(new AccelerateDecelerateInterpolator());
        animator2.start();
    }


    /*
     * 输入框的动画效果
     * @param view  控件
     * @param w     宽
     * @param h     高
     */
    private void inputAnimator(final View view, float w, float h) {

        AnimatorSet set = new AnimatorSet();

        ValueAnimator animator = ValueAnimator.ofFloat(0, w);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) view
                        .getLayoutParams();
                params.leftMargin = (int) value;
                params.rightMargin = (int) value;
                view.setLayoutParams(params);
            }
        });

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mInputLayout,
                "scaleX", 1f, 0.5f);
        set.setDuration(1000);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.playTogether(animator, animator2);
        set.start();
        set.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                /**
                 * 动画结束后，先显示加载的动画，然后再隐藏输入框
                 */
                progress.setVisibility(View.VISIBLE);
                progressAnimator(progress);
                mInputLayout.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }
        });


    }

    /*
     * 出现进度动画
     * @param view
     */
    private void progressAnimator(final View view) {
        PropertyValuesHolder animator = PropertyValuesHolder.ofFloat("scaleX",
                0.5f, 1f);
        PropertyValuesHolder animator2 = PropertyValuesHolder.ofFloat("scaleY",
                0.5f, 1f);
        ObjectAnimator animator3 = ObjectAnimator.ofPropertyValuesHolder(view,
                animator, animator2);
        animator3.setDuration(1000);
        animator3.setInterpolator(new JellyInterpolator());
        animator3.start();

    }

    /*勾选记住密码跳转的方法
    * 实际操作为通过SharedPreferences
    * 将账号密码保存到user文件中
    * 已完成*/
    private void writePwd(String id1, String pwd1){
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        editor.putString("id", id1);
        editor.putString("pwd", pwd1);
        editor.apply();
    }
    /*不勾选记住密码跳转的方法
     * 实际操作为通过SharedPreferences
     * 将user文件中信息清空
     * 已完成*/
    private void deletePwd(){
        SharedPreferences.Editor editor = getSharedPreferences("users", MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
    }

    /*已完成*/
    protected int checkUsers() throws InterruptedException {

        Thread thread=new Thread(){
            @Override
            public void run() {
                try{
                    OkHttpClient client =new OkHttpClient();
                    Request request =new Request.Builder()
                            .url("http://dlws.show.0552web.com/androidtest/bbusportUsers.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSON(responseData);
                }catch (Exception e){
                    flag = -2;
                    e.printStackTrace();
                }
            }

        };

        thread.start();
        thread.join(); //等待thread线程完成后再进行其他操作

       return flag;
    }


    /*解析JSON数据，已完成*/
    private void parseJSON(String responseData) {
        Gson gson = new Gson();
        List<Users> usersList = gson.fromJson(responseData, new TypeToken<List<Users>>() {
        }.getType());

        for (Users users : usersList) {
            if (users.getId().equals(id)) {
                if (users.getPwd().equals(pwd)) {
                    flag = 1;
                    usersNow=users;
                    break;
                } else {
                    flag = -1;
                    continue;
                }

            } else {
                flag = -1;
                continue;
            }
        }
    }

}
