
# Kata 2 - Repetición 2

Ingeniería del Software II - ULPGC

## Objetivo

Leer datos desde un InputStream, transformar cada fila TSV
en una entidad de dominio y generar un resumen mediante
OutputStream.

Esta repetición utiliza un recurso local como entrada
y memoria como destino de salida.

## Tecnologías

- Java 27
- Maven
- UTF-8

## Dataset

El archivo src/main/resources/employees.tsv contiene
25 empleados con nombre, departamento y sueldo mensual.

## Diseño

- Department: representa un departamento.
- Employee: representa un empleado inmutable.
- InputSource: contrato para abrir un origen de datos.
- ResourceInputSource: abre un recurso local.
- EmployeeLoader: contrato de carga de empleados.
- TsvEmployeeLoader: convierte filas TSV en objetos.
- DepartmentCounter: calcula la frecuencia por departamento.
- SummaryWriter: contrato de escritura.
- TextSummaryWriter: escribe el resumen en UTF-8.
- EmployeeReport: coordina carga, cálculo y escritura.
- Main: orquesta la aplicación.

El diseño aplica SRP, SoC, interfaces, composición
e inmutabilidad.

## Compilación

Se requiere JDK 27 y Maven.

    mvn clean compile

También se puede utilizar Maven > Lifecycle > compile
desde IntelliJ IDEA.

## Ejecución

Ejecutar software.ulpgc.katas.Main con este argumento:

    employees.tsv

También desde Maven:

    mvn -q compile exec:java -Dexec.args="employees.tsv"

## Resultado esperado

    Total de empleados: 25
    Development: 1
    R&D: 7
    Sales: 3
    Software Development: 13
    Training: 1

## Verificación

- Recurso existente: resumen de 25 empleados.
- Recurso inexistente: mensaje de error controlado.
- Salida escrita en ByteArrayOutputStream.

## Repeticiones

- repetition-1: entrada desde fichero y salida a consola.
- repetition-2: entrada desde recurso y salida en memoria.
- repetition-3: pendiente.

## Vídeo

El vídeo de máximo 7 minutos se añadirá al README
de la versión evaluable en main.
