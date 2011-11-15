/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mt.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

/**
 *
 * @author CodeHopper
 */
public class Autherntication extends AbstractInterceptor
{
    @Override
    public String intercept(ActionInvocation ai)
    {
            return "";
    }
}
