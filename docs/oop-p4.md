# Práctica 4: Tratamiento de errores

## Repaso de conceptos teóricos

## Aserciones

Las aserciones son un método para aumentar la fiabilidad del código que se esta implementando. Las aserciones son expresiones que representan una condición que debe cumplirse en una parte específica del código. Si una aserción no se cumple, el programa generará un error en ese punto.

> An assertion is a statement in the Java programming language that enables you to test your assumptions about your program. For example, if you write a method that calculates the speed of a particle, you might assert that the calculated speed is less than the speed of light.
>
> Each assertion contains a boolean expression that you believe will be true when the assertion executes. If it is not true, the system will throw an error. By verifying that the boolean expression is indeed true, the assertion confirms your assumptions about the behavior of your program, increasing your confidence that the program is free of errors.
>
> Experience has shown that writing assertions while programming is one of the quickest and most effective ways to detect and correct bugs. As an added benefit, assertions serve to document the inner workings of your program, enhancing maintainability.
> 
> -- <cite>[Documentación oficial de Java.](https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html)</cite>

A continuación, se muestra un ejemplo de una aserción implementada en Java:

```java
assert price > 0;
```

En este caso, la aserción comprueba que el valor del atributo *price* debe ser siempre mayor que 0, en el caso de que el valor sea 0 o inferior la aserción no se cumplirá. Esto permite comprobar que el programa esta realizando un funcionamiento correcto en este sentido.

Si la condición que sigue al keyword *assert* no se cumple el programa lanzará una excepción de tipo "AssertionError". Para que el mensaje de error asociado sea más descriptivo se puede especificar su contenido como se muestra a continuación:

```java
assert price > 0 : "El precio es menor que 0.";
```

Estas excepciones pueden ser capturadas, o en caso contrario el intérprete de Java se encargará de ellas y mostrará la excepción por consola. A continuación, se muestra un ejemplo del error generado cuando una aserción no se cumple:

```text
Exception in thread “main” java.lang.AssertionError
at AssertTest.main(AssertTest.java:14)
```
En el ejemplo anterior se muestra un error generado donde no se ha especificado ningún mensaje para que sea más descriptivo. A continuación, se muestra un error donde se ha especificado el mensaje:


```text
Exception in thread “main” java.lang.AssertionError: El precio es menor que 0.
at AssertTest.main(AssertTest.java:14)
```

Como se puede comprobar, la incorporación de mensajes aclaratorios es una buena práctica, ya que en el primer caso es díficil conocer el motivo de error del programa, a no ser que se visualice el contenido de la línea 14 del fichero "AssertTest.java".


## Abuso de null & Optional

En [[3]][Articulo Uso Null] se describe algunas de las consecuencias del abuso del valor `null` y los mecanismos que pueden ser utilizados para evitar su uso. En Java 8 se incorpora la clase `Optional`, que puede usarse para evitar su abuso.

> Optional is a container object used to contain not-null objects. Optional object is used to represent null with absent value. This class has various utility methods to facilitate code to handle values as ‘available’ or ‘not available’ instead of checking null values. It is introduced in Java 8 and is similar to what Optional is in Guava.
> -- <cite>[TutorialsPoint.com](https://www.tutorialspoint.com/java8/java8_optional_class.htm)</cite>

A continuación, en el siguiente fragmento de código se muestra un ejemplo de uso indebido del valor `null`:

```java
Album album = getAlbum("Random Memory Access");
if(album != null) {
	return album;
} else {
	// Avisar al usuario de que no se ha encontrado el album
}
```
En el siguiente fragmento de código se usa la clase `Optional`. En este caso, se evita la comparación de la variable `album` con el valor `null`, y se utiliza el método `isPresent` en su lugar.

```java
Optional<Album> albumOptional = getAlbum("Random Memory Access");
if(albumOptional.isPresent()) {
    return albumOptional.get();
} else {
    // Avisar al usuario de que no se ha encontrado el album
}
```

## Ejercicios propuestos

### Ejercicio 1

Dado los siguientes fragmentos de código, responder a las siguientes preguntas:

#### `Product.java`

```java
public class Product {
	
	private int code;
	private String name;
	private String category;
	private double weight;
	private double height;
	
	public Product(int code, String name, String category, double weight, double height) {
		
		this.code = code;
		
		if(name == null) {
			this.name = "";
		} else {
			this.name = name;
		}
		
		if(category == null) {
			this.category = "";
		} else {
			this.category = category;
		}
		
		this.category = category;
		this.weight = weight;
		this.height = height;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getHeight() {
		return this.height;
	}
}
```

### `ShoppingCart.java`

```java
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	
	Map<Product, Integer> shoppingCart;
	
	public ShoppingCart() {
		shoppingCart = new HashMap<Product, Integer>();
	}
	
	public void addProduct(Product product, int number) {
		
		if(shoppingCart.keySet().stream().filter(element -> element.getCode() == product.getCode()).count() == 0) {
			shoppingCart.put(product, number);
		}
	}
	
	public Product removeProduct(Product product) {
		if(shoppingCart.containsKey(product)) {
			shoppingCart.remove(product);
			return product;
		}  else {
			return null;
		}
	}
	
	public void printShoppingCartContent() {
		System.out.println("The shopping cart content is: ");
		
		for(Product product: shoppingCart.keySet()) {
			System.out.println(product.getCode() + " - " + product.getName() + " : " + shoppingCart.get(product));
		}
		
	}
}
```

### `Main.java`

```java
public class Main {
	
	public static void main(String args[]) {
		ShoppingCart shoppingCart = new ShoppingCart();
		
		Product product1 = new Product(1, "Product1", "Category1", -1.0, 2.0);
		Product product2 = new Product(2, "Product2", "Category2", 5.0, -6.0);
		Product product3 = new Product(3, "Product3", null, 5.0, 6.0);
		Product product4 = new Product(4, null, "Category4", 5.0, 6.0);
		Product product5 = new Product(4, "Product5", "Caregory5", 5.0, 6.0);
		Product product6 = new Product(-6, "Product6", "Caregory6", 5.0, 6.0);
		
		
		shoppingCart.addProduct(product1, 2);
		shoppingCart.addProduct(product2, 1);
		shoppingCart.addProduct(product3, 0);
		shoppingCart.addProduct(product4, -2);
		shoppingCart.addProduct(product5, 3);
		shoppingCart.addProduct(product6, 3);
		
		if(shoppingCart.removeProduct(product1) != null) {
			System.out.println("The product has been successfully deleted.");
		}
		
		shoppingCart.printShoppingCartContent();
	}
}
```

#### Preguntas propuestas

Completar las clases `Product.java` y `ShoppingCart.java` añadiendo aserciones donde sea necesario que permitan que se cumplan las siguientes condiciones:

a) En la clase `Product.java`:

