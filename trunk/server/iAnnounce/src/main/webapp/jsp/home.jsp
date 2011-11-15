<%-- 
    Document   : home
    Created on : Nov 2, 2011, 12:45:08 PM
    Author     : CodeHopper
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <script type="text/javascript" src="js/jstree/_lib/jquery.js"></script>
        <script type="text/javascript" src="js/jstree/jquery.jstree.js"></script>

        <style>
            #sideBar {
                background-color: #fff;
                padding: 8px;
                width: 250px;
                border: solid 1px #CCC;
                padding-bottom: 10px;

            }

            #catTitle {
                padding: 5px;
                text-align: center;
                color: black;
                font-weight: bold;
                font-size: 18px;
                background-color: #ccc;
            }

            #tree {
                padding: 8px;
            }

            .product
            {
                background-color: #fff;
                margin: 8px 8px 8px 8px;
                width: 220px;
                border: solid 1px #DEDEDE;
                float: left;
            }
            .productImage
            {
                height: 220px;
                width: auto;
                overflow: hidden;
                text-align: center;
            }

            .productDetail
            {
                height:90px;
                width:auto;
                border-top:solid 1px #dedede;
                padding:5px 5px 5px 5px;
                overflow:hidden;
                color:#083152;
            }
            .productDetail a:link, .productDetail a:active, .productDetail a:visited 
            {
                color: #083152;
                font-size: 15px;
                line-height: 18px;
                font-weight: bold;
                text-decoration: none;
            }

            .productDesc
            {
                color: #4D4D4D;
                margin-top: 5px;
                font-size: 12px;
                line-height: 14px;
            }

        </style>


        <script type="text/javascript">
            $(document).ready(function () {
                var str = eval('('+${treeJsonString}+')');
                $("#tree").jstree({
                    "themes" : {
                        "theme" : "apple",
                        "dots" : true,
                        "icons" : false
                    },
                    "plugins" : [ "themes", "json_data", "search", "adv_search", "ui", "contextmenu" ],
                    "search" : {
                        case_insensitive : true,
                        show_only_matches : true,
                        search_method : "contains"
                    },                    
                    "json_data":{
                        "data"  : str
                    },
                    callback: {
                        onchange: function(NODE, TREE_OBJ) {
                            $("#hidden_field").val(NODE.id);
                            alert(NODE.id);
                        }
                    }
                });
                $("#tree").bind('select_node.jstree', function (event, data) {
                    //var selectedNode = $.data(data.rslt.obj[0], "jstree");
                    if(data.rslt.obj[0].type=='category')
                        populateCategoryProduct(data.rslt.obj[0].id);
                    else
                        populateSubCategoryProduct(data.rslt.obj[0].id);
                });

  
                //                $("#tree").jstree("set_theme","apple");
            });

  
            function populateCategoryProduct(id)
            {
                $.ajax({
                    type:       "get",
                    url:        "getCategoryProducts",
                    data:       {categoryId: id},
                    success:    function(msg) {
                       
                        var data = eval('('+msg+')');
                        var html = '';
                        var len = data.length;
                        for (var i = 0; i< len; i++) {
                            html += getProductHtml(data[i]);
                        }
                        $("#products").html(html);
                    }
                });
                return false;
                
            }

            function populateSubCategoryProduct(id)
            {
                $.ajax({
                    type:       "get",
                    url:        "getSubCategoryProducts",
                    data:       {subcategoryId: id},
                    success:    function(msg) {
                       
                        var data = eval('('+msg+')');
                        var html = '';
                        var len = data.length;
                        for (var i = 0; i< len; i++) {
                            html += getProductHtml(data[i]);
                        }
                        $("#products").html(html);
                    }
                });
                return false;
                
            }
            

            function getProductHtml(product)
            {
                var html="<div class='product'>";
                html+="<div class='productLogo'></div>";
                html+="<div class='productImage'>";
                if(product.images.length>0)
                {
                    html+="<img src='getImage?imageId="+product.images[0].id+"'/>";
                }
                else
                    html+="<img src='images/default.png'/>";
                html+="</div>";
                html+="<div class='productDetail'>";
                html+="<a href='viewProduct?productId="+product.id+"'>"+product.name+" </a>";
                html+="    <br>";
                html+="<div class='productDesc'>"+ product.description+" </div>";
                html+="</div>";
                html+="</div>"
                return html;
            }
        </script>


    </head>
    <body>
        <div id="main" style="margin: auto; width: 80%;">
            <h1>Mexam-Buy&Sell</h1>

            <div id="sideBar" style="float: left; min-height: 400px; width: 25%;">
                <div id="catTitle">
                    Product Categories
                </div>
                <div id='tree'>
                </div>
            </div>


            <div id="container" style="float: left; margin-left: 15px; min-width: 750px; max-width: 750px; float:left; border: solid 1px #CCC; padding-bottom: 10px; min-height: 400px;" >
                <div id="catTitle">
                    Products
                </div>
                <div id='products'>
                    <div class='product'>
                        <div class='productLogo'></div>
                        <div class='productImage'>
                            <img src='images/default.png'/>
                        </div>
                        <div class='productDetail'>
                            <a href=""> WLC Series </a>
                            <br>
                            <div class="productDesc"> Waterline Controls Electronic Water Level Control System </div>
                        </div>
                    </div>
                </div>          

            </div>
        </div>
    </body>
</html>
