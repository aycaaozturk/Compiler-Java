package de.uniwue.jpp.compiler.error;

import de.uniwue.jpp.compiler.syntax.TokenLocation;
import de.uniwue.jpp.compiler.util.NameCache;
import de.uniwue.jpp.compiler.util.NameCacheImpl;

public class CannotFindNameError implements Error {
 //   Wird erstellt, wenn ein Variablenname nicht gefunden werden kann.
    int name;
    TokenLocation location;


  //  Erstellt einen CannotFindNameError.
    public CannotFindNameError(int name, TokenLocation location) {
        this.name=name;
        this.location=location;
    }


//    Gibt den Namen zurück.
    public int getName() {

        return name;

    }

    // Gibt Cannot find name '{name}' in this scope. aus, wobei {name} mithilfe des NameCache durch den Namen ersetzt
    // werden muss.
    @Override
    public String getMessage(NameCache names) {
        String cantFindName = names.getName(name);
        return "Cannot find name '" + cantFindName+"' in this scope.";

    }

    @Override
    public TokenLocation getLocation() {
        return location;
    }
}
