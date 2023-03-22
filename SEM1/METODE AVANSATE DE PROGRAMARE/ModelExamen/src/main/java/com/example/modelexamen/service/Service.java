package com.example.modelexamen.service;

import com.example.modelexamen.domain.MenuItem;
import com.example.modelexamen.domain.Table;
import com.example.modelexamen.repostiory.MenuItemDatabaseRepository;
import com.example.modelexamen.repostiory.TableDatabaseRepository;

public class Service {
    TableDatabaseRepository table;
    MenuItemDatabaseRepository menuItem;

    public Service() {
        this.table = new TableDatabaseRepository("jdbc:postgresql://localhost:5432/ModelExamen","postgres","postgres");
        this.menuItem=new MenuItemDatabaseRepository("jdbc:postgresql://localhost:5432/ModelExamen","postgres","postgres");
    }

    public Iterable<Table> getAllTables() {
        return table.findAll();
    }
    public Iterable<MenuItem> getAllMenuItems() {
        return menuItem.findAll();
    }
}
