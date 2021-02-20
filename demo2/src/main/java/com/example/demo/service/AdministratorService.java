package com.example.demo.service;

import com.example.demo.domain.Administrator;

import java.util.List;

public interface AdministratorService {

    List<Administrator> SelectAll();

    Administrator AdminLogin(String account, String password);

    Administrator AdminRegister(Administrator operator,String adminName,Integer adminSecureLevel,String adminPassword);

    Administrator SelectAdminByName(String adminName);

    String DeleteAdministrator(Administrator operator, Administrator target);

    String UpdateAdministrator(Administrator operator,Administrator target);
}
