from tensorflow.python.keras.models import load_model
import numpy as np
import cv2

class prediccion():
    """
    Carga el modelo de la red neuronal de la ruta especificada
    """
    def __init__(self):
        self.rutaModelo="models/modeloReconocimientoNumeros.keras"
        self.model=load_model(self.rutaModelo)
        self.width=128
        self.heigth=128
        self.paisa=[0, 1, 2,3,4]
        self.paisaCafetero=[12,6,1,0]
        self.rolo=[9,6,4]
        self.americano=[5,7,0,8]
        self.americanoLigth=[5,15]

    def predecir(self,imagen):
        """
            Toma la imagen de entrada y realiza el proceso de prediccion
        """
        imagen=cv2.resize(imagen,(self.width,self.heigth))
        imagen=imagen.flatten()
        imagen=np.array(imagen)
        imagenNormalizada=imagen/255
        pruebas=[]
        pruebas.append(imagenNormalizada)
        imagenesAPredecir=np.array(pruebas)
        predicciones=self.model.predict(x=imagenesAPredecir)
        claseMayorValor=np.argmax(predicciones,axis=1)
        return claseMayorValor[0],predicciones

    def cualPlato(self,predicciones):
        """
            Toma la imagen de entrada y realiza el proceso de prediccion
        """
        tastes = [self.paisa, self.paisaCafetero, self.rolo, self.americano, self.americanoLigth]
        print(predicciones)
        for taste in tastes:
            actualIngredientId = 0
            for ingredient in taste:
                taste[actualIngredientId] = predicciones[ingredient]
                actualIngredientId += 1
        probailitiesResults = [
            np.sum(tastes[0]) / len(tastes[0]),
            np.sum(tastes[1]) / len(tastes[1]),
            np.sum(tastes[2]) / len(tastes[2]),
            np.sum(tastes[3]) / len(tastes[3]),
            np.sum(tastes[4]) / len(tastes[4])
        ]
        platoMayorValor=np.argmax(probailitiesResults)
        return platoMayorValor, tastes[platoMayorValor]