- El valor del atributo `code` no puede ser un número negativo.
- El valor del atributo `name` no puede estar vacío.
- El valor del atributo `category` no puede estar vacío.
- El valor del atributo `weight` no puede ser un número negativo.
- El valor del atributo `height` no puede ser un número negativo.

Además, añadir un mensaje de error descriptivo en cada una de las aserciones que se hayan implementado.

b) En la clase `ShoppingCart.java`:

- No se puede añadir un producto con un número de unidades negativo o nulo.
- No se puede eliminar un producto que no existe en el carrito.

### Ejercicio 2

Dado el código del primer ejercicio, ¿existe algún uso indebido del valor `null`?. En caso afirmativo, reemplazar su uso por el de la clase `Optional` en los casos en los que sea necesario.


## Respuestas
### Ejercicio 1
a)
```java
public class Product {

private int code;
private String name;
private String category;
private double weight;
private double height;

public Product(int code, String name, String category, double weight, double height) {
	
	assert code >= 0 : "Error: El valor del código no puede ser negativo";
	assert !name.isEmpty() : "Error: El valor del nombre no puede estar vacío";
	assert !category.isEmpty() : "Error: El valor de la categoría no puede estar vacío";
	assert weight >= 0 : "Error: El valor del peso no puede ser negativo";
	assert height >= 0 : "Error: El valor de la altura no puede ser negativo";
	
	this.code = code;
	this.name = name;
	this.category = category;
	this.weight = weight;
	this.height = height;
}

public int getCode() {
	return code;
}

public void setName(String name) {
	assert !name.isEmpty() : "Error: El valor del nombre no puede estar vacío";
	this.name = name;
}

public String getName() {
	return this.name;
}

public void setCategory(String category) {
	assert !category.isEmpty() : "Error: El valor de la categoría no puede estar vacío";
	this.category = category;
}

public String getCategory() {
	return this.category;
}

public void setWeight(double weight) {
	assert weight >= 0 : "Error: El valor del peso no puede ser negativo";
	this.weight = weight;
}

public double getWeight() {
	return this.weight;
}

public void setHeight(double height) {
	assert height >= 0 : "Error: El valor de la altura no puede ser negativo";
	this.height = height;
}

public double getHeight() {
	return this.height;
}

}
```
b)
```java
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
Map<Product, Integer> shoppingCart;

public ShoppingCart() {
	shoppingCart = new HashMap<Product, Integer>();
}

public void addProduct(Product product, int number) {
	assert number > 0 : "Number of units must be greater than zero";
	
	if(shoppingCart.keySet().stream().filter(element -> element.getCode() == product.getCode()).count() == 0) {
		shoppingCart.put(product, number);
	}
}

public void removeProduct(Product product) {
	if(shoppingCart.containsKey(product)) {
		shoppingCart.remove(product);
	} else {
		throw new IllegalArgumentException("Product not found in the shopping cart.");
	}
}

public void printShoppingCartContent() {
	System.out.println("The shopping cart content is: ");
	
	for(Product product: shoppingCart.keySet()) {
		System.out.println(product.getCode() + " - " + product.getName() + " : " + shoppingCart.get(product));
	}
	
}
}
```

