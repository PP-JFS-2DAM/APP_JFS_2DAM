package com.svalero.toplaptop.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.util.ImageUtils;

import java.util.ArrayList;

public class UserAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<User> userArrayList;
    private LayoutInflater inflater;

    public UserAdapter(Activity context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = (User) getItem(position);

        convertView = inflater.inflate(R.layout.user_and_computer_adapter, null);
        ImageView userImage = (ImageView) convertView.findViewById(R.id.user_computer_item_imageView);
        TextView userNameAndSurnameTv = convertView.findViewById(R.id.user_computer_tv1);
        TextView userDniTv = convertView.findViewById(R.id.user_computer_tv2);

        if (user.getUserImage() != null) {  // Valido si no es null la foto, si no sale fallo nullpoint...
            userImage.setImageBitmap(ImageUtils.getBitmap(user.getUserImage()));
        } else {
            userImage.setImageResource(R.drawable.user);
        }
        userNameAndSurnameTv.setText(user.getName() + " " + user.getSurname());
        userDniTv.setText(user.getDni());

        return convertView;
    }

    @Override
    public int getCount() {
        return userArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
