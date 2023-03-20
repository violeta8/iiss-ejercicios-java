# Práctica 2: Refactoring

## Refactoring

### Razones para refactorizar

Posibles razones para refactorizar código, según [Code Complete]:

- Código duplicado
- Una función es demasiado larga
- Un bucle es demasiado largo o está demasiado anidado (demasiada complejidad)
- Una clase tiene poca cohesión
- Una interfaz no proporciona un nivel consistente de abstracción
- Una lista de parámetros tiene demasiados parámetros
- Los cambios dentro de una clase tienden a afectar a otras clases
- Las jerarquías de herencia tienen que ser modificadas en paralelo (afecta a todas las subclases un mismo cambio)
- Los atributos que se utilizan por varias clases no están organizados en una clase (se repiten)
- Una función utiliza más características propuestas por otra clase que de su propia clase
- Un tipo de datos primitivo esta sobrecargado
- Una clase no tiene una finalidad clara (no aporta mucho)
- Un objeto intermediario no hace nada
- Una función tiene un nombre que no especifica de forma clara su objetivo
- Los atributos de una clase son públicos
- Una subclase sólo utiliza un porcentaje mínimo de las funciones que hereda
- Se utilizan comentarios para explicar código díficil de entender
- Se usan variables globales
- Una función contiene código que parece que será necesario algún día pero en la actualidad no se utiliza


#### Refactorización en el uso de datos

- Reemplazar un número utilizado directamente por una constante
- Renombrar una variable para darle un nombre más claro o explicativo
- Convertir una variable que se usa en múltiples sitios en múltiples variables de único uso
- Usar una variable local para propósitos locales en lugar de un parámetro de una función
- Convertir el uso de datos primitivos en el uso de una clase
- Convertir un array (de bajo nivel) en un objeto
- Encapsular una colección

#### Refactorización en la implementación de sentencias/instrucciones

- Descomponer una expresión lógica
- Convertir una expresión lógica compleja en una función lógica con un nombre correcto para su definición
- Unificar fragmentos de código duplicado en diferentes partes de una expresión condicional
- Eliminar el uso de `break` o `return` en lugar del uso de variables de control en los bucles
- Devolver lo más rápido posible la solución en lugar de asignar el valor a una variable dentro de instrucciones `if`-`else`
- Reemplazar condicionales (especialmente las sentencias `case`) por el uso de polimorfismo

#### Refactorización en la implementación de funciones

- Extraer una función desde código que se repite en varios lugares
- Convertir una función demasiado larga en una clase
- Sustituir un algoritmo complejo por uno simple
- Combinar funciones similares en una única
- Pasar el objeto completo en lugar de seleccionar algunos campos específicos (en el caso de que sean muchos)
- Pasar algunos campos específicos en lugar del objeto completo (en el caso de que sean pocos campos los utilizados).

#### Refactorización en la implementación de clases/interfaces

- Extraer código especializado en subclases
- Combinar código similar en superclases
- Mover una función a otra clase en la que tenga más coherencia
- Convertir una clase demasiado larga en dos
- Eliminar una clase sin utilidad
- Reemplazar herencia por composición (en el caso de que sea necesario)
- Reemplazar composición por herencia (en el caso de que sea necesario)
- Unificar una superclase y una subclase si su implementación es muy similar.

#### Refactorización a nivel de sistema

- Cambiar la asociación unidireccional de clases a bidireccional (en caso de que sea necesario)
- Cambiar la asociación bidireccional de clases a unidireccional (en caso de que sea necesario)
- Proveer de una factoria para crear los objetos en lugar de usar un constructor simple
- Reemplazar los códigos de error con excepciones (en caso de que sea necesario).

## Data streams

Un data stream representa una secuencia de elementos que soportan diferentes tipos de operaciones para realizar cálculos sobre ellos

### Operaciones

Las operaciones sobre un stream pueden ser intermediarias o terminales

  - Las operaciones __intermediarias__ devuelven un nuevo stream permitiendo encadenar múltiples operaciones intermediarias sin usar punto y coma
  - Las operaciones __terminales__ son nulas o devuelven un resultado de un tipo diferente, normalmente un valor agregado a partir de cómputos anteriores

---

### Ejemplo v0.1

```java
public class Main{
  public static void main(String []args){      
    List<String> myList =
      Arrays.asList("a1", "a2", "b1", "c2", "c1");
    myList
      .stream()
      .filter(s -> s.startsWith("c"))
      .map(String::toUpperCase)
      .sorted()
      .forEach(System.out::println);      
  }
}
```

---

### Interfaces funcionales

- Las operaciones que se aplican sobre un _stream_ aceptan algún parámetro en forma de interfaz funcional o expresión lambda

  - Una __interfaz funcional__ es un objeto cuyo tipo (clase) representa a una función ejecutable con un cierto número de parámetros (normalmente 0, 1 o 2)
  - Una __expresión lambda__ es una interfaz funcional anónima, que especifica el comportamiento de la operación, pero sin especificar formalmente su nombre y tipo de parámetros

