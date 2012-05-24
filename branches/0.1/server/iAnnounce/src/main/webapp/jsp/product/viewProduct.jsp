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
        <script type="text/javascript" src="js/jquery.js"></script>        
        <link rel="stylesheet" href="css/tabs.css" type="text/css" />        
        <style>
            #prodTitleCont {
                background-color: #003560;
                margin: 8px;
                padding:8px;                
                width: 750px;
                border: solid 1px #CCC;
                padding-bottom: 8px;
                color: #ffffff;
                font-size: 14pt;
                font-weight: bold;
            }


            #prodDetailCont
            {
                max-width: 750px;
                overflow: hidden;
                padding: 8px;
            }


            #prodImageCont
            {
                height: 200px;
                width: 200px;
                overflow: hidden;
                text-align: center;
                border: solid 1px #CCC;
            }


            #prodDesc
            {
                font-weight: bold!important;
                font-size: 1.1em!important;
                padding: 8px;
                color: #083152;
            }
            #prodLongDesc
            {
                padding: 8px;
                text-align:justify;
                font-size: 10pt!important;
            }

            .prodDescTitle
            {
                background-color: #CCC;
                padding: 8px;
                color: #083152;
                font-weight: bold!important;
                font-size: 9pt!important;
            }

            .prodDescContent
            {
                font-size: 9pt!important;
            }

        </style>

        <script>
	
            // When the document loads do everything inside here ...
            $(document).ready(function(){
		
                // When a link is clicked
                $("a.tab").click(function () {
			
			
                    // switch all tabs off
                    $(".active").removeClass("active");
			
                    // switch this tab on
                    $(this).addClass("active");
			
                    // slide all content up
                    $(".content").hide();
			
                    // slide this content up
                    var content_show = $(this).attr("title");
                    $("#"+content_show).show();
		  
                });
	
            });
        </script>

        <title>JSP Page</title>
    </head>
    <body>
        <div id="main" style="margin: auto; width: 80%;">        
            <div id="prodTitleCont">
                <s:property value="product.name" />
            </div>

            <div id="prodDetailCont" style="margin-top: 10px">
                <div id="prodImageCont" style="float: left">
                    <s:if test="product.images.get(0).id>0">
                        <img src="getImage?imageId=<s:property value="product.images.get(0).id" />"/>
                    </s:if>
                    <s:else>
                        <img src='images/default.png'/>
                    </s:else>
                </div>

                <div id="prodDescCont" style="float: left">
                    <div id="prodDesc">
                        <s:property value="product.description"/><br>
                    </div>

                    <div id="prodLongDesc" style="max-width: 530px">
                        <s:property value="product.detailDescription"/><br>
                    </div>
                </div>
            </div>

            <div style="clear: both">
            </div>

            <div class="tabbed_area" style="max-width: 740px; margin: 8px">


                <ul class="tabs">
                    <li><a href="#" title="content_prodInfo" class="tab active">Product Information</a></li>
                    <li><a href="#" title="content_2" class="tab">Parts & Pricing</a></li>
                    <li><a href="#" title="content_3" class="tab">Related Documents</a></li>
                    <li><a href="#" title="content_3" class="tab">Customer Reviews</a></li>
                    <li><a href="#" title="content_3" class="tab">Products Q&A</a></li>


                </ul>


                <div id="content_prodInfo" class="content" style="display: block; ">
                    <div class="prodDescTitle">
                        Features 
                    </div>
                    <div style="padding: 8px" class="prodDescContent">
                        <s:property value="product.features" escape="false"/>
                    </div>

                    <div class="prodDescTitle">
                        Specifications
                    </div>
                    <div style="padding: 8px" class="prodDescContent">
                        <s:property value="product.specifications" escape="false"/>
                    </div>

                </div>
                <div id="content_2" class="content" style="display: none; ">
                </div>
                <div id="content_3" class="content" style="display: none; ">
                </div>

            </div>

<!--        Name: <s:property value="product.name" /><br>
        Description: <s:property value="product.description"/><br>
        Manufacturer: <s:property value="product.manufacturer.name" /><br>
        Category: <s:property value="product.subcategory.category.name" /><br>        
        Sub Category: <s:property value="product.subcategory.name" /><br>                
        Features: <s:property value="product.features" /><br>
        Specifications: <s:property value="product.specifications" /><br>
<!--       Image:<img src="getImage?image_id=<s:property value="product.images.get(0).id" />"/>

            -->

        </div>
    </body>
</html>
