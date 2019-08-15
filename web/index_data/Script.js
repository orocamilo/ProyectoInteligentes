function visualizarDataSet(e){
    e.preventDefault();
    $("#textarea").val(contenido);
}

function consultarDataSet(){
    var data = {
        operacion: "filenames"
    }
    $.get("/RegresionLineal/servletArchivo",data, function(resp){
        var array = JSON.parse(resp);
        $("#selectArchivo").html("");
        for (var i in array){
            $("#selectArchivo").append("<option>"+array[i]+"</option>");
        }
    });
}

function seleccionarArchivo(){
    var data = {
        operacion: "seleccion",
        archivo: $("#selectArchivo").val(),
        porcentaje: $("#inputModelPorc").val()
    };
    $.get("/RegresionLineal/servletArchivo",data, function(resp){
        alert(resp);
    });
}