package com.svalero.toplaptop.contract;

import android.content.Context;

import com.svalero.toplaptop.domain.Computer;

import java.util.List;

public interface ComputerListContract {
    interface Model {
        interface OnLoadComputersListener {
            void onLoadComputersSuccess(List<Computer> computers);

            void onLoadComputersError(String message);
        }

        interface OnDeleteComputerListener{
            void onDeleteComputerSuccess(String message);

            void onDeleteComputerError(String message);
        }

        void startDb(Context context);

        void loadAllComputers(OnLoadComputersListener listener);

        void loadComputersByBrand(OnLoadComputersListener listener, String query);

        void loadComputersByModel(OnLoadComputersListener listener, String query);

        void loadComputersByRam(OnLoadComputersListener listener, String query);

        void delete(OnDeleteComputerListener listener, Computer computer);
    }

    interface View {
        void listComputers(List<Computer> computers);

        void showMessage(String message);
    }

    interface Presenter {
        void loadAllComputers();

        void loadComputersByBrand(String query);

        void loadComputersByModel(String query);

        void loadComputersByRam(String query);

        void deleteComputer(Computer computer);
    }
}
