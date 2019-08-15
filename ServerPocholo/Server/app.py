#!flask/bin/python

import base64
from prediccion import prediccion
import cv2

from flask import Flask
app = Flask(__name__)
from flask import Flask
from flask import request
app = Flask(__name__)
import json

@app.route('/postjson', methods=['POST'])
def post():
    print(request.is_json)
    content = request.get_json()
    #print(content)
    print(content['id'])
    print(content['name'])
    return 'JSON posted'

@app.route('/postimg', methods=['POST'])
def postImg():
    print(request.is_json)
    content = request.get_json()
    fh = open("test.jpg", "wb")
    fh.write(base64.b64decode(content['image']))
    fh.close()
    return 'JSON posted'
@app.route('/postquees', methods=['POST'])
def postQueEs():
    print(request.is_json)
    content = request.get_json()
    fh = open("test"+content['idImage']+".jpg", "wb")
    fh.write(base64.b64decode(content['image']))
    fh.close()
    categorias=["Huevos","Arepas","Mantequilla","Chocolate","Pan","Cereales","Cafe","Leche","Tocino","Changua","Tamal","Papas","Calentado","Yuca frita","Jugo naranaja","Yogurth","Pollo"]
    reconocimiento=prediccion()
    imagenPrueba=cv2.imread("test"+content['idImage']+".jpg",0)
    indiceCategoria,prediciones=reconocimiento.predecir(imagenPrueba)
    response = {
        'idImagen':content['idImage'],
        'prediccion': categorias[indiceCategoria],
        'probabilidades': str(prediciones[0])
    }
    python2json = json.dumps(response)
    return python2json
@app.route('/postcualplato', methods=['POST'])
def postCualPlato():
    print(request.is_json)
    content = request.get_json()
    fh = open("test"+content['idPrueba']+".jpg", "wb")
    fh.write(base64.b64decode(content['image']))
    fh.close()
    categorias=["Huevos","Arepas","Mantequilla","Chocolate","Pan","Cereales","Cafe","Leche","Tocino","Changua","Tamal","Papas","Calentado","Yuca frita","Jugo naranaja","Yogurth","Pollo"]
    reconocimiento=prediccion()
    imagenPrueba=cv2.imread("test"+content['idPrueba']+".jpg",0)
    indiceCategoria,prediciones=reconocimiento.predecir(imagenPrueba)
    categorias=["Paisa","Paisa Cafetero","Rolo","Americano","Americano Light"]
    platoMayorValor, probabilidades=reconocimiento.cualPlato(prediciones[0])
    response = {
        'idPrueba':content['idPrueba'],
        'prediccion': categorias[platoMayorValor],
        'probabilidades': str(probabilidades)
    }
    python2json = json.dumps(response)
    return python2json
app.run(host='127.0.0.1', port=5000)