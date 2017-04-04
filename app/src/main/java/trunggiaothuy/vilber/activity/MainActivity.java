package trunggiaothuy.vilber.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import trunggiaothuy.vilber.R;
import trunggiaothuy.vilber.adapter.UserAdapter;
import trunggiaothuy.vilber.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ListView lsData;
    private UserAdapter adapter;
    private ArrayList<User> list = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        init();
        initArr();

        lsData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), DetailChatActivity.class));
            }
        });
    }

    private void init() {
        lsData = (ListView) findViewById(R.id.listView);
        adapter = new UserAdapter(this, list);
        lsData.setAdapter(adapter);
    }

    private void initArr() {
        list.add(new User("Nguyen Van A", R.drawable.ic_male, "10:30", "Hello"));
        list.add(new User("Hoang Thi N", R.drawable.ic_female, "10:20", "Alo ha"));
        adapter.notifyDataSetChanged();
    }

    public boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
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
}
