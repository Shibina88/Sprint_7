package ru.qa.scooter.praktikum;

import java.util.List;

public class OrderList {
    private List<OrderListResponse> orders;
    private PageInfo pageInfo;
    private List<AvailableStations> availableStations;

    public OrderList(List<OrderListResponse> orders, PageInfo pageInfo, List<AvailableStations> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public OrderList() {
    }

    public List<OrderListResponse> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderListResponse> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AvailableStations> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStations> availableStations) {
        this.availableStations = availableStations;
    }
}
