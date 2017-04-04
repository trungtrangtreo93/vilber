package trunggiaothuy.vilber.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;

import trunggiaothuy.vilber.R;
import trunggiaothuy.vilber.model.PathStorage;

/**
 * Created by HUNGVIET on 03/04/2017.
 */

public class ImageAdapter extends BaseAdapter {
    private static final String TAG = "ImageAdapter";
    private Context context;
    private ArrayList<PathStorage> list;
    private ImageLoader imageLoader;
    private static LayoutInflater inflater = null;

    public ImageAdapter(Context context, ArrayList<PathStorage> list) {
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
        ImageView imageCheck = (ImageView) vi.findViewById(R.id.imageCheck);
        if (list.get(position).isClick()) {
            imageCheck.setVisibility(View.VISIBLE);
            image.setAlpha(0.5f);
        } else {
            imageCheck.setVisibility(View.GONE);
            image.setAlpha(1f);
        }
        String decodedImgUri = Uri.fromFile(new File(list.get(position).getUri())).toString();

        imageLoader.displayImage(decodedImgUri, image);
        return vi;
    }
}
