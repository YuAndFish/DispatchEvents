package com.jiedai.dispatchevents;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jiedai.dispatchevents.views.FlexTextView;

public class DemoActivity_3 extends Activity implements OnClickListener {
    private FlexTextView mFlex = null;
    //    private ImageView    imageView = null;
    private LinearLayout layout = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_3);
        layout = (LinearLayout) this.findViewById(R.id.root);
//        imageView = (ImageView) this.findViewById(R.id.image);
//        imageView.setImageResource(R.drawable.pic7);
        layout.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
//                    moveHitsImage((int) event.getX(), (int) event.getY());
                }
                return false;
            }
        });
        mFlex = (FlexTextView) findViewById(R.id.demo3_text);
        mFlex.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mFlex) {
            Toast.makeText(getApplicationContext(), "DemoActivity_3", Toast.LENGTH_SHORT).show();
        }
    }

//    private void moveHitsImage(int x, int y)
//    {
//        ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(ViewGroup.MarginLayoutParams.WRAP_CONTENT,
//                ViewGroup.MarginLayoutParams.WRAP_CONTENT);
//        marginParams.setMargins(x, y, layout.getWidth() - (x + imageView.getWidth()), layout.getHeight() - (y + imageView.getHeight()));
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(marginParams);
//        imageView.setLayoutParams(params);
//    }

}
