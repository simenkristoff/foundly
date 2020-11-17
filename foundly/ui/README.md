# UI

Denne modulen inneholder brukergrensesnittet til [Foundly](/foundly/README.md).

## UI - JavaFX-GUI

![UI-banner](/resources/javafx_gui.jpg)

### Beskrivelse

UI-modulen er brukergrensesnittet i prosjektet vårt, og er et alternativ til [web-klienten](/foundly/web-client). Dette er et JavaFX-GUI,
som bruker fxml til å bygge brukergrensesnittet. 

Brukergrensesnittet viser en oversikt over gjenstander som enten er tapt eller funnet. Ved å trykke på en av knappene i navigasjons-baren, kan man filtrere gjenstandene etter deres tilstand (mistet/funnet), eller søke etter tekst i søkefeltet. Til høyre for søkefeltet er en *refresh-knapp* som kan brukes til å oppdatere data fra [**REST Api**](/foundly/rest-api/README.md). Data fra REST Api vil også oppdateres automatisk i et gitt tidsintervall - se [**konfigurasjon**](#konfigurasjon).

Videre kan brukeren legge til gjenstander som enten er tapt eller funnet, ved å benytte seg av de to knappene nederst i appen. Dette vil åpne et popup-vindu med felter for nødvendig informasjon som må fylles inn, og mulighet for opplastning av et bilde.

### Interaksjon med REST Api

I mappen [arkitektur](/foundly/architecture) finnes det et [sekvensdiagram](/foundly/architecture/sequencediagram-ui.png) som viser hvordan JavaFX-klienten samhandler med rest-api for å hente et nytt Item med bildefil. Denne sekvensen er vedlagt nedenfor.

I dette tilfellet har brukeren valgt å legge til en bildefil, og derav vil web-klienten sende ut to POST-requests - én for opplastning av bildet, og én for opplastning av Item.

![Sekvensdiagram for ui](/foundly/architecture/sequencediagram-ui.png)

### Konfigurasjon

Klienten leser innstillinger fra filen [**client.properties**](/foundly/ui/src/main/resources/foundly/ui/properties/client.properties), og bruker disse innstillingene i applikasjonen. Implementering av en slik fil gjør det lettere å konfigurere variabler, da man kun trenger å redigere de én plass. Klienten har også en [**backup/default fil**](/foundly/ui/src/main/resources/foundly/ui/properties/default.properties) for innstillinger. Denne filen brukes dersom det oppdages tomme eller udeklarerte felter i **client.properties**, det er dermed viktig at disse innstillingene ikke overskrives.

Følgende innstillinger lar seg konfigurere:

| Navn | Verdi | Standard | Beskrivelse |
| ------ | ------ | ------ | ------ |
| **APP** |  |  |  |
| app.title | String | Foundly | Tittel på applikasjonen | 
| app.width | Double | *720* | Applikasjonens bredde |
| app.height | Double | *640* | Applikasjonens høyde |
| app.maximized | Boolean | *false* | Fullskjerm |
| app.resizable | Boolean | *false* | Justering av størrelse |
| app.loadCounter | Int | *10* | Nedtelling for lasting av applikasjon. *1 = (1/10 sekund)* |
| **Ressurser** |  |  |  |
| resource.icon | String | *img/icons/icon.png* | Path til applikasjonens ikon |
| resource.logo | String | *img/logo.png* | Path til applikasjonens logo |
| **REST API** |  |  |  |
| api.hostname | String | *localhost* | URL til Rest-server |
| api.port | Int | *8098* | Port til Rest-server |
| api.protocol | String | *http* | Protokoll for data-transfer |
| api.resourcePath | String | */img* | Endpoint for ressurser |
| api.timeoutMillis | Int | *1000* | Timeout for tilkoblingstest *(millisekunder)* |
| api.refreshMillis | Int | *60000* | Intervall for oppdatering *(millisekunder)* |
| **LOCALES** |  |  |  |
| locale.dateFormat | String | *dd.MM.YY HH:mm* | Format for datoer |

## Struktur

### Pakker

**UI-modulen** er delt inn på følgende måte:

- [**foundly.ui.container**](/foundly/ui/src/main/java/foundly/ui/container) - oppsett for layout og utseende
- [**foundly.ui.control**](/foundly/ui/src/main/java/foundly/ui/control) - utvidelser av **JavaFx**-controls
- [**foundly.ui.control.form**](/foundly/ui/src/main/java/foundly/ui/control/form) - implementasjon av validering for inputs
- [**foundly.ui.control.validator**](/foundly/ui/src/main/java/foundly/ui/control/validator) - støtte for validering av inputs
- [**foundly.ui.controller**](/foundly/ui/src/main/java/foundly/ui/controller) - controllere for applikasjonen
- [**foundly.ui.dataaccess**](/foundly/ui/src/main/java/foundly/ui/dataaccess) - håndterer interaksjon med [REST Api](/foundly/rest-api/README.md).
- [**foundly.ui.effect**](/foundly/ui/src/main/java/foundly/ui/effect) - pakke med visuelle effekter for bruk i applikasjonen.

### Klassediagram

[Diagrammet](/foundly/architecture/classdiagram-ui.png) nedenfor illusterer modulens essensielle oppbygning og hvordan disse klassene samhandler.

![Klassediagram for Ui](/foundly/architecture/classdiagram-ui.png)

## Testing av kodekvalitet
Det er skrevet enhetstester for modulen som finnes [her](/foundly/ui/src/test/java/foundly/ui). 
Ved kjøring av testene vil **Jacoco** generere en rapport over **testdekingsgrad** i *target*. Rapporten kan sees ved å gå til *ui/target/site/jacoco* og åpne **index.html** i en nettleser - *open with -> Preview* i Gitpod.

I tillegg til enhetstesting, sjekker vi også kodekvaliteten med ulike analyseverktøy. Her har vi brukt [jacoco](https://github.com/jacoco/jacoco), [spotbugs](https://spotbugs.github.io) og [checkstyle](https://checkstyle.sourceforge.io) for bygg med maven.

## Bygging med Maven

### Avhengigheter

**UI-modulen** har følgende avhengigheter:

- [(**core**)](/foundly/core/README.md) - bruker core for å representere Item-objekter
- [(**spring-web**)](https://mvnrepository.com/artifact/org.springframework/spring-web) - Web -ressurser for client-side
- [(**javafx-controls**)](https://mvnrepository.com/artifact/org.openjfx/javafx-controls) - JavaFx-bibliotek
- [(**javafx-fxml**)](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml) - bruk av FXML-filer
- [(**spring-boot-starter-test**)](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test) - Testing med Spring Boot
 [(**junit-jupiter-api**)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api) - JUnit API
- [(**junit-jupiter-engine**)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine) - Kjøring av enhetstester
- [(**mockito-core**)](https://mvnrepository.com/artifact/org.mockito/mockito-core) - Mockito
- [(**testfx-core**)](https://mvnrepository.com/artifact/org.testfx/testfx-core) - core for testing av JavaFx-applikasjoner
- [(**testfx-junit5**)](https://mvnrepository.com/artifact/org.testfx/testfx-junit5) - integrerer TestFx med Junit

### Tillegg

**UI-modulen** har tillegg for:

- [(**maven-compiler-plugin**)](https://maven.apache.org/plugins/maven-compiler-plugin/) - kompilerer source-filene i prosjektet
- [(**javafx-maven-plugin**)](https://github.com/openjfx/javafx-maven-plugin) - kjøring av **JavaFx-applikasjonen**
- [(**maven-surefire-plugin**)](https://maven.apache.org/surefire/maven-surefire-plugin/) - kjøring av enhetstester
- [(**maven-checkstyle-plugin**)](https://checkstyle.sourceforge.io) - sjekking av kodekvalitet med **Checkstyle** 
- [(**spotbugs-maven-plugin**)](https://spotbugs.github.io) - finne bugs i koden med **Spotbugs**
- [(**jacoco-maven-plugin**)](https://github.com/jacoco/jacoco) - testdekningsgrad med **Jacoco**

## Kommandoer

### Kjøring
```
mvn javafx:run -f ui/pom.xml
```
