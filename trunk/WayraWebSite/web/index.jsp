<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="Persistencia.Rutas"%>
<%%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>WayraSearch</title>
    </head>
    <body>
    <h1 align="center">
        <b><font color="red" size="15">W</font>
        <font color="blue" size="15">a</font>
        <font color="orange" size="15">y</font>
        <font color="green" size="15">r</font>
        <font color="black" size="15">a</font></b>
        <br>
        <font color="black" size="5"><i>Search</i></font></h1>
    <form action="buscar.jsp">
        <table align="center">
            <tr><td align="center"><input type="text" name="cadena" value="" align="left"></input></td></tr>
            <tr><td align="center"><input maxlength="500" type="submit" align="center" name="btnBuscar" value="Buscar"  </td></tr>
            
        </table>


    </form>
    
    
    
    
    </body>
</html>


