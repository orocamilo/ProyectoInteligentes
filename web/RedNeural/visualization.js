var contador = 1;
var nodosEntrada = [];
var nodosIntermedio = [];
var nodosSalida = [];
var aristas = [];
var nodos = [];

function CrearRed(){
	var Nentrada = document.getElementById('Nentrada').value;
	var NporNivel = document.getElementById('NporNivel').value;
	var Nsalida = document.getElementById('Nsalida').value;	

	for(contador=1; contador<=Nentrada; contador++){
	nodosEntrada.push({id:contador, label:""+contador});
	nodos.push({id:contador, label:""+contador});	
	}
	for(var j=0; j<NporNivel; j++){
		nodosIntermedio.push({id:contador, label:""+contador});	
		nodos.push({id:contador, label:""+contador});	
		contador++;
	}
	for(var j=0; j<Nsalida; j++){
            
            nodosSalida.push({id:contador, label:""+contador});	
            nodos.push({id:contador, label:""+contador});	
            contador++;
	}
//aristas
	for(var j=0; j<nodosIntermedio.length; j++){
	conectar(nodosEntrada, j);
	conectar2(nodosSalida, j);	
	}
	graficar(aristas,nodos);
}

function conectar(nodos,j){
    for(var k=0; k<nodos.length; k++){
        var peso = Math.round(Math.random()*100)/100;
        aristas.push({from:nodos[k].id, to:nodosIntermedio[j].id, arrows:'to',font: {align: 'left'}});
    }
}
function conectar2(nodos,j){
    for(var k=0; k<nodos.length; k++){
        var peso = Math.round(Math.random()*100)/100;
        aristas.push({from:nodosIntermedio[j].id, to:nodos[k].id, arrows:'to',font: {align: 'left'}});
    }		
}


function graficar(edges, nodes){
    var container = document.getElementById('visualization');
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {
            layout: {
                hierarchical: {
                    direction: "LR",
                    sortMethod: "directed"
                }
            },
            interaction: {dragNodes :true},
            physics: {
                enabled: false
            },
            configure: {
              filter: function (option, path) {
                  if (path.indexOf('hierarchical') !== -1) {
                      return true;
                  }
                  return false;
              },
              showButton:false
            }
        };
    var network = new vis.Network(container, data, options);
}

function enviar(){
    var Nentrada = document.getElementById('Nentrada').value;
    var NporNivel = document.getElementById('NporNivel').value;
    var Nsalida = document.getElementById('Nsalida').value;
    var json = {
       
        servicio: "crear",
        entrada: Nentrada,
        oculta:NporNivel,
        salida: Nsalida
       
    };
    $.get("/RegresionLineal/servletRedNeural", json, function(resp){
        alert(resp);
    })
}

function enviar2(){
    var altoPetalo = document.getElementById("altoPetalo").value;
    var anchoPetalo = document.getElementById("anchoPetalo").value;
    var anchoSepalo = document.getElementById("anchoSepalo").value;
    var altoSepalo = document.getElementById("altoSepalo").value;
    var json = {
        servicio: "probar",
        dato1: altoSepalo,
        dato2: anchoSepalo,
        dato3: altoPetalo,
        dato4: anchoPetalo
    };
    $.get("/RegresionLineal/servletRedNeural", json, function(resp){
        
        var json = JSON.parse(resp);
        alert(json["pronostico"]);
        for (var i = 0; i < json["resul"].length; i++) {
            nodosSalida[i].label = json["resul"][i];
        }
    })
}