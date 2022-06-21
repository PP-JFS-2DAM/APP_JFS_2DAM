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
import com.svalero.toplaptop.domain.dto.OrderDTO;
import com.svalero.toplaptop.util.DateUtils;
import com.svalero.toplaptop.util.ImageUtils;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<OrderDTO> orderArrayList;
    private LayoutInflater inflater;

    public OrderAdapter(Activity context, ArrayList<OrderDTO> orderArrayList) {
        this.context = context;
        this.orderArrayList = orderArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        OrderDTO orderDTO = (OrderDTO) getItem(position);

        convertView = inflater.inflate(R.layout.user_and_computer_adapter, null);
        ImageView orderComputerImage = convertView.findViewById(R.id.user_computer_item_imageView);
        TextView orderDateAndComputerModel = convertView.findViewById(R.id.user_computer_tv1);
        TextView orderLicensePlateAndDescription = convertView.findViewById(R.id.user_computer_tv2);

        orderDateAndComputerModel.setTextSize(22);
        orderLicensePlateAndDescription.setTextSize(18);

        if (orderDTO.getComputerImageOrder() != null) {  // Valido si no es null la foto, si no sale fallo nullpoint...
            orderComputerImage.setImageBitmap(ImageUtils.getBitmap(orderDTO.getComputerImageOrder()));
        }
        orderDateAndComputerModel.setText(DateUtils.fromLocalDateToMyDateFormatString(orderDTO.getDate()) + " || " + orderDTO.getComputerBrandModel());
        orderLicensePlateAndDescription.setText(orderDTO.getComputerLicensePlate() + " || " + orderDTO.getDescription());

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
