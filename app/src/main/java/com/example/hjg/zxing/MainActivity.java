package com.example.hjg.zxing;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.journeyapps.barcodescanner.CaptureActivity;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private final static int SCANNIN_GREQUEST_CODE = 1;
    private ImageView mImageView;
    private Button btn_Create;
    private TextView mTextView;
    private Button btn_Scan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) findViewById(R.id.img);
        mTextView = (TextView) findViewById(R.id.result);
        btn_Create = (Button) findViewById(R.id.btn_create);
        btn_Scan = (Button) findViewById(R.id.btn_scan);
        btn_Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = "你好吗？";
                try {
                    str = new String(str.getBytes("UTF-8"),"ISO8859-1");
                    Bitmap bmp = encodeAsBitmap(str);
                    mImageView.setImageBitmap(bmp);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

        /*扫描二维码*/
        btn_Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                startActivityForResult(intent,1001);
            }
        });
    }
    Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) {
            return null;
        }
        return bitmap;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1001){
            Bundle bundle = data.getExtras();
            mTextView.setText(bundle.getString("result"));
            mImageView.setImageBitmap((Bitmap) data.getParcelableExtra("bitmap"));
        }
    }
}
