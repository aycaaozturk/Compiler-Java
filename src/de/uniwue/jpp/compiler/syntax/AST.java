package de.uniwue.jpp.compiler.syntax;

public interface AST {
//    Der AST (abstract syntax tree) speichert alle geparsten Expressions in einer Liste.
//    Die Abstraktion eines rekursiven Baums in eine einzelne Liste,
//    verringert die Anzahl an Allokationen, erhöht die Lokalität und erlaubt andere Speicheroptimierungen in den Expressions.
//

 //   Erstellt einen neuen (leeren) AST. Hierfür muss das Interface AST implementiert werden.

    public static AST create() {
       return new ASTImpl();
    }






//    Fügt den AST eine neue Expression hinzu und gibt den dazugehörigen Expressionindex aus.
//    Dieser Expressionindex soll einzigartig sein, d.h. zwei Aufrufe der Funktion dürfen nicht den gleichen Expressionindex
//    ausgeben. Genauer soll der zurückgegebene Wert stets um eins erhöht werden.
//    Gibt also ein Aufruf den Wert index (bspw. 2) zurück,
//    so soll der nächste Aufruf den Wert index + 1 (im Beispiel also 3) zurückgeben.
//    Es darf die Annahme gemacht werden, dass eine Expression nur einmal hinzugefügt wird.
//
    public int addExpression(Expression expression);


 //   Gibt für einen Expressionindex die dazugehörige Expression aus.
    public Expression getExpression(int expression);
}
