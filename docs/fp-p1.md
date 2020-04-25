# Práctica 1: Anónimos & Cierres

## <span style="color:blue">Repaso de conceptos teóricos</span>

## Anónimos (Expresiones Lambda)

Una expresión *Lambda* es una función anónima o método abstracto, es decir, un método que sólo esta definido en la interfaz pero no se le ha dado ninguna implementación. Por este motivo, el programador puede aportarle la implementación que le resulte de interés en cada ocasión.

> In computer programming, an anonymous function (function literal, lambda abstraction, or lambda expression) is a function definition that is not bound to an identifier. Anonymous functions are often arguments being passed to higher-order functions, or used for constructing the result of a higher-order function that needs to return a function.[1] If the function is only used once, or a limited number of times, an anonymous function may be syntactically lighter than using a named function. Anonymous functions are ubiquitous in functional programming languages and other languages with first-class functions, where they fulfill the same role for the function type as literals do for other data types.
> 
> -- <cite>[Wikipedia](https://en.wikipedia.org/wiki/Anonymous_function)</cite>


A continuación, se incluye un ejemplo del recorrido de una lista con la sintaxis de POO tradicional:

```java
for (Integer numero : Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)) {
	System.out.print(numero + " ");
}
```
Por otro lado, a continuación se incluye la misma implementación, pero utilizando expresiones *Lambda*:

```java
//Primera versión
Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(n -> System.out.print(n + " "));
 
//Segunda versión
Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).forEach(System.out::println);
```

En este caso, la función *forEach* acepta expresiones *Lambda* para poder configurar su comportamiento. En la primera versión, se imprimirá cada elemento (n) de la lista y posteriormente se añadirá un espacio. Por lo tanto, la salida del programa en consola sería:

```java
1 2 3 4 5 6 7 8 9 10
```
Por otro lado, en la segunda versión, se imprimirá cada elemento de la lista y se añadirá un salto de línea posteriormente. Por lo tanto, la salida del programa en este caso sería:

```java
1
2
3
4
5
6
7
8
9
10
```
### Stream API (recordatorio de Práctica 2 + ampliación)

Un *stream* representa una secuencia de elementos que soportan diferentes tipos de operaciones que permiten realizar cálculos sobre ellos. Las posibles operaciones que se pueden realizar sobre un *stream* pueden ser intermediarias o terminales. Las operaciones intermediarias devuelven un nuevo *stream* permitiendo encadenar múltiples operaciones intermediarias sin usar punto y coma. Por otro lado, las operaciones terminales son nulas o devuelven un resultado de un tipo diferente, por ejemplo un tipo numérico.

>![streams en Java 8](./figuras/streamEjemplo.png)
>
<small>por <cite>Benjamin, [Java 8 Stream Tutorial](https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/)</cite></small>

En el ejemplo anterior, las operaciones *filter*, *map* y *sorted* son operaciones intermediarias, mientras que la operación *forEach* es una operación terminal.

Más información: https://www.oracle.com/technetwork/es/articles/java/procesamiento-streams-java-se-8-2763402-esa.html

Por otro lado, se puede observar que la mayoría de las operaciones que se aplican sobre *streams* aceptan algún tipo de parámetro en forma de *expresión lambda*, la cual es una interfaz funcional que especifica el comportamiento exacto de la operación. Estas operaciones no pueden modificar el contenido del *stream* original. En el ejemplo anterior, se puede observar que ninguna de las operaciones modifica la variable *myList* añadiendo o eliminando elementos, sólo se realiza el filtrado de los elementos que no empiezan por "c", se transforman a mayúsculas, se ordenan en orden alfabético y se imprimen por pantalla.

Más información: https://www.oracle.com/technetwork/es/articles/java/expresiones-lambda-api-stream-java-2737544-esa.html

Finalmente, se incluye otro ejemplo del uso del Stream API en Java:

```java
ArrayList<Integer> mayores = (ArrayList<Integer>) Arrays
	.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
	.stream()
	.filter(x -> x > 5)
	.collect(
	Collectors.toCollection(() -> new ArrayList<Integer>()));

mayores.forEach(e -> System.out.println(e));
```

En este caso, en el ejemplo anterior se realiza el filtrado de los números que sean mayores que 5 y posteriormente, se imprimen por pantalla los elementos de la lista. Por lo tanto, la salida del programa por consola en este caso sería:

```java
1
2
3
4
5
```

## Cierres (Closure)

Cuando se realiza la declaración de una variable local, esa variable tiene un alcance local. Es decir, la definición de estas variables sólo existen dentro del bloque o función dónde se ha realizado su declaración.

Por ejemplo:

```java
int suma(int a, int b) {
	int suma = a + b;
	return suma;
}

int sumaDoble(int a, int b) {
	suma = a*2 + b+2; //falla
	return suma;
}
```

En este caso, en la primera función se realiza la asignación de la suma de las variables *a* y *b* a una variable local *suma* y posteriormente, se devuelve el valor de esta variable. En la segunda función, se realiza el mismo procedimiento que en la primera función pero realizando la suma de los dobles de las variables *a* y *b*. El problema en esta función, es que se usa la variable local definida en la primera función, la cual no esta definida fuera de su ámbito y resultaría en un error de compilación. Para solventarlo, se tendría que definir la variable local *suma* también en la segunda función.

Sin embargo, el uso de los cierres (*closures*) permiten encapsular funciones completas (incluyendo sus variables locales) en otra variable, permitiendo su uso en diferentes lugares del programa.

> Closures are nothing but stateful functions! A few definitions from the web include:
> 
- A closure is a combination of a function bundled together (enclosed) with references to its surrounding state.
- A closure gives you access to an outer function’s scope from an inner function.
>
> Uses of Closures include data privacy, partial application & currying.
>
> -- <cite>[Ramkumar Manavalan](https://medium.com/@rmanavalan/5-ways-to-implement-closures-in-java-8-590790659ac5)</cite>

Por ejemplo:

```java
@FunctionalInterface
public interface NumToTextConverter {
	String convert(int x);
}

static void closureTypeDemo() {
	NumToTextConverter textOfWeekDay = new NumToTextConverter() {
		String [] weeks = {"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
		
		@Override
		public String convert(int num) {
			return (num > 0 && num <= weeks.length) ? weeks[num-1] : null;
		};
	};
	System.out.println(textOfWeekDay.convert(1));
}
```

En este caso, la variable *textOfWeekDay* puede considerarse un cierre (*closure*), ya que agrupa la definición de la función *convert*, y además, los datos de los días de la semana (privados, pero accesibles desde la función de conversión). 

Finalmente, la salida del programa anterior sería *Mon*, ya que la función *convert* devolvería la primera posición del array *weeks*.

## <span style="color:blue">Ejercicios propuestos</span>

### Ejercicio 1

Dado los siguientes fragmentos de código responder a las siguientes preguntas:

#### DataOperations.java

```java
public interface DataOperations {
	public void print(int[] data);
	public int[] filterPairs(int[] data);
}
```

#### DataOperationsImpl.java

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

#### Main.java

```java
import java.util.Arrays;

public class Main {
	
	public static void main(String args[]) {
		
		int [] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		
		System.out.println("data = " + Arrays.toString(data));
		
		DataOperations operations = new DataOperationsImpl();
		
		operations.print(data);
		
		data = operations.filterPairs(data);
		
		operations.print(data);
	}
}
```

#### Preguntas propuestas

a) Utilice el uso de expresiones *Lambda* + Stream API para transformar la implementación de las operaciones del API "DataOperations" a la sintaxis de programación funcional.

b) Además, haciendo uso de expresiones *Labmda* + Stream API añada al API "DataOperations" la implementación de las siguientes operaciones:

