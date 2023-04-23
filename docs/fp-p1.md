# Práctica 1: Anónimos & Cierres

## Ejercicios propuestos

### Ejercicio 1

Dado los siguientes fragmentos de código que implementan un API dado por la interfaz `DataOperations`, responder a las siguientes preguntas:

#### `DataOperations.java`

```java
public interface DataOperations {
    public void print(int[] data);
    public int[] filterPairs(int[] data);
}
```

#### `DataOperationsImpl.java`

```java
public class DataOperationsImpl implements DataOperations {
    @Override
    public void print(int[] data) {
        for(int element: data) {
            System.out.print(element + ", ");
        }
        System.out.println();
    }

    @Override
    public int[] filterPairs(int[] data) {
        int index = 0;
        int[] dataAux = new int[data.length];
        for(int element: data) {
            if((element % 2) != 0) {
                dataAux[index] = element;
                index++;
            }
        }
        return dataAux;
    }
}
```

#### `Main.java`

```java
import java.util.Arrays;

public class Main {
    public static void main(String args[]) {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        System.out.println("data = " + Arrays.toString(data));
        DataOperations operations = new DataOperationsImpl();
        operations.print(data);
        data = operations.filterPairs(data);
        operations.print(data);
    }
}
```

#### Preguntas propuestas

1. Utilice expresiones *lambda* y el API de *streams* de Java para cambiar la implementación de las operaciones de la interfaz `DataOperations` usando los mecanismos de la programación funcional.

2. Además, haciendo uso de expresiones *labmda* y del API de *streams*, añada a la interfaz de `DataOperations` las siguientes operaciones y su implementación:

- Operación que devuelva la lista de números ordenada descendentemente.
- Operación que multiplique todos los números de la lista por 10 e imprima el resultado.
- Operación que devuelva el resultado de la suma de todos los números de la lista.

## Respuestas para preguntas propuestas 1
1) En la implementación de print(), se utiliza el método forEach() de la clase Stream para imprimir cada elemento del arreglo data. 
En la implementación de filterPairs(), se utiliza el método filter() de la clase Stream para filtrar los elementos pares y se convierte el resultado a un arreglo usando el método toArray().
#### `DataOperations.java`

```java
public class DataOperationsImpl implements DataOperations {
    @Override
    public void print(int[] data) {
        Arrays.stream(data).forEach(element -> System.out.print(element + ", "));
        System.out.println();
        }

    @Override
    public int[] filterPairs(int[] data) {
    return Arrays.stream(data).filter(element -> (element % 2) != 0).toArray();
    }
}
```

2)
#### `DataOperationsImpl.java`

```java
public interface DataOperations {
    public void print(int[] data);
    public int[] filterPairs(int[] data);
    public int[] sortDesc(int[] data);
    public void printMultiplied(int[] data);
    public int sum(int[] data);
}
```

```java
public class DataOperationsImpl implements DataOperations {
    @Override
    public void print(int[] data) {
        Arrays.stream(data).forEach(element -> System.out.print(element + ", "));
        System.out.println();
    }

    @Override
    public int[] filterPairs(int[] data) {
        return Arrays.stream(data).filter(element -> (element % 2) != 0).toArray();
    }

    @Override
    public int[] sortDesc(int[] data) {
        return Arrays.stream(data).boxed().sorted(Collections.reverseOrder()).mapToInt(Integer::intValue).toArray();
    }

    @Override
    public void printMultiplied(int[] data) {
        Arrays.stream(data).map(element -> element * 10).forEach(element -> System.out.print(element + ", "));
        System.out.println();
    }

    @Override
    public int sum(int[] data) {
        return Arrays.stream(data).sum();
    }
}
```

### Ejercicio 2

Dado los siguientes fragmentos de código que implementan un API cuya interfaz viene dada por `DataSorter`, responder a las siguientes preguntas.

#### `DataSorter.java`

```java
public interface DataSorter {
    public String[] sort(String[] data);
}
```

#### `DataSorterAsc.java`

```java
import java.util.Arrays;

public class DataSorterAsc implements DataSorter {
    public String[] sort(String[] data) {
        Arrays.sort(data);
        return data;
    }
}
```

#### `DataSorterDesc.java`