- Las operaciones aplicadas no pueden modificar el _estado_ del stream original 

---

En el ejemplo anterior, se puede observar que:

- `filter`, `map` y `sorted` son operaciones intermediarias
- `forEach` es una operación terminal
- Ninguna de las operaciones modifica el estado de `myList` añadiendo o eliminando elementos
- Sólo se filtran ciertos elementos, se transforman a mayúsculas, se ordenan (por defecto, alfabéticamente) y se imprimen por pantalla

---

### Ejemplo v0.2

```java
    List<String> myList =
      Arrays.asList("a1", "a2", "b1", "c2", "c1");

    myList
      .stream()
      .filter(s -> s.startsWith("c"))
      .map(String::toUpperCase)
      .sorted()
      .forEach(System.out::println);
    
    myList
      .stream()
      .reduce( (a,b) -> a + " " + b )
      .ifPresent(System.out::println);
```

---

### Más información

- Winterbe: [Java 8 stream tutorial](https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/)
- Oracle: [Procesamiento de datos con streams de Java](https://www.oracle.com/lad/technical-resources/articles/java/processing-streams-java-se8.html) 
- Oracle: [Introducción a Expresiones Lambda y API Stream en Java](https://www.oracle.com/lad/technical-resources/articles/java/expresiones-lambda-api-stream-java-part2.html)

## Ejercicios propuestos

### Ejercicio 1

Dado los siguientes fragmentos de código, responder a las siguientes preguntas:

#### `GroupOfUsers.java`

```java
public class GroupOfUsers {
 
    private static Map<String, Integer> usersWithPoints =
      new HashMap<String, Integer>() {{
        put("User1", 800);
        put("User2", 550);
        put("User3", 20);
        put("User4", 300);
    }};
    
    public List<String> getUsers() {
        List<String> users = new ArrayList<String>();
        
        //Sorting users by points
        usersWithPoints.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .forEachOrdered(x -> users.add(x.getKey()));
        
        //Capitalizing the names of the users
        List<String> usersCapitalized = new ArrayList<String>();
        users.forEach(x -> usersCapitalized.add(x.toUpperCase()));
        
        return usersCapitalized;
 }

}
```

#### `Main.java`

```java
    ...
    GroupOfUsers group = new GroupOfUsers();
    List<String> users = group.getUsers();
    System.out.println("The users are: " + users);
    ...
```

#### Preguntas propuestas

En la siguiente lista se incluyen 10 posibles problemas que pueden encontrarse en el código de la implementación anterior:

- Código duplicado 
- Funciones con nombre que no especifica de forma clara su objetivo
- Rutinas demasiado largas
- Bucles demasiado largos o demasiado anidados
- Funciones con demasiada responsabilidad (no tienen asignada una única responsabilidad u operación a resolver)
- Lista de parámetros con demasiados parámetros
- Los cambios de una clase tienden a afectar a otras
- Se utilizan comentarios para explicar código díficil de entender
- Se usan variables globales
- Los cambios dentro de una clase tienden a afectar a otras clases

a) ¿Existe algún tipo de problema en la implementación anterior de los que se incluye en la lista anterior? ¿Es necesario aplicar refactoring en este caso? En el caso de que existan problemas, indique cuáles son y qué tipos de problemas piensa que generarían en el futuro si no se aplica el refactoring ahora.

Funciones con nombre que no especifica de forma clara su objetivo: el nombre de la función getUsers no especifica claramente qué es lo que hace la función. Sería mejor cambiar el nombre de la función a algo más claro, como getUsersSortedAndCapitalizedByPoints.


Funciones con demasiada responsabilidad: la función getUsers hace dos cosas distintas: ordenar los usuarios por puntos y luego capitalizar sus nombres. Sería mejor dividir esta función en dos funciones más pequeñas, cada una con una única responsabilidad.


Se usan variables globales: la variable usersWithPoints es una variable global, lo que puede dificultar la comprensión de cómo se utiliza en la función getUsers. Sería mejor pasar esta variable como parámetro a la función getUsers.


Los cambios dentro de una clase tienden a afectar a otras clases: si se quisiera cambiar la forma en que se ordenan los usuarios (por ejemplo, si se quisiera ordenarlos por orden alfabético en lugar de por puntos), esto afectaría a la función getUsers y posiblemente también a otras funciones que utilicen la variable usersWithPoints. Sería mejor encapsular la lógica de ordenamiento en una función separada para que los cambios no afecten a otras partes del código.


En general, estos problemas podrían dificultar el mantenimiento y la evolución del código en el futuro. Por lo tanto, sería recomendable aplicar refactoring para abordar estos problemas y hacer el código más fácil de entender y mantener.

b) En el caso de que la implementación necesite la aplicación de refactoring, realice los cambios oportunos e indique las mejoras que aporta su implementación respecto a la original.
```java
public class GroupOfUsers {
 
    public List<String> getUsersSortedAndCapitalizedByPoints(Map<String, Integer> usersWithPoints) {
        List<String> users = new ArrayList<String>();
        
        //Sorting users by points
        usersWithPoints.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .forEachOrdered(x -> users.add(x.getKey()));
        
        //Capitalizing the names of the users
        List<String> usersCapitalized = new ArrayList<String>();
        users.forEach(x -> usersCapitalized.add(x.toUpperCase()));
        
        return usersCapitalized;
    }
}
```
### Ejercicio 2

