# Payments EX

This is a small project for demonstrate how a standalone payment process could work.

## Getting Started

First you need to download this project to your local environment.

### Prerequisites

Prerequisites to run this project.

* Java JDK version "1.8" or later.
* Maven version "3.5" or later.


### Installing

1. Clone to your local repository.

```
$git clone https://github.com/matterhorn33/PaymentsEx.git
```

2. Execute maven command to build the project inside your new cloned repository.

```
$mvn clean install
```

3. Navigate directory until you're in /target directory of your cloned repository.

```
$cd [my_home_dir]/[my_repositories_dir]/ClipPaymentEx/target/
```

4. Execute the Java .jar file with name's reference "jar-with-dependencies" using command "java -jar".

```
$java -jar ClipPaymentsEx-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

### About storing the DATA.

This application stores it's data on the file system in plain Text files (.txt), in case you need to store
the data in a DB, I suggest you consider use a NoSQL DB like MongoDB, the reason is because MongoDB stores 
the data similar the format we are treating our information, like JSON objects.

Taking in count the previous comments, we can have the collection of transactions of a single user in the same
document. The next schema is a proposition of how we can model our DB in Mongo for this application. 

We could use an Embedded Relationship as shown.

```
{
   _id: objectId, 
   user_id: Integer,
   payments: [	
      {
         transaction_id:TEXT,
         amount: TEXT,
         date: DATE_TIME,
         description: TEXT 
      },
      {
         transaction_id:TEXT,
         amount: TEXT,
         date: DATE_TIME,
         description: TEXT 
      }
   ]
}
```

## Built With

* Java JDK version (1.8.0_181)
* Eclipse Java EE IDE Photon version (4.8.0)
* Maven version (3.5.8)


## Authors

* **Israel Santiago** - *An assessment for Clip* 


