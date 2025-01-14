package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.JukeboxAccountCollection;

class JukeboxAccountCollectionTest {

	@Test
	void test1() {
		JukeboxAccountCollection accounts = new JukeboxAccountCollection();
		assertFalse(accounts.checkLogged());
		assertFalse(accounts.attemptLogin("Chris", "11"));
		assertTrue(accounts.attemptLogin("Chris", "1"));
		assertEquals(accounts.getCurrentAccount().getName(),"Chris");
		assertTrue(accounts.checkLogged());
		accounts.logout();
		assertFalse(accounts.checkLogged());
	}
	
	@Test
	void testLoginLogout() {
		JukeboxAccountCollection accounts = new JukeboxAccountCollection();
		assertFalse(accounts.checkLogged());
		assertFalse(accounts.attemptLogin("Chris", "11"));
		assertTrue(accounts.attemptLogin("Chris", "1"));
		assertFalse(accounts.attemptLogin("Chris", "1"));
		accounts.logout();
		assertFalse(accounts.attemptLogin("Devon", "222"));
		assertTrue(accounts.attemptLogin("Devon", "22"));
		assertFalse(accounts.attemptLogin("River", "333"));
		accounts.logout();
		assertFalse(accounts.attemptLogin("Rivers", "333"));
		assertTrue(accounts.attemptLogin("River", "333"));
	}

}
