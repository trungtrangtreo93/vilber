package trunggiaothuy.vilber.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.yinglan.keyboard.HideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;
import trunggiaothuy.vilber.R;
import trunggiaothuy.vilber.adapter.ImageAdapter;

public class DetailChatActivity extends AppCompatActivity {


    private static final String TAG = DetailChatActivity.class.getSimpleName();
    private ImageView imgEmoji;
    private EmojIconActions emojIcon;
    private View rootView;
    private EmojiconEditText emojiconEditText;
    private ImageView imgGalery;
    private GridView gridView;
    private ImageAdapter adapter;
    //
    private String[] FilePathStrings;
    private String[] FileNameStrings;
    private File[] listFile;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chat);
        HideUtil.init(this);
        init();
        isStoragePermissionGranted();
        rootView = findViewById(R.id.root_view);
        emojIcon = new EmojIconActions(this, rootView, emojiconEditText, imgEmoji);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.ic_insert_emoticon);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");
            }

            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed");
            }
        });
        imgGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gridView.setVisibility(View.VISIBLE);
                adapter = new ImageAdapter(DetailChatActivity.this, getFilePaths());
                // Set the LazyAdapter to the GridView
                gridView.setAdapter(adapter);

            }
        });
    }

    private void init() {
        imgEmoji = (ImageView) findViewById(R.id.imgEmoji);
        imgGalery = (ImageView) findViewById(R.id.imgGalery);
        gridView = (GridView) findViewById(R.id.gridView);
        rootView = findViewById(R.id.llEdt);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);

        //init data from sd card
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
            //resume tasks needing this permission
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        InputMethodManager imm = (InputMethodManager)
                this.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } else {
            Toast.makeText(this, "aaa", Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    public ArrayList<String> getFilePaths() {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        ArrayList<String> resultIAV = new ArrayList<String>();
        String[] directories = null;
        if (u != null) {
            c = managedQuery(u, projection, null, null, null);
        }
        if ((c != null) && (c.moveToFirst())) {
            do {
                String tempDir = c.getString(0);
                tempDir = tempDir.substring(0, tempDir.lastIndexOf("/"));
                try {
                    dirList.add(tempDir);
                } catch (Exception e) {
                }
            }
            while (c.moveToNext());
            directories = new String[dirList.size()];
            dirList.toArray(directories);
        }
        for (int i = 0; i < dirList.size(); i++) {
            File imageDir = new File(directories[i]);
            File[] imageList = imageDir.listFiles();
            if (imageList == null)
                continue;
            for (File imagePath : imageList) {
                try {

                    if (imagePath.isDirectory()) {
                        imageList = imagePath.listFiles();

                    }
                    if (imagePath.getName().contains(".jpg") || imagePath.getName().contains(".JPG")
                            || imagePath.getName().contains(".jpeg") || imagePath.getName().contains(".JPEG")
                            || imagePath.getName().contains(".png") || imagePath.getName().contains(".PNG")
                            || imagePath.getName().contains(".gif") || imagePath.getName().contains(".GIF")
                            || imagePath.getName().contains(".bmp") || imagePath.getName().contains(".BMP")
                            ) {
                        String path = imagePath.getAbsolutePath();
                        resultIAV.add(path);
                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return resultIAV;
    }
}
