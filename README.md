# <u>L3 Miage - BDD -TP1 - EXO1</u>

* Pour pouvoir démarrer tous les TP de manière simple, assurez-vous que vous avez tous les prérequis :
    * voir les [prérequis](prerequis.md)

# Objectif de l'exercice :

Dans cet exercice, vous allez devoir apprendre à :

* mapper des tables existantes en entity JPA
* générer des tables grâce à la déclaration des entity JPA

## 1 Définition d'une entity avec une table déjà existante

***Mettre une phrases d'accroche***
![car entity](doc/exo1/pictures/car_entity.png)

### 1.1 `@Table` & `@Entity`

Dans un premier temps, nous allons déclarer la classe correspondante :

* Dans le package `fr.uga.l3miage.tp1.exo1.models` nous allons déclarer une nouvelle classe `CarEntity`

```java
public class CarEntity {
}
```

* Nous allons ensuite utiliser les annotations disponibles de JPA afin de correspondre au modèle ci-dessus :
    * Nous allons rajouter l'annotation à la classe
      ```java
          @Entity
          public class CarEntity{}
      ```

      afin d'indiquer que cette classe correspond à une table dans la base de données.
    * Nous allons ensuite ajouter l'annotation à la classe
      ```java
      @Entity
      @Table(name="car_porshe")
      public class CarEntity{}
      ```
      pour indiquer que l'entité que nous sommes en train de définir correspond à la table `car_porshe` en base de
      données.

### 1.2 `@Id`

À ce stade du TP, votre éditeur de code devraiT vous signaler un problème sur l'entité `CarEntity`.
En effet, si l'on regarde sur le modèle ([carEntity](#1-définition-dune-entity-avec-une-table-déjà-existante)) l'attribut `immat` correspond à la clé primaire (PK).

### 1.2 `@Column`

L'annotation `@Column` permet de définir à quels champs de la table en base de données correspondent les attributs de la classe.
Elle sert également à préciser certaines informations sur la colonne elle-même (sa taille, son format, etc.)

Si on reprend la définition de notre classe `CarEntity` nous devons déclarer petit-à-petit les attributs de notre
classe :

* pour l'attribut `immat` :
  ```java
    @Entity
    @Table(name="car_porshe")
    public class CarEntity{
        @Column(name = "immat",length = 8)
        private String immatriculation;
    }
  ```
  Ici, nous précisons que l'attribut `immatriculation` de la classe `CarEntity` correspond à la colonne `immat` en base de
  données.

  De plus nous donnons l'indication que la taille de cet attribut ne peux pas excéder 8 grâce au <p style="color:green">
  length=8</p>

* pour l'attribut `cylinderCapacity` :
  Comme pour l'attribut `immat`, nous ajoutons l'attribut avec l'annotation `@Column`
  ```java
    @Entity
    @Table(name="car_porshe")
    public class CarEntity{
        //Before
        @Column(name = "cylinder_capacity")
        private Double cylinderCapacity;
        //After
    }
  ```
* Nous allons ensuite répéter le processus petit-à-petit pour tous les attributs :
    * `weight`
        * ```java
            @Column(name = "weight")
            private Integer weight;
          ```
    * `torque`
        *   ```java
            @Column(name = "torque")
            private Integer torque;
            ```
    * `power`
        * ```java
            @Column(name ="power")
            private Integer power;
          ```
    * `circulationDate`
        *   ```java
            @Column(name= "circulation_date")
            private LocalDate circulationDate;
            ```

### 1.3 Les types `Enum`

Dans l'entité CarEntity, il y a 2 enum :

* `PowerType`
* `WeightType`

Elles sont représentées de la manière suivante en BD :

* `PowerType` est un entier (`integer`)
    ![powerType]()
* `WeightType` est une chaine de caractères (`varchar(255)`)
    ![weightType]()

Ainsi, on se rend compte que la gestion d'une enum est particulière en JPA !

#### 1.3.1 Déclaration des types enum

Avant toutes choses, déclarons les 2 types enum dans le package `fr.uga.l3miage.tp1.exo1.enums`
de cette manière : 
* `PowerType`
    ```java
    public enum PowerType {
        CH, // type en chevaux
        CHF // type en chevaux fiscaux
    }
    ```
* `WeightRange`
    ```java
    public enum WeightUnity {
        KG, // unité en européen
        POUND, // unité anglaise
        LIVRE // unité anglaise
    }
    ```

#### 1.3.2 @Enumerated

L'annotation `@Enumerated` indique à JPA que l'attribut qui contient cette annotation est un type énuméré.
Cette annotation attend 1 argument `EnumType` qui correspond à comment gérer l'enum en base de données :

* `EnumType.ORDINAL`: donne l'indication de représenter l'enum par un entier (integer) dans la base de données
* `EnumType.STRING`: donne l'indication de représenter l'enum par une chaine de caractères (string) dans la base de données

#### 1.3.3 Les types enum dans l'entité `CarEntity`

Maintenant que nous avons compris comment fonctionnent les types enum en JPA, nous pouvons déclarer les attributs `powerType`
et `weightType`.

Comme énoncé précédemment, l'attribut `powerType` est de type integer dans la BD.
Nous devons donc le déclarer en `EnumType.ORDINAL`, ce qui donne : 