```java
import java.util.Arrays;
import java.util.Collections;

public class DataSorterDesc implements DataSorter {
    public String[] sort(String[] data) {
        Arrays.sort(data, Collections.reverseOrder());
        return data;
    }
}

```

#### `Main.java`

```java
import java.util.Arrays;

public class Main {
    public static void main(String args[]) {
        String [] data = {"H", "S", "I", "V", "E", "W", "M", "P", "L",  "C", "N", "K",
                 "O", "A", "Q", "R", "J", "D", "G", "T", "U", "X", "B", "Y", "Z", "F"};
        System.out.println("data = " + Arrays.toString(data));
        DataSorter dataSorter = new DataSorterDesc();
        dataSorter.sort(data);
        System.out.println("data (desc) = " + Arrays.toString(data));
        dataSorter = new DataSorterAsc();
        dataSorter.sort(data);
        System.out.println("data (asc) = " + Arrays.toString(data));
    }
}
```

#### Preguntas propuestas

1. Utilice cierres (*closures*) para cambiar la implementación de las clases `DataSorterAsc` y `DataSorterDesc` usando los mecanismos de la programación funcional.

2. Añada un tercer cambio haciendo uso de cierres (*closures*) para realizar la ordenación aleatoria de los elementos, siguiendo el mismo enfoque aplicado con las clases `DataSorterAsc` y `DataSorterDesc` en el apartado anterior.


### Respuestas para preguntas propuestas Ejercicio 2
1. Para cambiar la implementación de las clases `DataSorterAsc` y `DataSorterDesc` usando cierres (closures), se puede utilizar la interfaz funcional Comparator, que permite definir el orden de los elementos de una colección. La interfaz Comparator tiene un único método abstracto llamado `compare()`, que toma dos argumentos y devuelve un entero. Este entero indica el resultado de la comparación entre los dos elementos: si es negativo, el primer elemento es menor que el segundo; si es positivo, el primer elemento es mayor que el segundo; si es cero, ambos elementos son iguales.
Con esta idea, se puede definir un `Comparator` para ordenar de manera ascendente y otro para ordenar de manera descendente. Luego, se pueden utilizar estos comparadores en la implementación de las clases `DataSorterAsc` y `DataSorterDesc`. 
### `DataSorterAsc.java`
```java	
import java.util.Arrays;
import java.util.Comparator;

public class DataSorterAsc implements DataSorter {
    public String[] sort(String[] data) {
        Arrays.sort(data, Comparator.naturalOrder());
        return data;
    }
}
```	
### `DataSorterDesc.java`
```java
import java.util.Arrays;
import java.util.Comparator;

public class DataSorterDesc implements DataSorter {
    public String[] sort(String[] data) {
        Arrays.sort(data, Comparator.reverseOrder());
        return data;
    }
}
```
2. Para implementar una ordenación aleatoria de los elementos utilizando cierres (closures), se puede crear una nueva clase llamada DataSorterRandom que implemente la interfaz DataSorter. En su método sort(), en lugar de usar Arrays.sort() con un comparador específico como se hizo en las implementaciones anteriores, se puede hacer uso de un cierre que genere un número aleatorio para comparar los elementos.
### `DataSorterRandom.java`
```java
import java.util.Arrays;
import java.util.Random;

public class DataSorterRandom implements DataSorter {
    public String[] sort(String[] data) {
        Random random = new Random();
        Arrays.sort(data, (a, b) -> random.nextInt(3) - 1);
        return data;
    }
}
```
## Referencias

[Java 8 Stream Tutorial]: https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
[[1] Blog: Java 8 Stream Tutorial.][Java 8 Stream Tutorial]
[API Java Streams]: https://www.oracle.com/technetwork/es/articles/java/procesamiento-streams-java-se-8-2763402-esa.html
[[2] Documentación Oficial Java: Procesamiento de datos con streams de Java SE 8.][API Java Streams]
[API Java Funciones Lambda + Streams]: https://www.oracle.com/technetwork/es/articles/java/expresiones-lambda-api-stream-java-2737544-esa.html
[[3] Documentación Oficial Java: Introducción Expresiones Lambda y API Stream en Java SE 8.][API Java Funciones Lambda + Streams]
[Closures in Java]: https://medium.com/@rmanavalan/5-ways-to-implement-closures-in-java-8-590790659ac5
[[4] Blog: 5 ways to implement Closures in Java 8.][Closures in Java]