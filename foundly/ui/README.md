![UI-banner](/resources/javafx_gui.jpg)
# UI

Denne modulen inneholder brukergrensesnittet til [Foundly](/foundly/README.md).

## UI - JavaFX-GUI

### Beskrivelse

UI-modulen er brukergrensesnittet i prosjektet vårt, og er et alternativ til [web-klienten](/foundly/web-client). Dette er et JavaFX-GUI,
som bruker fxml til å bygge brukergrensesnittet. 

Brukergrensesnittet viser en oversikt over gjenstander som enten er tapt eller funnet. Ved å trykke på en av knappene i navigasjons-baren, kan man filtrere gjenstandene etter deres tilstand (mistet/funnet), eller søke etter tekst i søkefeltet.
Videre kan brukeren legge til gjenstander som enten er tapt eller funnet, ved å benytte seg av de to knappene nederst i appen. Dette vil åpne et popup-vindu med felter for nødvendig informasjon som må fylles inn, og mulighet for opplastning av et bilde.


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

### Sekvensdiagram

I mappen [arkitektur](/foundly/architecture) finnes det et [sekvensdiagram](/foundly/architecture/sequencediagram-ui.png)
som viser hvordan JavaFX-klienten samhandler med rest-api for å hente et nytt Item med bildefil.

![sekvensdiagram](/foundly/architecture/sequencediagram-ui.png)

## Testing av kodekvalitet
Det er skrevet enhetstester for modulen som finnes i [her](/foundly/ui/src/test/java/foundly/ui).

I tillegg til enhetstesting, sjekker vi også kodekvaliteten med ulike analyseverktøy. Her har vi brukt [jacoco](https://github.com/jacoco/jacoco), [spotbugs](https://spotbugs.github.io) og [checkstyle](https://checkstyle.sourceforge.io) for bygg med maven.

## Bygging med Maven

### Avhengigheter

**UI-modulen** har følgende avhengigheter:

- [(**core**)](/foundly/core/README.md) - bruker core for å representere Item-objekter
- [(**javafx-controls**)](https://mvnrepository.com/artifact/org.openjfx/javafx-controls) - JavaFx-bibliotek
- [(**javafx-fxml**)](https://mvnrepository.com/artifact/org.openjfx/javafx-fxml) - bruk av FXML-filer
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
