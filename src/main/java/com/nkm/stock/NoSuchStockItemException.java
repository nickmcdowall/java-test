package com.nkm.stock;

public class NoSuchStockItemException extends RuntimeException {
    public NoSuchStockItemException(String key) {
        super("No stock item exists with key: " + key);
    }
}
