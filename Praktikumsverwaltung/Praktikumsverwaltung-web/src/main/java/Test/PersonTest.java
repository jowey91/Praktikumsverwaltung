package Test;

import domain.Person;
import domain.Rollen;
import org.ektorp.CouchDbConnector;
import org.ektorp.CouchDbInstance;
import org.ektorp.http.HttpClient;
import org.ektorp.http.StdHttpClient;
import org.ektorp.impl.StdCouchDbConnector;
import org.ektorp.impl.StdCouchDbInstance;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private CouchDbConnector db;
    private Person testPerson;

    @BeforeEach
    void init() throws MalformedURLException {
        HttpClient httpClient = new StdHttpClient.Builder()
                .url("http://192.168.178.57:5984")
                .build();
        CouchDbInstance dbInstance = new StdCouchDbInstance(httpClient);
        db = new StdCouchDbConnector("Praktikumsverwaltung", dbInstance);
        db.createDatabaseIfNotExists();

    }

    @AfterEach
    void cleanup() {
        db.delete(testPerson);
    }

    @Test
    void testPersistPersonWithoutAttributes() throws MalformedURLException {

        testPerson = new Person();

        db.create(testPerson);
        assertNotNull(db.find(Person.class, testPerson.getId()));
    }

    @Test
    void testPersistPersonWithForename() {
        testPerson = new Person();
        testPerson.setForename("Max");

        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        assertEquals("Max", resultPerson.getForename());
    }

    @Test
    void testPersistPersonWithFamilyname() {
        testPerson = new Person();
        testPerson.setFamilyname("Mustermann");

        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        assertEquals("Mustermann", resultPerson.getFamilyname());
    }

    @Test
    void testPersistPersonWithMatrikelnummer() {
        testPerson = new Person();
        testPerson.setMatrikelnummer("112233");

        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        assertEquals("112233", resultPerson.getMatrikelnummer());

    }

    @Test
    void testPersistPersonWithRolle() {
        testPerson = new Person();
        testPerson.setRolle(Rollen.DOZENT);

        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        assertEquals(Rollen.DOZENT, resultPerson.getRolle());

    }

    @Test
    void testPersistPersonWithSemester() {
        testPerson = new Person();
        testPerson.setSemester(10);

        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        assertEquals(10, resultPerson.getSemester());
    }

    @Test
    void testPersistPersonWithRevisionNr(){
        testPerson = new Person();
        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        assertEquals(testPerson.getRev(), resultPerson.getRev());
    }

    @Test
    @Disabled
    void testDeletePerson() {
        testPerson = new Person();
        db.create(testPerson);
        Person resultPerson = db.find(Person.class, testPerson.getId());
        assertNotNull(resultPerson);
        db.delete(testPerson);
        assertNull(db.find(Person.class, testPerson.getId()));
    }

}
