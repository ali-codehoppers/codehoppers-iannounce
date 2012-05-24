package com.mt.actions.ajaxActions;

import com.google.gson.GsonBuilder;
import com.mt.hibernate.entities.State;
import com.mt.services.CountryService;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;

public class GetStates extends ActionSupport
{
    private int countryId;
    private List<State> states;
    private CountryService countryService;
    private String jsonString="";    

    public void setCountryService(CountryService countryService)
    {
        this.countryService = countryService;
    }

    public void setCountryId(int countryId)
    {
        this.countryId = countryId;
    }

    @Override
    public String execute() throws Exception
    {
        states = countryService.getById(countryId).getStates();
        jsonString = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create().toJson(states);
        return SUCCESS;
    }

    public String getJsonString()
    {
        return jsonString;
    }
    
}