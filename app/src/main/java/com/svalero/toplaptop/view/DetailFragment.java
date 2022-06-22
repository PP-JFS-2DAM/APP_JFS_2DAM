package com.svalero.toplaptop.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.svalero.toplaptop.R;
import com.svalero.toplaptop.util.DateUtils;
import com.svalero.toplaptop.util.ImageUtils;

import java.time.LocalDate;
import java.util.Base64;

public class DetailFragment extends Fragment {

    private closeDetails closeDetails;
    private String activity;
    private final String VIEW_COMPUTER_ACTIVITY = "ComputerListView";
    private final String VIEW_USER_ACTIVITY = "UserListView";
    private final String VIEW_ORDER_ACTIVITY = "OrderListView";

    public FloatingActionButton closeButton;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View detailView = inflater.inflate(R.layout.fragment_user_detail, container, false);

        Activity thisActivity = getActivity();
        Log.i("StringACT", thisActivity.toString());
        if (thisActivity != null) {
            if (thisActivity.toString().contains(VIEW_COMPUTER_ACTIVITY)) {
                activity = VIEW_COMPUTER_ACTIVITY;
            } else if (thisActivity.toString().contains(VIEW_USER_ACTIVITY)) {
                activity = VIEW_USER_ACTIVITY;
            } else if (thisActivity.toString().contains(VIEW_ORDER_ACTIVITY)) {
                activity = VIEW_ORDER_ACTIVITY;
            }
        }

        closeButton = detailView.findViewById(R.id.close_detail_button);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDetails.hiddeDetails();
            }
        });

        return detailView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView imageView = view.findViewById(R.id.fragment_detail_imageview);
        TextView textView1 = view.findViewById(R.id.fragment_detail_textview1);
        TextView textView2 = view.findViewById(R.id.fragment_detail_textview2);
        TextView textView3 = view.findViewById(R.id.fragment_detail_textview3);
        TextView textView4 = view.findViewById(R.id.fragment_detail_textview4);

        switch (activity) {
            case VIEW_COMPUTER_ACTIVITY:    // TODO fragment de detalles de motos para otro dia
                break;
            case VIEW_USER_ACTIVITY:
                if (getArguments() != null) {
                    if (!getArguments().getString("user_image").equalsIgnoreCase(""))
                        imageView.setImageBitmap(ImageUtils.getBitmap(Base64.getDecoder().decode(getArguments().getString("user_image"))));
                    textView1.setText(getArguments().getString("name") + " " + getArguments().getString("surname"));
                    textView2.setText(getArguments().getString("dni"));
                    if (getArguments().getBoolean("vip")) {
                        textView3.setText(R.string.vip);
                    } else {
                        textView3.setText(R.string.no_vip);
                    }
                }
                break;
            case VIEW_ORDER_ACTIVITY:
                if (getArguments() != null) {
                    if (getArguments().getByteArray("computer_image") != null)
                        imageView.setImageBitmap
                                (ImageUtils.getBitmap(getArguments().getByteArray("computer_image")));
                    textView1.setText(DateUtils.fromLocalDateToMyDateFormatString
                            (LocalDate.parse(getArguments().getString("date"))));
                    textView2.setText(getArguments().getString("name"));
                    textView3.setText(getArguments().getString("model") + " || " + getArguments().getString("license"));
                    textView4.setText(getArguments().getString("description"));
                }
                break;
        }   // End switch
    }

    /**
     * Interfaz para comunicar el boton de cerrar con la clase desde la que se llama al fragment
     * sobreescribiendolo en esta
     */
    public interface closeDetails {
        void hiddeDetails();
    }

    /**
     * Se llama cuando un fragmento se adjunta por primera vez a su contexto (La clase que lo llama).
     * {@link #onCreate(Bundle)} will be called after this.
     *
     * @param context
     */
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof closeDetails) {
            closeDetails = (DetailFragment.closeDetails) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }
}