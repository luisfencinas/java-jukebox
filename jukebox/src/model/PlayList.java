package model;

public class PlayList {

	private class Node {
		private Song song;
		private Node next;

		public Node(Song setSong) {
			song = setSong;
			next = null;
		}

		public Song getSong() {
			return song;
		}

		public void setNext(Node setNext) {
			next = setNext;
		}

		public Node next() {
			return next;
		}
	}

	private Node head, tail;
	private int n;

	public PlayList() {
		head = null;
		tail = null;
		n = 0;
	}

	public boolean isEmpty() {
		return n == 0;
	}

	public void enqueue(Song song) {
		Node node = new Node(song);
		if (n == 0) {
			head = node;
			tail = node;
			n++;
		} else {
			tail.setNext(node);
			tail = node;
		}
	}

	public Song peek() {
		return head.getSong();
	}

	public Song dequeue() {
		if (!this.isEmpty()) {
			Song returnSong = head.getSong();
			head = head.next();
			return returnSong;
		} else {
			return null;
		}
	}

	public String toString() {
		String string = "";
		Node curr = head;
		while (curr != null) {
			string += curr.getSong().getTitle() + ", ";
			curr = curr.next();
		}
		return string;
	}
}
