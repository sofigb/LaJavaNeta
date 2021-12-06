
package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.repository.TransactionRepository;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

   @Service
public class TransactionService {
     @Autowired    
     private PayeeService payeeService;
    
     @Autowired   
     private TransactionRepository transactionRep; 
     
     public void create(Transaction transaction) throws MyException{
         //validar la longuitud de reference
          Validation.validationName(transaction.getReference());
          
                 
         
         
     }
     
}
