package com.example.demo.service;

import com.example.demo.domain.Administrator;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

public interface AdministratorService {

    List<Administrator> SelectAll();

    String AdminLogin(String account, String password, Model model, RedirectAttributes redirectAttributes);

    Administrator AdminRegister(Administrator operator,String adminName,Integer adminSecureLevel,String adminPassword);

    Administrator SelectAdminByName(String adminName);

    String DeleteAdministrator(Administrator operator, Administrator target);

    String UpdateAdministrator(Administrator operator,Administrator target);
}
