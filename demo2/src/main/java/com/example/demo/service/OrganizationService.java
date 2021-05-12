package com.example.demo.service;
import com.example.demo.domain.Organization;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface OrganizationService {
    List<Organization> SelectAllOrganization();
    String Login(String account, String password, HttpSession session, RedirectAttributes redirectAttributes);

//    int RegisterNewOrganization(String orgname, String orgpassword, String orgmail, String orgheadpic);

    int DeleteOrganization(List<Organization> list);

    Organization checkLegality(Long orgId);
}
