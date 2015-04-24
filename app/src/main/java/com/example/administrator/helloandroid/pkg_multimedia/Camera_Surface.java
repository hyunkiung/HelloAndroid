package com.example.administrator.helloandroid.pkg_multimedia;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.administrator.helloandroid.R;

import java.io.IOException;

public class Camera_Surface extends AppCompatActivity implements SurfaceHolder.Callback, View.OnClickListener {

    // 흐름 확인용 로그 출력 메소드
    private static String TAG = "로그 / 카메라 Surface";
    private void show_Log(String msg) { Log.d(TAG, msg); }
    // 메세지 토스트 메소드 (공용)
    void show_Toast(CharSequence toast_msg) { Toast.makeText(this, toast_msg, Toast.LENGTH_SHORT).show(); }

    private ActionBar action_Bar;   // 액션바
    private SurfaceView mSFV_camera;
    private Button mBTN_shutter;
    private Button mBTN_flash;

    private Camera mCamera;
    private Camera.Parameters mCameraParameter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_surface);

        action_Bar = this.getSupportActionBar();
        action_Bar.hide();

        // 실행 순서
        //getHolder => eglSurfaceAttrib not implemented => surfaceCreated => surfaceChanged
        //onClick Start => onPictureTaken ==>
        //savedImageUri (content://media/external/images/media/120)
        //uri (content://media/external/images/media/120)

        mSFV_camera = (SurfaceView) findViewById(R.id.sfv_camera);
        mBTN_shutter = (Button) findViewById(R.id.btn_shutter);

        show_Log("onCreate =====");
        mSFV_camera.getHolder().addCallback(this);
        mSFV_camera.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mBTN_shutter.setOnClickListener(this);

//        Camera mCamera = null;
//        mCamera = Camera.open();
//        Camera.Parameters mCameraParameter;
//        mCameraParameter = mCamera.getParameters();
//        mCameraParameter.setFlashMode("torch");
//        mCamera.setParameters(mCameraParameter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) // 세로 전환시
        {
            // 배경 화면 교체 처리
            show_Log("화면 전환11111");
        }
        else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)// 가로 전환시
        {
            // 배경 화면 교체 처리
            show_Log("화면 전환22222");
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        show_Log("surfaceCreated");
        mCamera = Camera.open();

        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        show_Log("surfaceChanged");
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        show_Log("surfaceDestroyed");
        // 미리보기 중지
        mCamera.stopPreview();
        // 카메라 메모리 해제
        mCamera.release();
        mCamera = null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_shutter :
                mCamera.takePicture(null, null, new Camera.PictureCallback() {
                    @Override
                    public void onPictureTaken(byte[] data, Camera camera) {
                        // 1차원 배열 data를 2차원 data로 변환해서 Bitmap 으로 리턴
                        // 사진데이타를 비트맵 객체로 저장
                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length, null);

                        // 사진폴더에 저장. 파일 이름은 자동으로 부여 됨
                        String savedImageUri = MediaStore.Images.Media.insertImage(getContentResolver(), bmp, "", null);

                        // 저장된 파일의 uri 를 취함
                        Uri uri = Uri.parse(savedImageUri);
                        show_Log("저장위치=" + uri);

                        // Media Scan
                        // 사진 앱 들의 db를 갱신하기 위해서 (사진 앱을 실행했을 때 이 파일이 바로 검색 되도록)
                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

                        //Toast.makeText(Camera_Surface.this, "사진이 저장되었습니다", Toast.LENGTH_SHORT).show();

                        // 다시 프리뷰를 작동
                        camera.startPreview();
                    }
                });
                break;
            case R.id.btn_flash :
                CameraFlash_OnOff();
                break;
        }
    }

    // 카메라 플래쉬 온오프 메소드
    private void CameraFlash_OnOff() {
        mCameraParameter = mCamera.getParameters();
        String flashMode = mCameraParameter.getFlashMode();
        show_Log(flashMode);
        //mCameraParameter.setFlashMode("torch");
        //mCamera.setParameters(mCameraParameter);
    }
}


// <uses-permission android:name="android.permission.CAMERA" /> 매니페스트 추가 해야 됨
// 1. 카메라 프리뷰 보기 위해 SurfaceView 를 써야만 됨. 고속 렌더링이 가능 View
// 2. SurfaceView 의 holder 에 Callback 을 연결. SurfaceHolder.Callback 구현
// 3. Camera 객체를 각가의 콜백에서 사용

// 롤리팝에 추가된 Camera2 사용법
// https://github.com/googlesamples/android-Camera2Basic