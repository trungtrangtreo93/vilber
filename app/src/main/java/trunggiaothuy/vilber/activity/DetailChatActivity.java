package trunggiaothuy.vilber.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
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
import trunggiaothuy.vilber.model.PathStorage;

public class DetailChatActivity extends BaseActivity {


    private static final String TAG = DetailChatActivity.class.getSimpleName();
    private ImageView imgEmoji;
    private EmojIconActions emojIcon;
    private View rootView;
    private EmojiconEditText emojiconEditText;
    private ImageView imgGalery, imgFile, imgAudio;
    private GridView gridView;
    private ImageAdapter adapter;
    private ArrayList<PathStorage> list = new ArrayList<>();
    private int count = 0;
    private int PICKFILE_REQUEST_CODE = 100;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_chat);
        HideUtil.init(this);
        init();
        emojIcon = new EmojIconActions(this, rootView, emojiconEditText, imgEmoji);
        emojIcon.ShowEmojIcon();
        emojIcon.setIconsIds(R.drawable.ic_action_keyboard, R.drawable.ic_insert_emoticon);
        emojIcon.setKeyboardListener(new EmojIconActions.KeyboardListener() {
            @Override
            public void onKeyboardOpen() {
                Log.e(TAG, "Keyboard opened!");
                imgEmoji.setImageResource(R.drawable.ic_emoji_select);
                imgGalery.setImageResource(R.drawable.ic_image);
                imgFile.setImageResource(R.drawable.ic_attach_file);
                imgAudio.setImageResource(R.drawable.ic_settings_voice);
                slideToBottom(gridView);
            }

            @Override
            public void onKeyboardClose() {
                Log.e(TAG, "Keyboard closed");
            }
        });

        imgGalery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeKeyboard();
                imgEmoji.setImageResource(R.drawable.ic_insert_emoticon);
                imgGalery.setImageResource(R.drawable.ic_image_select);
                imgFile.setImageResource(R.drawable.ic_attach_file);
                imgAudio.setImageResource(R.drawable.ic_settings_voice);
                slideToTop(gridView);
                adapter = new ImageAdapter(DetailChatActivity.this, list);
                gridView.setAdapter(adapter);

            }
        });
        imgFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgEmoji.setImageResource(R.drawable.ic_insert_emoticon);
                imgGalery.setImageResource(R.drawable.ic_image);
                imgFile.setImageResource(R.drawable.ic_attach_file_select);
                imgAudio.setImageResource(R.drawable.ic_settings_voice);
                //
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (count >= 10) {
                    if (list.get(i).isClick()) {
                        list.get(i).setClick(false);
                        count--;
                    } else
                        Toast.makeText(DetailChatActivity.this, "Bạn chỉ dc upload 10 ảnh", Toast.LENGTH_SHORT).show();

                } else {
                    if (list.get(i).isClick()) {
                        list.get(i).setClick(false);
                        count--;
                    } else {
                        list.get(i).setClick(true);
                        count++;
                    }
                }
                adapter.notifyDataSetChanged();


            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void init() {
        imgEmoji = (ImageView) findViewById(R.id.imgEmoji);
        imgGalery = (ImageView) findViewById(R.id.imgGalery);
        gridView = (GridView) findViewById(R.id.gridView);
        imgAudio = (ImageView) findViewById(R.id.imgAudio);
        imgFile = (ImageView) findViewById(R.id.imgFile);
        rootView = findViewById(R.id.llEdt);
        emojiconEditText = (EmojiconEditText) findViewById(R.id.emojicon_edit_text);
        rootView = findViewById(R.id.root_view);
        //init data from sd card
        getFilePaths();
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
        }
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        if (resultCode == PICKFILE_REQUEST_CODE) {
            Toast.makeText(this, "call", Toast.LENGTH_SHORT).show();
            if (resultCode == Activity.RESULT_OK) {
                Log.e(TAG, data.getDataString().toString());
            }
        }
    }

    public ArrayList<PathStorage> getFilePaths() {
        Uri u = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Images.ImageColumns.DATA};
        Cursor c = null;
        SortedSet<String> dirList = new TreeSet<String>();
        list = new ArrayList<PathStorage>();
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
                        list.add(new PathStorage(path, false));
                    }
                }
                //  }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }
}
