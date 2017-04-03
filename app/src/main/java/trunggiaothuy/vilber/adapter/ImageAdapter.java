package trunggiaothuy.vilber.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;

import trunggiaothuy.vilber.R;

/**
 * Created by HUNGVIET on 03/04/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private static final String TAG = "ImageAdapter";
    private Context context;
    private ArrayList<String> list;
    private ImageLoader imageLoader;
    private static LayoutInflater inflater = null;

    public ImageAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));
        inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return list.size();

    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.gridview_item, parent, false);
        ImageView image = (ImageView) vi.findViewById(R.id.image);

        String decodedImgUri = Uri.fromFile(new File(list.get(position))).toString();

        imageLoader.displayImage(decodedImgUri, image);
        Log.e(TAG, list.get(position));
        return vi;
    }
}
