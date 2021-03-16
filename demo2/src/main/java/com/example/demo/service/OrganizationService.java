package com.example.demo.service;
import com.example.demo.domain.Organization;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

public interface OrganizationService {
    List<Organization> SelectAllOrganization();
    ModelAndView Login(String account, String password);

    int RegisterNewOrganization(String orgname, String orgpassword, String orgmail, String orgheadpic);

    int DeleteOrganization(List<Organization> list);
}
