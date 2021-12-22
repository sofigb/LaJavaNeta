package com.edu.egg.virtual_wallet.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CryptoAPIController {

    @GetMapping("/cryptoAPI") // Preguntar a dani
    public ModelAndView crypto (){
        ModelAndView mav = new ModelAndView("cryptocurrency-quotes");
        return mav;
    }
}