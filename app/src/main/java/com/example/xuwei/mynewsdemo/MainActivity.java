package com.example.xuwei.mynewsdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.xuwei.mynewsdemo.adapter.RecyclerViewAdapter;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.wxic.xuwei.recyclerviewlibrary.EnhanceRecyclerView;
import cn.wxic.xuwei.recyclerviewlibrary.MyItemDecoration;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView)
    EnhanceRecyclerView mRecyclerView;
    private View view;
    private RecyclerView.Adapter adapter;
    private ArrayList<News> list;
    private String HTTPURL = "http://litchiapi.jstv.com/api/GetFeeds?column=3&PageSize=20&pageIndex=1&val=100511D3BE5301280E0992C73A9DEC41";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, OrientationHelper.VERTICAL));
        initData();

    }

   private void initData() {
        list = new ArrayList<News>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(HTTPURL).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    JSONObject jo1 = new JSONObject(response.body().string());
                    JSONObject jo2 = jo1.getJSONObject("paramz");
                    JSONArray ja = jo2.getJSONArray("feeds");
                    News news = null;
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject data = ja.getJSONObject(i).getJSONObject(
                                "data");
                        String imageUrl = "http://litchiapi.jstv.com"
                                + data.getString("cover");
                        String title = data.getString("subject");
                        String summary = data.getString("summary");
                        news = new News(imageUrl, title, summary);
                        list.add(news);
                        Log.e("第"+i+"个",summary);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(5);


                }

            }

            @Override
            public void onFailure(Request arg0, IOException arg1) {
                Toast.makeText(MainActivity.this,"新闻接口访问不到数据，请检查接口！！！",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < 15; i++) {
                    String imageurl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492432020&di=91bd257fe59386eff937d1bd71bb32af&imgtype=jpg&er=1&src=http%3A%2F%2Fcimage.tianjimedia.com%2FuploadImages%2FthirdImages%2F2017%2F075%2F8V6XL66J083F.jpg"   ;
                    String title="x";
                    String summry="xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
                    News news=new News(imageurl,title,summry);
                    list.add(news);
                }
            }
        });

       for (int i = 0; i < 15; i++) {
           String imageurl="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492432020&di=91bd257fe59386eff937d1bd71bb32af&imgtype=jpg&er=1&src=http%3A%2F%2Fcimage.tianjimedia.com%2FuploadImages%2FthirdImages%2F2017%2F075%2F8V6XL66J083F.jpg"   ;
           String title="x";
           String summry="xxxxxxxxxxxxxxxxxxxxxxxxxxxx";
           News news=new News(imageurl,title,summry);
           list.add(news);
       }

        adapter = new RecyclerViewAdapter(this, list);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addItemDecoration(new MyItemDecoration(this,10));

        mRecyclerView.setPullToRefreshListener(new EnhanceRecyclerView.PullToRefreshListener() {
            @Override
            public void onRefreshing() {
                refreshData();
            }
        });
        mRecyclerView.setLoadMoreListener(new EnhanceRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });
    }
    public void refreshData(){
        mHandler.sendEmptyMessageDelayed(1,1000);
    }

    public void loadMoreData(){
        mHandler.sendEmptyMessageDelayed(2,1000);
    }
    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    adapter = new RecyclerViewAdapter(MainActivity.this, list);
                    mRecyclerView.setAdapter(adapter);
                    mRecyclerView.addItemDecoration(new MyItemDecoration(MainActivity.this,10));
                    break;
                case 1:
                  News      news = new News("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491837205927&di=3282c0c2f73b84e49127422e378cb0f3&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D6df623d4978fa0ec7f926c0913a775d8%2F0eb30f2442a7d933c7b30594ab4bd11373f00139.jpg", "我是刷新出来的", "瑞雯花木兰");
                        list.add(0,news);

                    mRecyclerView.setRefreshComplete();
                    break;
                case 2:
                    News      news2 = new News("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1491836990744&di=1e8e4f57831dd92643361829738c1281&imgtype=0&src=http%3A%2F%2Fatt.bbs.duowan.com%2Fforum%2F201310%2F17%2F145010ffg4nybgy44nhwnq.png", "我是加载出来的", "李青龙的传人");
                    list.add(news2);

                    mRecyclerView.setLoadMoreComplete();
                    break;
                case  5:
                    Toast.makeText(MainActivity.this,"新闻接口访问不到数据，请检查接口！！！已经替换成其他图片",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("add header");
        menu.add("add footer");
        menu.add("change");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}
