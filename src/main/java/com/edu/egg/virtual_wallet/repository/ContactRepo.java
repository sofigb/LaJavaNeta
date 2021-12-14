package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepo extends JpaRepository<Contact, Integer> {

    boolean existsContactByEmail(String email);
}