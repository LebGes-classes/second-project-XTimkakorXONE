package com.my_app.entities.warehouse;

import com.my_app.entities.product.Product;
import com.my_app.entities.warehouse_cell.Cell;

public interface IWarehouse {
    void sendProduct();
    void putInCell(Cell cell, Product product);
    Product getFromCell(Cell cell, Product product);
    
}
