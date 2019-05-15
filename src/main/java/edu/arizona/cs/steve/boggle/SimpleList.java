package edu.arizona.cs.steve.boggle;

public class SimpleList<E> {
	
	private Object[] list;
	public int length;
	public String title;
	
	public SimpleList(String title) {
		this.length = 0;
		list = new Object[10];
		this.title = title;
	}
	
	public void push(E element) {
		if (this.length == list.length) {
			Object[] tmp = new Object[list.length+10];
			for (int i = 0; i < list.length; i++) {
				tmp[i] = list[i];
			}
			list = tmp;
		}
		list[this.length] = element;
		length++;

		//System.out.println("Pushed. Length is now "+this.length);
	}
	
	public E get(int index) {
		if (index < 0 || index >= length) {
			System.out.println("OUT OF BOUNDS: " + index);
			return null;
		} else
			return (E)list[index];
	}

}
