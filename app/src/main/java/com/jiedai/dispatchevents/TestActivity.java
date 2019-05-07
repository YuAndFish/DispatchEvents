package com.jiedai.dispatchevents;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jiedai.dispatchevents.views.DragTextView;
import com.jiedai.dispatchevents.views.MyButton;

/**
 * Create by yuheng
 * date：2019/4/25
 * description：
 */
public class TestActivity extends AppCompatActivity {
    private MyButton my_btn = null;
    private DragTextView drag_tv = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        my_btn = findViewById(R.id.my_btn);
        my_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "my_btn", Toast.LENGTH_SHORT).show();
            }
        });

        drag_tv = findViewById(R.id.drag_tv);
        drag_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "drag_tv", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
