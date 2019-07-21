/**
 * Created by Camilo on 23/02/2018.
 */
var num = 1;
function agregar_fila(fila) {
    //var html = $("#cuerpito").html();
    if(num === fila){
        num++;
        var html = "<tr><th scope='row'>"+num+"</th>";
        html +="<td class='form-group'><input class='form-control Xi' type='tel' placeholder='Xi' oninput='agregar_fila("+num+")'></td>";
        html += "<td class='form-group'><input class='form-control Yi' type='tel' placeholder='Yi' oninput='agregar_fila("+num+")'></td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td>";
        html += "<td>&blank;</td></tr>";
        $("#cuerpito").append(html);
    }
}

function calcular() {
    var elementsX = document.getElementsByClassName("Xi");
    var elementsY = document.getElementsByClassName("Yi");
    var X=[];
    var Y=[];
    getValues(elementsX,X);
    getValues(elementsY,Y);
    var json = {
        x:X,
        y:Y
    };
    alert(JSON.stringify(json));
}

function getValues(elements, array) {
    for (var i in elements){
        if(elements[i].value == "" || elements[i] == null)
            break;
        array.push(elements[i].value);
    }
}