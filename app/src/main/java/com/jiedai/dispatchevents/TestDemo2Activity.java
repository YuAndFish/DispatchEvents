package com.jiedai.dispatchevents;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jiedai.dispatchevents.utils.ScreenSizeUtils;
import com.jiedai.dispatchevents.views.DispatchListView;
import com.jiedai.dispatchevents.views.HorScrollView2;

import java.util.ArrayList;

/**
 * Create by yuheng
 * date：2019/4/30
 * description：
 */
public class TestDemo2Activity extends AppCompatActivity {
    private HorScrollView2 hor_scroll_view2 = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo2);
        hor_scroll_view2 = findViewById(R.id.hor_scroll_view2);
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) getLayoutInflater().inflate(R.layout.test_content_layout2, hor_scroll_view2, false);
            layout.getLayoutParams().width = ScreenSizeUtils.getScreenWidth(this);
//            LayoutEx layoutEx = (LayoutEx) layout.findViewById(R.id.LayoutEx);
//            layoutEx.setHorizontalScrollViewEx2(mListContainer);
            TextView textView = layout.findViewById(R.id.title);
            textView.setText("page " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255 / (i + 1), 255 / (i + 1), 0));
            createList(layout);
            hor_scroll_view2.addView(layout);
        }
    }

    private void createList(ViewGroup layout) {
        DispatchListView listView = layout.findViewById(R.id.list);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            list.add("name " + i);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.content_list_item, R.id.name, list);
        listView.setAdapter(adapter);
        listView.setHorScrollView2(hor_scroll_view2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TestDemo2Activity.this, "click item", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        LogUtils.d("dispatchTouchEvent action:" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.d("onTouchEvent action:" + event.getAction());
        return super.onTouchEvent(event);
    }

}
