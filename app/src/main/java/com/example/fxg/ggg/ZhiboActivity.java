package com.example.fxg.ggg;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.projection.MediaProjectionManager;
import android.opengl.GLES20;
import android.opengl.GLException;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.faucamp.simplertmp.RtmpHandler;
import com.seu.magicfilter.utils.MagicFilterType;

import net.ossrs.yasea.SrsCameraView;
import net.ossrs.yasea.SrsEncodeHandler;
import net.ossrs.yasea.SrsPublisher;
import net.ossrs.yasea.SrsRecordHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.microedition.khronos.opengles.GL10;

import io.vov.vitamio.utils.Log;

/**
 * Created by Sikang on 2017/5/2.
 */

public class ZhiboActivity extends Activity implements SrsEncodeHandler.SrsEncodeListener, RtmpHandler.RtmpListener, SrsRecordHandler.SrsRecordListener, View.OnClickListener {
    private static final String TAG = "CameraActivity";

    private ImageButton mPublishBtn;
    private ImageButton mCameraSwitchBtn;
    private ImageButton mPhoto;
    private ImageButton mEncoderBtn;
    private SrsPublisher mPublisher;
    private String rtmpUrl;
    private int zhibo_index=0;//0为未直播
    private int bianma_index=0;//0为硬编码
    private ByteBuffer mCaptureBuffer;
    String userName="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_zhibo);

        mPublishBtn = (ImageButton) findViewById(R.id.publish);
        mCameraSwitchBtn = (ImageButton) findViewById(R.id.swCam);
        mPhoto = (ImageButton) findViewById(R.id.mPhoto);
        mEncoderBtn = (ImageButton) findViewById(R.id.swEnc);
        mPublishBtn.setOnClickListener(this);
        mCameraSwitchBtn.setOnClickListener(this);
        mEncoderBtn.setOnClickListener(this);
        mEncoderBtn.setBackgroundResource(R.drawable.set);
        mPhoto.setBackgroundResource(R.drawable.photo);
        mCameraSwitchBtn.setBackgroundResource(R.drawable.change);
        mPublishBtn.setBackgroundResource(R.drawable.start);

        mPublisher = new SrsPublisher((SrsCameraView) findViewById(R.id.glsurfaceview_camera));
        //编码状态回调
        mPublisher.setEncodeHandler(new SrsEncodeHandler(this));
        mPublisher.setRecordHandler(new SrsRecordHandler(this));
        //rtmp推流状态回调
        mPublisher.setRtmpHandler(new RtmpHandler(this));
        //预览分辨率
        mPublisher.setPreviewResolution(1280, 720);
        //推流分辨率
        mPublisher.setOutputResolution(720, 1280);
        //传输率
        mPublisher.setVideoHDMode();
        //开启美颜（其他滤镜效果在MagicFilterType中查看）
        mPublisher.switchCameraFilter(MagicFilterType.BEAUTY);
        //打开摄像头，开始预览（未推流）
        mPublisher.startCamera();

