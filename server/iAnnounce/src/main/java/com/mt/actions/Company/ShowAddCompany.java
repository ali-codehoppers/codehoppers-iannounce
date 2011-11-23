package com.mt.actions.Company;

import com.mt.hibernate.entities.Country;
import com.mt.hibernate.entities.LookUp;
import com.mt.hibernate.entities.State;
import com.mt.services.CountryService;
import com.mt.services.LookUpService;
import com.opensymphony.xwork2.ActionSupport;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShowAddCompany extends ActionSupport {

    private List<Country> countries;
    private List<LookUp> companyCategories;
    private CountryService countryService;
    private LookUpService lookUpService;

    public void setCountryService(CountryService countryService) {
        this.countryService = countryService;
    }

    public void setLookUpService(LookUpService lookUpService) {
        this.lookUpService = lookUpService;
    }

    @Override
    public String execute() throws Exception {

        countries = countryService.getAll();
        companyCategories = lookUpService.findByName("COMPANY_CATEGORY");
        return SUCCESS;
    }

    public List<Country> getCountries() {
        return countries;
    }

    public List<LookUp> getCompanyCategories() {
        return companyCategories;
    }
    
    public List<State> getStates() {
        return new ArrayList<State>();
    }
}
