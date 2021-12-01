#MutantApp

Para desplegar este proyecto es necesario seguir los siguientes pasos:

1. Instalar Maven.
2. Instalar Java.
3. Clonar el repositorio en la carpeta deseada.
4. Ejecutar el comando `mvn install` (desde la carpeta raíz del proyecto).

### Desplegar desde una terminal de comandos

Para desplegar la aplicación usando la terminal de comandos se debe correr el siguiente comando:

`mvn spring-boot:run` (en la ubicación del archivo `pom.xml`)

### Desplegar usando un IDE

Si se desea desplegar la aplicación usando un IDE, es necesario habilitar el **procesamiento de anotaciones**.
- En IntelliJ, por ejemplo, la opción _Enable annotation processing_ debe estar activada.

Esto se debe hacer para que la librería de Lombok sea capaz de generar los getters y setters de los POJOs.

### Anotaciones importantes

- Se deben cambiar los valores presentes en el **application.properties** con los valores correctos para la conexión a la base de datos.
- La base de datos debe ser creada previamente antes de desplegar la aplicación. Para ello usar el comando (`CREATE DATABASE mutant_db;`).

### cURLs de ejemplo

Para validar que una cadena de ADN pertenece a un mutante o no y persistirlo en base de datos:

`curl --location --request POST 'http://localhost:8080/mutant' \
--header 'Content-Type: application/json' \
--data-raw '{
"dna": [
"ATGCGA",
"CAGTGC",
"TTATGT",
"AGAATG",
"CCCATA",
"CGGTTG"
]
}'`

Este request retornará:

- 200 OK: Si la cadena de adn pertenece a un mutante.
- 403 FORBIDDEN: Si la cadena de adn pertenece a un humano.
  

Para consultar las estadísticas sobre mutantes y humanos validados se puede usar el siguente request:

`curl --location --request GET 'http://localhost:8080/stats'`

Este request retornará un JSON de la forma:

`{"countMutantDna":2, "countHumanDna":3, "ratio":0.6666666666666666}`
