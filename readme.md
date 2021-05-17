# Morfify: A Metamorphic Test solution for Web Application

Progetto sviluppato per l'attività di tirocinio svolta presso il Corso di Laurea Triennale in Informatica presso l'Università degli studi di Napoli Federico II.
Anno accademico: 2020/2021

## Breve Descrizione
Morfify è un ambiente di metamorphic testing per applicazioni web.
Utilizza la tecnica di Metamorphic Testing per testare potenzialmente tutte le  funzionalità di qualsiasi programma, partendo semplicemente da un caso di test (Source test case) registrato con Selenium IDE.

Grazie all'utilizzo di una Metamorphic Relation, ovvero una proprietà espressa in codice di una funzionalità del programma previsto, Morify è in grado di creare dei test case con leggere variazioni sull'input, chiamati Follow-up test case e di verificare se questi test siano "simili" in termini di contenuti delle pagine visitate durante la loro esecuzione.

Come esempio d'uso, è presente Petclinic, il quale ha svolto il ruolo di _sample_, dato che, è servito per imparare ad usare quest'approccio al testing, che al sottoscritto (prima dell'inizio del progetto) era sconosciuto.
## Panoramica delle tecnologie
###Selenium
**Sito web:** https://www.selenium.dev/
È un framework di testing ed indica una suite, composta da diversi strumenti: Selenium IDE,  Selenium WebDriver, Selenium Builder,Selenium Server, Selenium Grid; nel nostro caso, sono stati utilizzati solo i primi due.
Fornisce un linguaggio di dominio (DSL), ovvero un linguaggio di specifica dedicato a  problemi di un dominio specifico o a una particolare soluzione tecnica.

Permette di scrivere test per alcuni tra i maggiori linguaggi di programmazione, tra cui Java, Python, Ruby, C\# e PHP.
Si tratta infine di uno strumento multi piattaforma, disponibile per Windows, Linux e Mac.
### Petclinic
**Sorgente originario:** https://github.com/spring-projects/spring-petclinic
Il sorgente è una _**web application**_ di gestione di una clinica veterinaria, permette l'aggiunta/ricerca dei proprietari di animali, nonchè l'aggiunta di quest'ultimi e delle loro visite alla clinica.

Il tutto è costruito con vari framework di Spring:
Spring Server
Spring Boot
Spring Authentication

L'applicativo permette di scegliere di salvare il DBMS con tecnologia in-memory (H2) oppure in modo tradizionale (MySql)
## Architettura del sistema
![ComponentiMorfify](https://user-images.githubusercontent.com/20641545/118481966-66fcbb00-b714-11eb-8040-3a3d733ba957.png)

...

## Note

