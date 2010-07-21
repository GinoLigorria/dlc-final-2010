
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="wayraWeb.Buscar"%>
<%@page import="Dominio.Documento"%>
<%@page import="java.util.ArrayList"%>
<% String s=request.getParameter("cadena");%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
    </head>
    <body>
        <h1 align="center" ><b><font color="red" size="15">W</font>
        <font color="blue" size="15">a</font>
        <font color="orange" size="15">y</font>
        <font color="green" size="15">r</font>
        <font color="black" size="15">a</font></b>
        <br>
        <font color="black" size="5"><i>Search</i></font></h1>
   <form action="buscar.jsp">
        <table align="center">
            <tr><td align="center"><input type="text" name="cadena" value="<%=s%>" align="left"></input></td></tr>
            <tr><td align="center"><input maxlength="500" type="submit" align="center" name="btnBuscar" value="Buscar"  </td></tr>
        </table>
    </form>
    <h2 align="left"><font size="5" color="blue"><i>Results</i></font></h2>
<table align="left">
<%
ArrayList a=new ArrayList<Documento>();
Buscar b =new Buscar();
a=b.hacerBusqueda(s);
for(int i=0;i<a.size();i++)
{
Documento doc=(Documento)a.get(i);
String st=doc.getRuta();
String breaf=doc.getBrief();
%>
<tr>
        
        <td><br><%=st.toString()%></br></td>
        </tr>
        <tr>
        <td><font color="#D0D0D0"> <i><%=breaf.toString()%></i></font> </td>

</tr>
<%
}
%>
<tr>

<td align="center"><br><br>
    <link rel="index" href="index.jsp" type="text" >
    <a href="index.jsp" type="text/html">Volver</a></link>
</td>

</tr>
</table>

  </body>
</html>



