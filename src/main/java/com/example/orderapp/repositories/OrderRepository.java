package com.example.orderapp.repositories;

import com.example.orderapp.models.OrderState;
import com.example.orderapp.models.Pizza;

import java.util.List;

public interface OrderRepository {

    boolean OrderAPizza(Long userId, List<String> pizzaNameList);
    void ChangeOrderState(Long orderId, OrderState orderState);
}
