package de.uniwue.jpp.compiler.error;

import de.uniwue.jpp.compiler.syntax.TokenLocation;
import de.uniwue.jpp.compiler.util.NameCache;

public interface Error {

    //  Gibt Expected <{expected}>, but gotten <{gotten}>.
    //  aus, wobei {expected} durch die erwartete Tokenart und {gotten} durch die bekommene Tokenart ersetzt werden muss.
    String getMessage(NameCache names);


    TokenLocation getLocation();
}
