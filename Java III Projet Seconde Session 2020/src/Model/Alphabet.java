package Model;

public enum Alphabet {
	
	A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, V, W, X, Y, Z;
	
	public Alphabet getNext() {										// methode trouvée ici : https://digitaljoel.nerd-herders.com/2011/04/05/get-the-next-value-in-a-java-enum/ 
		return values()[(ordinal()+1) % values().length];
	   }

}
