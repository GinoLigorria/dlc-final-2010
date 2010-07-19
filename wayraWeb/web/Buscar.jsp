<?xml version="1.0" encoding="UTF-8"?>
<!-- 
    Document   : Buscar
    Created on : 18/07/2010, 23:12:00
    Author     : Facu
-->
<jsp:root version="2.1" xmlns:f="http://java.sun.com/jsf/core" xmlns:ice="http://www.icesoft.com/icefaces/component" xmlns:h="http://java.sun.com/jsf/html" xmlns:jsp="http://java.sun.com/JSP/Page" xmlns:webuijsf="http://www.sun.com/webui/webuijsf">
    <jsp:directive.page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"/>
    <f:view>
        <html id="outputHtml1">
            <head id="outputHead1">
                <ice:outputStyle href="./resources/rime/rime.css" id="outputStyle1"/>
                <ice:outputStyle href="./xmlhttp/css/xp/xp.css" id="outputStyle2"/>
            </head>
            <body id="outputBody1" style="-rave-layout: grid">
                <ice:form id="form1" style="height: 444px">
                    <ice:outputLabel id="output2"
                                     style="color: rgb(0, 0, 0); font-family: 'Arial','Helvetica',sans-serif; font-size: 12px; font-weight: bold; left: 24px; top: 240px; position: absolute; width: 718px" value="Resultados WayraWeb"/>
                    <ice:dataTable footerClass="tableResult" headerClass="tableResult" headerClasses="tableResult" id="dt1" rowClasses="tableResult"
                    style="height: 26px; left: 14px; top: 254px; position: absolute" styleClass="tableResult" value="#{Buscar.documentos}" var="currentRow" width="806">
                        <ice:column>
                            <ice:graphicImage value="#{currentRow['ruta']}"/>
                            <f:facet name="header">
                                <ice:outputText value="Tipo"/>
                            </f:facet>
                        </ice:column>
                        <ice:column>
                            <ice:outputText value="#{currentRow['brief']}"/>
                            <f:facet name="header">
                                <ice:outputText value="ID Documento"/>
                            </f:facet>
                        </ice:column>
                        <ice:column>
                            <ice:outputLink target="_blank" value="#{currentRow['ruta']}" visible="true">
                                <ice:outputText value="#{currentRow['ruta']}"/>
                            </ice:outputLink>
                            <f:facet name="header">
                                <ice:outputText value="Path del Documento"/>
                            </f:facet>
                        </ice:column>
                        <ice:column>
                            <ice:outputText value="#{currentRow['nombre']}"/>
                            <f:facet name="header">
                                <ice:outputText value="Nombre"/>
                            </f:facet>
                        </ice:column>
                        <ice:column>
                            <ice:outputText value="#{currentRow['tamanio']}"/>
                            <f:facet name="header">
                                <ice:outputText value="Tamaño"/>
                            </f:facet>
                        </ice:column>
                        <ice:column>
                            <ice:outputText value="#{currentRow['peso']}"/>
                            <f:facet name="header">
                                <ice:outputText value="Peso"/>
                            </f:facet>
                        </ice:column>
                        <ice:dataPaginator fastStep="3" for="dt1" id="icePager" immediate="true" paginator="true" paginatorMaxPages="4">
                            <f:facet name="first">
                                <ice:graphicImage style="border:none;" title="First Page" url="./xmlhttp/css/xp/css-images/arrow-first.gif"/>
                            </f:facet>
                            <f:facet name="last">
                                <ice:graphicImage style="border:none;" title="Last Page" url="./xmlhttp/css/xp/css-images/arrow-last.gif"/>
                            </f:facet>
                            <f:facet name="previous">
                                <ice:graphicImage style="border:none;" title="Previous Page" url="./xmlhttp/css/xp/css-images/arrow-previous.gif"/>
                            </f:facet>
                            <f:facet name="next">
                                <ice:graphicImage style="border:none;" title="Next Page" url="./xmlhttp/css/xp/css-images/arrow-next.gif"/>
                            </f:facet>
                            <f:facet name="fastforward">
                                <ice:graphicImage style="border:none;" title="Fast Forward" url="./xmlhttp/css/xp/css-images/arrow-ff.gif"/>
                            </f:facet>
                            <f:facet name="fastrewind">
                                <ice:graphicImage style="border:none;" title="Fast Backwards" url="./xmlhttp/css/xp/css-images/arrow-fr.gif"/>
                            </f:facet>
                        </ice:dataPaginator>
                    </ice:dataTable>
                    <ice:outputLabel id="outputLabel1"
                                     style="font-family: 'Verdana','Arial','Helvetica',sans-serif; font-size: 18px; font-weight: bold; height: 42px; left: 192px; top: 48px; position: absolute; width: 502px"
                                     value="Wayra Web" visible="true"/>
                                     <ice:inputText id="inputText1" style="left: 94px; top: 142px; position: absolute; width: 456px" value="#{Buscar.cadenaDeBusqueda}"/>
                                     <ice:commandButton action="#{Buscar.hacerBusqueda}" id="button1" style="left: 576px; top: 144px; position: absolute; width: 120px" value="Buscar"/>
                </ice:form>
            </body>
        </html>
    </f:view>
    <webuijsf:form id="form1"/>
</jsp:root>
