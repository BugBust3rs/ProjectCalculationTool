package com.example.projectcalculationtool.Repository;

import java.util.List;

public interface CRUDInterface {
    void create(Object o);
    List<Object> getAll();
    void update(Object o);
    void delete(int id);
}
