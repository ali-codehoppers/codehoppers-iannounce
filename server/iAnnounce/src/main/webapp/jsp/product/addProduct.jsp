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
        <link rel="stylesheet" href="css/jquery.wysiwyg.css" type="text/css" />
        <script type="text/javascript" src="js/wysiwyg/jquery.wysiwyg.js"></script>        

        <script type="text/javascript">
            function populateSubCategories(id){
                $.ajax({
                    type:       "get",
                    url:        "getSubCategories",
                    data:       {categoryId: id},
                    success:    function(msg) {
                        var data = eval('('+msg+')');
                        var html = '<option value=0>--SubCategories--</option>';
                        var len = data.length;
                        for (var i = 0; i< len; i++) {
                            html += '<option value="' + data[i].id + '">' + data[i].name + '</option>';
                        }
                        $('#addProduct_subcategoryId').html(html);
                    }
                });
                return false;
            }
   
        </script>
        <script type="text/javascript">
            $(function()
            {
                $('#addProduct_features').wysiwyg();
                $('#addProduct_specifications').wysiwyg();                
            });
        </script>


        <title>Add New Product</title>
    </head>
    <body>
        <h1>Add New Product!</h1>
        <s:form action="addProduct" enctype="multipart/form-data" method="POST">
            <s:textfield name="name" label="Product name"/>
            <s:textarea name="description" label="Short Description"/>
            <s:textarea name="detailDescription" label="Detail Description"/>
            <s:select name="manufacturerId" list="manufacturers" listKey="id" listValue="name" headerKey="0" headerValue="--Manufacturers--" label="Select the manufacturer" />            
            <s:select name="categoryId" list="categorys" listKey="id" listValue="name" headerKey="0" headerValue="--Categories--" label="Select a category" onchange="populateSubCategories(this.value)"/>                        
            <s:select name="subcategoryId" headerKey="0" list="subcategorys" listKey="id" listValue="name" headerValue="--SubCategories--" label="Select a sub-category" />                                    
            <s:textarea name="features" label="Features" cssStyle="min-width:550px"/>                        
            <s:textarea name="specifications" label="Specifications" cssStyle="min-width:550px"/>                                    
            <s:file name="image" label="Image"></s:file>
            <s:submit value="Add Product" align="center"/>
        </s:form>
    </body>
</html>
