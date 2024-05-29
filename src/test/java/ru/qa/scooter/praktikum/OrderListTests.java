package ru.qa.scooter.praktikum;

import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderListTests {
    private CourierCard.OrderClient orderClient;
    private CourierClient courierClient;
    private CourierData courierData;
    private OrderRequest orderRequest;
    private int courierId;
    private int orderId;

    @Before
    public void setUp() {
        orderClient = new CourierCard.OrderClient();
        courierClient = new CourierClient();
        courierData = CourierData.courierGenerator();
        orderRequest = OrderRequest.orderGenerator();
    }

    @Test
    @DisplayName("200 OK: successful get order list")
    public void getOrderLists() {
        CourierCard.Order order = getOrderByCourier();
        OrderListFilter orderListFilter = new OrderListFilter(courierId, order.getOrder().getMetroStation(), 30, 0);
        OrderList orderList = orderClient.getOrderList(orderListFilter);
        assertEquals(orderList.getOrders().get(0).getCourierId(), courierId);
        assertEquals(orderList.getOrders().get(0).getMetroStation(), order.getOrder().getMetroStation());
        assertEquals(orderList.getOrders().get(0).getFirstName(), order.getOrder().getFirstName());
        assertEquals(orderList.getOrders().get(0).getLastName(), order.getOrder().getLastName());
    }

    private CourierCard.Order getOrderByCourier() {
        int orderTrack = getOrderTrack();
        CourierCard.Order order = orderClient.getOrderByTrack(orderTrack);
        getCourierId();
        orderId = order.getOrder().getId();
        orderClient.accept(order.getOrder().getId(), courierId);
        return order;
    }

    private int getOrderTrack() {
        return orderClient.create(orderRequest)
                .then()
                .extract()
                .body().path("track");
    }

    private void getCourierId() {
        courierClient.create(courierData);
        courierId = courierClient.login(CourierCard.getCredsFrom(courierData))
                .then()
                .extract()
                .body().path("id");
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
        orderClient.finish(orderId);
    }
}
