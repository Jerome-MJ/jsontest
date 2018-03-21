package top.mj93.jsontest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.util.ArrayList;

public class MainActivity extends Activity {

    ArrayList<BaseBean> lists=new ArrayList<>();
    private TextView mResultTextView;
    private EditText mNumberTextView;
    private StringBuffer sb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResultTextView = (TextView) findViewById(R.id.tv_result);
        mNumberTextView = (EditText) findViewById(R.id.et_number);
        sb=new StringBuffer();

    }

    public void resetNumber(View v){
        String s = mNumberTextView.getText().toString();
        setNumber(Integer.valueOf(s));
    }

    public void setNumber(int number){
        lists.clear();
        for (int i = 0; i < number; i++) {
            BaseBean bean=new BaseBean();
            bean.setAge(i);
            if(i%2==0){
                bean.setSex("男");
            }else{
                bean.setSex("女");
            }
            bean.setName("学生"+(i+1));
            lists.add(bean);
        }
        sb.append("\n");
        sb.append("开始：").append(number).append("数据生成");
        mResultTextView.setText(sb);
    }


    public void moshi(View v){
        Long moshiStart =System.currentTimeMillis();
        moShi2Json(lists);
        long time = System.currentTimeMillis() - moshiStart;
        sb.append("\n").append("Moshi Time：").append(time);
        mResultTextView.setText(sb);
    }

    public void gson(View v){
        Long gsonStart =System.currentTimeMillis();
        gson2Json(lists);
        long time = System.currentTimeMillis() - gsonStart;
        sb.append("\n").append("Gson Time：").append(time);
        mResultTextView.setText(sb);
    }

    public void fastjson(View v){
        Long fastJsonStart=System.currentTimeMillis();
        fast2Json(lists);
        long time = System.currentTimeMillis() - fastJsonStart;
        sb.append("\n").append("FastJson Time：").append(time);
        mResultTextView.setText(sb);
    }

    private static void moShi2Json(Object o){
        Moshi moshi=new Moshi.Builder().build();
        JsonAdapter<Object> adapter = moshi.adapter(Object.class);
        String s = adapter.toJson(o);
    }

    private static void gson2Json(Object o){
        Gson gson=new Gson();
        String s = gson.toJson(o, new TypeToken<Object>() {
        }.getType());
    }

    private  static void fast2Json(Object o){
        String s = JSON.toJSONString(o);
    }


}
