# Rest-api

Denne modulen utgjør tjenestelaget for [Foundly](/foundly/README.md).

[[_TOC_]]

## Rest-api

### Beskrivelse

**Rest-api** er bygget med [Spring Boot](/README.md/#spring-boot), og har en innebygd **Tomcat-server** og en **H2 Database**. Modulen håndterer requests fra [**Web-client**](/foundly/web-client/README.md) og [**ui**](/foundly/ui/README.md), og støtter metoder som **POST**, **GET**, **PUT**, **DELETE**.

Modulen fungerer som et mellomledd mellom klientene og deres interaksjoner med databasen. Via http-requests kan klientene hente ut eller oppdatere data i databasen. Rest-api prosesserer requestene og benytter [**persistenslaget (JPA)**](#avhengigheter) til å håndtere data i databasen. Deretter returnerer rest-api en respons til klienten, enten i form av data hentet ut fra databasen eller en melding.

#### Konfigurasjon for REST Serveren

Konfigurasjonsfilen ligger i modulens ressursmappe og heter [**application.properties**](/foundly/rest-api/src/main/resources/application.properties). 

- Serveren er konfigurert til å kjøre på port **8098**
- Databasen lagrer data til filen [**foundlydb**](/foundly/rest-api/data/foundlydb.mv.db)
- Grensesnitt for databasen kan sees i nettleser ved å legge til **/h2_console** bakerst på URL-adressen til rest-api. - f.eks: 'http://localhost:8098/h2_console'
    - **brukernavn**: admin
    - **passord**: foundly



### Sekvenser

#### Interaksjon mellom ui og rest-api

Nedenfor vises sekvensen når en bruker legger til en ny gjenstand med **JavaFx-appen** i ui. I dette tilfellet har brukeren valgt å legge til en bildefil, og derav vil ui-klienten sende ut to POST-requests - én for opplastning av bildet, og én for opplastning av Item.

![Sekvensdiagram for ui](/foundly/architecture/sequencediagram-ui.png).
#### Interaksjon mellom web-client og rest-api

Nedenfor vises sekvensen når en bruker legger til en ny gjenstand med **web-klienten**. I dette tilfellet har brukeren valgt å legge til en bildefil, og derav vil web-klienten sende ut to POST-requests - én for opplastning av bildet, og én for opplastning av Item.

![Sekvensdiagram for web-client](/foundly/architecture/sequencediagram-web-client.png).

## Struktur

### Pakker

**Rest-api**-modulen er delt inn på følgende måte:

- [**foundly.restapi.presentation**](/foundly/rest-api/src/main/java/foundly/restapi/presentation) - her ligger filene som håndterer end-points
- [**foundly.restapi.service**](/foundly/rest-api/src/main/java/foundly/restapi/service) - håndterer domenelogikken i rest-api 
- [**foundly.restapi.persistence**](/foundly/rest-api/src/main/java/foundly/restapi/persistence) - håndterer persistens-spesifikke operasjoner
- [**foundly.restapi.entity**](/foundly/rest-api/src/main/java/foundly/restapi/entity) - denne pakken inneholder enheter som brukes av modulen. F.eks. **ResponseMessage**
- [**foundly.restapi.exception**](/foundly/rest-api/src/main/java/foundly/restapi/exception) - tilpassede unntak
- [**foundly.restapi.exception.advice**](/foundly/rest-api/src/main/java/foundly/restapi/exception/advice) - printer meldinger knyttet til et unntak til loggen

### Lagring

- [**uploads/**](/foundly/rest-api/uploads) - mappe for bildeopplastninger
- [**data/**](/foundly/rest-api/data) - inneholder SQL-filene med lagret data som brukes av **H2 Databasen**.

### Database

Databasen har én tabell kalt **ITEMS**, og har følgende struktur:

<sub>* **INDEX** primær-nøkkel</sub>

| ID* | TITLE | DESCRIPTION | STATE | EMAIL | PHONE | DATE | IMAGE |
| ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| BIGINT | VARCHAR | VARCHAR | ENUM: [LOST\|FOUND] | VARCHAR | VARCHAR | TIMESTAMP | VARCHAR |

**Eksempel på tabell med oppførte records.**

| ID* | TITLE | DESCRIPTION | STATE | EMAIL | PHONE | DATE | IMAGE |
| ------ | ------ | ------ | ------ | ------ | ------ | ------ | ------ |
| 1 | Funnet mobil | Fant en iPhone 9 på R2 i dag tidlig | FOUND | xxx@gmail.com | 92381124 | 2020-11-12 11:18:47.94716 | iphone.png |
| 2 | Mistet mobil | Mistet mobilen min på R2 i dag | LOST | yyy@gmail.com | 94421528 | 	2020-11-11 15:17:22.504222 | null |

### Klassediagram
[Diagrammet](/foundly/architecture/classdiagram-rest-api.png) nedenfor illusterer modulens essensielle oppbygning og hvordan disse klassene samhandler.

![Klassediagram for REST Api](/foundly/architecture/classdiagram-rest-api.png)

## Testing av kodekvalitet
Det er skrevet enhetstester for modulen som finnes i [her](/foundly/restapi/src/test/java/foundly/restapi).

I tillegg til enhetstesting, sjekker vi også kodekvaliteten med ulike analyseverktøy. Her har vi brukt [jacoco](https://github.com/jacoco/jacoco), [spotbugs](https://spotbugs.github.io) og [checkstyle](https://checkstyle.sourceforge.io) for bygg med maven.

## Bygging med Maven

### Avhengigheter

**Rest-api**-modulen har følgende avhengigheter:

- [(**core**)](/foundly/core/README.md) - bruker core for å representere Item-objekter
- [(**spring-boot-starter-data-jpa**)](https://www.javatpoint.com/spring-boot-starter-data-jpa) - utgjør persistenslaget i rest-api, og håndterer CRUD-funksjoner til og fra SQL-databasen
- [(**spring-boot-starter-web**)](https://www.javatpoint.com/spring-boot-starter-web) - ressurser for Spring Boot Web som brukes av Rest Api bla. Tomcat-server.
- [(**h2**)](https://www.h2database.com/html/main.html) - Java SQL database
- [(**spring-boot-starter-test**)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test) - Testing med Spring Boot
- [(**junit-jupiter-api**)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api) - JUnit API 
- [(**junit-jupiter-engine**)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine) - Kjøring av JUnit-tester
- [(**mockito-core**)](https://www.javatpoint.com/spring-boot-starter-web) - Mockito

### Tillegg

**Rest-api**-modulen har tillegg for:

- [(**spring-boot-maven-plugin**)](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/) - kjøring av **Spring Boot Rest API**
- [(**maven-compiler-plugin**)](https://maven.apache.org/plugins/maven-compiler-plugin/) - kompilerer source-filene i prosjektet
- [(**maven-resources-plugin**)](https://maven.apache.org/plugins/maven-resources-plugin/) - flytting av web-client-byggget til Tomcat sin public-mappe 
- [(**maven-surefire-plugin**)](https://maven.apache.org/surefire/maven-surefire-plugin/) - kjøring av enhetstester
- [(**maven-checkstyle-plugin**)](https://checkstyle.sourceforge.io) - sjekking av kodekvalitet med **Checkstyle** 
- [(**spotbugs-maven-plugin**)](https://spotbugs.github.io) - finne bugs i koden med **Spotbugs**
- [(**jacoco-maven-plugin**)](https://github.com/jacoco/jacoco) - testdekningsgrad med **Jacoco**

## Kommandoer

### Kjøring
Kjører i gang rest-api med server
```
mvn spring-boot:run
```

### Start
Starter rest-api. I motsetning til `run` hindrer ikke denne funksjonen andre *mål* fra å kjøre på applikasjon, og brukes deriblant til kjøring av integrasjonstester. 
```
mvn spring-boot:start
```

### Stop
Stopper rest-api etter at det har blit startet med *start*-målet. Brukes etter hver test.
```
mvn spring-boot:stop
```

### Testing
For å teste denne modulen spesifikt:
```
test -f rest-api/pom.xml
```
