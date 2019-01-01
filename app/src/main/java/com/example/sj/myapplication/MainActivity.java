package com.example.sj.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
//        System.loadLibrary("opencv_java3");
    }

    private ListView mListView;
    private SelectAdapter mAdapter;
    private List<String> mData = new ArrayList<>();
    private String[] mTitles = new String[]{
            "OpenCV Android 开发框架",
            "Mat与Bitmap对象",
            "Mat像素操作",
            "图像操作",
            "基本特征检测",
            "特征监测与匹配",
            "相机使用",
            "OCR识别",
            "人脸美颜",
            "人眼实时跟踪与渲染"
    };
    private static final int REQUEST_CAPTURE_IMAGE = 0X110;
    private final String PATH = "/sdcard/DCIM/Camera";
    private String FILE_PATH;
    private Uri mFileUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
//        TextView tv = (TextView) findViewById(R.id.sample_text);
//        tv.setText(stringFromJNI());

        initOpenCV();

//        final ImageView img = findViewById(R.id.image);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
//                Mat src = new Mat();
//                Mat dst = new Mat();
//
//                Utils.bitmapToMat(bitmap, src);
//                Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGRA2GRAY);
//                Utils.matToBitmap(dst, bitmap);
//
//                img.setImageBitmap(bitmap);
//                src.release();
//                dst.release();
//            }
//        });

        initView();
        initData();


    }

    private void initView() {
        mListView = findViewById(R.id.listview);
    }

    private void initData() {
        for (int i = 1; i <= mTitles.length; i++) {
            mData.add("第" + i + "章   " + mTitles[i - 1]);
        }
        mAdapter = new SelectAdapter(this, mData);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, MatBItmap.class);
                intent.putExtra("title", mData.get(i));
                startActivity(intent);

            }
        });
        mFileUrl = Uri.fromFile(getSaveFilePath());

    }

    /**
     * 拍照
     *
     * @param view
     */
    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = mFileUrl;
        Log.d("sjj", "path = " + fileUri.getPath());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);

    }

    private File getSaveFilePath() {
//        String f = System.currentTimeMillis() + ".jpg";
//        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
//        File file = new File(path);
//        if (!file.exists()) {
//            file.mkdir();
//        }
//        return new File(path + f);
        String f = System.currentTimeMillis() + ".jpg";
        String path = PATH + File.separator;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        FILE_PATH = path + f;
        return new File(FILE_PATH);
    }

    /**
     * 从相册选择
     *
     * @param view
     */
    public void select(View view) {
    }

    private void initOpenCV() {
        boolean sucess = OpenCVLoader.initDebug();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURE_IMAGE:

                this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mFileUrl));

                break;
        }
    }


    /**
     * 将图片保存到图库
     *
     * @param bitmap
     */
    private void saveImageToGallery(Bitmap bitmap) {

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();




    public native int intFromJNI();

    public native boolean ijj();


}
