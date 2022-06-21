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
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.util.ImageUtils;

import java.util.List;

public class ComputerAdapter extends BaseAdapter {

    private Context context;
    private List<Computer> computerArrayList;
    private LayoutInflater inflater;

    public ComputerAdapter(Activity context, List<Computer> computerArrayList) {
        this.context = context;
        this.computerArrayList = computerArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Computer computer = (Computer) getItem(position);

        convertView = inflater.inflate(R.layout.user_and_computer_adapter, null);
        ImageView computerImage = (ImageView) convertView.findViewById(R.id.user_computer_item_imageView);
        TextView computermodel = convertView.findViewById(R.id.user_computer_tv1);
        TextView computerPlate = convertView.findViewById(R.id.user_computer_tv2);

        if (computer.getComputerImage() != null) {  // Valido si no es null la foto, si no sale fallo nullpoint...
            computerImage.setImageBitmap(ImageUtils.getBitmap(computer.getComputerImage()));
        }
        computermodel.setText(computer.getBrand() + " " + computer.getModel());
        computerPlate.setText(computer.getLicensePlate());

        return convertView;
    }

    @Override
    public int getCount() {
        return computerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return computerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
