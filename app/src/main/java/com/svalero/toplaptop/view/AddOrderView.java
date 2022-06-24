package com.svalero.toplaptop.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.contract.AddOrderContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.Technical;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.presenter.AddOrderPresenter;
import com.svalero.toplaptop.util.DateUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddOrderView extends AppCompatActivity implements AddOrderContract.View {

    private Spinner userSpinner;
    private Spinner computerSpinner;
    private EditText etDescription;
    private TextView tvDate;
    private Button addButton;
    private Intent intent;

    private User user;
    private Order order;
    private Computer computer;
    private Technical technical;
    private List<User> users;
    private List<Computer> computers;
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

    public List<User> getUsers() {
        return users;
    }

    public List<Computer> getComputers() {
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

        user = new User(0, "", "", "", false, 0, 0, "");
        order = new Order(0, null, "", null, null);
        computer = new Computer();
        technical = new Technical();
        technical.setId(1);

        presenter = new AddOrderPresenter(this);
        users = new ArrayList<>();
        computers = new ArrayList<>();
        fillSpinners(user);
        intent();

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                fillSpinners(users.get(userSpinner.getSelectedItemPosition()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fillSpinners(user);
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

        if (modifyOrder) {
            order.setId(intent.getIntExtra("id", 0));
            tvDate.setText(DateUtils.fromLocalDateToMyDateFormatString
                    (LocalDate.parse(intent.getStringExtra("date"))));
            etDescription.setText(intent.getStringExtra("description"));

            addButton.setText(R.string.modify_capital);
        }
    }

    public void addOrder(View view) {

        if (!(tvDate.getText().toString().trim()).equalsIgnoreCase(""))
            order.setOrderDate(tvDate.getText().toString().trim());
        order.setDescription(etDescription.getText().toString().trim());
        order.setTechnical(technical);
        computer.setId(computers.get(computerSpinner.getSelectedItemPosition()).getId());
        order.setComputer(computer);

        presenter.addOrModifyOrder(order, modifyOrder);

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

    @Override
    public void loadUserComputerSpinner(List<Computer> computers) {

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
    public void addComputer(View view) {
        if (!(tvDate.getText().toString().trim()).equalsIgnoreCase(""))
            order.setOrderDate(tvDate.getText().toString().trim());
        order.setDescription(etDescription.getText().toString().trim());

        presenter.addOrModifyOrder(order, modifyOrder);
    }

    @Override
    public void cleanForm() {
        etDescription.setText("");
        tvDate.setText("");
        order = new Order(0, null, "", null, null);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Rellena los spinner para añadirlos a la orden
     *
     * @param user 0 rellena el spinner de users con todos ellos,
     *             > 0 rellena el spinner de computers con las motos del id enviado por parametro
     */
    private void fillSpinners(User user) {

        if (user.getId() == 0) {    // USER
            presenter.loadUsersSpinner();
        } else {    // COMPUTER
            presenter.loadUserComputerSpinner(user);
        }

    }
}
