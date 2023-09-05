# Coworking Space

Eine Java-Applikation, die die Verwaltung von einem Coworking Space ermöglicht. In diesem Projekt wurde das Framework "Quarkus" verwendent. Es bietet eine RESTful API um Benutzer, Buchungen, Bewertungen, Follower und die Verfügbarkeit von Arbeitsplätzen zu verwalten.

## Erste Schritte

1. Stelle sicher, dass Docker installiert ist und läuft.
1. Stelle sicher, dass Visual Studio Code und die Erweiterung Remote Container installiert ist.
1. Klone (clone) das Projekt lokal, um damit arbeiten zu können (git clone https://github.com/erson53/m223-Coworkingspace.git).
1. Öffne das Projekt mit Visual Studio Code.
1. Öffne das Projekt im Entwicklungscontainer.
1. Starte das Projekt mit dem Kommando `Quarkus: Debug current Quarkus Project`
1. Probiere die Client-Applikation unter http://localhost:8080 aus.
1. Schaue die API auf http://localhost:8080/q/swagger-ui/ an.

## Datenbank

Die Daten werden in einer PostgreSQL-Datenbank gespeichert. In der Entwicklungsumgebung wird diese in der [docker-compose-yml](./.devcontainer/docker-compose.yml) konfiguriert.

### Datenbankadministration

Über http://localhost:5050 ist PgAdmin4 erreichbar. Damit lässt sich die Datenbank komfortabel verwalten. Der Benutzername lautet `zli@example.com` und das Passwort `zli*123`. Die Verbindung zur PostgreSQL-Datenbank muss zuerst mit folgenden Daten konfiguriert werden:
 - Host name/address: `db`
 - Port: `5432`
 - Maintenance database: `postgres`
 - Username: `postgres`
 - Password: `postgres`

## Testdaten

Um Testdaten in der Datenbank zu erhalten muss das Projekt gestartet werden, anschliessend werden sie direkt eingefügt. Hier sind zwei Beispiel-User um die Endpoints zu testen:

Login-URL: http://localhost:8080/users/login

Admin:
 - "email": "Admin@admin2.com",
 - "password": "passwort456"

Member:
 - "email": "max@example.com"
 - "password": "passwort123"

 ## Automatische Tests

Die Automatischen Tests kann man unter dem Test Ordner, unter Java finden. Es gibt Tests für den User und für die Bookings. Ich konnte jedoch nicht alle testen da es bei einigen Endpunkte Komplikationen gab.