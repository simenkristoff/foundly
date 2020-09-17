[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.idi.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2020/gr2050/gr2050)

# Foundly
Dette prosjektet er konfigurert med maven og er bygget opp med tre lag; domenelag, brukergrensesnitt (UI) og persistens. Prosjektet inneholder tester for persistens.

## Organisering
Prosjektet **[Foundly](foundly/)** er organisert med 4 kildekodemapper: Kodemappe og ressursmappe for både appen og tester.

- **src/main/java** for koden til applikasjonen
- **src/main/resources** for ressurser til applikasjonen
- **src/test/java** for testkoden
- **src/test/resources** for ressurser til testkoden
##

## Domenelaget

Pakken **[foundly.core](foundly/src/main/java/foundly/core/)** utgjør domenelaget i denne applikasjonen.
Domenelaget inneholder alle klasser og logikk knyttet til dataene som applikasjonen handler om og håndterer.

 

## Brukergrensesnittlaget

Pakken **[foundly.ui](foundly/src/main/java/foundly/ui/)** utgjør brukergrensesnittlaget i denne applikasjonen.
Brukergrensesnittlaget inneholder alle klasser og logikk knyttet til visning og handlinger på dataene i domenelaget.


## Persistenslaget

Pakken **[foundly.database](foundly/src/main/java/foundly/database/)** utgjør persistenslaget i denne applikasjonen.
Persistenslaget inneholder alle klasser og logikk for lagring (skriving og lesing) av dataene i domenelaget. Vårt persistenslag implementerer lagring av data i en database.

## Maven

Vårt bygg har tillegg for:
- oppsett av java (**maven-compiler-plugin**)
- testing (**maven-surefire-plugin**)
- kjøring av javafx (**javafx-maven-plugin**)
- sjekking av kodekvalitet med [checkstyle](https://checkstyle.sourceforge.io) (**maven-checkstyle-plugin**) og [spotbugs](https://spotbugs.github.io) (**spotbugs-maven-plugin**)
- testdekningsgrad med [jacoco](https://github.com/jacoco/jacoco) (**jacoco-maven-plugin**)

