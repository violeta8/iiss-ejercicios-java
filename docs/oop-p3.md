# Práctica 3

## <span style="color:blue">Repaso de conceptos teóricos</span>

## Inyección de dependencias

### Definición

La inyección de dependencias es un patrón de diseño utilizado para implementar la inversión de control (IC, se invierte el flujo tradicional del programa). Por este motivo, permite la creación de objetos fuera de una clase y proporciona esos objetos a otra clase dependiente de ellos de diferentes formas. Utilizando la IC, se traslada la creación y unión de los objetos fuera de la clase que depende de ellos.

>![Modelo de inyección de dependencias](./figuras/inyeccionDependenciasModelo.png)
>
<small>por <cite>TutorialsTeacher, [Dependenccy Injection](https://www.tutorialsteacher.com/ioc/dependency-injection)</cite></small>

A continuación, se describen los tipos de inyección de dependencias existentes:

- Inyección a través del constructor: En este tipo de inyección la clase inyectora suministra la dependencia (servicio) a través del constructor de la clase dependente (cliente).

- Inyección a través de propiedades: En este tipo de inyección la clase inyectora suministra la dependenca (servicio) a través de un método "set" de la clase dependiente (cliente).

- Inyección a través de métodos: En este tipo de inyección la clase inyectora suministra la dependencia (servicio) a través de un API establecido por la clase dependiente en el que se especifican el/los método/s para suministrar la dependencia (cliente).

Frameworks para facilitar la implementación de inyección de dependencias en Java: https://www.vogella.com/tutorials/DependencyInjection/article.html.

## Programación orientada a aspectos

### Definición

Los aspectos nos permiten agrupar código que se ejecutará en varios lugares en un módulo independiente. Además, este código será inyectado en tiempo de ejecución o compilación (dependiendo del framework) en los puntos de corte especificados en el código fuente. En este caso, la programación orientada a aspectos (AOP) permite introducir nueva funcionalidad dentro de una clase, sin que ésta deba tener conocimiento de su existencia.

> Aspect-oriented programming (AOP) complements object-oriented programming by allowing the developer to dynamically modify the static object-oriented model to create a system that can grow to meet new requirements, allowing an application to adopt new characteristics as it develops.
> 
> -- <cite>[Vangie Beal](https://www.webopedia.com/TERM/A/aspect_oriented_programming.html)</cite>

AspectJ, framework para facilitar la implementación de inyección de dependencias en Java: https://www.eclipse.org/aspectj/doc/released/progguide/starting.html

## <span style="color:blue">Ejercicios propuestos</span>

### Ejercicio 1

Dado los siguientes fragmentos de código responder a las siguientes preguntas:

#### DBAccess.java

```java
public interface DBAccess {
	
	public void initConnection();

}
```

#### DBAccessA.java

```java
public class DBAccessA implements DBAccess {
	
	public DBAccessA() {}
	
	public void initConnection() {
		System.out.println("Init A connection with database..");
	}

}
```

#### DBAccessB.java

```java
public class DBAccessB implements DBAccess {
	
	public DBAccessB() {}
	
	public void initConnection() {
		System.out.println("Init B connection with database..");
	}

}
```

#### DBClient.java

```java
public class DBClient {
	
	private DBAccess dbAccess;
	
	public DBClient(DBAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
	
	public void setDBAccess(DBAccess dbAccess) {
		this.dbAccess = dbAccess;
	}
	
	public void getAllFromDataBase() {
		dbAccess.initConnection();
		System.out.println("Returning all data from database..");
	}
	
	public void getSomeDataFromDataBase() {
		dbAccess.initConnection();
		System.out.println("Returning some data from database..");
	}

}
```

#### Main.java

```java
public class Main {
	
	public static void main(String args[]) {
		DBAccess dbAccessB = new DBAccessB();
		DBClient client = new DBClient(dbAccessB);
		System.out.println("Querying all data from database..");
		client.getAllFromDataBase();
		
		DBAccess dbAccessA = new DBAccessA();
		client.setDBAccess(dbAccessA);
		System.out.println("Querying some data from database..");
		client.getSomeDataFromDataBase();
	}

}
```

#### Preguntas propuestas

a) ¿Se realiza inyección de dependencias entre las clases anteriores?, si es así identifique la clase inyectora, el servicio y el cliente.

b) En el caso de que exista inyección de dependencias además indique:

- El método de inyección que se realiza (constructor, propiedad o método).
- La/s línea/s donde se realiza la inyección de dependencias.


### Ejercicio 2

Dado los siguientes fragmentos de código responder a las siguientes preguntas:

#### Bank.java

```java
public class Bank {
	
	public Bank() {}
	
	public void createUser() {
		System.out.println("Creating user..");
	}
	
	public void makeTransaction() {
		System.out.println("Making transaction..");
	}
	
	public void takeMoneyOut() {
		System.out.println("Taking money out..");
	}
	
	public void showUsers() {
		System.out.println("Showing users..");
	}

}
```

#### Main.java

```java
public class Main {
	
	private static Scanner input = new Scanner(System.in);

	public static void main(String args[]) {
		
		System.out.println("AspectJ Bank");
		System.out.println("------------");
		System.out.println("1 - Create user");
		System.out.println("2 - Make transaction");
		System.out.println("3 - Take money out");
		System.out.println("4 - Show users");
		System.out.println("5  - Exit");
		
		int option = Integer.valueOf(input.nextLine());
		Bank bank = new Bank();
		
		switch(option) {
		case 1:
			bank.createUser();
			break;
		case 2:
			bank.makeTransaction();
			break;
		case 3:
			bank.takeMoneyOut();
			break;
		case 4:
			bank.showUsers();
			break;
		case 5:
			System.out.println("Exiting..");
			break;
		}
	}
	
}
```

#### LoginAspect.java

```java
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LoginAspect {
	@Before("..TO-DO..")
    public void before(JoinPoint joinPoint){
        //...TO-DO..
    }
	
	@After("..TO-DO..")
    public void after(JoinPoint joinPoint){
        //...TO-DO..
    }
}
```

#### Preguntas propuestas

Complete en la clase "LoginAspect.java" las secciones "TO-DO" de forma que se cumplan las siguientes condiciones:

a) Mostrar el mensaje "The login is required" antes de la ejecución de las operaciones "makeTransaction" y "takeMoneyOut".

b) Mostrar el mensaje "The database is empty" después de la ejecución de la operación "showUsers".

c) Sustituya el fichero "LoginAspect.java" por el fichero "LoginAspect.aj" incluyendo la misma funcionalidad utilizando la sintaxis de AspectJ.