        if (Build.VERSION.SDK_INT >= 21) {
            startActivityForResult(
                    ((MediaProjectionManager) getSystemService("media_projection")).createScreenCaptureIntent(),1);
        } else {
            Log.e("TAG", "版本过低,无法截屏");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //开始/停止推流
            case R.id.publish:
                if (zhibo_index==0) {
                    //String userName="";
                    userName=MD5Utils.read_id();
                    if(userName==""){
                        Toast.makeText(getApplicationContext(), "请登录：", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else {
                        rtmpUrl = "rtmp://39.108.181.154/live/123";
                        if (TextUtils.isEmpty(rtmpUrl)) {
                            Toast.makeText(getApplicationContext(), "地址不能为空！", Toast.LENGTH_SHORT).show();
                        }
                        mPublisher.startPublish(rtmpUrl);
                        mPublisher.startCamera();

                        if (bianma_index==0) {
                            Toast.makeText(getApplicationContext(), "当前使用硬编码", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "当前使用软编码", Toast.LENGTH_SHORT).show();
                        }
                        mPublishBtn.setBackgroundResource(R.drawable.stop);
                        zhibo_index=1;
                        mEncoderBtn.setEnabled(false);
                    }

                } else if (zhibo_index==1) {
                    mPublisher.stopPublish();
                    mPublisher.stopRecord();
                    mPublishBtn.setBackgroundResource(R.drawable.start);
                    mEncoderBtn.setEnabled(true);
                    zhibo_index=0;
                }
                break;
            //切换摄像头
            case R.id.swCam:
                mPublisher.switchCameraFace((mPublisher.getCamraId() + 1) % Camera.getNumberOfCameras());
                break;
            //切换编码方式
            case R.id.swEnc:
                if (bianma_index==0) {
                    mPublisher.switchToSoftEncoder();
                    bianma_index=1;
                    Toast.makeText(getApplicationContext(), "切换至软编码！", Toast.LENGTH_SHORT).show();
                } else if (bianma_index==1) {
                    mPublisher.switchToHardEncoder();
                    Toast.makeText(getApplicationContext(), "切换至硬编码！", Toast.LENGTH_SHORT).show();
                    bianma_index=0;
                }
                break;
            case R.id.mPhoto:

                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mPublisher.resumeRecord();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPublisher.pauseRecord();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPublisher.stopPublish();
        mPublisher.stopRecord();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mPublisher.stopEncode();
        mPublisher.stopRecord();
        mPublisher.setScreenOrientation(newConfig.orientation);
        if (zhibo_index==1) {
            mPublisher.startEncode();
        }
        mPublisher.startCamera();
    }

    @Override
    public void onNetworkWeak() {
        Toast.makeText(getApplicationContext(), "网络型号弱", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkResume() {

    }

    @Override
    public void onEncodeIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    private void handleException(Exception e) {
        try {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            mPublisher.stopPublish();
            mPublisher.stopRecord();
            zhibo_index=0;
            mPublishBtn.setBackgroundResource(R.drawable.start);
        } catch (Exception e1) {
            //
        }
    }

    @Override
    public void onRtmpConnecting(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpConnected(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpVideoStreaming() {

    }

    @Override
    public void onRtmpAudioStreaming() {

    }

    @Override
    public void onRtmpStopped() {
        Toast.makeText(getApplicationContext(), "已停止", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpDisconnected() {
        Toast.makeText(getApplicationContext(), "未连接服务器", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRtmpVideoFpsChanged(double fps) {

    }

    @Override
    public void onRtmpVideoBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpAudioBitrateChanged(double bitrate) {

    }

    @Override
    public void onRtmpSocketException(SocketException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIOException(IOException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    @Override
    public void onRtmpIllegalStateException(IllegalStateException e) {
        handleException(e);
    }

    @Override
    public void onRecordPause() {
        Toast.makeText(getApplicationContext(), "Record paused", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordResume() {
        Toast.makeText(getApplicationContext(), "Record resumed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordStarted(String msg) {
        Toast.makeText(getApplicationContext(), "Recording file: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordFinished(String msg) {
        Toast.makeText(getApplicationContext(), "MP4 file saved: " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRecordIOException(IOException e) {
        handleException(e);
    }

    @Override
    public void onRecordIllegalArgumentException(IllegalArgumentException e) {
        handleException(e);
    }

    private class InsetDap implements Runnable{
        String sql = "Insert into zhibo values(?);";
        Connection connection;
        ResultSet rs;
        PreparedStatement pstm;
        int n = 0;
        public int setZhiboName() {
            return n;
        }

        @Override
        public void run() {
            try {
                connection = DB.getConnection();
                pstm = connection.prepareStatement(sql);
                pstm.setString(0, userName);
                n = pstm.executeUpdate();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

//    public void onDrawFrame() {
//
//        Bitmap bitmap = Bitmap.createBitmap(DpUtil.getScreenWidth(mContext),
//                DpUtil.getScreenHeight(mContext), Bitmap.Config.RGB_565);
////创建新的画布
//        Canvas canvas = new Canvas(bitmap);
////设置画布背景色
//        canvas.drawColor(Color.WHITE);
////设置绘制路径、画笔
//        canvas.drawPath(path, paint);
//        // 保存bimap到sdcard
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(GetFilePathUtils.getFilePath(mContext) + "a.png");
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
//            fos.flush();
//            fos.close();
//        } catch (java.io.IOException e) {
//            e.printStackTrace();
//
//    }

//    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        int mOutputWidth = width;
//        int mOutputHeight = height;
//        mCaptureBuffer = ByteBuffer.allocate(mOutputWidth * mOutputHeight * 4);
//        mBitmap = Bitmap.createBitmap(mOutputWidth, height, Bitmap.Config.ARGB_8888);
//        GLES20.glViewport(0, 0, width, height);
//        mFilter.onOutputSizeChanged(width, height);
//    }


//        public void onDrawFrame(GL10 gl) {
//            int mOutputWidth = mPublisher.getPreviewWidth();
//            int mOutputHeight = mPublisher.getPreviewHeight();
//
//            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//            // 获取GLSurfaceView的图片并保存
//            if (isTakePicture) {
//                Bitmap bmp = createBitmapFromGLSurface(0, 0, mOutputWidth, mOutputHeight, gl);
//                isTakePicture = false;
//            }
//        }
//
//            private Bitmap createBitmapFromGLSurface ( int x, int y, int w, int h;GL10 gl){
//                int bitmapBuffer[] = new int[w * h];
//                int bitmapSource[] = new int[w * h];
//                IntBuffer intBuffer = IntBuffer.wrap(bitmapBuffer);
//                intBuffer.position(0);
//                try {
//                    gl.glReadPixels(x, y, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE,
//                            intBuffer);
//                    int offset1, offset2;
//                    for (int i = 0; i < h; i++) {
//                        offset1 = i * w;
//                        offset2 = (h - i - 1) * w;
//                        for (int j = 0; j < w; j++) {
//                            int texturePixel = bitmapBuffer[offset1 + j];
//                            int blue = (texturePixel >> 16) & 0xff;
//                            int red = (texturePixel << 16) & 0x00ff0000;
//                            int pixel = (texturePixel & 0xff00ff00) | red | blue;
//                            bitmapSource[offset2 + j] = pixel;
//                        }
//                    }
//                } catch (GLException e) {
//                    return null;
//                }
//                return Bitmap.createBitmap(bitmapSource, w, h, Bitmap.Config.ARGB_8888);
//            }

}
