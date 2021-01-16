# Metamorphic testing
**Sorgente originario:** https://github.com/spring-projects/spring-petclinic

Il materiale prodotto dal sottoscritto è presente nelle classi di test (src/test), sovrascritte da quelle precedenti.

Progetto sviluppato per l'attività di tirocinio svolta presso il Corso di Laurea Triennale in Informatica presso l'Università degli studi di Napoli Federico II.

Anno accademico: 2020/2021
##Breve Descrizione
Il progetto prevede l'uso della tecnica di metamorphic testing per testare l'applicativo PetClinic, il quale ha svolto il ruolo di _sample_, dato che, è servito per imparare ad usare quest'approccio al testing, che al sottoscritto (prima dell'inizio del progetto) era sconosciuto.

Il test si focalizza sulla verifica di una determinata sezione del sorgente: Le operazione CRUD effettuabili sul Model della classe Pet.

Il testing prevede, partendo da dei casi di test già registrati _(source)_ di creare a **runtime** dei casi di test automatici _(follow-up)_
##Panoramica delle tecnologie

###Petclinic
Il sorgente è una _**web application**_ di gestione di una clinica veterinaria, permette l'aggiunta/ricerca dei proprietari di animali, nonchè l'aggiunta di quest'ultimi e delle loro visite alla clinica.

Il tutto è costruito con vari framework di Spring:
Spring Server
Spring Boot
Spring Authentication

L'applicativo permette di scegliere di salvare il DBMS con tecnologia in-memory (H2) oppure in modo tradizionale (MySql)
###Registrazione e creazione dei test
Per la creazione dei test case sono state usate ampiamente le librerie selenium e l'uso dei vari driver per la loro esecuzione.

In particolare, si è usato Selenium IDE per usare una registrazione di tipo capture & replay sui test source; nonchè utilizzato il formato di salvattaggio di questi test (File JSON) per costruire un'interpetre in grado di prendere qualsiasi progetto di Selenium IDE e costruire dei test case in ambiente Java JUnit con leggere variazioni sull'input.

...

##Note

NON E' CONSENTITO IL RIUSO ANCHE PARZIALE DI CODICE E DOCUMENTAZIONE CREATA DAL SOTTOSCRITTO PRESENTE IN QUESTA PAGINA.

