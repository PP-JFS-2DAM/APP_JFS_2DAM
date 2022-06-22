package com.svalero.toplaptop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddUserContract;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.presenter.AddUserPresenter;
import com.svalero.toplaptop.util.ImageUtils;

import java.util.Base64;

public class AddUserView extends AppCompatActivity implements AddUserContract.View, OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private User user;
    private GoogleMap map;
    private Marker marker;
    private Switch vipSwitch;
    private ImageView userImage;
    private EditText etName;
    private EditText etSurname;
    private EditText etDni;
    private Intent intent;
    private Button addButton;
    private AddUserPresenter presenter;

    private boolean modifyUser;

    public Button getAddButton() {
        return addButton;
    }

    public void setModifyUser(boolean modifyUser) {
        this.modifyUser = modifyUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        vipSwitch = findViewById(R.id.vip_switch_add_user);
        userImage = findViewById(R.id.user_imageView);
        etName = findViewById(R.id.name_edittext_add_user);
        etSurname = findViewById(R.id.surname_edittext_add_user);
        etDni = findViewById(R.id.dni_edittext_add_user);
        addButton = findViewById(R.id.add_user_button);

        presenter = new AddUserPresenter(this);
        user = new User();

        // Permisos para la camara y almacenar en el dispositivo
        if (ContextCompat.checkSelfPermission(AddUserView.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(AddUserView.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddUserView.this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 1000
            );
        }

        // Carga el fragment del mapa de Google
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        intent();
    }

    private void intent() {

        intent = getIntent();
        modifyUser = intent.getBooleanExtra("modify_user", false);
        // Si se está editando el user, obtiene los datos del user y los pinta en el formulario
        if (modifyUser) {
            user.setId(intent.getIntExtra("id", 0));
            user.setVip(intent.getBooleanExtra("vip", false));
            user.setLatitude(intent.getFloatExtra("latitud", 0));
            Log.i("userr", "lat" + user.getLatitude());
            user.setLongitude(intent.getFloatExtra("longitud", 0));
            Log.i("userr", "lon" + user.getLongitude());

            if (!intent.getStringExtra("user_image").equalsIgnoreCase(""))
                userImage.setImageBitmap(ImageUtils.getBitmap(Base64.getDecoder().decode(intent.getStringExtra("user_image"))));
            etName.setText(intent.getStringExtra("name"));
            etSurname.setText(intent.getStringExtra("surname"));
            etDni.setText(intent.getStringExtra("dni"));
            vipSwitch.setChecked(intent.getBooleanExtra("vip", false));

            addButton.setText(R.string.modify_capital);
        }
    }

    @Override
    public void addUser(View view) {
        user.setName(etName.getText().toString().trim());
        user.setSurname(etSurname.getText().toString().trim());
        user.setDni(etDni.getText().toString().trim());
        //user.setUserImage();
        Log.i("userr", user.toString());
        byte[] bytes = (ImageUtils.fromImageViewToByteArray(userImage));
        user.setUserImage(Base64.getEncoder().encodeToString(bytes));
        //user.setUserImage("s");
        //Log.i("userr",  Base64.getEncoder().encodeToString(bytes));
        Log.i("userr", user.getUserImage());
        Log.i("userr", user.toString());
        //byte[] decode = Base64.getDecoder().decode(s);
        //Log.i("userr",  Base64.getEncoder().encodeToString(decode));
        //userImage.setImageBitmap(ImageUtils.getBitmap(decode));
        presenter.addOrModifyUser(user, modifyUser);
    }

    @Override
    public void cleanForm() {
        userImage.setImageResource(R.drawable.user);
        etName.setText("");
        etSurname.setText("");
        etDni.setText("");
        vipSwitch.setChecked(false);

        user.setVip(false);
        user.setLatitude(0);
        user.setLongitude(0);
        marker.remove();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Cambia el texto del switch y el valor booleano VIP del user
     *
     * @param view
     */
    public void switchVip(View view) {

        if (vipSwitch.isChecked()) {
            vipSwitch.setText(R.string.vip);
            user.setVip(true);
        } else {
            vipSwitch.setText(R.string.no_vip);
            user.setVip(false);
        }

    }
    //Método para tomar foto

    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }
    // Muestra la vista previa en un imageWiev de la foto tomada

    static final int REQUEST_IMAGE_CAPTURE = 1;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            userImage.setImageBitmap(imageBitmap);
        }
    }


    /**
     * Se ejecuta cuando esté el mapa cargado y asi poder interactuar con el
     *
     * @param googleMap
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;    // Asignamos el mapa pasado por parámetro a nuestra variable de tipo GoogleMap
        googleMap.setOnMapClickListener(this);  // Establecemos un listener de click sencillo para el mapa

        if (user.getLatitude() != 0 && user.getLongitude() != 0) {  // Si el user tiene ubicación
            onMapClick(new LatLng(user.getLatitude(), user.getLongitude()));    // Pone un Marker
            map.moveCamera(CameraUpdateFactory.newLatLng    // Centra la camara y asigna un zoom
                    (new LatLng((user.getLatitude() - 0.06), user.getLongitude())));
            map.moveCamera(CameraUpdateFactory.zoomTo(11));
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        if (marker != null)     // Si el marker NO está vacio,
            marker.remove();    // lo borramos para asignarle las coordenadas del click
        marker = map.addMarker(new MarkerOptions().position(latLng));
        user.setLatitude((float) latLng.latitude);    // Asignamos las coordenadas del marker a la
        user.setLongitude((float) latLng.longitude);   // dirección del user

    }
}