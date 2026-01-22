package de.uniwue.jpp.compiler.error;

import de.uniwue.jpp.compiler.syntax.TokenKind;
import de.uniwue.jpp.compiler.syntax.TokenLocation;
import de.uniwue.jpp.compiler.util.NameCache;

public class ExpectedPrimaryExpressionError implements Error {
 //   Wird erstellt, wenn eine primäre Expression erwartet wurde, aber ein Token vorzufinden war,
    //   das nicht dem Anfang einer primären Expression entspricht.

    TokenKind gotten;
    TokenLocation location;


    public ExpectedPrimaryExpressionError(TokenKind gotten, TokenLocation location) {
       this.gotten=gotten;
       this.location=location;
    }


    public TokenKind getGotten() {
      return gotten;

    }



   // Gibt Expected primary expression, but gotten <{gotten}>. aus,
   // wobei {gotten} durch die bekommene Tokenart ersetzt werden muss.
    @Override
    public String getMessage(NameCache names) {
      return "Expected primary expression, but gotten <"+ gotten.toString()+">.";
    }


 //   Gibt die Location aus.
    @Override
    public TokenLocation getLocation() {

      return location;
    }
}
