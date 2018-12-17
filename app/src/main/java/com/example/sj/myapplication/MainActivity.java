package com.example.sj.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(stringFromJNI());

        initOpenCV();

        final ImageView img = findViewById(R.id.image);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background);
                Mat src = new Mat();
                Mat dst = new Mat();

                Utils.bitmapToMat(bitmap, src);
                Imgproc.cvtColor(src, dst, Imgproc.COLOR_BGRA2GRAY);
                Utils.matToBitmap(dst, bitmap);

                img.setImageBitmap(bitmap);
                src.release();
                dst.release();
            }
        });

    }

    private void initOpenCV() {
        boolean sucess = OpenCVLoader.initDebug();
        if (sucess) {
            Log.d("sjj", "init OpenCV sucess");
        } else {
            Log.d("sjj", "init OpenCV failed");

        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();


    public void getMyName() {
        Log.d("sjj", "zhe shi wo de name");
    }


    public native int intFromJNI();

    public native boolean ijj();
}
