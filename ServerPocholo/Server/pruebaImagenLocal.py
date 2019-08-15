import cv2

from prediccion import prediccion

#categorias=["Huevos","Arepas",...]
categorias=["Huevos","Arepas","Mantequilla","Chocolate","Pan","Cereales","Cafe","Leche","Tocino","Changua","Tamal","Papas","Calentado","Yuca frita","Jugo naranaja","Yogurth","Pollo"]
reconocimiento=prediccion()
imagenPrueba=cv2.imread("test/7/7_4_19.jpg",0)
indiceCategoria=reconocimiento.predecir(imagenPrueba)
print("La imamgen cargada es ",categorias[indiceCategoria])
while True:
    cv2.imshow("imagen",imagenPrueba)
    k=cv2.waitKey(30) & 0xff
    if k==27:
        break
cv2.destroyAllWindows()
