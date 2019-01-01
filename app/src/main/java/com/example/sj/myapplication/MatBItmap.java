package com.example.sj.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import org.opencv.android.OpenCVLoader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MatBItmap extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.

    private static final int REQUEST_CAPTURE_IMAGE = 0X110;
    private static final int REQUEST_GET_IMAGE = 0X111;
    private final String PATH = "/sdcard/DCIM/Camera";
    private String FILE_PATH;

    private ImageView mImageView;
    private Uri mFileUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mat_bitmap);
        mImageView = findViewById(R.id.img);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");


        setTitle(title);


    }

    /**
     * 拍照
     *
     * @param view
     */
    public void takePhoto(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri fileUri = mFileUrl;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, REQUEST_CAPTURE_IMAGE);

    }

    /**
     * 从相册选择
     *
     * @param view
     */
    public void select(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "图像选择。。。"), REQUEST_GET_IMAGE);

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

    private void initOpenCV() {
        boolean sucess = OpenCVLoader.initDebug();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case REQUEST_CAPTURE_IMAGE:

                this.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, mFileUrl));

                break;
            case REQUEST_GET_IMAGE:
                String path =  "";

                Uri fileUri = data.getData();
                String docId = DocumentsContract.getDocumentId(fileUri);
                if ("com.android.providers.media.documents".equals(fileUri.getAuthority())){
                    String id = docId.split(":")[1];//解析出数字格式的id
                    String selection = MediaStore.Images.Media._ID + "=" + id;
                    path = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                }

                Log.d("sjj", "path = " + fileUri.getPath());
                if (fileUri == null) return;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(path, options);
                int w = options.outWidth;
                int h = options.outHeight;
                int inSample = 1;
                if (w > 1000 || h > 1000) {
                    while (Math.max(w / inSample, h / inSample) > 1000) {
                        inSample *= 2;
                    }
                }
                options.inJustDecodeBounds = false;
                options.inSampleSize = inSample;
                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(path, options);
                mImageView.setImageBitmap(bitmap);

                break;
        }
    }


    private String getImagePath(Uri uri,String selection){
        String path = null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


}
