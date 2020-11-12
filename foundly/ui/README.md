![UI-banner](foundly/resources/javafx-gui.jpg)
# UI

Denne modulen inneholder brukergrensesnittet til [Foundly](/foundly/README.md).

## UI - JavaFX-GUI

### Beskrivelse

UI-modulen er brukergrensesnittet i prosjektet vårt, og er et alternativ til [web-klienten](/foundly/web-client). Dette er et JavaFX-GUI,
som bruker fxml til å bygge brukergrensesnittet. 

Brukergrensesnittet viser en oversikt over gjenstander som enten er tapt eller funnet. Ved å trykke på en av knappene i navigasjons-baren, kan man filtrere gjenstandene etter deres tilstand (mistet/funnet), eller søke etter tekst i søkefeltet.
Videre kan brukeren legge til gjenstander som enten er tapt eller funnet, ved å benytte seg av de to knappene nederst i appen. Dette vil åpne et popup-vindu med felter for nødvendig informasjon som må fylles inn, og mulighet for opplastning av et bilde.


## Struktur

### Generell struktur
UI-modulen er delt inn på følgende måte:

- **src/** - inneholder selve ui-modulen og og har underlagt mappene **main** og **test**
    - **main/** - inneholder alle ressurser som brukes i ui-modulen.
        - **java/foundly/ui** -
            - **container/** - oppsett for layout og utseende
            - **control/** - validering av input
            - **controller/** - kontrollere som styrer hendelser i appen
            - **dataaccess/** - kobling til rest-api
            - **effect/** - utseende-effekter
            - **App.java** - Launcher-applikasjonen for prosjektet
            - **SplashScreen.java** - Kjører mens hovedapplikasjonen laster
        - **resources/foundly/ui/** -
            - **css/** - inneholder css-stilark
            - **img/** - inneholder bildefilene
            - **views/** - inneholder fxml-filene
    - **test/** - inneholder tester for ui-modulen
        - **java/foundly/ui/**
            - **controller** - inneholder tester for controllerene
            - **dataaccess** - inneholder tester for dataaccess-klassene
            - **AppTest.java** - test for AppTest.java

## Sekvensdiagram

I mappen [arkitektur](/foundly/architecture) finnes det et [sekvensdiagram](/foundly/architecture/sequencediagram-ui.png)
som viser hvordan JavaFX-klienten samhandler med rest-api for å hente et nytt Item med bildefil.

![sekvensdiagram](/foundly/architecture/sequencediagram-ui.png)

## Testing av kodekvalitet
I tillegg til enhetstesting, tester vi også kodekvaliteten med ulike analyseverktøy. Her har vi brukt [jacoco](https://github.com/jacoco/jacoco), [spotbugs](https://spotbugs.github.io) og [checkstyle](https://checkstyle.sourceforge.io) for bygg med maven.