- Operación que devuelva la lista de números ordenada descendentemente.
- Operación que multiplique todos los números de la lista por 10 e imprima el resultado.
- Operación que devuelva el resultado de la suma de todos los números de la lista.


### Ejercicio 2

Dado los siguientes fragmentos de código responder a las siguientes preguntas:

#### DataSorter.java

```java
public interface DataSorter {
	public String[] sort(String[] data);
}
```

#### DataSorterAsc.java

```java
import java.util.Arrays;

public class DataSorterAsc implements DataSorter {
	
	public String[] sort(String[] data) {
		Arrays.sort(data);
		return data;
	}
}
```

#### DataSorterDesc.java

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

#### Main.java

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

a) Utilice el uso de cierres (*closures*) para transformar la implementación de las clases *DataSorterAsc* y *DataSorterDesc* a la sintaxis de la programación funcional.

b) Añada una tercera transformación haciendo uso de cierres (*closures*) de una clase que realice la ordenación aleatoria de los elementos, siguiendo el mismo enfoque que el que ha seguido con las clases *DataSorterAsc* y *DataSorterDesc* en el apartado anterior.


## Referencias

[Funcion anonima]: https://en.wikipedia.org/wiki/Anonymous_function
[[1] Definición de función anónima.][Funcion anonima]
[Java 8 Stream Tutorial]: https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
[[2] Blog Java 8 Stream Tutorial.][Java 8 Stream Tutorial]
[API Java Streams]: https://www.oracle.com/technetwork/es/articles/java/procesamiento-streams-java-se-8-2763402-esa.html
[[3] API Java Streams.][API Java Streams]
[API Java Funciones Lambda + Streams]: https://www.oracle.com/technetwork/es/articles/java/expresiones-lambda-api-stream-java-2737544-esa.html
[[4] API Java Funciones Lambda + Streams.][API Java Funciones Lambda + Streams]
[Closures in Java]: https://medium.com/@rmanavalan/5-ways-to-implement-closures-in-java-8-590790659ac5
[[5] Blog Closures in Java.][Closures in Java]