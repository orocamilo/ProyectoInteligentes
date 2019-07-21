var nodes = new vis.DataSet([
    {id: 1, label: 'Node 1'},
    {id: 2, label: 'Node 2'},
    {id: 3, label: 'Node 3'},
    {id: 4, label: 'Node 4'},
    {id: 5, label: 'Node 5'}
]);

// create an array with edges
var edges = new vis.DataSet([
    {from: 1, to: 3},
    {from: 1, to: 2},
    {from: 2, to: 4},
    {from: 2, to: 5},
    {from: 3, to: 3}
]);

function obtenerArbol(){
    $.get("/RegresionLineal/servletArbol",function(resp){
        var json = JSON.parse(resp);
        var secuencia = 2;
        var arbol = json.arbol;
        var raiz = json.raiz;
        var nodos = [{id: 1, label: raiz}];
        var aristas = [];
        var cola = [nodos[0]];
        while(cola.length > 0){
            var nodo = cola.pop();
            var hijos = arbol[nodo.label];
            for(var i in hijos){
                nodos.push({id:secuencia, label:i});
                aristas.push({from:nodo.id, to:secuencia});
                secuencia++;
                var nodito = {id:secuencia, label:arbol[nodo.label][i][1]};
                nodos.push(nodito);
                aristas.push({from:secuencia-1, to:secuencia});
                if(arbol[nodito.label] !== undefined){
                    cola.push(nodito);
                }
                secuencia++;
            }
        }
        console.log(arbol);
        graficar(aristas,nodos);
    });
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
                    direction: "UD",
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

obtenerArbol();