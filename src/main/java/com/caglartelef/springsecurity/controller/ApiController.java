package com.caglartelef.springsecurity.controller;

import com.caglartelef.springsecurity.config.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class ApiController {

    @Autowired
    private ConfigurationService configurationService;

    @GetMapping(value = "/admin/login")
    public String adminLogin(){
        System.out.println(configurationService.getCurrentUserName());
        return configurationService.getCurrentUserName();
    }

    @GetMapping(value = "/secAdmin/login")
    public String secAdminLogin(){
        System.out.println(configurationService.getCurrentUserName());
        return configurationService.getCurrentUserName();
    }

}
