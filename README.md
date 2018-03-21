> 在安卓项目中经常会涉及到JSON的转换，下面针对市场上各种JSON解析库做对比，本次主要对GSON、FastJson、Moshi库做对比


## 简单对比
| 模块     | Gson 2.8.2 | FastJson 1.1.68 | Moshi 1.5.0 |
| -------- | ---------- | --------------- | ----------- |
| 文件大小 | 233kb      | 229kb           | 124kb       |
| 公司     | google     | alibaba         | square      |
从文件大小来看：Gson>FastJson>Moshi，文件选用**Moshi**最优
## 性能对比
### 相关代码
Bean类代码
```

public class BaseBean {
    private String name;
    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
```
主方法
```

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

```
### 结果展示

**FastJson更优**，Gson次之，Moshi最慢

| 次数 | Moshi   | Gson    | FastJson |
| ---- | ------- | ------- | -------- |
| 10   | 3ms     | 7ms     | 1ms      |
| 100  | 25ms    | 23ms    | 14ms     |
| 1000 | 230ms   | 172ms   | 102ms    |
| 1w   | 2336ms  | 1680ms  | 1004ms   |
| 10w  | 26539ms | 18875ms | 11258ms  |
#### 魅族m2 note手机
![S80321-114910.jpg](https://upload-images.jianshu.io/upload_images/5951694-9815dc6d7a4ee54c.jpg?imageMogr2/auto-orient/strip%7CimageView2/2/w/500)
