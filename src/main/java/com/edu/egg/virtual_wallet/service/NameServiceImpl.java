package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.repository.NameRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NameServiceImpl implements NameService {

    @Autowired
    private NameRepo nameRepository;

    @Override
    @Transactional
    public void createName(Name newName) {
        newName.setActive(true);
        nameRepository.save(newName);
    }

    @Override
    @Transactional
    public void deactivateName(Integer id) { nameRepository.deleteById(id); }

    @Override
    @Transactional
    public void editName(Name updatedName) {
        if(nameRepository.findById(updatedName.getId()).isPresent()) {
            nameRepository.save(updatedName);
        }
    }
}