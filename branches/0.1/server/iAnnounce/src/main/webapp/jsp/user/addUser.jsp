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
                            $('#addUser_stateId').html(html);
                        }
                    });
                }
                return false;
            }
   
        </script>

        <title>Add New User</title>
    </head>
    <body>
        <h1>Add New User!</h1>
        <s:form action="addUser" method="POST">
            <s:textfield name="name" label="Name"/>
            <s:textarea name="address" label="Address"/>
            <s:textfield name="city" label="City"/>
            <s:select name="countryId" list="countries" listKey="id" listValue="name" headerKey="0" headerValue="--Country--" label="Select the country" onchange="populateStates(this.value)"/>                        
            <s:select name="stateId" list="states" listKey="id" listValue="name" headerKey="0" headerValue="--State--" label="Select the state" />            
            <s:textfield name="contactNo" label="Contact # "/>
            <s:textfield name="contactNo2" label="Alternate Contact # "/>            
            <s:textfield name="email" label="Email "/>            
            <s:textfield name="userName" label="Username"/>                        
            <s:password name="password" label="Password"/>       
            <s:submit value="Add User" align="center"/>
        </s:form>
    </body>
</html>
