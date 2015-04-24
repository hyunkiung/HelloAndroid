package com.example.administrator.helloandroid.pkg_multimedia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.File;

public class Camera_Intent extends AppCompatActivity implements View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 카메라 인텐트 : ";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mIV_capture;
    private Button mBTN_OnCamera;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_intent);

        mIV_capture = (ImageView) findViewById(R.id.iv_capture);
        mBTN_OnCamera = (Button) findViewById(R.id.btn_OnCamera);
        mBTN_OnCamera.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        // 카메라 호출
        Intent mIntent = new Intent();
        mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        // 사진 폴더 설정
        File filePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        file = new File(filePath, "demo.jpg");
        mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        // onActivityResult 한테 주고
        startActivityForResult(mIntent, REQUEST_IMAGE_CAPTURE);
        show_Log("1.." + String.valueOf(mIntent));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        show_Log("2.." + String.valueOf(data));
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 3;

            Bitmap bitPhoto = BitmapFactory.decodeFile(file.getAbsolutePath(), option);
            mIV_capture.setImageBitmap(bitPhoto);

        }
    }
}
