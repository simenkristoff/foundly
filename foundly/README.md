# Foundly

[[_TOC_]]

## Foundly - Lost & Found
Foundly er en digital versjon av det fysiske konseptet hittegodskontor. Konseptet går ut på at om man finner noe gjenglemt, eller mister noe, kan man legge dette ut i appen med bilde og beskrivelse slik at det vil dukke opp for andre i nærheten. Dette fører da forhåpentligvis til at eier og gjenstand blir gjenforent. Appen bygger på frivilighet og ærlighet og skal være gratis for brukere.

### Brukergrensesnitt
Brukergrensesnittet er minimalistisk bygget opp med gjennomgående fargebruk. Designet er responsivt, noe som vil si at det tilpasser seg skjermstørrelsen på enheten du bruker. I tillegg har du mulighet til å filtrere gjenstander basert på om de er mistet eller funnet, eller søke etter ord i beskrivelse eller tittel for å finne mer spesifikke gjenstander. På bildet under kan man se hvordan brukergrensesnittet er bygget opp og hvordan det tilpasser seg ulike enheter.
![Web-client banner](/resources/foundly_responsive_view.png)

### Brukerhistorie
Brukerhistorier ligger [her](/brukerhistorier.md).

### Oppbygning
Foundly er bygget opp i flere moduler som utgjør en trelagsarkitektur med [**domenelogikk**](/foundly/core/README.md), brukergrensesnitt og persistens. Sammen utgjør disse all funksjonaliteten i appen. Når du legger inn en mistet gjenstand via web-klienten, vil den sende dette til [**service-laget**](/foundly/rest-api/README.md) i rest-serveren, som videre vil sørge for at dette blir lagret i H2-databasen. Samhandlingen kan sees tydelig i pakkediagrammet.

![Pakekdiagram](/foundly/architecture/Pakkediagram 1.png)

Vi har også lagt opp prosjektet slik at både web-klienten og JavaFX-appen fungerer som likeverdige grensesnitt. Under kan man se hvordan de to ulike brukergrensesnittene ser ut.

[**Ui (JavaFx-client)**](/foundly/ui/README.md)             |  [**Web-client (Vue.js)**](/foundly/web-client/README.md) 
:-------------------------:|:-------------------------:
![Ui](/resources/javafx_gui.jpg)  |  ![Web-client](/resources/foundly_web_client.jpg)

