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
import com.svalero.toplaptop.domain.dto.OrderDTOAdapter;
import com.svalero.toplaptop.util.DateUtils;
import com.svalero.toplaptop.util.ImageUtils;

import java.util.ArrayList;
import java.util.Base64;

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OrderDTOAdapter> orderArrayList;
    private LayoutInflater inflater;

    public OrderAdapter(Activity context, ArrayList<OrderDTOAdapter> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderDTOAdapter orderDTOAdapter = (OrderDTOAdapter) getItem(position);

        convertView = inflater.inflate(R.layout.user_and_computer_adapter, null);
        ImageView orderComputerImage = convertView.findViewById(R.id.user_computer_item_imageView);
        TextView orderDateAndComputerModel = convertView.findViewById(R.id.user_computer_tv1);
        TextView orderRamAndDescription = convertView.findViewById(R.id.user_computer_tv2);

        orderDateAndComputerModel.setTextSize(22);
        orderRamAndDescription.setTextSize(18);

        if (!orderDTOAdapter.getComputerImageOrder().equalsIgnoreCase(""))
            orderComputerImage.setImageBitmap(ImageUtils.getBitmap(Base64.getDecoder().decode(orderDTOAdapter.getComputerImageOrder())));

        orderDateAndComputerModel.setText(orderDTOAdapter.getDate() + " || " + orderDTOAdapter.getComputerBrandModel());
        orderRamAndDescription.setText(orderDTOAdapter.getComputerRam() + " || " + orderDTOAdapter.getDescription());

        return convertView;
    }

    @Override
    public int getCount() {
        return orderArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
