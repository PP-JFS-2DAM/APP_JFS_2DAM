package com.svalero.toplaptop.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.adapters.OrderAdapter;
import com.svalero.toplaptop.contract.OrderListContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.domain.dto.OrderDTOAdapter;
import com.svalero.toplaptop.presenter.OrderListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class OrderListView extends AppCompatActivity implements OrderListContract.View,
        AdapterView.OnItemClickListener, DetailFragment.closeDetails {

    public ArrayList<OrderDTOAdapter> ordersDTOArrayList;

    public OrderAdapter orderDTOArrayAdapter;
    private Computer computer;
    private User user;
    private FrameLayout frameLayout;
    private String orderBy;
    public Spinner findSpinner;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Fecha", "User", "Moto", "Matrícula"};
    private final String DEFAULT_STRING = "";
    private OrderListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        presenter = new OrderListPresenter(this);
        computer = new Computer();
        user = new User();
        ordersDTOArrayList = new ArrayList<>();
        frameLayout = findViewById(R.id.frame_layout_order);
        findSpinner = findViewById(R.id.find_spinner_view_order);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);
        orderBy = DEFAULT_STRING;

        findOrdersBy(DEFAULT_STRING);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findOrdersBy(DEFAULT_STRING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_actionbar, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_order_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findOrdersBy(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findOrdersBy(newText.trim());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void listUsers(ArrayList<OrderDTOAdapter> ordersDTOArrayList) {
        ListView ordersListView = findViewById(R.id.order_listview);
        registerForContextMenu(ordersListView);
        this.ordersDTOArrayList = ordersDTOArrayList;

        orderDTOArrayAdapter = new OrderAdapter(this, ordersDTOArrayList);

        ordersListView.setAdapter(orderDTOArrayAdapter);
        ordersListView.setOnItemClickListener(this);

    }

    /**
     * Carga el ArrayList del ListView y busca en cada elemento la cadena de texto que se introduce
     * en el searchView del ActionBar, según el atributo seleccionado en el Spinner
     *
     * @param query cadena de texto que se introduce en el searchView del ActionBar
     */
    private void findOrdersBy(String query) {
        ordersDTOArrayList.clear();
        presenter.loadAllOrders();

        switch (findSpinner.getSelectedItemPosition()) {
            case 0:
                ordersDTOArrayList.clear();
                presenter.loadAllOrders();
                ordersDTOArrayList.removeIf // TODO por fecha hacer así, cargar todos y quitar los que no coincidan
                (orderDTO -> (!String.valueOf(orderDTO.getDate()).contains(query)));
                break;
            case 1:
                ordersDTOArrayList.removeIf // TODO para el resto busqueda filtrada
                        (orderDTO -> (!orderDTO.getUserNameSurname().toLowerCase().contains(query.toLowerCase())));
                break;
            case 2:
                ordersDTOArrayList.removeIf
                        (orderDTO -> (!orderDTO.getComputerBrandModel().toLowerCase().contains(query.toLowerCase())));
                break;
            case 3:
                ordersDTOArrayList.removeIf
                        (orderDTO -> (!orderDTO.getComputerRam().toLowerCase().contains(query.toLowerCase())));
                break;
        }   // End switch
        orderBy(orderBy);
    }

    /**
     * Ordena el ArrayList del ListView según los atributos de los objetos OrderDTOAdapter y
     * notifica los cambios al ArrayAdapter para que pinte de nuevo el ListView
     *
     * @param orderBy Recibe un String segun el boton del ActionBar pulsado
     */
    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        Collections.sort(ordersDTOArrayList, new Comparator<OrderDTOAdapter>() {
            @Override
            public int compare(OrderDTOAdapter o1, OrderDTOAdapter o2) {
                switch (orderBy) {
                    case "date":
                        return String.valueOf(o1.getDate()).compareToIgnoreCase(String.valueOf(o2.getDate()));
                    case "user_name":
                        return o1.getUserNameSurname().compareToIgnoreCase(o2.getUserNameSurname());
                    case "ram":
                        return o1.getComputerRam().compareToIgnoreCase(o2.getComputerRam());
                    case "computer_model":
                        return o1.getComputerBrandModel().compareToIgnoreCase(o2.getComputerBrandModel());
                    default:
                        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }   // End switch
            }
        });
        orderDTOArrayAdapter.notifyDataSetChanged();
    }

    /**
     * Opciones del menú ActionBar "ordenar por:"
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
            case R.id.order_by_date_item:
                orderBy("date");
                return true;
            case R.id.order_by_user_item:
                orderBy("user_name");
                return true;
            case R.id.order_by_ram_item:
                orderBy("ram");
                return true;
            case R.id.order_by_computer_model_item:
                orderBy("computer_model");
            default:
                return super.onOptionsItemSelected(item);
        }   // End switch
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
     * Opciones del menu contextual
     *
     * @param item
     * @return
     */
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        Intent intent = new Intent(this, AddOrderView.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar moto
                OrderDTOAdapter orderDTOAdapter = ordersDTOArrayList.get(info.position);

                intent.putExtra("modify_order", true);
                intent.putExtra("id", orderDTOAdapter.getId());
                intent.putExtra("date", String.valueOf(orderDTOAdapter.getDate()));
                intent.putExtra("description", orderDTOAdapter.getDescription());

                startActivity(intent);
                return true;
            case R.id.detail_menu:                      // Detalles de la moto
                showDetails(info.position);
                return true;
            case R.id.add_menu:                         // Añadir moto
                startActivity(intent);
                return true;
            case R.id.delete_menu:                      // Eliminar moto
                deleteOrder(info);
                return true;
            default:
                return super.onContextItemSelected(item);
        }   // End switch
    }

    private void deleteOrder(AdapterView.AdapterContextMenuInfo info) {

        OrderDTOAdapter orderDTOAdapter = ordersDTOArrayList.get(info.position);
        Order order = new Order();
        order.setId(orderDTOAdapter.getId());

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_order)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteOrder(order);
                        findOrdersBy(DEFAULT_STRING);
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

    /**
     * Llama al fragment para los detalles y le pasa datos
     *
     * @param position
     */
    private void showDetails(int position) {

        OrderDTOAdapter orderDTOAdapter = ordersDTOArrayList.get(position);

        Bundle datos = new Bundle();
        datos.putByteArray("computer_image", orderDTOAdapter.getComputerImageOrder());
        datos.putString("date", String.valueOf(orderDTOAdapter.getDate()));
        datos.putString("name", orderDTOAdapter.getUserNameSurname());
        datos.putString("model", orderDTOAdapter.getComputerBrandModel());
        datos.putString("license", orderDTOAdapter.getComputerRam());
        datos.putString("description", orderDTOAdapter.getDescription());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(datos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.order_detail, detailFragment)
                .commit();

        frameLayout.setVisibility(View.VISIBLE);
    }


    public void addOrder(View view) {
        Intent intent = new Intent(this, AddOrderView.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showDetails(position);
    }

    @Override
    public void hiddeDetails() {
        frameLayout.setVisibility(View.GONE);
    }
}