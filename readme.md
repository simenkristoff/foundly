[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2050/gr2050)

# Foundly
Dette prosjektet er konfigurert med maven og er bygget opp med tre lag; domenelag, brukergrensesnitt (UI) og persistens. Prosjektet inneholder tester for alle tre lag.

## Organisering
Prosjektet **[Foundly](foundly/)** er organisert en parent-modul (foundlyParent) og tre submoduler; core, ui, database

- **foundly/core** for kode og ressurser til kjernelogikk
- **foundly/database** for kode og ressurser til database - vårt persistenslag
- **foundly/ui** for kode og ressurser til brukergrensesnitt
##

## Domenelaget

Pakken **[foundly.core](foundly/core)** utgjør domenelaget i denne applikasjonen.
Domenelaget inneholder alle klasser og logikk knyttet til dataene som applikasjonen handler om og håndterer, samt tester og ressurser til disse klassene.

 

## Brukergrensesnittlaget

Pakken **[foundly.ui](foundly/ui/)** utgjør brukergrensesnittlaget i denne applikasjonen.
Brukergrensesnittlaget inneholder alle klasser og logikk knyttet til visning og handlinger på dataene i domenelaget.


## Persistenslaget

Pakken **[foundly.database](foundly/database)** utgjør persistenslaget i denne applikasjonen.
Persistenslaget inneholder alle klasser og logikk for lagring (skriving og lesing) av dataene i domenelaget. Vårt persistenslag implementerer lagring av data i en database.

## Maven

Vårt bygg har tillegg for:
- oppsett av java (**maven-compiler-plugin**)
- testing (**maven-surefire-plugin**)
- kjøring av javafx (**javafx-maven-plugin**)
- sjekking av kodekvalitet med [checkstyle](https://checkstyle.sourceforge.io) (**maven-checkstyle-plugin**) og [spotbugs](https://spotbugs.github.io) (**spotbugs-maven-plugin**)
- testdekningsgrad med [jacoco](https://github.com/jacoco/jacoco) (**jacoco-maven-plugin**)

## Kjøring av prosjektet
1. Åpne prosjektet i gitpod ved hjelp av knappen øverst.
2. Fra /workspace/gr2050/foundly, skriv kommandoen **mvn install** i terminalvinduet.
3. Kjør deretter kommandoen **mvn javafx:run -f ui/pom.xml**
4. Legg inn hittegodset ditt!

