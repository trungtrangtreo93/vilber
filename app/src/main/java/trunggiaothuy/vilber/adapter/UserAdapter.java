package trunggiaothuy.vilber.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.io.File;
import java.util.ArrayList;

import trunggiaothuy.vilber.R;
import trunggiaothuy.vilber.model.PathStorage;
import trunggiaothuy.vilber.model.User;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by HUNGVIET on 04/04/2017.
 */

public class UserAdapter extends BaseAdapter {
    private static final String TAG = "ImageAdapter";
    private Context context;
    private ArrayList<User> list;
    private ImageLoader imageLoader;
    private static LayoutInflater inflater = null;

    public UserAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
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
            vi = inflater.inflate(R.layout.item_user, parent, false);
        TextView txtName = (TextView) vi.findViewById(R.id.txtName);
        TextView txtChat = (TextView) vi.findViewById(R.id.txtChat);
        TextView txtTime = (TextView) vi.findViewById(R.id.txtTime);
        ImageView imgUser = (ImageView) vi.findViewById(R.id.imgUser);

        txtName.setText(list.get(position).getName());
        txtChat.setText(list.get(position).getMessage());

        txtTime.setText(list.get(position).getTime());

        imgUser.setImageResource(list.get(position).getImage());

        return vi;
    }
}
