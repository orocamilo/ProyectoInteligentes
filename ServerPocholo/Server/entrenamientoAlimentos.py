import matplotlib.pyplot as plt
import numpy as np
import math
import cv2

# Import the keras API
from tensorflow.python.keras.models import Sequential
from tensorflow.python.keras.layers import InputLayer, Input
from tensorflow.python.keras.layers import Reshape, MaxPooling2D
from tensorflow.python.keras.layers import Conv2D, Dense, Flatten
from tensorflow.python.keras.optimizers import Adam

# Cargar datos de ejemplo
import tensorflow as tf
from tensorflow.examples.tutorials.mnist import input_data
#from Keras.ImpresionImagenes import Impresion


def cargarDatos(fase, numeroCategorias, limite):
    imagenesCargadas = []
    etiquetas = []
    valorEsperado = []
    for categoria in range(0, numeroCategorias):
        for idImagen in range(1, limite + 1):
            ruta = fase+str(categoria)+"/"+str(categoria) + \
                "_4_"+str(idImagen)+".jpg"
            print(ruta)
            imagen = cv2.imread(ruta, 0)
            imagen = imagen.flatten()
            imagen = imagen/255
            imagenesCargadas.append(imagen)
            etiquetas.append(categoria)
            probabilidades = np.zeros(numeroCategorias)
            probabilidades[categoria] = 1
            valorEsperado.append(probabilidades)
    imagenesEntrenamiento = np.array(imagenesCargadas)
    etiquetasEntrenamiento = np.array(etiquetas)
    valoresEsperados = np.array(valorEsperado)
    return imagenesEntrenamiento, etiquetasEntrenamiento, valoresEsperados


img_size = 128
# Numero de neuronas de la cnn
img_size_flat = img_size*img_size
# Parametrizar la forma de imagenes
num_chanels = 1
# RGB, HSV -> num_chanels=3
img_shape = (img_size, img_size, num_chanels)
num_clases = 17
limiteImagenesPrueba = 40
imagenes, etiquetas, probabilidades = cargarDatos(
    "dataset/", num_clases, limiteImagenesPrueba)

model = Sequential()
# Capa entrada
model.add(InputLayer(input_shape=(img_size_flat,)))
# Reformar imagen
model.add(Reshape(img_shape))

# Capas convolucionales
model.add(Conv2D(kernel_size=3, strides=1, filters=32,
                 padding='same', activation='relu', name='capa_convolucion_1'))
model.add(MaxPooling2D(pool_size=2))

model.add(Conv2D(kernel_size=3, strides=1, filters=64,
                 padding='same', activation='relu', name='capa_convolucion_2'))
model.add(MaxPooling2D(pool_size=2))

model.add(Conv2D(kernel_size=3, strides=1, filters=64,
                 padding='same', activation='relu', name='capa_convolucion_3'))
model.add(MaxPooling2D(pool_size=2))

model.add(Conv2D(kernel_size=3, strides=1, filters=128,
                 padding='same', activation='relu', name='capa_convolucion_4'))
model.add(MaxPooling2D(pool_size=2))

# Aplanar imagen
model.add(Flatten())
# Capa densa
model.add(Dense(128, activation='relu'))


# Capa salida
model.add(Dense(num_clases, activation='softmax'))

# Compilacioon del modelo
optimizador = Adam(lr=1e-3)
model.compile(optimizer=optimizador,
              loss='categorical_crossentropy',
              metrics=['accuracy']
              )

limiteImagenesPrueba = 20
imagenesPrueba, etiquetasPrueba, probabilidadesPrueba = cargarDatos(
    "test/", num_clases, limiteImagenesPrueba)

# Entrenamiento del modelo
model.fit(
    x=imagenes,
    y=probabilidades,
    steps_per_epoch=500,
    epochs=1
)


resultados = model.evaluate(x=imagenesPrueba, y=probabilidadesPrueba)
print("{0}: {1:.2%}".format(model.metrics_names[1], resultados[1]))
# Carpeta y nombre del archivo como se almacenara el modelo
nombreArchivo = 'models/modeloReconocimientoNumeros.keras'
model.save(nombreArchivo)
model.summary()
