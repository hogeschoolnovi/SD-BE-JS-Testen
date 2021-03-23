# Voorbeeldcode JUnit & Mockito
Bijgevoegd vind je voorbeeldcode over hoe je jouw Service-klasse kan scannen met Mockito. We maken hier geen gebruik van
`@SpringBootTest`, wat betekent dat de testen niet afhankelijk zijn van het hebben van een database-connectie en het
draaien van Spring Boot.

De `PersonServiceImpl.java`- klasse heeft nog geen 100% coverage. Dat kan ook niet helemaal, maar de methode
`createResponseObject` zou nog wel getest kunnen worden. Deze is echter `private`. Hoe zou jij de methode testen?

## Postman
Er in de folder __postman__ zit een bestand dat je zelf kunt importeren in Postman. Je kunt dan zo de Rest-endpoints
checken.

## Gesplitte testen
In dit voorbeeld hebben we een onderscheid gemaakt tussen unittesten en de __grotere__ testen. Dit zijn testen waar
gedeeltes van of de gehele applicatie opgestart moet worden. Dit zijn bij definitie testen die een langere looptijd
hebben.

Wanneer we `mvn clean verify` draaien worden standaard alleen de unit-testen gedraaid. Dit hebben we gedaan, omdat
unit-testen een stuk minder tijd innemen en ook minder vaak gelopen hoeven te worden dan de andere testen. Wanneer we
ook de andere testen willen laten draaien, moeten we het volgende commando gebruiken:
`mvn clean verify -DskipTests=true`.

Je kunt naar het PR kijken om precies te zien wat de verschillen zijn in het __pom.xml__ bestand.

Een slimme opsplitsing voor jezelf is, wanneer je klasse `@SpringBootTest` of `@WebMvcTest` heeft, dan heeft deze het
Spring-Framework nodig om te kunnen testen. Dit soort testen wil je dan ook het liefst als integratietest draaien.

*Belangrijk:* Dit voorbeeld is heel rigoreus in de scheiding tussen unit-testen en WebMvCTesten. In de praktijk zul je
WebMvcTesten nog wel vaak bij de unit-testen zien, omdat deze alleen de controller-laag testen.

Het verschil tussen WebMvc en SpringBootTest is dat de `@WebMvc`-annotatie voor het testen van de Controller-laag is.
Allleen de @RestController-klassen worden dus opgestart. Niet de service, repository en andere klassen. De
`@SpringBootTest`-annotatie zal de volledige applicatie laden en alle benodigde klassen inladen. Deze annotatie is dus
een stuk langzamer.

## Zelf oefenen

1. Schrijf een test om de foutmelding te checken wanneer de gebruiker geen repeatedPassword invult.
1. Schrijf een test om de foutmelding te checken wanneer de gebruiker geen email invult.
1. Schrijf een test om de foutmelding te checken wanneer de gebruiker geen username invult.
1. Schrijf een test om de foutmelding te checken wanneer de gebruiker geen password en geen repeteated password invult.
1. *Uitdagend*: Kun je een test schrijven waar je de `getAllUsers()`-methode test?