package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import model.JukeboxAccount;

class JukeboxAccountTest {

	@Test
	void testGetters() {
		JukeboxAccount aJBA = new JukeboxAccount("Chris", "1");
	}

	@Test
	void testDate() {
		JukeboxAccount acc = new JukeboxAccount("Chris", "1");
		assertTrue(acc.attemptSong());
		assertTrue(acc.attemptSong());
		assertTrue(acc.attemptSong());
		assertFalse(acc.attemptSong());
		acc.pretendItsTomorrow();
		assertTrue(acc.attemptSong());
		assertTrue(acc.attemptSong());
		assertTrue(acc.attemptSong());
		assertFalse(acc.attemptSong());
	}

	@Test
	void testLogin() {
		JukeboxAccount acc = new JukeboxAccount("Chris", "1");
		assertTrue(acc.attemptLogin("Chris", "1"));
		assertFalse(acc.attemptLogin("Chris", "2"));
		assertFalse(acc.attemptLogin("chris", "1"));
	}

	@Test
	void testSongsAvailable() {
		JukeboxAccount acc = new JukeboxAccount("Chris", "1");
		assertEquals(0, acc.getSongsSelected());
		assertTrue(acc.attemptSong());
		assertEquals(1, acc.getSongsSelected());
		assertTrue(acc.attemptSong());
		assertEquals(2, acc.getSongsSelected());
		assertTrue(acc.attemptSong());
		assertEquals(3, acc.getSongsSelected());
		assertFalse(acc.attemptSong());

		acc.pretendItsTomorrow();
		acc.attemptRefresh();
		assertEquals(0, acc.getSongsSelected());
		assertTrue(acc.attemptSong());
		assertEquals(1, acc.getSongsSelected());
		assertTrue(acc.attemptSong());
		assertEquals(2, acc.getSongsSelected());
		assertTrue(acc.attemptSong());
		assertEquals(3, acc.getSongsSelected());
		assertFalse(acc.attemptSong());
	}

}
