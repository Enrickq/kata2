
# Kata 2 - Entrada, salida y carga de datos

Ingeniería del Software II - ULPGC

## Objetivo

Leer datos mediante InputStream, convertir filas TSV
en entidades Employee, calcular frecuencias por
departamento y escribir un informe con OutputStream.

## Tecnologías

- Java 27
- Maven
- JUnit Jupiter 5
- UTF-8

## Dataset

El archivo `src/main/resources/employees.tsv`
contiene 25 empleados.

Columnas:

- name
- department
- monthly_income

## Diseño

El programa está separado en responsabilidades.

### Dominio

- `Employee`: empleado inmutable.
- `Department`: departamento.

### Entrada

- `InputSource`: contrato para abrir un origen.
- `FileInputSource`: entrada desde fichero.
- `ResourceInputSource`: entrada desde recurso local.
- `EmployeeLoader`: interfaz de carga.
- `TsvEmployeeLoader`: analiza TSV y crea empleados.

### Transformación

- `DepartmentCounter`: cuenta empleados por departamento.

### Salida

- `SummaryWriter`: contrato de escritura.
- `TextSummaryWriter`: escribe el informe en UTF-8.

### Orquestación

- `EmployeeReport`: coordina carga, cálculo y escritura.
- `Main`: conecta los adaptadores y streams.

Se aplican SRP, SoC, interfaces, composición e inmutabilidad.

## Requisitos

- JDK 27
- Maven

## Compilación y pruebas

Desde la raíz del repositorio:

    mvn clean verify

En IntelliJ IDEA:

Maven > Lifecycle > verify

## Ejecución

### Fichero a consola

    mvn -q compile exec:java -Dexec.args="file src/main/resources/employees.tsv console"

### Recurso local a consola

    mvn -q compile exec:java -Dexec.args="resource employees.tsv console"

### Fichero a fichero

    mvn -q compile exec:java -Dexec.args="file src/main/resources/employees.tsv resumen.txt"

También se puede ejecutar Main desde IntelliJ
mediante configuraciones Application con esos argumentos.

## Ejemplo de salida

    Total de empleados: 25
    Development: 1
    R&D: 7
    Sales: 3
    Software Development: 13
    Training: 1

## Repeticiones deliberadas

Se ha rehecho el mismo ejercicio tres veces.

### Repetición 1

- Rama: `repetition-1`
- Tag: `rep-01`
- Entrada: FileInputStream
- Salida: System.out

### Repetición 2

- Rama: `repetition-2`
- Tag: `rep-02`
- Entrada: recurso local
- Salida: ByteArrayOutputStream

### Repetición 3

- Rama: `repetition-3`
- Tag: `rep-03`
- Entrada: fichero y recurso local
- Salida: consola y fichero
- Pruebas automatizadas mediante JUnit

La versión final evaluable se encuentra en `main`.

## Verificación

La suite incluye 13 pruebas automatizadas.

Se comprueba:

- Equivalencia entre fichero y recurso.
- Escritura en fichero y en memoria.
- Fichero inexistente.
- Recurso inexistente.
- Entrada completamente vacía.
- Archivo con cabecera y sin empleados.
- Cabecera inválida.
- Fila mal formada.
- Sueldo no numérico.
- Independencia del orden de las filas.
- Conservación de caracteres UTF-8.
- Rechazo de empleados con sueldo mensual negativo

Las pruebas se ejecutan con:

    mvn clean verify

## Extensibilidad

Para admitir URLs se puede implementar otro InputSource.
Para cambiar el formato del informe se puede implementar
otro SummaryWriter.

La lógica de DepartmentCounter no necesita modificaciones.


## Vídeo explicativo

[Ver vídeo explicativo de la Kata 2 en YouTube](https://youtu.be/QZezq5mRbKw)

En el vídeo se explican:

- El objetivo de la kata y el dataset TSV de 25 empleados.
- La lectura mediante InputStream y la escritura mediante OutputStream.
- El diseño del dominio con objetos inmutables.
- La aplicación de SRP, SoC, interfaces y composición.
- La ejecución con diferentes orígenes y destinos.
- La verificación mediante 13 pruebas automatizadas con JUnit.
- La gestión de errores y la independencia del orden de las filas.
- Las tres repeticiones deliberadas y el historial Git.

