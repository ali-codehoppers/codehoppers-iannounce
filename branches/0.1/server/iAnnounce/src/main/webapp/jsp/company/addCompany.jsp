<%-- 
    Document   : addProduct
    Created on : Nov 4, 2011, 11:55:06 AM
    Author     : CodeHopper
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <script type="text/javascript" src="js/jstree/_lib/jquery.js"></script>

        <script type="text/javascript">
            function populateStates(id){
                if(id && id > 0)
                {
                    $.ajax({
                        type:       "get",
                        url:        "getStates",
                        data:       {countryId: id},
                        success:    function(msg) {
                            var data = eval('('+msg+')');
                            var html = '<option value=0>--State--</option>';
                            var len = data.length;
                            for (var i = 0; i< len; i++) {
                                html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                            }
                            $('#addCompany_stateId').html(html);
                        }
                    });
                }
                return false;
            }
   
        </script>

        <title>Add New Company</title>
    </head>
    <body>
        <h1>Add New Company!</h1>
        <s:form action="addCompany" method="POST">
            <s:textfield name="name" label="Name"/>
            <s:textarea name="address" label="Address"/>
            <s:textfield name="city" label="City"/>
            <s:select name="countryId" list="countries" listKey="id" listValue="name" headerKey="0" headerValue="--Country--" label="Select the country" onchange="populateStates(this.value)"/>                        
            <s:select name="stateId" list="states" listKey="id" listValue="name" headerKey="0" headerValue="--State--" label="Select the state" />            
            <s:textfield name="phoneNo" label="Phone Number"/>
            <s:textfield name="faxNo" label="Fax Number"/>
            <s:textfield name="zip" label="Zip"/>            
            <s:textfield name="webAddress" label="Web Address"/>            
            <s:select name="companyCategoryId" list="companyCategories" listKey="code" listValue="description" headerKey="0" headerValue="--Category--" label="Select the category" />                        
            
            
            <s:submit value="Add Company" align="center"/>
        </s:form>
    </body>
</html>
