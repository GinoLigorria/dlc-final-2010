
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%@page import="wayraWeb.Buscar"%>
<%@page import="Dominio.Documento"%>
<%@page import="java.util.ArrayList"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultados</title>
    </head>
    <body>
        <h1 align="center">Results</h1>
    <form align="center" border="1" action="index.jsp" >
        <td>
<%
ArrayList a=new ArrayList<Documento>();
String s=request.getParameter("cadena");
Buscar b =new Buscar();
a=b.hacerBusqueda(s);
for(int i=0;i<a.size();i++)
{
Documento doc=(Documento)a.get(i);
String st=doc.getRuta();
%>


        <tr><%=st.toString()%></tr>


<%
}
%>
<tr><input type="submit" value="Volver"></input></tr>
</td>
</form>
  </body>
</html>
