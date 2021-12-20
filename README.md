# Test Global Logic para BCI

_Prueba para demostrar conocimientos sobre Java y Sprint Boot_

### Pre-requisitos 📋

_Clonar el repositorio, esperar que se descarguen las dependencias y correr la aplicación; Las pruebas se pueden realizar con postman_

```
 Post localhost:8080/api/users/sign-up

en el Boddy ejemplo:
{  
  "password": "Francisco18",
  "name": "Francisco",
  "email":"francisco@gmail.com",
  "phones":[{
      "number" : 45137583,
      "cityCode" : 11,
      "countryCode": "capital" 
  }]
}

Para login:
localhost:8080/api/users/login
En el Header poner el token obtenido por la creación del Usuario (KEY= Authorization Value = token)

en el body ejemplo:
{  
  "password": "Francisco18", 
  "email":"francisco@gmail.com" 
}

```
## Informacion Relevante 🛠️

Los diagramas se Encuentran dentro de la carpeta resources/Diagram



---
⌨️ con ❤️ por [@IngFrancisco](https://github.com/ingfrancisco1988) 😊
