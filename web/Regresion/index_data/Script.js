/**
 * Created by Camilo on 23/02/2018.
 */
var num = 1;
var X;
var Y;
function agregar_fila(fila) {
    //var html = $("#cuerpito").html();
    if (num === fila) {
        num++;
        var html = "<tr><th scope='row'>" + num + "</th>";
        html += "<td class='form-group'><input id='X" + num + "' class='form-control Xi' type='tel' placeholder='Xi' oninput='agregar_fila(" + num + ")' onkeypress='return justNumbers(event);'></td>";
        html += "<td class='form-group'><input id='Y" + num + "' class='form-control Yi' type='tel' placeholder='Yi' oninput='agregar_fila(" + num + ")' onkeypress='return justNumbers(event);'></td>";
        html += "<td class='xy'>&blank;</td>";
        html += "<td class='x2'>&blank;</td>";
        html += "<td class='y2'>&blank;</td>";
        html += "<td class='pronostico'>&blank;</td>";
        html += "<td class='ErrorAbs'>&blank;</td>";
        html += "<td class='SumaErrorAbs'>&blank;</td>";
        html += "<td class='mad'>&blank;</td>";
        html += "<td class='ErrorNor'>&blank;</td>";
        html += "<td class='SumaErrorNor'>&blank;</td>";
        html += "<td class='ts'>&blank;</td>";
        $("#cuerpito").append(html);
    }
}

function ponerValores(resp) {
    var json = JSON.parse(resp);
    for (var key in json) {
        var array = $("." + key)
        for (var i in json[key]) {
            array[i].innerHTML = json[key][i];
        }
    }
    graficar(X, Y, json["pronostico"]);
}

function calcular() {
    var elementsX = document.getElementsByClassName("Xi");
    var elementsY = document.getElementsByClassName("Yi");
    X = [];
    Y = [];
    getValues(elementsX, X);
    getValues(elementsY, Y);
    var json = {
        x: X,
        y: Y
    };
    $.get("/RegresionLineal/servletRegresionLineal", JSON.stringify(json), ponerValores);
}

function getValues(elements, array) {
    for (var i in elements) {
        if (elements[i].value == "" || elements[i].value == null)
            break;
        array.push(elements[i].value);
    }
}

function leerArchivo(e) {
    var archivo = e.target.files[0];
    if (!archivo) {
        return;
    }
    var lector = new FileReader();
    lector.onload = function (e) {
        var contenido = e.target.result;
        mostrarContenido(contenido);
    };
    lector.readAsText(archivo);
}

function mostrarContenido(contenido) {
    var filas = contenido.split("\n");
    for (var i = 1; i <= filas.length; i++) {
        var xi = document.getElementById("X" + i);
        var yi = document.getElementById("Y" + i);
        var fila = filas[i - 1].split(/[\s;]/);
        if (fila[0] != "" && fila[1] != undefined) {
            xi.value = fila[0];
            yi.value = fila[1];
            agregar_fila(i);
        }
    }
}

function justNumbers(e){
    var keynum = window.event ? window.event.keyCode : e.which;
    if ((keynum == 8) || (keynum == 46))
        return true;

    return /\d/.test(String.fromCharCode(keynum));
}

function mostrar(){
    $.get("/RegresionLineal/servletRegresionLineal")
}


