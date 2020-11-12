# Core

Denne modulen inneholder kjernelogikken til [Foundly](/foundly/README.md).

## Core

### Beskrivelse

Core er domenelogikklaget i prosjektet vårt. Modulen er sentrert rundt Item-klassen. Hver mistet-/funnet-post på oppslagstavla 
får all informasjonen sin, blant annet tittel, mobilnummer, beskrivelse og e-post, lagret i et Item-objekt. 

## Struktur

### Generell struktur
Core-modulen er delt inn på følgende måte:

- **src/** - inneholder selve core-modulen og og har underlagt mappene **main** og **test**
    - **main/java/foundly/core** - inneholder alle ressurser som brukes i core-modulen.
        - **json/** - inneholder klasser for konvertering av Item-objekter til String og motsatt (serialization og deserialization)
        - **model/** - inneholder all informasjon om en mistet/funnet-post, og håndterer responsmelding til rest-api
    - **test/** - inneholder tester for core-modulen
        - **java/foundly/core/**
            - **json/** - tester serializer og deserializer i json-mappen
            - **model/** - tester Item og ResponseMessage i model-mappen

## Klassediagram

I mappen [arkitektur](/foundly/architecture) finnes det et [klassediagram](/foundly/architecture/classdiagram-core.png)
som viser oppførselen og strukturen til klassene i core-modulen.

![klassediagram](/foundly/architecture/classdiagram-core.png)