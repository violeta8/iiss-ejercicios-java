# Práctica 1: Herencia, composición y polimorfismo

## Ejercicios propuestos

### Ejercicio 1

Dados los siguientes fragmentos de código, responder a las siguientes preguntas:

#### `ElementsSet.java`

```java
public class ElementsSet<E> extends HashSet<E> {
    //Number of attempted elements insertions using the "add" method
    private int numberOfAddedElements = 0;

    public ElementsSet() {}

    @Override
    public boolean add(E element) {
        numberOfAddedElements++; //Counting the element added
        return super.add(element);
    } 

    @Override
    public boolean addAll(Collection<? extends E> elements) {
        numberOfAddedElements += elements.size(); //Counting the elements added
        return super.addAll(elements);
    } 

    public int getNumberOfAddedElements() {
        return numberOfAddedElements;
    }
}
```

#### `Main.java`

```java
    ...
    ElementsSet<String> set = new ElementsSet<String>();
    set.addAll(Arrays.asList("One", "Two", "Three"));
    System.out.println(set.getNumberOfAddedElements());
    ...
```

#### Preguntas propuestas

a) ¿Es el uso de la herencia adecuado para la implementación de la clase `ElementsSet`? ¿Qué salida muestra la función `System.out.println` al invocar el método `getNumberOfAddedElements`, 3 o 6? 

El uso de la herencia para implementar la clase ElementsSet es adecuado porque ElementsSet es una extensión de la clase HashSet en Java. 

Al extender HashSet, ElementsSet hereda todos los métodos y campos públicos de la clase HashSet.

 Respecto a la salida la invocación del método 'getNumberOfAddedElements' es 6.


b) En el caso de que haya algún problema en la implementación anterior, proponga una solución alternativa usando composición/delegación que resuelva el problema.

La solución alternativa que usaré sera con composición, ya que la clase ElementsSet no es una subclase de HashSet, sino que tiene un objeto de tipo HashSet como campo.
```java
public class ElementsSet<E> {

    //Delega el objeto para encargarse de las operaciones del set
    private HashSet<E> 

    public ElementsSet() {
        delegateSet = new HashSet<>();
    }

    public boolean add(E element) {
        return delegateSet.add(element);
    } 

    public boolean addAll(Collection<? extends E> elements) {
        return delegateSet.addAll(elements);
    } 

    //Delegate all other set operations to the HashSet object
    public int size() {
        return delegateSet.size();
    }

    public boolean isEmpty() {
        return delegateSet.isEmpty();
    }

}
```

### Ejercicio 2

Dado los siguientes fragmentos de código responder a las siguientes preguntas:

#### `Animal.java`

```java
public abstract class Animal {
    //Number of legs the animal holds
    protected int numberOfLegs = 0;

    public abstract String speak();
    public abstract boolean eat(String typeOfFeed);
    public abstract int getNumberOfLegs();
}
```

#### `Cat.java`

```java
public class Cat extends Animal {
    @Override
    public String speak() {
        return "Meow";
    }

    @Override
    public boolean eat(String typeOfFeed) {
        if(typeOfFeed.equals("fish")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNumberOfLegs() {
        return super.numberOfLegs;
    }
}
```

#### `Dog.java`

```java
public class Dog extends Animal {
    @Override
    public String speak() {
        return "Woof";
    }

    @Override
    public boolean eat(String typeOfFeed) {
        if(typeOfFeed.equals("meat")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNumberOfLegs() {
        return super.numberOfLegs;
    }
}
```

#### `Main.java`

```java
    ...
    Animal cat = new Cat();
    Animal dog = new Dog();
    System.out.println(cat.speak());
    System.out.println(dog.speak());
    ...
```

#### Preguntas propuestas

a) ¿Es correcto el uso de herencia en la implementación de las clases `Cat` y `Dog`? ¿Qué beneficios se obtienen?

En este ejemplo faltaría el constructor en cada clase para el numero de patas de cada animal, por tanto el uso de éste estaría incorrecto pero si que es verdad que al usar herencia, se pueden definir características y comportamientos generales en una clase más general (Animal) y luego extender esas características y comportamientos en subclases más específicas (Cat y Dog). Esto permite una mayor modularidad y flexibilidad en el diseño de nuestro código, ya que podemos agregar fácilmente nuevas subclases (como Bird o Fish) que también extienden la clase Animal y proporcionan su propia implementación de los métodos abstractos.


b) En el caso de que el uso de la herencia no sea correcto, proponga una solución alternativa. ¿Cuáles son los beneficios de la solución propuesta frente a la original?

La solucion sería:

```java
public class Animal {
    //esta clase guardara el numero de piernas,el tipo de comida y el sonido del animal
    private int numberOfLegs;
    private String typeOfFeed;
    private String sound;

    public Animal(int numberOfLegs, String typeOfFeed, String sound) {
        this.numberOfLegs = numberOfLegs;
        this.typeOfFeed = typeOfFeed;
        this.sound = sound;
    }

    public String speak() {
        return sound;
    }

    public boolean eat(String typeOfFeed) {
        if(typeOfFeed.equals(this.typeOfFeed)) {
            return true;
        } else {
            return false;
        }
    }

    public int getNumberOfLegs() {
        return numberOfLegs;
    }
}
```
```java
public class Dog extends Animal {
    private Animal animal = new Animal(4, "meat", "woof");

    @Override
    public String speak() {
        return "woof";
    }

    @Override
    public boolean eat(String typeOfFeed) {
        if(typeOfFeed.equals("meat")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNumberOfLegs(int n) {
        return n;
    }
}
```
Para el caso de cat.java: 

```java
public class Cat extends Animal {
    private Animal animal = new Animal(4, "fish", "meow");

    @Override
    public String speak() {
        return "meow";
    }

    @Override
    public boolean eat(String typeOfFeed) {
        if(typeOfFeed.equals("fish")) {
            return true;
        } else {
            return false;
        }
    }

   @Override
    public int getNumberOfLegs(int n) {
        return n;
    }
}
```