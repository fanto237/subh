# Subh - Library
A library online platform for borrowing books and administration users and books
 
## About the project
This application has been developed as part a school module for programming web application learning  proposed by the [University Bremerhaven](https://www.hs-bremerhaven.de/start/) for its computer science students.

The knowledge in Java EE, JavaServer Faces, Contexts and Dependency Injection and Hibernate has been improved and was necessary for the realization of this project.

## Installation
For running this app it is necessary to have at least [Apache Maven](https://mirror.synyx.de/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.zip) and [Docker](https://github.com/docker/toolbox/releases/download/v18.09.3/DockerToolbox-18.09.3.exe), both installed on your device.
After that just have to :
* clone this repository ``git clone https://github.com/fantozor/subh.git``
* run the script ``./buildAndRun.sh``

## Project Structures
* ``/beans :`` here are all **backend Beans** classes implemented. The **loginBean** is present in almost all others beans as **ManagedProperty**, with scope **SessionScoped** and carry the current user.

* ``/dataAccessObjects :`` here are all classes need for transactions on the database and the hibernate calls.

* ``/exceptions :`` include own exceptions thrown by the registration   .

* ``/models :`` include all POJO classes used by hibernate for tables generating. Some data will added into the tables the custom sql file ``import.sql``.

* ``/models.enums :`` here are enum classes for Category of books and user's role.

* ``/utilities :`` Validators and EncryptionsHelper to encrypt the password using a random salt before saving in the database.

## Key Dependencies
- Hibernate: Open-Source-Persistence- and ORM-Framework for Java application. Alternative Eclipse-Link
- MySQL: an open source database management system developed by Oracle. Alternative  H2
- Primefaces: Open source framework for JavaServer Faces featuring over 100 components, touch optimized mobilekit, client side validation, theme engine and more.
- Bootsfaces: JSF framework based on Bootstrap 3 and jQuery UI.

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
