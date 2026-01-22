package de.uniwue.jpp.compiler.syntax;

public class TokenLocation {
    int first;
    int last;

    public TokenLocation(int first, int last) {
        this.first=first;
        this.last=last;
    }

    public int getFirst() {
        return  first;
    }

    public int getLast() {
        return last;
    }


   // Erstellt eine neue TokenLocation, die bei this.first beginnt und bei last.last endet.
    public TokenLocation join(TokenLocation last) {
       TokenLocation joint = new TokenLocation(this.first , last.last);  //last adli tokenin last pozisyonu
        return joint;

    }
}
