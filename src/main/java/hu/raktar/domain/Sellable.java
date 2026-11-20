package hu.raktar.domain;

import hu.raktar.exception.OutOfStockException;

public interface Sellable {
    void sell(int quantity) throws OutOfStockException;
    double getPrice();
}