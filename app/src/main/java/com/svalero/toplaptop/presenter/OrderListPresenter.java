package com.svalero.toplaptop.presenter;

import com.svalero.toplaptop.contract.OrderListContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.domain.dto.OrderDTOAdapter;
import com.svalero.toplaptop.model.OrderListModel;
import com.svalero.toplaptop.view.OrderListView;

import java.util.ArrayList;

public class OrderListPresenter implements OrderListContract.Presenter {

    private OrderListModel model;
    private OrderListView view;
    private ArrayList<Order> ordersArrayList;
    private ArrayList<OrderDTOAdapter> ordersDTOArrayList;

    public OrderListPresenter(OrderListView view) {
        model = new OrderListModel(view.getApplicationContext());

        this.view = view;
    }

    @Override
    public void loadAllOrders() {
        ordersArrayList = new ArrayList<>();
        ordersDTOArrayList = new ArrayList<>();

        model.startDb(view.getApplicationContext());
        ordersArrayList = model.loadAllOrders();

        for (Order order : ordersArrayList) {
            //User user = model.loadUserById(order.getUser().getId());
            Computer computer = model.loadComputerById(order.getComputer().getId());
            OrderDTOAdapter orderDTOAdapter = new OrderDTOAdapter();

            orderDTOAdapter.setId(order.getId());
            orderDTOAdapter.setDate(order.getOrderDate());
            //orderDTOAdapter.setUserNameSurname(user.getName() + " " + user.getSurname());
            orderDTOAdapter.setComputerBrandModel(computer.getBrand() + " " + computer.getModel());
            orderDTOAdapter.setComputerRam(computer.getRam());
            orderDTOAdapter.setComputerImageOrder(computer.getComputerImage());
            orderDTOAdapter.setDescription(order.getDescription());

            ordersDTOArrayList.add(orderDTOAdapter);
        }
        view.listUsers(ordersDTOArrayList);
    }

    @Override
    public void deleteOrder(Order order) {
        model.startDb(view.getApplicationContext());
        model.deleteOrder(order);
    }
}