Dado los siguientes fragmentos de código, responder a las siguientes preguntas:

#### `GroupOfUsers.java`

```java
public class GroupOfUsers {
    
    private static Map<String, Integer> usersWithPoints_Group1 =
      new HashMap<String, Integer>() {{
        put("User1", 800);
        put("User2", 550);
        put("User3", 20);
        put("User4", 300);
    }};
    
    private static Map<String, Integer> usersWithPoints_Group2 =
      new HashMap<String, Integer>() {{
        put("User1", 10);
        put("User2", 990);
        put("User3", 760);
        put("User4", 230);
    }};
    
    private static Map<String, Integer> usersWithPoints_Group3 =
      new HashMap<String, Integer>() {{
        put("User1", 1000);
        put("User2", 200);
        put("User3", 5);
        put("User4", 780);
    }};
    
    public List<ArrayList<String>> getUsers() {
        List<String> users1 = new ArrayList<String>();
        List<String> users2 = new ArrayList<String>();
        List<String> users3 = new ArrayList<String>();
        List<ArrayList<String>> users = new ArrayList<ArrayList<String>>();
        
        //Sorting users by points
        usersWithPoints_Group1.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .forEachOrdered(x -> users1.add(x.getKey()));
        
        usersWithPoints_Group2.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .forEachOrdered(x -> users2.add(x.getKey()));
        
        usersWithPoints_Group3.entrySet()
        .stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .forEachOrdered(x -> users3.add(x.getKey()));
        
        //Capitalizing the names of the users
        List<String> usersCapitalized1 = new ArrayList<String>();
        List<String> usersCapitalized2 = new ArrayList<String>();
        List<String> usersCapitalized3 = new ArrayList<String>();
        
        users1.forEach(x -> usersCapitalized1.add(x.toUpperCase()));
        users2.forEach(x -> usersCapitalized2.add(x.toUpperCase()));
        users3.forEach(x -> usersCapitalized3.add(x.toUpperCase()));
        
        //Adding users to the main list
        users.add((ArrayList<String>)usersCapitalized1);
        users.add((ArrayList<String>)usersCapitalized2);
        users.add((ArrayList<String>)usersCapitalized3);
        
        return users;
    }

}
```

#### `Main.java`

```java
    ...
    GroupOfUsers group = new GroupOfUsers();
    List<ArrayList<String>> users = group.getUsers();
    System.out.println("The users are: " + users);
    ...
```

#### Preguntas propuestas

Responda a las siguientes cuestiones, teniendo en cuenta la lista de los 10 posibles problemas del ejercicio anterior

a) El software del ejercicio anterior ha evolucionado añadiendo nueva funcionalidad en su implementación. ¿Existe algún tipo de problema en esta versión de la implementación de los que se incluyen en la lista? ¿Es necesario aplicar refactoring en este caso? En el caso de que existan problemas, indique cuáles son y qué tipos de problemas piensa que generarían en el futuro si no se aplica el refactoring ahora.

En este código se repiten los mismos problemas que en el ejercicio anterior, y además se añade la duplicación de muchas líneas de código. Esto hace que el tiempo de ejecución y la memoria utilizada aumenten, y se multiplican por el número de líneas duplicadas. En resumen, se están cometiendo errores que pueden afectar negativamente al rendimiento y la escalabilidad del código.

b) En el caso de que la implementación necesite la aplicación de refactoring, realice los cambios oportunos e indique las mejoras que aporta su implementación respecto a la original.

En este caso, el código se encuentra en el archivo "PJava2E2.java". Se ha reducido el uso de memoria eliminando lo que no es necesario, y se ha creado una lista para almacenar los resultados de las funciones lambda. Además, todas las operaciones necesarias para obtener los resultados se han realizado en una sola función lambda para cada uno de los grupos existentes. En resumen, se ha mejorado la eficiencia y la organización del código.



## Referencias

[Code Complete]: https://www.amazon.es/Code-Complete-Practical-Costruction-Professional/dp/0735619670
\[1\] [Code Complete: A Practical Handbook of Software Construction, Second Edition.][Code Complete]