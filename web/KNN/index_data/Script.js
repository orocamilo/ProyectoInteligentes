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
var knearest;

function leerArchivo(e) {
    var archivo = e.target.files[0];
    if (!archivo) {
        return;
    }
    var lector = new FileReader();
    lector.onload = function (e) {
        contenido = e.target.result;
    };
    lector.readAsText(archivo);
}

function enviarDatos(){
    var k = document.getElementById("txtK").value;
    $.post("/RegresionLineal/servletKNN",{k:k},mostrarContenido);
    contenido = "";
}


function mostrarContenido(resp) {
    var json= JSON.parse(resp);
    console.log(json);
    modelo = json.modelo;
    prueba = json.prueba;
    pronostico = json.pronostico;
    knearest = json.knearest;
    it2=0;
    it1=0;
    document.getElementById("lblPorcentaje").innerHTML = "Porcentaje de Acierto: " + json.porcentaje +"%";
    ponerEncabezadoTabla("#encabezadoTabla", false, modelo[0]);
    ponerEncabezadoTabla("#encabezadoTabla2", true, prueba[0]);
    ponerEnTablaPrueba("#cuerpito",false,it1,modelo);
    ponerEnTablaPrueba("#cuerpito2",true,it2,prueba);
    
}

function ponerEncabezadoTabla(idTabla, esPrueba, registro){
    var tabla = $(idTabla);
    tabla.html("");
    tabla.append("<th scope='col'>#</th>");
    for(var atributo in registro){
        if(atributo !== "#clase" && atributo !== registro["#clase"])
            tabla.append("<th scope='col'>"+atributo+"</th>");
    }
    tabla.append("<th scope='col'>"+registro["#clase"]+"</th>");
    if(esPrueba){
        tabla.append("<th scope='col'>Pronóstico</th>");
        tabla.append("<th scope='col'>K Nearest Neighbors</th>");
    }
}

function ponerEnTablaPrueba(id,bandera,inicio,array){
    var tabla = $(id);
    tabla.html("");
    for (var i = inicio; i < inicio+limite && i < array.length; i++) {
        tabla.append("<tr>");
        tabla.append("<td>"+(i+1)+"</td>");
        for (var atributo in array[i]) {
            if(atributo !== "#clase" && atributo !== array[i]["#clase"])
                tabla.append("<td>"+array[i][atributo]+"</td>");
          
        }
        tabla.append("<td>"+array[i][array[i]["#clase"]]+"</td>");
        
        if(bandera){
            tabla.append("<td>"+pronostico[i]+"</td>");
            tabla.append("<td>"+"<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#exampleModalScrollable' onclick='mostrarKNearest("+i+")'>Ver</button>"+"</td>");
        }
        tabla.append("</tr>");
    }
}

function mostrarKNearest(i){
    ponerEncabezadoTabla("#encabezadoTabla3", false, knearest[0][0]);
    ponerEnTablaPrueba("#cuerpito3",false,0,knearest[i]);
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


function enviarArchivo(e){
    e.preventDefault();
    var data = new FormData($("#formArchivo")[0]);
    
    $.ajax({
        url: "/RegresionLineal/servletArchivo",
        type: 'POST',
        data: data,
        contentType: false,
        processData: false,
        success: function(resp){
            alert("enviado");
        },
        error: function(){
            alert("falló");
        }
    });
}