### Ejercicio 2
En la clase Product, hay dos casos en los que se asigna un valor predeterminado vacío ("") a un atributo en lugar de asignarle null. Esto es un problema ya que un valor vacío y null son conceptos diferentes y pueden tener diferentes significados en la lógica del programa. En lugar de asignar un valor vacío, debería asignarse null y luego validar en los métodos de acceso que el valor no sea null.

En la clase ShoppingCart, en el método removeProduct(), se devuelve null cuando se intenta eliminar un producto que no existe en el carrito. Esto podría ser confuso para el cliente de la clase, ya que no queda claro si null significa que el producto se ha eliminado correctamente o si simplemente no se encontró en el carrito. Sería mejor lanzar una excepción en este caso, lo que permitiría al cliente manejar la situación de manera más explícita y adecuada.
## `Product.java`
En la clase Product, se puede usar la clase Optional para devolver un valor vacío en caso de que se intente crear un objeto Product con un valor null para name o category. Además, se puede usar Optional para verificar si el valor de code, weight o height es negativo y, si es así, devolver un Optional vacío en lugar de lanzar una excepción.
```java
import java.util.Optional;

public class Product {
	
	private int code;
	private String name;
	private String category;
	private double weight;
	private double height;
	
	public Product(int code, String name, String category, double weight, double height) {
		
		if (code < 0 || weight < 0 || height < 0) {
			throw new IllegalArgumentException("Invalid argument: code, weight and height must be positive.");
		}
		
		Optional<String> optName = Optional.ofNullable(name);
		this.name = optName.orElse("");
		
		Optional<String> optCategory = Optional.ofNullable(category);
		this.category = optCategory.orElse("");
		
		this.code = code;
		this.weight = weight;
		this.height = height;
	}
	
	public int getCode() {
		return code;
	}
	
	public void setName(String name) {
		Optional<String> optName = Optional.ofNullable(name);
		this.name = optName.orElse("");
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setCategory(String category) {
		Optional<String> optCategory = Optional.ofNullable(category);
		this.category = optCategory.orElse("");
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setWeight(double weight) {
		if (weight < 0) {
			throw new IllegalArgumentException("Invalid argument: weight must be positive.");
		}
		
		this.weight = weight;
	}
	
	public double getWeight() {
		return this.weight;
	}
	
	public void setHeight(double height) {
		if (height < 0) {
			throw new IllegalArgumentException("Invalid argument: height must be positive.");
		}
		
		this.height = height;
	}
	
	public double getHeight() {
		return this.height;
	}
}
```	

## `ShoppingCart.java`
En la clase ShoppingCart, se puede usar Optional para verificar si un producto ya existe en el carrito antes de intentar eliminarlo y devolver un Optional vacío en lugar de devolver null. Además, se puede usar Optional para verificar si el número de unidades a agregar es negativo o nulo y, si es así, devolver un Optional vacío en lugar de lanzar una excepción. Aquí está el código modificado:
```java
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ShoppingCart {
	
	Map<Product, Integer> shoppingCart;
	
	public ShoppingCart() {
		shoppingCart = new HashMap<Product, Integer>();
	}
	
	public void addProduct(Product product, int number) {
		Optional<Product> optionalProduct = Optional.ofNullable(product);
		if(number > 0 && optionalProduct.isPresent() && shoppingCart.keySet().stream().noneMatch(element -> element.getCode() == product.getCode())) {
			shoppingCart.put(product, number);
		}
	}
	
	public Optional<Product> removeProduct(Product product) {
		Optional<Product> optionalProduct = Optional.ofNullable(product);
		if(optionalProduct.isPresent() && shoppingCart.containsKey(product)) {
			shoppingCart.remove(product);
			return optionalProduct;
		}  else {
			return Optional.empty();
		}
	}
	
	public void printShoppingCartContent() {
		System.out.println("The shopping cart content is: ");
		
		for(Product product: shoppingCart.keySet()) {
			System.out.println(product.getCode() + " - " + product.getName() + " : " + shoppingCart.get(product));
		}
		
	}
}

```

## Referencias

[API Java]: https://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html
[[1] Java documentación oficial.][API Java]

[Libro Assertions]: https://cdn.ttgtmedia.com/searchDomino/downloads/CH07_0672326280.pdf
[[2] Capítulo de libro Threads, Exceptions, and Assertions (java21days.com)][Libro Assertions]

[Articulo Uso Null]: https://leanmind.es/es/blog/evitar-null-tambien-en-kotlin/
[[3] Blog Null, un viejo enemigo del lado oscuro.][Articulo Uso Null]