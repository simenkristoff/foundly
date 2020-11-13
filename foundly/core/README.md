# Core

Denne modulen inneholder kjernelogikken til [Foundly](/foundly/README.md).

[[_TOC_]]

## Core

### Beskrivelse

Core er domenelogikklaget i prosjektet vårt. Modulen er sentrert rundt Item-klassen. Hver mistet-/funnet-post på oppslagstavla 
får all informasjonen sin, blant annet tittel, mobilnummer, beskrivelse og e-post, lagret i et Item-objekt. 

## Struktur

### Pakker

**Core-modulen** er delt inn på følgende måte:

- [**foundly.core.json**](/foundly/core/src/main/java/foundly/core/json) - inneholder klasser for konvertering av Item-objekter til String og motsatt (serialization og deserialization)
- [**foundly.core.model**](/foundly/core/src/main/java/foundly/core/model) - inneholder objekter som er sentrale i applikasjon bla. **Item** som representerer en funnet/tapt gjenstand.

### Klassediagram

I mappen [arkitektur](/foundly/architecture) finnes det et [klassediagram](/foundly/architecture/classdiagram-core.png)
som viser oppførselen og strukturen til klassene i core-modulen.

![klassediagram](/foundly/architecture/classdiagram-core.png)

## Testing av kodekvalitet
Det er skrevet enhetstester for modulen som finnes i [her](/foundly/core/src/test/java/foundly/core).

I tillegg til enhetstesting, sjekker vi også kodekvaliteten med ulike analyseverktøy. Her har vi brukt [jacoco](https://github.com/jacoco/jacoco), [spotbugs](https://spotbugs.github.io) og [checkstyle](https://checkstyle.sourceforge.io) for bygg med maven.

## Bygging med Maven

### Avhengigheter

**Core-modulen** har følgende avhengigheter:

- [(**spring-boot-starter-data-jpa**)](https://www.javatpoint.com/spring-boot-starter-data-jpa) - brukes for å deklarere objektene som ligger i pakken **foundly.core.model**, slik at de kan lagres i databasen.
- [(**spring-boot-starter-web**)](https://www.javatpoint.com/spring-boot-starter-web) - ressurser for Spring Boot Web. Brukes for å serialize og deserialize objekter i **core**.

### Tillegg

**Core-modulen** har tillegg for:

- [(**maven-compiler-plugin**)](https://maven.apache.org/plugins/maven-compiler-plugin/) - kompilerer source-filene i prosjektet
- [(**maven-surefire-plugin**)](https://maven.apache.org/surefire/maven-surefire-plugin/) - kjøring av enhetstester
- [(**maven-checkstyle-plugin**)](https://checkstyle.sourceforge.io) - sjekking av kodekvalitet med **Checkstyle** 
- [(**spotbugs-maven-plugin**)](https://spotbugs.github.io) - finne bugs i koden med **Spotbugs**
- [(**jacoco-maven-plugin**)](https://github.com/jacoco/jacoco) - testdekningsgrad med **Jacoco**
