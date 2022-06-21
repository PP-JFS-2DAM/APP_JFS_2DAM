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
import android.widget.Toast;

import com.svalero.toplaptop.R;
import com.svalero.toplaptop.adapters.UserAdapter;
import com.svalero.toplaptop.contract.UserListContract;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.presenter.UserListPresenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserListView extends AppCompatActivity implements UserListContract.View,
        AdapterView.OnItemClickListener, DetailFragment.closeDetails {

    public List<User> users;
    public UserAdapter userArrayAdapter;
    private String orderBy;
    private FrameLayout frameLayout;
    public Spinner findSpinner;
    private final String[] FIND_SPINNER_OPTIONS = new String[]{"Nombre", "Apellido", "Dni"};
    private final String DEFAULT_STRING = "";
    private UserListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user);

        users = new ArrayList<>();
        presenter = new UserListPresenter(this);
        userArrayAdapter = new UserAdapter(this, users);
        frameLayout = findViewById(R.id.frame_layout_user);
        findSpinner = findViewById(R.id.find_spinner_view_user);
        ArrayAdapter<String> adapterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, FIND_SPINNER_OPTIONS);
        findSpinner.setAdapter(adapterSpinner);
        orderBy = DEFAULT_STRING;

        findUsersBy(DEFAULT_STRING);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findUsersBy(DEFAULT_STRING);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_actionbar, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_user_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                findUsersBy(query.trim());
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                findUsersBy(newText.trim());
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void listUsers(List<User> users) {

        ListView usersListView = findViewById(R.id.user_lisview);
        registerForContextMenu(usersListView);
        this.users = users;

        userArrayAdapter = new UserAdapter(this, this.users);

        usersListView.setAdapter(userArrayAdapter);
        usersListView.setOnItemClickListener(this);

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void findUsersBy(String query) {
        users.clear();

        if (query.equalsIgnoreCase(DEFAULT_STRING)) {
            presenter.loadAllUsers();
        } else {
            switch (findSpinner.getSelectedItemPosition()) {
                case 0:
                    presenter.loadUsersByName(query);
                    break;
                case 1:
                    presenter.loadUsersBySurname(query);
                    break;
                case 2:
                    presenter.loadUsersByDni(query);
                    break;
            }
        }
        orderBy(orderBy);
    }

    private void orderBy(String orderBy) {
        this.orderBy = orderBy;

        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                switch (orderBy) {
                    case "name":
                        return o1.getName().compareToIgnoreCase(o2.getName());
                    case "surname":
                        return o1.getSurname().compareToIgnoreCase(o2.getSurname());
                    case "dni":
                        return o1.getDni().compareToIgnoreCase(o2.getDni());
                    default:
                        return String.valueOf(o1.getId()).compareTo(String.valueOf(o2.getId()));
                }
            }
        });
        userArrayAdapter.notifyDataSetChanged();
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
            case R.id.order_by_name_item:
                orderBy("name");
                return true;
            case R.id.order_by_surname_item:
                orderBy("surname");
                return true;
            case R.id.order_by_dni_item:
                orderBy("dni");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Método para cuando se crea el menu contextual, infle el menu con las opciones
     *
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo
            menuInfo) {
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

        Intent intent = new Intent(this, AddUserView.class);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.modify_menu:                      // Modificar usere
                User user = users.get(info.position);

                intent.putExtra("modify_user", true);
                intent.putExtra("id", user.getId());
                intent.putExtra("user_image", user.getUserImage());
                intent.putExtra("name", user.getName());
                intent.putExtra("surname", user.getSurname());
                intent.putExtra("dni", user.getDni());
                intent.putExtra("vip", user.isVip());
                intent.putExtra("latitud", user.getLatitude());
                intent.putExtra("longitud", user.getLongitude());

                startActivity(intent);
                return true;
            case R.id.detail_menu:                      // Detalles del usere
                showDetails(info.position);
                return true;
            case R.id.add_menu:                         // Añadir usere
                startActivity(intent);
                return true;
            case R.id.delete_menu:                      // Eliminar usere
                deleteUser(info);
                return true;
            default:
                return super.onContextItemSelected(item);
        }   // End switch
    }

    private void deleteUser(AdapterView.AdapterContextMenuInfo info) {
        User user = users.get(info.position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.are_you_sure_delete_user)
                .setPositiveButton(R.string.yes_capital, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteUser(user);
                        findUsersBy(DEFAULT_STRING);
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

    private void showDetails(int position) {

        User user = users.get(position);

        Bundle datos = new Bundle();
        datos.putByteArray("user_image", user.getUserImage());
        datos.putString("name", user.getName());
        datos.putString("surname", user.getSurname());
        datos.putString("dni", user.getDni());
        datos.putBoolean("vip", user.isVip());
        datos.putFloat("latitude", user.getLatitude());
        datos.putFloat("longitude", user.getLongitude());

        DetailFragment detailFragment = new DetailFragment();
        detailFragment.setArguments(datos);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.user_detail, detailFragment)
                .commit();

        frameLayout.setVisibility(View.VISIBLE);
    }

    public void addUser(View view) {
        Intent intent = new Intent(this, AddUserView.class);
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