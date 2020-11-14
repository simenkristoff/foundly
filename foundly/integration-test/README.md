# Integrasjonstesting

Denne modulen håndterer integrasjonstesting av modulene i [**Foundly**](/foundly/README.md).

[[_TOC_]]

## Om testen
Tester integrasjonen av [**core**](/foundly/core/README.md), [**REST Api**](/foundly/rest-api/README.md) og [**JavaFx-klienten (ui)**](/foundly/ui/README.md). Integrasjonstesten starter REST Api med en ny database som lagres i lokalt minne under testingen. Testens hensikt er å forsikre om at klienten og REST Api forveksler data til og fra databasen uten problemer. 

Testen er rigget til å legge inn en gjenstand som er tapt, og deretter verifisere at det er blitt lagt til nøyaktig én gjenstand. Deretter legges det til en gjenstand som er funnet, og det verifiseres at det er blitt lagt til to gjenstander - én tapt og én funnet.

Til slutt testes filtrering av disse gjenstandene. Testen skal da verifisere at det ligger kun én gjenstand i hvert filter (tapt/funnet).

## Bygging med Maven

### Avhengigheter

**Integrasjonstesten** har følgende avhengigheter:

- [(**core**)](/foundly/core/README.md) - domenelogikk
- [(**rest-api**)](/foundly/rest-api/README.md) - tjeneste- og persistenslaget
- [(**ui**)](/foundly/ui/README.md) - brukergrensesnitt
- [(**junit-jupiter-api**)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-api) - JUnit API
- [(**junit-jupiter-engine**)](https://mvnrepository.com/artifact/org.junit.jupiter/junit-jupiter-engine) - Kjøring av enhetstester
- [(**mockito-core**)](https://mvnrepository.com/artifact/org.mockito/mockito-core) - Mockito
- [(**testfx-core**)](https://mvnrepository.com/artifact/org.testfx/testfx-core) - core for testing av JavaFx-applikasjoner
- [(**testfx-junit5**)](https://mvnrepository.com/artifact/org.testfx/testfx-junit5) - integrerer TestFx med Junit

### Tillegg

**Core-modulen** har tillegg for:

- [(**maven-compiler-plugin**)](https://maven.apache.org/plugins/maven-compiler-plugin/) - kompilerer source-filene i prosjektet
- [(**spring-boot-maven-plugin**)](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/) - kjøring av **Spring Boot Rest API**
- [(**maven-failsafe-plugin**)](https://maven.apache.org/surefire/maven-failsafe-plugin/) - Kjøring av integrasjonstester

## Kommandoer

### Kjøring
Testen kjøres under Maven Default Lifecycle, men den kan også kjøres alene ved å bruke følgende kommando:
```
mvn verify -f integration-test/pom.xml
```

### Hoppe over integrasjonstest
For å hoppe hover **integrasjonstesten** kan argumentet **-Dit.skip=true** legges til bak en kommando.

For eksempel:
```
mvn clean install -Dit.skip=true
```
