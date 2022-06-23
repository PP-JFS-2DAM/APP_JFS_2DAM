package com.svalero.toplaptop.model;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;

import com.svalero.toplaptop.api.ToplaptopApi;
import com.svalero.toplaptop.api.ToplaptopApiInterface;
import com.svalero.toplaptop.contract.ComputerListContract;
import com.svalero.toplaptop.database.AppDatabase;
import com.svalero.toplaptop.domain.Computer;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComputerListModel implements ComputerListContract.Model {

    private AppDatabase db;
    private Context context;
    private ToplaptopApiInterface api;
    private List<Computer> computers;

    @Override
    public void startDb(Context context) {
        this.context = context;
        computers = new ArrayList<>();
        api = ToplaptopApi.buildInstance();
        db = Room.databaseBuilder(context,
                        AppDatabase.class, "computer").allowMainThreadQueries()
                .fallbackToDestructiveMigration().build();
    }

    @Override
    public void loadAllComputers(OnLoadComputersListener listener) {
        computers.clear();

        Call<List<Computer>> computerCall = api.getComputers();

        loadComputersCallEnqueue(listener, computerCall);
    }

    @Override
    public void loadComputersByBrand(OnLoadComputersListener listener, String query) {
        computers.clear();

        Call<List<Computer>> computerCall = api.getComputersByBrand(query);

        loadComputersCallEnqueue(listener, computerCall);
    }

    @Override
    public void loadComputersByModel(OnLoadComputersListener listener, String query) {
        computers.clear();

        Call<List<Computer>> computerCall = api.getComputersByModel(query);

        loadComputersCallEnqueue(listener, computerCall);
    }

    @Override
    public void loadComputersByRam(OnLoadComputersListener listener, String query) {
        computers.clear();

        Call<List<Computer>> computersCall = api.getComputersByRam(query);

        loadComputersCallEnqueue(listener, computersCall);
    }

    /**
     * Envía la solicitud de forma asíncrona y notifica la devolución de llamada de su respuesta
     * o si se produjo un error al hablar con el servidor, crear la solicitud o procesar la respuesta.
     *
     * @param listener OnLoadComputersListener
     * @param call     Lista de Computers
     */
    private void loadComputersCallEnqueue(OnLoadComputersListener listener, Call<List<Computer>> call) {
        call.enqueue(new Callback<List<Computer>>() {
            @Override
            public void onResponse(Call<List<Computer>> call, Response<List<Computer>> response) {
                computers = response.body();
                listener.onLoadComputersSuccess(computers);
            }

            @Override
            public void onFailure(Call<List<Computer>> call, Throwable t) {
                listener.onLoadComputersError("An error has occurred");
                t.printStackTrace();
            }
        });
    }

    @Override
    public void delete(OnDeleteComputerListener listener, Computer computer) {
        Call<Void> computerCall = api.deleteComputer(computer.getId());

        computerCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                listener.onDeleteComputerSuccess("Computer deleted successfully");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onDeleteComputerError("The computer couldn't be deleted");
                t.printStackTrace();
            }
        });
    }
}
