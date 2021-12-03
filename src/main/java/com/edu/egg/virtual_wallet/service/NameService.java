package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Name;

public interface NameService {

    void createName(Name newName);
    void deactivateName(Integer id);
    void editName(Name updatedName);
}
