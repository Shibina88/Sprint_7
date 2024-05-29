package ru.qa.scooter.praktikum;

public class Order {
    private OrderResponse order;

    public Order() {
    }

    public Order(OrderResponse order) {
        this.order = order;
    }

    public OrderResponse getOrder() {
        return order;
    }

    public void setOrder(OrderResponse order) {
        this.order = order;
    }
}
