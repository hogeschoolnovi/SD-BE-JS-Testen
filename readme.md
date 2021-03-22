# Voorbeeldcode JUnit & Mockito
Bijgevoegd vind je voorbeeldcode over hoe je jouw Service-klasse kan scannen met Mockito. We maken hier geen gebruik van
`@SpringBootTest`, wat betekent dat de testen niet afhankelijk zijn van het hebben van een database-connectie en het
draaien van Spring Boot.

De `PersonServiceImpl.java`- klasse heeft nog geen 100% coverage. Dat kan ook niet helemaal, maar de methode
`createResponseObject` zou nog wel getest kunnen worden. Deze is echter `private`. Hoe zou jij de methode testen?