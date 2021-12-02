
package com.edu.egg.virtual_wallet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

   @Service
public class TransactionService {
     @Autowired    
     private PayeeService payeeService;
     @Autowired   
     private TransactionRepository transactionRep; 
     
     
}
