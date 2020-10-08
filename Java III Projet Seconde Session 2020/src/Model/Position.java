package Model;

public class Position<T> {
	T value;
	public Position() {}
	public Position(T val) {this.value=val;}
	public T getValue() {return value;}

}
