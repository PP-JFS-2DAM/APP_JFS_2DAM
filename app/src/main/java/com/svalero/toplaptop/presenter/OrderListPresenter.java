package com.svalero.toplaptop.presenter;

import com.svalero.toplaptop.contract.OrderListContract;
import com.svalero.toplaptop.domain.Computer;
import com.svalero.toplaptop.domain.User;
import com.svalero.toplaptop.domain.Order;
import com.svalero.toplaptop.domain.dto.OrderDTO;
import com.svalero.toplaptop.model.OrderListModel;
import com.svalero.toplaptop.view.OrderListView;

import java.util.ArrayList;

public class OrderListPresenter implements OrderListContract.Presenter {

    private OrderListModel model;
    private OrderListView view;
    private ArrayList<Order> ordersArrayList;
    private ArrayList<OrderDTO> ordersDTOArrayList;

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
            User user = model.loadUserById(order.getUser().getId());
            Computer computer = model.loadComputerById(order.getComputer().getId());
            OrderDTO orderDTO = new OrderDTO();

            orderDTO.setId(order.getId());
            orderDTO.setDate(order.getOrderDate());
            orderDTO.setUserNameSurname(user.getName() + " " + user.getSurname());
            orderDTO.setComputerBrandModel(computer.getBrand() + " " + computer.getModel());
            orderDTO.setComputerLicensePlate(computer.getLicensePlate());
            orderDTO.setComputerImageOrder(computer.getComputerImage());
            orderDTO.setDescription(order.getDescription());

            ordersDTOArrayList.add(orderDTO);
        }
        view.listUsers(ordersDTOArrayList);
    }

    @Override
    public void deleteOrder(Order order) {
        model.startDb(view.getApplicationContext());
        model.deleteOrder(order);
    }
}
