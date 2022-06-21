package com.svalero.toplaptop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddOrderContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.presenter.AddOrderPresenter;
import com.svalero.toplaptop.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddOrderView extends AppCompatActivity implements AddOrderContract.View {

    private Spinner userSpinner;
    private Spinner computerSpinner;
    private EditText etDescription;
    private TextView tvDate;
    private Button addButton;
    private Intent intent;

    private Order order;
    private ArrayList<User> users;
    private ArrayList<Computer> computers;
    private boolean modifyOrder;
    private AddOrderPresenter presenter;

    public Spinner getUserSpinner() {
        return userSpinner;
    }

    public Spinner getComputerSpinner() {
        return computerSpinner;
    }

    public Button getAddButton() {
        return addButton;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public ArrayList<Computer> getComputers() {
        return computers;
    }

    public void setModifyOrder(boolean modifyOrder) {
        this.modifyOrder = modifyOrder;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order);

        userSpinner = findViewById(R.id.user_spinner_add_order);
        computerSpinner = findViewById(R.id.computer_spinner_add_order);
        etDescription = findViewById(R.id.description_edittext_add_order);
        tvDate = findViewById(R.id.date_textlabel_add_order);
        addButton = findViewById(R.id.add_order_button);

        order = new Order(0, null, "", null, null);

        presenter = new AddOrderPresenter(this);
        users = new ArrayList<>();
        computers = new ArrayList<>();
        fillSpinners(0);
        intent();

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillSpinners(users.get(userSpinner.getSelectedItemPosition()).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillSpinners(0);
    }

    public void selectDate(View view) {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String stringMonth = String.valueOf(month + 1);
                if ((month + 1) < 10) {
                    stringMonth = "0" + stringMonth;
                }
                tvDate.setText(dayOfMonth + "/" + stringMonth + "/" + year);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void intent() {

        intent = getIntent();
        modifyOrder = intent.getBooleanExtra("modify_order", false);

        if (modifyOrder) {  // Si se edita una moto, obtiene sus datos y los pinta en el formulario
            order.setId(intent.getIntExtra("id", 0));
            tvDate.setText(DateUtils.fromLocalDateToMyDateFormatString
                    (LocalDate.parse(intent.getStringExtra("date"))));
            etDescription.setText(intent.getStringExtra("description"));

            addButton.setText(R.string.modify_capital);
        }
    }

    public void addOrder(View view) {

        if (!(tvDate.getText().toString().trim()).equalsIgnoreCase(""))
            order.setOrderDate(DateUtils.fromMyDateFormatStringToLocalDate(tvDate.getText().toString().trim()));
        order.setDescription(etDescription.getText().toString().trim());

        presenter.addOrder(order, modifyOrder);

    }

    @Override
    public void cleanForm() {
        etDescription.setText("");
        tvDate.setText("");
        order = new Order(0, null, "", null, null);
    }

    /**
     * Rellena los spinner para añadirlos a la orden
     *
     * @param idUser 0 rellena el spinner de users con todos ellos,
     *                 > 0 rellena el spinner de computers con las motos del id enviado por parametro
     */
    private void fillSpinners(int idUser) {

        if (idUser == 0) {    // USER
            presenter.fillUserSpinner();
        } else {    // COMPUTER
            presenter.fillComputerSpinner(idUser);
        }

    }

    @Override
    public void fillComputerSpinner(ArrayList<Computer> computers) {

        this.computers.clear();
        this.computers.addAll(computers);

        String[] arrayComputerSpinner = new String[computers.size()];

        for (int i = 0; i < computers.size(); i++) {
            arrayComputerSpinner[i] = computers.get(i).getBrand() + " " + computers.get(i).getModel();
        }

        ArrayAdapter<String> adapterComputerSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayComputerSpinner);
        computerSpinner.setAdapter(adapterComputerSpinner);

    }

    @Override
    public void fillUserSpinner(ArrayList<User> users) {

        this.users.clear();
        this.users.addAll(users);

        String[] arrayUserSpinner = new String[users.size()];

        for (int i = 0; i < users.size(); i++) {
            arrayUserSpinner[i] = users.get(i).getName() + " " + users.get(i).getSurname();
        }

        ArrayAdapter<String> adapterUserSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayUserSpinner);
        userSpinner.setAdapter(adapterUserSpinner);

    }
}
