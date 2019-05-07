package com.jiedai.dispatchevents;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiedai.dispatchevents.utils.MyUtils;
import com.jiedai.dispatchevents.views.HorizontalScrollViewEx;

import java.util.ArrayList;

public class DemoActivity_1 extends Activity implements OnClickListener, HorizontalScrollViewEx.OnGetChildIndex {
    private static final String TAG = "DemoActivity_1";

    private HorizontalScrollViewEx mListContainer;
    private Button mTodayBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_1);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = (HorizontalScrollViewEx) findViewById(R.id.container);
        mTodayBtn = (Button) findViewById(R.id.today);
        mTodayBtn.setVisibility(View.INVISIBLE);
        mListContainer.setOnGetChildIndex(this);
        final int screenWidth = MyUtils.getScreenMetrics(this).widthPixels;
        final int screenHeight = MyUtils.getScreenMetrics(this).heightPixels;
        for (int i = 0; i < 5; i++) {
//        	TestLayout layout = new TestLayout(getApplicationContext()); 
//        	LayoutParams params = new LayoutParams(screenWidth, LayoutParams.WRAP_CONTENT);
//        	layout.setLayoutParams(params);
//        	if (i == 0) {
//				layout.setBgColor(Color.parseColor("#C9E2D8"));
//			} else if (i == 1) {
//				layout.setBgColor(Color.parseColor("#C9D3E2"));
//			} else if (i == 2) {
//				layout.setBgColor(Color.parseColor("#E2C9D3"));
//			}
//        	mListContainer.addView(layout);
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout, mListContainer, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = (TextView) layout.findViewById(R.id.title);
            mTodayBtn.setOnClickListener(this);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            mListContainer.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = (ListView) layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            datas.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.content_list_item, R.id.name, datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(DemoActivity_1.this, "click item",
                        Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        mListContainer.showTodayMonth();
    }

    @Override
    public void getChildIndex(int index) {
        if (null != this.mTodayBtn) {
            // 当滑动不是本月时进行的接口回调，显示"今"这个按钮
            if (index == 0) {
                this.mTodayBtn.setVisibility(View.INVISIBLE);
            } else {
                this.mTodayBtn.setVisibility(View.VISIBLE);
            }
        }
    }

}
