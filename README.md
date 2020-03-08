# Subh - Library
A library online platform for borrowing books and administration users and books
 
## About the project
This application has been developed as part of a school module proposed by the [University Bremerhaven](https://www.hs-bremerhaven.de/start/) for learning in web application.

The knowledge in Java EE, JavaServer Faces, Contexts and Dependency Injection and Hibernate has been improved and was necessary for the realization of this project.

##Project Structures
* ``/beans :`` here are all ``@ManagedBeans`` classes implemented. The ``loginBean`` is present in almost all others beans as ``ManagedProperty``, with scope ``SessionScoped`` and carry the current user.

* ``/dataAccessObjects :`` here are all classes need for transactions on the database and the hibernate calls.

* ``/exceptions :`` include own exceptions thrown by the registration   .

* ``/models :`` include all POJO classes used by hibernate for tables generating. Some data will added into the tables the custom sql file ``import.sql``.

* ``/models.enums :`` here are enum classes for Category of books and user's role.

* ``/utilities :`` Validators and EncryptionsHelper to encrypt the password using a random salt before saving in the database.

##Key Dependencies
* ``Hibernate :`` Open source ORM tool for the Java programming language.


## Features

#### User
* ``can edit his profil``
* ``can borrow a certain amoung of books depends on his role``
* ``can give back borrowed books``

#### Admin
* ``can list all users``
* ``can list all books``
* ``can add new books in the library``
* ``edit/update existing books``
* ``can delete books``
* ``can change role of other user``
* ``have the same action like a normal user too``