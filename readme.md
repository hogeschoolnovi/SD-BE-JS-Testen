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
In dit voorbeeld hebben we een onderscheid gemaakt tussen unittesten en integratietesten. Wanneer we `mvn clean verify` 
draaien worden standaard alleen de unit-testen gedraaid. Dit hebben we gedaan, omdat unit-testen een stuk minder tijd 
innemen en ook minder vaak gelopen hoeven te worden dan integratie. Wanneer we ook de integratietesten willen laten
lopen, moeten we het volgende commando gebruiken: `mvn clean verify -DskipTests=true`.

Je kunt naar het PR kijken om precies te zien wat de verschillen zijn in het __pom.xml__ bestand.

Een slimme opsplitsing voor jezelf is, wanneer je klasse `@SpringBootTest` heeft, dan heeft deze het Spring-Framework
nodig om te kunnen testen. Dit soort testen wil je dan ook het liefst als integratietest draaien.