package com.svalero.toplaptop.presenter;

import com.svalero.toplaptop.contract.ComputerListContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.model.ComputerListModel;
import com.svalero.toplaptop.view.ComputerListView;

import java.util.List;

public class ComputerListPresenter implements ComputerListContract.Presenter, ComputerListContract.Model.OnLoadComputersListener, ComputerListContract.Model.OnDeleteComputerListener {

    private ComputerListModel model;
    private ComputerListView view;

    public ComputerListPresenter(ComputerListView view) {
        model = new ComputerListModel();
        model.startDb(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllComputers() {
        model.loadAllComputers(this);
    }

    @Override
    public void loadComputersByBrand(String query) {
        model.loadComputersByBrand(this, query);
    }

    @Override
    public void loadComputersByModel(String query) {
        model.loadComputersByModel(this, query);
    }

    @Override
    public void loadComputersByRam(String query) {
        model.loadComputersByRam(this, query);
    }

    @Override   // OnLoadComputersListener SUCCESS
    public void onLoadComputersSuccess(List<Computer> computers) {
        view.listComputers(computers);
    }

    @Override   // OnLoadComputersListener ERROR
    public void onLoadComputersError(String message) {
        view.showMessage(message);
    }

    @Override
    public void deleteComputer(Computer computer) {
        model.delete(this, computer);
    }

    @Override   // OnDeleteComputersListener SUCCESS
    public void onDeleteComputerSuccess(String message) {
        view.showMessage(message);
    }

    @Override   // OnDeleteComputersListener ERROR
    public void onDeleteComputerError(String message) {
        view.showMessage(message);
    }
}
