package de.uniwue.jpp.compiler.syntax;

import javax.swing.text.DefaultStyledDocument;
import java.util.*;

public class ASTImpl implements AST {
    List<Expression> expressionList;
    Map<Integer, Expression> indexOfExpressionList;
    int counter;

    public ASTImpl() {
        this.expressionList = new ArrayList<>();
        this.indexOfExpressionList = new HashMap<>();
        this.counter = 0;

    }
    //    Fügt den AST eine neue Expression hinzu und gibt den dazugehörigen Expressionindex aus.
//    Dieser Expressionindex soll einzigartig sein, d.h. zwei Aufrufe der Funktion dürfen nicht den gleichen Expressionindex
//    ausgeben. Genauer soll der zurückgegebene Wert stets um eins erhöht werden.
//    Gibt also ein Aufruf den Wert index (bspw. 2) zurück,
//    so soll der nächste Aufruf den Wert index + 1 (im Beispiel also 3) zurückgeben.
//    Es darf die Annahme gemacht werden, dass eine Expression nur einmal hinzugefügt wird.
//
    @Override
    public int addExpression(Expression expression) {
        Set<Map.Entry<Integer, Expression>> mapSet = indexOfExpressionList.entrySet();

        if (expressionList.contains(expression) == false) {  //listede yoksa
            expressionList.add(expression);
            indexOfExpressionList.put(counter, expression);
            return counter++;
        } else {   //listede var
            int index = 0;
            for (Map.Entry<Integer, Expression> ent : mapSet) {
                if (ent.getValue().equals(expression)) {
                    index = ent.getKey();
                }
            }
          //  counter++;
            return index;
        }
    }

    @Override
    public Expression getExpression(int expression) {
        return expressionList.get(expression);
    }
}
