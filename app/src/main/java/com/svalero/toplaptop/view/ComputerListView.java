package com.svalero.toplaptop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.adapters.ComputerAdapter;
import com.svalero.toplaptop.contract.ComputerListContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.presenter.ComputerListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ComputerListView extends AppCompatActivity implements ComputerListContract.View, AdapterView.OnItemClickListener {

    public List<Computer> computers;
    public ComputerAdapter computerArrayAdapter;
    public Spinner findSpinner;
    private String orderBy;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Marca", "Modelo", "Ram"};
    private final String DEFAULT_STRING = "";
    private ComputerListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_computer);
        findSpinner = findViewById(R.id.find_spinner_view_computer);
        computers = new ArrayList<>();
        presenter = new ComputerListPresenter(this);
        computerArrayAdapter = new ComputerAdapter(this, computers);

        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);

        orderBy = DEFAULT_STRING;

        findComputersBy(DEFAULT_STRING);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findComputersBy(DEFAULT_STRING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.computer_actionbar, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_computer_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findComputersBy(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findComputersBy(newText.trim());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void listComputers(List<Computer> computers) {

        ListView computersListView = findViewById(R.id.computer_lisview);
        registerForContextMenu(computersListView);
        this.computers = computers;

        computerArrayAdapter = new ComputerAdapter(this, this.computers);

        computersListView.setAdapter(computerArrayAdapter);
        computersListView.setOnItemClickListener(this);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Busca las motos según la consulta en la barra de búsqueda del ActionBar y el parámetro de
     * búsqueda seleccionado en el Spinner.
     * Si no hay nada escrito en la barra de búsqueda carga todas las motos.
     * Finalmente llama a OrderBy para ordenarlas según lo indicado en las opciones del ActionBar
     *
     * @param query
     */
    private void findComputersBy(String query) {
        computers.clear();

        if (query.equalsIgnoreCase(DEFAULT_STRING)) {
            presenter.loadAllComputers();
        } else {
            switch (findSpinner.getSelectedItemPosition()) {
                case 0:
                    presenter.loadComputersByBrand(query);
                    break;
                case 1:
                    presenter.loadComputersByModel(query);
                    break;
                case 2:
                    Log.i("RAM", " presenter.loadComputersByRam(query)");
                    presenter.loadComputersByRam(query);
                    break;
            }
        }
        orderBy(orderBy);
    }

    /**
     * Ordena el ArrayList de motos según la opción seleccionada en el ActionBar
     *
     * @param orderBy String por el cuál ordenar las motos
     */
    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        Collections.sort(computers, new Comparator<Computer>() {
            @Override
            public int compare(Computer o1, Computer o2) {
                switch (orderBy) {
                    case "brand":
                        return o1.getBrand().compareToIgnoreCase(o2.getBrand());
                    case "model":
                        return o1.getModel().compareToIgnoreCase(o2.getModel());
                    case "ram":
                        return o1.getRam().compareToIgnoreCase(o2.getRam());
                    default:
                        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }
            }
        });
        computerArrayAdapter.notifyDataSetChanged();
    }

    /**
     * Método para cuando se crea el menu contextual, infle el menu con las opciones
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.listview_menu, menu);

    }

    /**
     * Opciones del menú ActionBar
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.order_by_default_item:
                orderBy("");
                return true;
            case R.id.order_by_brand_item:
                orderBy("brand");
                return true;
            case R.id.order_by_model_item:
                orderBy("model");
                return true;
            case R.id.order_by_ram_item:
                orderBy("ram");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Opciones del menu contextual
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this, AddComputerView.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        final int itemSelected = info.position;

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar moto
                Computer computer = computers.get(itemSelected);
                intent.putExtra("modify_computer", true);

                intent.putExtra("id", computer.getId());
                intent.putExtra("computer_image", computer.getComputerImage());
                intent.putExtra("brand", computer.getBrand());
                intent.putExtra("model", computer.getModel());
                intent.putExtra("ram", computer.getRam());
                intent.putExtra("userId", computer.getUser().getId());

                startActivity(intent);
                return true;

            case R.id.detail_menu:                      // Detalles de la moto

                // Todo FALTA usar un fragment para mostrar una ficha con los detalles de la moto

                return true;

            case R.id.add_menu:                         // Añadir moto
                startActivity(intent);
                return true;

            case R.id.delete_menu:                      // Eliminar moto
                deleteComputer(info);
                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }

    private void deleteComputer(AdapterView.AdapterContextMenuInfo info) {
        Computer computer = computers.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_computer)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteComputer(computer);
                        findComputersBy(DEFAULT_STRING);
                    }
                })
                .setNegativeButton(R.string.no_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();
    }

    public void addComputer(View view) {
        Intent intent = new Intent(this, AddComputerView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}