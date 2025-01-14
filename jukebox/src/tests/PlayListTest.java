package tests;

import org.junit.jupiter.api.Test;

import model.PlayList;
import model.Song;

public class PlayListTest {

	private Song song1 = new Song("Title1", "Playtime", "Artist", "MP3");
	private Song song2 = new Song("Title2", "Playtime", "Artist", "MP3");
	private Song song3 = new Song("Title3", "Playtime", "Artist", "MP3");

	@Test
	public void testEnqueue() {
		PlayList list = new PlayList();
		list.enqueue(song1);
		list.enqueue(song2);
		list.enqueue(song3);
		System.out.println(list.toString());
	}

	@Test
	public void testPeek() {
		PlayList list = new PlayList();
		list.enqueue(song1);
		list.enqueue(song2);
		list.enqueue(song3);
		System.out.println(list.peek().getTitle());
	}

	@Test
	public void testDequeue() {
		PlayList list = new PlayList();
		list.enqueue(song1);
		list.enqueue(song2);
		list.enqueue(song3);
		System.out.println(list.dequeue().getTitle());
		System.out.println(list.dequeue().getTitle());
		System.out.println(list.dequeue().getTitle());
	}

}
