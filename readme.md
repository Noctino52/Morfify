# Status del progetto  [![License](https://img.shields.io/github/license/Noctino52/Morfify)](LICENSE.md) [![GitHub issues](https://img.shields.io/github/issues/Noctino52/Morfify)](https://github.com/Noctino52/Morfify/issues) [![](https://img.shields.io/badge/Informatica%20Unina-project%20-32)](http://informatica.dieti.unina.it/index.php/it/)

Morfify è ancora in una sua prima versione sperimentale, eventuali nuove versioni verranno rilasciate qui.

# Morfify
> A Metamorphic Test solution for Web Application
> Progetto sviluppato per l'attività di tirocinio e tesi di laurea triennale svolta presso il Corso di Laurea Triennale in Informatica presso l'Università degli studi di Napoli Federico II.
> Anno accademico: 2020/2021

*Read this in other languages: [English](README.EN.md).*

# Come iniziare
L'applicativo attualmente non presenta installer e và importato nel proprio IDE per essere anche solo eseguito.
Bisogna inanzitutto [creare una nuova repository Git](https://docs.github.com/en/articles/creating-a-new-repository), effettuare una clonazione del contenuto della repository originale, per poi effettaure il *mirror* con la copia locale.
Tramite *command line* di Git:

```
$ git clone --bare https://github.com/Noctino52/Morfify
$ cd Morfify
$ git push --mirror https://github.com/Noctino52/Morfify
$ cd ..
$ rm -rf Morfify
```

# Breve Descrizione
Morfify è un ambiente di metamorphic testing per applicazioni web.
Utilizza la tecnica di Metamorphic Testing per testare potenzialmente tutte le  funzionalità di qualsiasi programma, partendo semplicemente da un caso di test (Source test case) registrato con Selenium IDE.  
Grazie all'utilizzo di una Metamorphic Relation, ovvero una proprietà espressa in codice di una funzionalità del programma previsto, Morfify è in grado di creare dei test case con leggere variazioni sull'input, chiamati Follow-up test case e di verificare se questi test siano "simili" in termini di contenuti delle pagine visitate durante la loro esecuzione.  

Per mostrare le potenzialità del programma, è presente un'esempio d'uso con la web application di sample PetClinic.
## [Selenium](https://www.selenium.dev/)
È un framework di testing ed indica una suite, composta da diversi strumenti: Selenium IDE,  Selenium WebDriver, Selenium Builder,Selenium Server, Selenium Grid; nel nostro caso, sono stati utilizzati solo i primi due.  
Fornisce un linguaggio di dominio (DSL), ovvero un linguaggio di specifica dedicato a  problemi di un dominio specifico o a una particolare soluzione tecnica.

Permette di scrivere test per alcuni tra i maggiori linguaggi di programmazione, tra cui Java, Python, Ruby, C\# e PHP.  
Si tratta infine di uno strumento multi piattaforma, disponibile per Windows, Linux e Mac.  
## [Petclinic](https://github.com/spring-projects/spring-petclinic) 
Il sorgente è una _**web application**_ di gestione di una clinica veterinaria, permette l'aggiunta/ricerca dei proprietari di animali, nonchè l'aggiunta di quest'ultimi e delle loro visite alla clinica.

Il tutto è costruito con vari framework di Spring:
- Spring Server
- Spring Bootmk 
- Spring Authentication

L'applicativo permette di scegliere di salvare il DBMS con tecnologia in-memory (H2) oppure in modo tradizionale (MySql)
## Architettura del sistema 
Morfify è diviso in 5 componenti principali:

![ComponentiMorfify](https://user-images.githubusercontent.com/20641545/118481966-66fcbb00-b714-11eb-8040-3a3d733ba957.png)

1.**Creazione Source Test case**: E' la componente che si occupa di prendere in input il test case da dover eseguire, si tratta di un parser JSON (TestCaseCreator) che prende in input un file generato da Selenium IDE e lo trasforma in un oggetto della classe TestCase; banalmente è la componente che porta l'input dentro all envirement.  
2.**Creazione Follow-Up Test cases**: E' la componente che si occupa, partendo da una copia del caso di test sorgente, di applicare le modifiche imposte dall'insieme delle regole date in input.L'output del sistema in questione è sempre uno o più oggetti TestCase definito come un Follow-Up.  
3.**Esecuzione Test Case**: Grazie alle API di Selenium WebDriver, vengono fatti eseguire i casi di test sia quelli d'input che quelli generati.  
4.**Raccolta delle pagine**: Durante l'esecuzione, è affiancato un listner che tiene traccia dei cambiamenti di pagina, sia da un punto di vista di codice (HTML,CSS etc...) che banalmente visivo (Crea uno screenshot per ogni pagina), l'esecuzione di un test case diventa una lista ordinata di pagine che vengono visitate e sarà parte integrante dell'output.  
5.**Verifica**: Tramite L'informazione raccolte durante l'esecuzione di Source e Follow-Up test cases, si mettono a confronto le pagine visitate dal sorgente e quelle dei vari Follow-up; le pagine devono essere "quasi-simili" tra di loro altrimenti si sarà verificato un Faulty del Source test case.  

## Struttura delle directory
Nella cartella *src* è presente sia l'applicativo di PetClinic (directory *main* in poi) che l'ambiente PetClinic (directory *test* in poi).  
La cartella *testFile* è dove verranno presi i file *.side* registrati con Selenium IDE (modificabile dal main).  
La cartella *documentazione* contiene tutto il materiale prodotto attorno a Morfify.  
La cartella *Output* contiene una lista ordinata di pagine per tutti i Source e Follow-Up test case creati e prodotti durante l'esecuzione.  
## Documentazione
Per ulteriori approffondimenti, fare riferimento ad il capitolo tre,quattro e cinque della [tesi di laurea](documentazione/Tesi.pdf) associata a questo progetto.
## Autori
[Ivan Capasso](https://github.com/Noctino52/Morfify) (Founder)

[Luigi Libero Lucio Starace](https://github.com/luistar) (QA)

### Copyright

>Copyright (c) the respective contributors, as shown by the AUTHORS file.
>
>This program is free software: you can redistribute it and/or modify
>it under the terms of the GNU Affero General Public License as published
>by the Free Software Foundation, either version 3 of the License, or
>(at your option) any later version.
>
>This program is distributed in the hope that it will be useful,
>but WITHOUT ANY WARRANTY; without even the implied warranty of
>MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
>GNU Affero General Public License for more details.
>
>You should have received a copy of the GNU Affero General Public License
>along with this program.  If not, see <http://www.gnu.org/licenses/>.
