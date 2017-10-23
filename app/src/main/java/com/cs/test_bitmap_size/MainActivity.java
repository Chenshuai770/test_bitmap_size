package com.cs.test_bitmap_size;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mBtnMian1;
    private ImageView mIvMain1;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:

                    String obj = (String) msg.obj;
                    tvMain1.setText(obj);
                    Log.d("MainActivity", obj);
                    break;
            }
        }
    };
    private TextView tvMain1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {
        mBtnMian1 = (Button) findViewById(R.id.btn_mian1);
        mIvMain1 = (ImageView) findViewById(R.id.iv_main1);

        mBtnMian1.setOnClickListener(this);
        tvMain1 = (TextView) findViewById(R.id.tv_main1);
        tvMain1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_mian1:

                final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.abc);
                Bitmap scaledBitmap = getScaledBitmap(bitmap,500,500);
                String base64String = getBase64String(scaledBitmap);
                tvMain1.setText(base64String);
                Log.d("MainActivity", base64String);

               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap scaledBitmap = getScaledBitmap(bitmap,250,250);
                        String base64String = getBase64String(scaledBitmap);
                        Message message = new Message();
                        message.what = 1;
                        message.obj = base64String;
                        handler.sendMessage(message);

                    }
                }).start();
*/
                break;
        }
    }



    private Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight)
    {
        int bWidth = b.getWidth();
        int bHeight = b.getHeight();

        int nWidth = bWidth;
        int nHeight = bHeight;

        if(nWidth > reqWidth)
        {
            int ratio = bWidth / reqWidth;
            if(ratio > 0)
            {
                nWidth = reqWidth;
                nHeight = bHeight / ratio;
            }
        }

        if(nHeight > reqHeight)
        {
            int ratio = bHeight / reqHeight;
            if(ratio > 0)
            {
                nHeight = reqHeight;
                nWidth = bWidth / ratio;
            }
        }

        return Bitmap.createScaledBitmap(b, nWidth, nHeight, true);
    }


    private String getBase64String(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

        byte[] imageBytes = baos.toByteArray();

        String base64String = Base64.encodeToString(imageBytes, Base64.NO_WRAP);

        return base64String;
    }

}