```java
@Column(name = "power_type")
@Enumerated(EnumType.ORDINAL)
private PowerType powerType;
```
Attention : n'oubliez pas le `@Column`

Ensuite, nous pouvons déclarer l'attribut `weightType`, une chaine de caractère en BD. 
Contrairement à l'attribut `powerType`, nous devons le déclarer en `EnumType.STRING` :

```java
@Column(name= "weight_unity")
@Enumerated(EnumType.STRING)
private WeightUnity weightUnity;
```



<html lang="fr">
<div style="background-color: aqua;color: black">
<div>
<h3>Note</h3>
</div>
<aside>
<p style="margin: 10px;color: black">
2 écrire une note avec un peu de style pour expliquer quand on utilise <b>ordinal</b>/ ou non 
</p>
</aside>
</div></html>

#### 1.4.1 @Id

L'annotation `@Id` permet de dire à JPA que l'attribut qui a cette annotation représente la clé primaire en BD. 

Donc dans notre cas, nous devons ajouter : 
```java
@Entity
@Table(name="car_porshe")
public class CarEntity{
    @Id
    @Column(name = "immat",length = 8)
    private String immatriculation;
}
```

### 1.5  À votre tour

Maintenant que vous savez définir une entité, à vous de créer l'entité `BikeEntity` adaptée au modèle ci-dessous :

### 1.6 Valider votre implémentation

JPA nous permet de valider si nos entités correspondent à la réalité physique en base de données. 
Pour lui permettre cela, nous renseignons la propriété suivante : 
* `spring.jpa.hibernate.ddl-auto` et lui donnons la valeur `validate`

dans le fichier [application.yml](server/src/main/resources/application.yml)

#### 1.7 lancer la commande

Pour voir si votre implémentation est correcte, vous devez démarrer le serveur web qui execute JPA (le framework `Spring` dont nous parlerons dans un cours et TP ultérieurement).
```shell
mvn test
```
Ou alors lancer la classe de test [TestExo1.java](server/src/test/java/fr/uga/l3miage/tp1/exo1/TestExo1.java)


## 2 Définition d'une entité qui n'existe pas en BD

L'un des avantages majeur de JPA, c'est qu'il nous permet également de créer le schéma que l'on implémente directement en BD. A son démarrage, il va analyser les entités existantes et va ensuite créer un DDL (Data Definition Langage) et l'exécuter.

Dans cette partie du TP, nous allons observer comment JPA crée les entités lorsqu'elles sont scannées.

### 2.1 Setup JPA

Comme énoncé plus haut, pour que JPA puisse créer directement nos tables en fonction des entités, nous devons changer la propriété suivante : 
* `spring.jpa.hibernate.ddl-auto` et lui donner la valeur `create-drop`
  dans le fichier [application.yml](server/src/main/resources/application.yml)

#### Note : 
* Pour la valeur `spring.jpa.hibernate.ddl-auto`, 5 modes sont possibles :

    |    mode     |                                                   fonction                                                   |
    |:------------------------------------------------------------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------:|
    |   create    |              Permet de créer le schéma détecté en base de donnés au démarrage du serveur spring              |
    | create-drop | Même action que create, et il va en plus supprimer toutes les tables en BD lors de l'arrêt du serveur Spring |
    |   update    |  Fait une comparaison entre le modèle JPA et le modèles physique et applique les modifications nécessaires   |
    |  validate   |                    Permet de valider si le modèle JPA correspond au modèle physique en BD                    |
    |    none     |                                                 Ne fait rien                                                 |

### 2.2 Ajout d'une nouvelle classe

Ajoutez ces nouvelles classe dans le package `fr.uga.l3miage.tp1.exo1.models` : 

⚠️ ne les modifiez pas, c'est important !

```java
@Entity
public class ClassTestJPA {
    @Id
    private String myIdTest;
    
    private Double jpaSeed;
    
    private LocalDate savedAt;
    
    private PowerType powerType;
}
```

```java
@Entity
public class ClassTestJPATwo {
    @Id
    private String myIdTest;
    
    @Column(unique)
    private String testName;
    
    @Column(precision = 1)
    private Double jpaSeed;
    
    @Column()
    private Date savedAt;
}
```

### 2.3 Créer les classes

Pour cela, rien de plus simple, il vous suffit de démarrer le serveur Spring. En effet, puisque vous avez changé la propriété `spring.jpa.hibernate.ddl-auto` à `create-drop`, JPA va s'en charger pour vous.

#### 2.3.1 Lancer via Intellij

#### 2.3.1 Lancer via CMD


### 2.4 Voir l'état de votre BD

Pour cela référez vous à la doc des [prérequis](prerequis.md) et allez voir sur `pgAdmin` votre BD

### 2. Questions

1. Décrivez ce que vous observez en base de données lorsque l'on déclare une classe comme `ClassTestJPA` ?
2. Quels sont les effets des propriétés ajoutées dans les @Column dans la classe `ClassTestJPATwo` ?
3. À votre avis, dans quels cas est-il nécessaire de mettre les annotations `@Column` ?


---
# <div style="text-align: center;">Fin de l'exo 1</div>