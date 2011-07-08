/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package restful.controller;

import actions.struts.BaseActionClass;
import com.opensymphony.xwork2.ModelDriven;
import hibernate.entities.Person;

import java.util.Collection;

import services.PersonService;

/**
 *
 * @author CodeHopper
 */

//public class RestfulFirstController extends BaseActionClass implements ModelDriven<Object>{
public class RestfulFirstController{

    private Person p=new Person();

    private Collection<Person> list;

    
    public Object getModel()
    {
        return (list != null ? list : p);
    }

//    public HttpHeaders show() {
//		return new DefaultHttpHeaders("show").disableCaching();
//	}
//    public HttpHeaders index(){
//        list=personService.getAll();
//        return new DefaultHttpHeaders("index").disableCaching();
//    }
    
    

}
