<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
    <head>
        <script src="http://code.jquery.com/jquery-latest.js"></script>
        <link rel="stylesheet" href="http://dev.jquery.com/view/trunk/plugins/treeview/jquery.treeview.css" type="text/css" media="screen" />
        <script type="text/javascript" src="http://dev.jquery.com/view/trunk/plugins/treeview/jquery.treeview.js"></script>
        <script>
            $(document).ready(function(){
                $("#browser").treeview();
                $("#add").click(function() {
                    var branches = $("<li><span class='folder'>New Sublist</span><ul>" + 
                        "<li><span class='file'>Item1</span></li>" + 
                        "<li><span class='file'>Item2</span></li>" +
                        "</ul></li>").appendTo("#browser");
                    $("#browser").treeview({
                        add: branches
                    });
                });
            });
        </script>


    </head>

    <body>
        <p>
    <s:form action="Home" id="">

        <s:submit />
    </s:form>

    <ul id="browser" class="filetree">
        <li><span class="folder">Folder 1</span>
            <ul>
                <li><span class="file">Item 1.1</span></li>
            </ul>
        </li>
        <li><span class="folder">Folder 2</span>
            <ul>
                <li><span class="folder">Subfolder 2.1</span>
                    <ul id="folder21">
                        <li><span class="file">File 2.1.1</span></li>
                        <li><span class="file">File 2.1.2</span></li>
                    </ul>
                </li>
                <li><span class="file">File 2.2</span></li>
            </ul>
        </li>
        <li class="closed"><span class="folder">Folder 3 (closed at start)</span>
            <ul>
                <li><span class="file">File 3.1</span></li>
            </ul>
        </li>
        <li><span class="file">File 4</span></li>
    </ul>


</p>
</body>
</html>
