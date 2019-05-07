package com.jiedai.dispatchevents;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jiedai.dispatchevents.utils.ScreenSizeUtils;
import com.jiedai.dispatchevents.views.HorScrollView;

import java.util.ArrayList;

/**
 * Create by yuheng
 * date：2019/4/29
 * description：
 */
public class TestDemo1Activity extends AppCompatActivity implements View.OnClickListener, HorScrollView.OnGetChildListener {
    private Button btn_today = null;
    private HorScrollView hor_scroll_view = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo1);
        btn_today = findViewById(R.id.btn_today);
        hor_scroll_view = findViewById(R.id.hor_scroll_view);
        hor_scroll_view.setOnGetChildListener(this);

        int screenWidth = ScreenSizeUtils.getScreenWidth(this);
        for (int i = 0; i < 5; i++) {
            ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.content_layout, hor_scroll_view, false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = layout.findViewById(R.id.title);
            btn_today.setOnClickListener(this);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            hor_scroll_view.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        ListView listView = layout.findViewById(R.id.list);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            list.add("name " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.content_list_item, R.id.name, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestDemo1Activity.this, "click item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_today:
                hor_scroll_view.showToday();
                break;
        }
    }

    @Override
    public void getChildIndex(int index) {
        // 当滑动不是本月时进行的接口回调，显示"今"这个按钮
        if (index == 0) {
            btn_today.setVisibility(View.INVISIBLE);
        } else {
            btn_today.setVisibility(View.VISIBLE);
        }
    }
}
