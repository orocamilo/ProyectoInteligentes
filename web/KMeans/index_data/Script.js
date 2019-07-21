/**
 * Created by Camilo on 23/02/2018.
 */

var modelo;
var prueba;
var pronostico;
var it2=0;
var it1=0;
var limite = 10;
var contenido = "";

function solicitarDatos(){
    var k = document.getElementById('txtK').value ;
    $.get("/RegresionLineal/servletKMeans?k="+k,mostrarContenido);
}


function mostrarContenido(resp) {
    console.log(resp)
    var json = JSON.parse(resp);
    console.log(json);
    $("#division").html("");
    for(var key in json){
        var html= "<div class='panel-group' id='accordion' role='tablist' aria-multiselectable='true'>";
        html += crearPanel(key)+"</div>";
        $("#division").append(html);
        ponerEnTablaPrueba("#cuerpo"+key,json[key]);
    }
}

function crearTabla(id){
    var html = "<table class='table'>";
    html +=   "<thead>";
    html +=   "<tr>";
    html +=   "<th scope='col'>#</th>";
    html +=   "<th scope='col'>Ancho Sepalo</th>";
    html +=   "<th scope='col'>Alto Sepalo</th>";
    html +=   "<th scope='col'>Ancho Petalo</th>";
    html +=   "<th scope='col'>Alto Petalo</th>";
    html +=   "<th scope='col'>Clase</th>";
    html +=   "</tr>";
    html +=   "</thead>";
    html +=   "<tbody id='cuerpo"+id+"'>";
    html +=   "</tbody>";
    html += "</table>";
    return html;
}

function crearPanel(id){
    var html = "<div class='panel panel-default'>";
    html += "<div class='panel-heading' role='tab' id='heading"+id+"'>";
    html +=  "<h4 class='panel-title'>";
    html +=    "<a role='button' data-toggle='collapse' data-parent='#accordion' href='#collapse"+id+"' aria-expanded='false' aria-controls='collapseOne'>";
    html +=    "Grupo del centroide: "+ id;
    html +=    "</a>";
    html +=  "</h4>";
    html += "</div>";
    html += "<div id='collapse"+id+"' class='panel-collapse collapse' role='tabpanel' aria-labelledby='heading"+id+"'>";
    html +=  "<div class='panel-body'>";
    html += crearTabla(id);
    html +=  "</div>";
    html +="</div>";
    html +="</div>";
    return html;
}

function ponerEnTablaPrueba(id,array){
    var tabla = $(id);
    for (var i = 0; i < array.length; i++) {
        tabla.append("<tr>");
        tabla.append("<td>"+(i+1)+"</td>");
        tabla.append("<td>"+array[i].anchoSepalo+"</td>");
        tabla.append("<td>"+array[i].altoSepalo+"</td>");
        tabla.append("<td>"+array[i].anchoPetalo+"</td>");
        tabla.append("<td>"+array[i].altoPetalo+"</td>");
        tabla.append("<td>"+array[i].clase+"</td>");
        tabla.append("</tr>");
    }
}

function next(id){
    if(id === "#cuerpito"){
        if(it1 + limite < modelo.length){
            it1 += limite;
            ponerEnTablaPrueba(id,false,it1,modelo);
        }
    }else{
        if(it2 + limite < prueba.length){
            it2 += limite;
            ponerEnTablaPrueba(id,true,it2,prueba);
        }
    }
}

function previous(id){
    if(id === "#cuerpito"){
        if(it1 - limite >= 0){
            it1 -= limite;
            ponerEnTablaPrueba(id,false,it1,modelo);
        }
    }else{
        if(it2 - limite >= 0){
            it2 -= limite;
            ponerEnTablaPrueba(id,true,it2,prueba);
        }
    }
}