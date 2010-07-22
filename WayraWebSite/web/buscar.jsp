
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
    <h3 align="left"><font size="5" color="blue"><i>Results</i></font></h3>
<table align="left">
<%
long inicio = System.currentTimeMillis();
ArrayList a=new ArrayList<Documento>();
Buscar b =new Buscar();
a=b.hacerBusqueda(s);
int count=0;
StringBuffer url=request.getRequestURL();
String url1=url.toString();
url1=url1.substring(0,url1.lastIndexOf('/')+1);
for(int i=0;i<a.size();i++)
{
Documento doc=(Documento)a.get(i);
String st=doc.getRuta();
String aux=st;
st=st.substring(st.lastIndexOf('/')+1, st.length());
String breaf=doc.getBrief();
%>
<tr>



        <td><br><a href="<%=aux.toString()%>"><%=st.toString()%></a></br></td>
        </tr>
        <tr>
        <td><font color="black"> <i><%=breaf.toString()%></i></font> </td>

</tr>
<%
count++;
}
long fin = (System.currentTimeMillis() - inicio)/1000;
%>

<H2><font size="2" color="black"><i>Cantidad de resultados: <%=String.valueOf(count)%> - Tiempo transcurrido aprox. <%=String.valueOf(fin)%> Seg.</i></font></H2>
<!--H4><font size="2" color="black"><i>Tiempo transcurrido aprox. %=String.valueOf(fin)%> Seg.</i></font></H4!-->
<tr>
<td align="center"><br><br>
    <a href="index.jsp" type="text/html">Volver</a>
</td>
</tr>
</table>
<applet  code=Indizador.class codebase="C:\Proyecto DLC 2010\WayraWebSite\build\web\WEB-INF\classes\wayraWeb\" height="500" width="500"></applet>
  </body>
</html>



