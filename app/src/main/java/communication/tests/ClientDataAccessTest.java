package communication.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.rmi.UnexpectedException;

import org.junit.jupiter.api.*;

import clientDataAccess.ClientDataAccessObject;
import databaseClasses.User;

class ClientDataAccessTest {

	 private static ClientDataAccessObject client;
	 private static final String           IP       = "127.0.0.1";
	 private static String                 username = "system user", password = "system user";

	 @BeforeAll
	 static void init() throws UnexpectedException {
		  client = new ClientDataAccessObject(username, password, IP);
		  client.register();
	 }

	 @Test
	 void signTest() throws UnexpectedException {
		  assertNotNull(client.login());
		  assertEquals(username, client.login().getUsername());
		  ClientDataAccessObject testClient = new ClientDataAccessObject("not logged", "not logged");
		  assertNull(testClient.login());
		  testClient = new ClientDataAccessObject(null, null);
		  assertThrows(IllegalArgumentException.class, () -> new User(null));
	 }
	 @Test
	 void dataTest() throws UnexpectedException {
		  client.saveBasket("a");
		  assertNotNull(client.login());
		  assertEquals(username, client.login().getUsername());
		  ClientDataAccessObject testClient = new ClientDataAccessObject("not logged", "not logged");
		  assertNull(testClient.login());
		  testClient = new ClientDataAccessObject(null, null);
		  assertThrows(IllegalArgumentException.class, () -> new User(null));
	 }

}
