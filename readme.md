[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2050/gr2050)

![Project banner](resources/banner.png)


<div align="center">
<span><b>Velkommen til gruppe 2050 sitt Gitlab-prosjekt</b></span>
<br>
<sub><b>Utviklet av:</b> Selma Bergstrand, Lea Haug Sandberg, Peter Skaar Nordby, Simen Kristoffersen</sub>
</div>
<hr>

[[_TOC_]]

## Om prosjektet
Prosjektet, **[Foundly](foundly/)**, er konfigurert som et multi-modul prosjekt med maven. Modulene utfyller en trelagsapplikasjon med et [domenelag](foundly/core) (core) , [brukergrensesnitt](foundly/ui) (ui) og [persistens](foundly/rest-api/data) (lagring). Videre benytter applikasjonen et [Rest API](foundly/rest-api) for å håndtere data mellom brukergrensesnitt- og persistens-laget.

### Moduler
Prosjektets fire moduler tilhører pom-filen [foundlyParent](foundly/pom.xml), og er ytteligere beskrevet i egne README-filer.
- **[foundly.core](foundly/core)** utgjør domenelaget, og inneholder standardiserte objekter samt. logikk for å fremstille disse objektene.
- **[foundly.rest-api](foundly/rest-api)** API-laget bygget med [Spring Boot](#spring-boot). Denne modulen har en innebygd Tomcat server og håndterer alle requests sendt til denne serveren.
- **[foundly.ui](foundly/ui)** lokalt brukergrensesnitt bygget med [JavaFx](#javafx)
- **[foundly.web-client](foundly/web-client)** web-brukergrensesnitt bygget med [Vue.js](#vuejs)
##

### Arkitektur

### Teknologier
Prosjektet er bygget på rammeverkene Spring Boot, JavaFx, Vue.js. 

#### Spring Boot
<a href="https://spring.io/">
<img src="/resources/spring-boot-logo.png" alt="Spring Boot Logo" width="150px">
</a>

##### Generelt

<hr>

Spring Boot er et open source, mikroservicebasert Java-nettverk. Spring Boot-rammeverket skaper et fullstendig produksjonsklart miljø som er helt konfigurerbart ved hjelp av den forhåndsbygde koden i kodebasen. Microservice-arkitekturen gir utviklere et fullverdig program, inkludert innebygde applikasjonsservere.

##### Nøkkelfunksjoner

<hr>

| Funksjon | Fordel |
| ------ | ------ |
| **Fleksibelt** | Spring Boot gir en fleksibel måte å konfigurere Java Beans, XML-konfigurasjoner og databasetransaksjoner på. |
| **Prossesering** | Yter kraftig batchbehandling og administrerer REST-endepunkter. |
| **Enkel konfigurering** | I Spring Boot er alt automatisk konfigurert på forhånd, og krever ingen manuelle konfigurasjoner for å virke. |
| **Innebygd serves** | Inkluderer innebygd [Tomcat](http://tomcat.apache.org/) server som ikke krever ytteligere installasjoner for å bruke. |

<hr>

#### JavaFx
<a href="https://openjfx.io/">
<img src="/resources/javafx-logo.png" alt="JavaFx Logo" width="150px">
</a>

##### Generelt

<hr>

JavaFX er et Java-bibliotek som forenkler utvikling av Rich Internet Applications (RIA). Applikasjoner som er skrevet ved hjelp av dette biblioteket, kan kjøres på tvers av ulike plattformer. Applikasjonene kan også kjøres på ulike enheter slik som datamaskiner, mobiltelefoner, TV-er og nettbrett.

Utvikling av brukergrensesnitt (GUI) med JavaFX krever Java AWT og Swing.

##### Nøkkelfunksjoner

<hr>

| Funksjon | Fordel |
| ------ | ------ |
| **FXML** | JavaFX kan lese FXML-filer som er bygget på XML markdown, og kan bruke disse filene til å definere et brukergrensesnitt. |
| **Scene Builder** | JavaFx tilbyr et program kalt Scene Builder, som er et drag-and-drop-grensesnitt og brukes til å utvikle FXML-filer/views. |
| **Innebygde brukergrensesnittkontroller** | JavaFx legger opp til bruk av kontrollere for brukergrensesnitt hvilket legger opp til utvikling av et fullverdig program. |
| **CSS stiler** | JavaFx kan lese CSS-filer og derav bruke CSS-stiler til å utforme applikasjonen. |
| **Rikt sett med API-er** | JavaFx-biblioteket tilbyr et rikt sett med API-er for å utvikle brukergrensesnitt. |

<hr>

#### Vue.js
<a href="https://vuejs.org/">
<img src="/resources/vuejs-logo.png" alt="Vue.js Logo" width="150px">
</a>

##### Generelt

<hr>

Vue er et moderne front-end-rammeverk som bygger på et komponent-basert arkitektur, og forenkler utvikling av et nettsted eller en webapp. Den er lett, fleksibel og enkel å komme i gang med. Vue er et fullstendig i seg selv, men er lagt opp for å inkludere flere plugins til å møte applikasjonens behov i.e. **biblioteksmodularisering**.

Bibliotekmodularisering ved hjelp av et rammeverk er vanlig i frontend-utvikling. Både React og Angular har modularisering. Men det som skiller Vue.js fra andre alternativer er dens "høye frakobling", eller med andre ord - hvor enkelt det er å utvide funksjonalitetene, og hvor god synergien er mellom Vue og inkluderte moduler. For eksempel, hvis vi ønsker å organisere og gjengi små visuelle komponenter, er alt vi trenger Vue.js 'core' bibliotek. Etter hvert som applikasjonen vokser, har vi biblioteker for å administrere ruter som 'vue-router', biblioteker for å administrere den globale tilstanden som 'vuex' eller biblioteker for å bygge responsive webapplikasjoner som 'bootstrap-vue'. I tillegg, hvis applikasjonen vår må optimaliseres eller trenger god SEO, kan vi inkludere biblioteket 'vue-server-rendering'.

##### Nøkkelkvalifikasjoner

<hr>

| Funksjon | Fordel |
| ------ | ------ |
| Virtuell DOM | Endringer i Vue blir ikke gjort i Document Object Model (DOM), men i stedet blir det laget en kopi i form av JavaScript (Viruell DOM). Når endringer i DOM blir gjort vil de først endres i JavaScript, og deretter synkroniseres med den virkelige DOM. Denne prosessen optimaliserer minnebruk, og endringer kan gjøres raskere |
| Databinding | Databindingsfunksjonen hjelper utvikleren med å manipulere eller tilordne verdier til HTML-attributter, endre stil, tildele klasser ved hjelp av et bindingsdirektiv som kalles v-bind. |
| Komponent arkitektur | Komponenter brukes til å lage tilpassede elementer, som kan gjenbrukes i HTML. |
| Håndtering av Events | Attributtet v-on kan legges til DOM-elementene for å lytte til hendelser/events i Vue. |

<hr>

## Maven

Vårt bygg har tillegg for:
- oppsett av java [(**maven-compiler-plugin**)](https://maven.apache.org/plugins/maven-compiler-plugin/)
- testing [(**maven-surefire-plugin**)](https://maven.apache.org/surefire/maven-surefire-plugin/)
- samkjøring av node-kommandoer med maven-kommandoer [(**frontend-maven-plugin**)](https://github.com/eirslett/frontend-maven-plugin)
- flytting av web-ressurser til Tomcat public-mappe [(**maven-resources-plugin**)](https://maven.apache.org/plugins/maven-resources-plugin/)
- kjøring av Spring Boot Rest API [(**spring-boot-maven-plugin**)](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/html/)
- kjøring av javafx [(**javafx-maven-plugin**)](https://github.com/openjfx/javafx-maven-plugin)
- sjekking av kodekvalitet med checkstyle [(**maven-checkstyle-plugin**)](https://checkstyle.sourceforge.io) og spotbugs [(**spotbugs-maven-plugin**)](https://spotbugs.github.io)
- testdekningsgrad med jacoco [(**jacoco-maven-plugin**)](https://github.com/jacoco/jacoco)

## Kjøring av prosjektet
1. Åpne prosjektet i gitpod ved hjelp av knappen øverst.
2. Fra **/workspace/gr2050/foundly**, skriv kommandoen **`mvn clean install`** i ett av de ledige terminalvinduene.
3. For å starte Rest API brukes kommandoen **`mvn spring-boot:run -f rest-api/pom.xml`**.
4. Gå deretter til det ledige terminalvinduet, og kjør kommandoen **`mvn javafx:run -f ui/pom.xml`** for å starte JavaFx-klienten.
5. Åpne port **`6080`** for å se JavaFx-klienten.
6. Åpne port **`8098`** for å se Web-klienten.
7. Legg inn hittegodset ditt!

