package com.svalero.toplaptop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddComputerContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.presenter.AddComputerPresenter;
import com.svalero.toplaptop.util.ImageUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class AddComputerView extends AppCompatActivity implements AddComputerContract.View {

    private Computer computer;
    private User user;

    private Button addButton;
    private ImageView computerImage;
    private Spinner userSpinner;
    private EditText etBrand;
    private EditText etModel;
    private EditText etRam;
    private Intent intent;
    private AddComputerPresenter presenter;

    private boolean modifyComputer;
    public List<User> users;

    public Button getAddButton() {
        return addButton;
    }

    public void setModifyComputer(boolean modifyComputer) {
        this.modifyComputer = modifyComputer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_computer);

        computerImage = findViewById(R.id.computer_imageView);
        etBrand = findViewById(R.id.brand_edittext_add_computer);
        etModel = findViewById(R.id.model_edittext_add_computer);
        etRam = findViewById(R.id.ram_edittext_add_computer);
        userSpinner = findViewById(R.id.user_spinner_add_computer);
        addButton = findViewById(R.id.add_computer_button);

        presenter = new AddComputerPresenter(this);
        computer = new Computer();
        user = new User();
        users = new ArrayList<>();

        presenter.loadUsersSpinner(); //MVP
        intent();
    }


    @Override
    protected void onResume() {
        super.onResume();

        presenter.loadUsersSpinner(); //MVP
    }

    @Override
    public void loadUserSpinner(List<User> users) {

        this.users.clear();
        this.users.addAll(users);

        String[] arraySpinner = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            arraySpinner[i] = users.get(i).getName() + " " + users.get(i).getSurname();
        }

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arraySpinner);
        userSpinner.setAdapter(adapterSpinner);

    }

    private void intent() {

        intent = getIntent();
        modifyComputer = intent.getBooleanExtra("modify_computer", false);
        // Si se está editando la moto, obtiene los datos de la moto y los pinta en el formulario
        if (modifyComputer) {
            computer.setId(intent.getIntExtra("id", 0));
            user.setId(intent.getIntExtra("userId", 0));
            Log.i("userId", "id" + user.getId());
            computer.setUser(user);
            if (!intent.getStringExtra("computer_image").equalsIgnoreCase(""))
                computerImage.setImageBitmap(ImageUtils.getBitmap(Base64.getDecoder().decode(intent.getStringExtra("computer_image"))));
            etBrand.setText(intent.getStringExtra("brand"));
            etModel.setText(intent.getStringExtra("model"));
            etRam.setText(intent.getStringExtra("ram"));

            addButton.setText(R.string.modify_capital);
        }
    }

    @Override
    public void addComputer(View view) {

        computer.setBrand(etBrand.getText().toString().trim());
        computer.setModel(etModel.getText().toString().trim());
        computer.setRam(etRam.getText().toString().trim());
        user.setId(users.get(userSpinner.getSelectedItemPosition()).getId());
        Log.i("userId", "id" + user.getId());
        computer.setUser(user);
        computer.setComputerImage(Base64.getEncoder().encodeToString(ImageUtils.fromImageViewToByteArray(computerImage)));
        presenter.addOrModifyComputer(computer, modifyComputer);

    }

    @Override
    public void cleanForm() {

        computerImage.setImageResource(R.drawable.computer_default);
        etBrand.setText("");
        etModel.setText("");
        etRam.setText("");

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    //Método para tomar foto
    public void takePhoto(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    // Muestra la vista previa en un imageWiev de la foto tomada
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            computerImage.setImageBitmap(imageBitmap);
        }
    }

}