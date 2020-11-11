![Web-client banner](/resources/foundly_web_client.jpg)

# Web-client

Denne modulen inneholder web-klienten for [Foundly](/foundly/README.md).

[[_TOC_]]

## Web-klient

### Beskrivelse

Web-klienten representerer [brukergrensesnittet](/foundly/ui/README.md) for web-applikasjoner, og er bygget med rammeverket [Vue.js](/README.md/#vuejs). Web-klienten viser en oversikt over gjenstander som er enten tapt eller funnet. Ved å trykke på en av knappene navigasjons-baren kan man filtrere gjenstandene etter deres tilstand (mistet/funnet), eller søke etter tekst i søkefeltet.

Videre kan brukeren legge til gjenstander som enten er tapt eller funnet, ved å benytte seg av de to knappene i nettsidens footer-seksjon. Dette vil åpne et popup-vindu med felter for nødvendig informasjon som må fylles inn, og mulighet for opplastning av et bilde.

### Responsitivt design/utforming

Nettsiden er utformet med hensyn på brukervennlighet for alle nettlesere og enheter til dags dato. Som vist i bildet nedenfor vil nettsiden tilpasse seg enheten og nettleseren som brukes, nettopp for å gi brukeren en optimal brukeropplevelse.

![Responsive design](/resources/foundly_responsive_view.png)

### Plugins
- [**Axios**](https://github.com/axios/axios) - http-bibliotek brukt for å sende requests til Rest-API.
- [**Bootstrap**](https://getbootstrap.com/) - CSS-rammeverk brukt for utforming av *look and feel*.
- [**Bootstrap Vue**](https://bootstrap-vue.org/) - prosjektet bruker pakkens ikoner som samsvarer med Bootstrap *look and feel*.
- [**jQuery**](https://jquery.com/) - forenkler skriving JavaScript-funksjoner som ellers ville krevd mange linjer.
- [**Moment**](https://momentjs.com/) - brukes til formattering av dato og klokkeslett.
- [**Vue-router**](https://router.vuejs.org/) - håndterer nettstedets url-adresser og sørger for å levere riktig view/komponent til en gitt adresse.
- [**Vuelidate**](https://vuelidate.js.org/) - brukes for validering av input. 


## Struktur

### Generell struktur
Web-klienten er delt inn på følgende måte:

- **static/** - inneholder de ferdig kompilerte filene som genereres når web-klienten bygges for produksjon.
- **src/** - inneholder selve web-klienten og og har underlagt mappene **assets**, **components** og **services/**
    - **assets/** - inneholder alle ressurser som brukes web-klienten.
        - **img/** - bildefiler
        - **js/** - JavaScript-filer
        - **sass/** - moduler for web-klienten sitt stilark
    - **components/** - komponenter
    - **services/** - klasser/tjenester
- **tests/** - inneholder alle testene for web-klienten
    - **e2e/** - end-to-end tester (e2e) bygget på [**Nightwatch**](#end-to-end-med-nightwatch)
    - **resources/** - testressurser som kan benyttes av testene
    - **unit/** - enhetstester bygget på [**Jest**](#enhetstesting-med-jest)

### Struktur på SASS/stilark

For utforming av **stilark** benytter web-klienten seg av **SASS** (Syntactically Awesome Style Sheets) fremfor **CSS**. SASS er en CSS preprosessor, som legger til rette for bruk av spesielle funksjoner som variabler, nestede regler og mixins til vanlig CSS. Ved bruk av SASS kan man også dele opp SASS-filene i mindre moduler, for så å importere de inn i én fil før stilene prosesseres. Vi valgte å bruke denne teknologien, da dette gjør kodingsprosessen enklere og mer effektiv.

Struktur for stilarkets moduler:

- **style.scss** - er filen hvor alle stilene samles og kompileres til én fil.
- **abstracts/** - abstracte filer som indirekte påvirker stilene
    - **variables/** - globale variabler
- **components/** - stiler for komponenter
- **elements/** - stiler for grunnleggende HTML-elementer
- **layout/** - nettsidens oppsett/layout
- **typography** - stiler for skrift/fonts

## Testing
Prosjektet er rigget med to type tester, enhets- og end-to-end-tester.

#### Enhetstesting med Jest
Jest er et JavaScript-testverktøy opprettet av Facebook som håndterer kjøring av tester og verifisering av 'assertions'. Enhetstestene kan kjøres med kommandoen **`npm run test:unit`**

#### End-to-end med Nightwatch
E2e-testene er designet for å teste web-klientens funksjonalitet i sin helhet. For å få til dette har vi brukt Nightwatch. Nightwatch er et automatisert testrammeverk for webapplikasjoner og nettsteder, skrevet i Node. js. E2e-testene kan kjøres med kommandoen **`npm run test:e2e`**

## Bygging med maven

Modulen er bygget med tillegg for:
- samkjøring av node-kommandoer med maven-kommandoer [(**frontend-maven-plugin**)](https://github.com/eirslett/frontend-maven-plugin)

## Kommandoer

### Installasjon
Installerer alle nødvendige pakker/node_modules
```
npm install
```

### Testing

#### Enhetstest
```
npm run test:unit
```

#### E2e-test
```
npm run test:e2e
```

### Utvikling
Kompilerer prosjektet og oppdaterer bygget ved endring av kode. Kjøres lokalt på port **8080**.
```
npm run serve
```

### Bygging for produksjon
Bygger prosjektet for bruk i produksjon og minimaliserer koden.
```
npm run build
```

### Linting
Sjekker kodekvalitet/linter prosjektet.
```
npm run lint
```

### Reset
Resetter prosjektet/sletter node_modules-mappen
```
npm run clean
```
