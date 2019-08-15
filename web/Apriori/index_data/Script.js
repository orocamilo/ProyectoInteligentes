
function obtenerDataSet(){
    
    $.get("/RegresionLineal/servletApriori",{operacion: "dataset"}, function(resp){
        var json = JSON.parse(resp);
        for(var i in json){
            var celda1 = "<td>"+i+"</td>";
            var celda2 = "<td>"+json[i]+"</td>";
            $("#tablaTransacciones").append("<tr>"+celda1+celda2+"</tr>");
        }
    });
}

function calcularAsociaciones(){
    data = {
        operacion: "asociaciones",
        confianza: $("#inputConfianza").val(),
        soporte: $("#inputSoporte").val()
    };
    $.get("/RegresionLineal/servletApriori",data, function(resp){
        var lineas = resp.split("\n");
        for(var i in lineas){
            $("#asociaciones").append("<p>"+lineas[i]+"</p>");
        }
        
    });
}

