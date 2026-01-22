package de.uniwue.jpp.compiler.error;

import de.uniwue.jpp.compiler.syntax.TokenKind;
import de.uniwue.jpp.compiler.syntax.TokenLocation;
import de.uniwue.jpp.compiler.util.NameCache;

public class ExpectedTokenError implements Error {
    //Wird erstellt, wenn eine gewisse Tokenart erwartet wurde, aber eine andere vorzufinden war.
    TokenKind expected;
    TokenKind gotten;
    TokenLocation location;


    // Erstellt einen ExpectedTokenError.
    public ExpectedTokenError(TokenKind expected, TokenKind gotten, TokenLocation location) {
        this.expected=expected;
        this.gotten=gotten;
        this.location=location;
    }

    //Gibt die erwartete Tokenart aus.
    public TokenKind getExpected() {
        return expected;
    }

    public TokenKind getGotten() {
        return gotten;
    }


    //  Gibt Expected <{expected}>, but gotten <{gotten}>.
    //  aus, wobei {expected} durch die erwartete Tokenart und {gotten} durch die bekommene Tokenart ersetzt werden muss.
    @Override
    public String getMessage(NameCache names) {
         return "Expected <" + expected.toString() + ">, but gotten <"+ gotten.toString()+">.";

    }

    @Override
    public TokenLocation getLocation() {
        return location;
    }
}
