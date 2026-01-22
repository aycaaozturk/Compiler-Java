package de.uniwue.jpp.compiler.syntax;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import de.uniwue.jpp.compiler.error.Error;
import de.uniwue.jpp.compiler.error.ExpectedPrimaryExpressionError;
import de.uniwue.jpp.compiler.error.ExpectedTokenError;

public class Parser {
//    Dieser Parser ist grundsätzlich ein sogenannter recursive-decent Parser.
//    Das heißt, dass er mit einer erwarteten Syntax gestartet wird
//    (in diesem Fall wird eine Expression gefolgt von dem Ende des Texted erwartet)
//    und versucht wird rekursiv die einzelnen Bestandteile zu parsen.
//    Das ist eine Art des top-down Parsens, die im Gegensatz zu einem bottom-up Parser steht,
//    welcher versucht die Tokens selbst zu immer größeren Strukturen zusammenzufassen.
//
//    Ähnlich wie beim Lexer bekommt der Parser eine Menge von Daten.
//    Diese werden durch Aufruf der verschiedenen Instanzmethoden verarbeitet und der Zustand des Parsers ändert sich.
//    Das heißt, dass der Parser immer auf ein Token zeigt (am Anfang das erste)
//    und durch die Aufrufe der parse-Methoden über die Liste iteriert. Kein Token wird mehrfach geparst.

    List<Token> tokens;
    int position;
    ASTImpl ast;
    List<Error> errors;
    //Token(TokenKind, position, data)   (data: NamenCache index)
//    EOF,    //"", bzw. keine Darstellung, hat somit eine Länge von 0	"End Of File", bezeichnet das Ende des Quellcodes
//    Unknown,  //Jegliches unbekanntes Zeichen, hat somit eine Länge von 1	Bezeichnet ein unbekanntes Zeichen
//
//    Number,  //Verschiedene Darstellungen möglich (siehe Grammatik)	Bezeichnet eine Zahl
//    Identifier,   //Verschiedene Darstellungen möglich (siehe Grammatik)	Bezeichnet eine Variable
//
//    LetKeyword,  //"let"
//    InKeyword,  //"in"
//
//    Plus,   // "+"
//    Minus,    // "-"
//    Times,   //  "*"
//    Divide,   //  "/"
//    Modulo,   //  "%"
//
//    Equals,   // "="
//
//    OpenParen,   //  "("
//    CloseParen,   //  ")"


    //   Erstellt einen neuen Parser mit Startposition 0.
    public Parser(List<Token> tokens) {
        this(tokens, 0);

    }

    //   Erstellt einen neuen Parser mit der übergebenen Startposition.
    public Parser(List<Token> tokens, int position) {
        this.tokens = tokens;
        this.position = position;
        this.ast = new ASTImpl();
        this.errors = new ArrayList<>();

    }

    //   Gibt den Tokenindex des derzeitigen Tokens zurück.
    public int getCurrentIndex() {
        return position;
    }

    //    Gibt den erstellten AST zurück.
    public AST getAST() {

        return ast;
    }

    //    Gibt alle Fehler zurück, die beim Parsen entstanden sind.
    public List<Error> getErrors() {

        return errors;
    }

    //    Gibt das derzeitige Token zurück, ohne es zu konsumieren.
    public Token currentToken() {
//        if (position < tokens.size()) {
//            return tokens.get(position);
//        }
//        else {
//            Token end = new Token(TokenKind.EOF, position);
//            return end;
//        }
        return tokens.get(position);
    }

    //   Konsumiert das derzeitige Token. Gibt also das derzeitige Token zurück und,
    //   sofern das derzeitige Token nicht bereits schon das Letzte ist, erhöht die Position auf die Nächste.
    public Token consumeToken() {  //yedigini dönüyor, positision arttiriyor
        Token current = currentToken();
        if (position < tokens.size() - 1) {
            position++;

        }
        return current;

    }

    //   Erwartet ein Token. Konsumiert das derzeitige Token, wenn es der übergebenen Art entspricht.
    //   Produziert ansonsten einen Fehler und erstellt ein Token.
    //   Der produzierte Fehler soll ein ExpectedTokenError sein,
    //   dessen Location beim derzeitigen Token startet und bei diesem auch endet.
    //   Das erstellte Token soll als Art die übergebene Art, und als Position die des derzeitigen Tokens haben.
    public Token expectToken(TokenKind kind) {   //bunu bekliyor, o gelirse tüketiyor
        Token current = currentToken();          //tükettikten sonra on (current) return ediyor
        if (kind == TokenKind.EOF) {
            return current;
        }
        if (current.getKind() == kind) {
            return consumeToken();
        } else {
            TokenLocation loc = new TokenLocation(position, position);
            ExpectedTokenError expTokenError = new ExpectedTokenError(kind, current.getKind(), loc);
            errors.add(expTokenError);
            Token returnToken = new Token(kind, current.position);

            return returnToken;
        }

    }
//   <primary> ::= <identifier> | <number> | "(" <expression> ")"
//  Parst eine Expression, falls eine primäre Expression geparst werden kann. Ansonsten wird ein leeres Optional zurückgegeben.
//  Eine primäre Expression ist eine der Folgenden:
//    Expression	              Anmerkungen
//    Number	                  Der Wert der Zahl soll in der Expression gespeichert werden.
//    Identifier	              Der Namensindex soll in der Expression gespeichert werden.
//    Parenthesized	              Nur die innere Expression wird zurückgegeben.

//    Zum Parsen empfiehlt es sich zunächst anzuschauen,
//    ob das derzeitige Token überhaupt der Anfang einer primären Expression ist.
//    Ist dies nicht der Fall soll ein ExpectedPrimaryExpressionError produziert werden.
//
//    Bei den Number- und Identifier-Expressions genügt es das Token zu konsumieren und
//    mithilfe der Daten des Tokens die dazugehörige Expression zu erstellen.
//    Bei der parenthesized Expression soll zunächst das "("-Token konsumiert werden.
//    Danach kann über einen Aufruf von parseExpression die innere Expression geparst werden.
//    Am Ende sollte noch das ")"-Token erwartet werden. Es kann dann einfach die innere Expression ausgegeben werden.

    public Optional<Expression> parsePrimaryExpression() {
        Token currentToken = currentToken();   //o anlik token primar ise onu parse edior
        if (currentToken.getKind() == TokenKind.Number) {
            int primaryToken = this.position;
            Token numberToken = consumeToken();
            Expression numberExpression = Expression.makeNumber(numberToken.getData(), primaryToken);
            return Optional.of(numberExpression);

        } else if (currentToken.getKind() == TokenKind.Identifier) {
            int primaryToken = this.position;
            Token idToken = consumeToken();
            Expression idExpression = Expression.makeIdentifier(idToken.getData(), primaryToken);
            return Optional.of(idExpression);


        } else if (currentToken().getKind() == TokenKind.OpenParen) {
            consumeToken();
            Optional<Expression> innerExp = parseExpression();
            expectToken(TokenKind.CloseParen);
            return innerExp;

        }
        TokenLocation loc = new TokenLocation(position, position);
        Error exp = new ExpectedPrimaryExpressionError(currentToken.getKind(), loc);
        errors.add(exp);
        return Optional.empty();

    }

//    <operator-add> ::= "+" | "-"
//  <operator-mul> ::= "*" | "/" | "%"
//
//  <binary-add>   ::= <binary-mul> | <binary-add> <operator-add> <binary-mul>
//  <binary-mul>   ::= <primary> | <binary-mul> <operator-mul> <primary>
//    Aus der Grammatik ergibt sich die gleiche Weise die beiden Expressions zu parsen,
//    wobei Subexpression und Operator andere Methoden/Tokens benötigen
//
//                           parseBinaryMultiplicative	                    parseBinaryAdditive
//    Subexpression	             primäre Expression(identifier, number)	  multiplikative binäre Expression
//    Operator	              Times, Divide, Modulo	                            Plus, Minus

//    Das Parsen funktioniert dann wie gefolgt:
//    1.  Subexpression wird geparst und als left gespeichert
//    2.  Ist das derzeitige Symbol ein passender Operator, geht es weiter mit Schritt 3, ansonsten wird left zurückgegeben
//    3.  Subexpression wird geparst und als right gespeichert
//    4.  Erstelle neue Expression aus left, right und dem Operator und speichere diese in left.
//        Hierfür müssen left und right dem AST hinzugefügt werden.

//    5.  Wiederhole ab Schritt 2
//    Sofern bereits bei Schritt 1 keine Expression geparst werden kann,
//    soll direkt ein leeres Optional zurückgegeben werden.
//    Kann bei Schritt 3 keine Expression geparst werden, soll direkt left ausgegeben werden.
//    Das primäre Token einer binären Expression ist der Operator.

    public Optional<Expression> parseBinaryMultiplicative() {
        Optional<Expression> leftExp = parsePrimaryExpression();
//        if (!leftExp.isPresent() ) {
//            leftExp = parseBinaryMultiplicative();
//            if (leftExp.isPresent() == false) {
//                return Optional.empty();
//            }
//        }
        if (!leftExp.isPresent() ) {

            return Optional.empty();

        }

        while (true) {
            Token current = currentToken();
            TokenKind currentKind = current.getKind();
            if (currentKind != TokenKind.Divide && currentKind != TokenKind.Modulo  && currentKind != TokenKind.Times) {
                return leftExp;
            }
            int primaryToken = this.position;
            Token operator = consumeToken();  //operatorse tüketti
            Optional<Expression> rightExp = parsePrimaryExpression();
            if (rightExp.isPresent() == false) {
                return leftExp;
            }
            int leftid = ast.addExpression(leftExp.get());
            int rightid = ast.addExpression(rightExp.get());
            if (operator.kind == TokenKind.Divide) {
                Expression e = new Expression(ExpressionKind.Div, leftid, rightid, primaryToken);
                leftExp = Optional.of(e);
            } else if (operator.kind == TokenKind.Times) {
                Expression e = new Expression(ExpressionKind.Mul, leftid, rightid, primaryToken);
                leftExp = Optional.of(e);
            } else if (operator.kind == TokenKind.Modulo) {
                Expression e = new Expression(ExpressionKind.Mod, leftid, rightid, primaryToken);
                leftExp = Optional.of(e);
            }

        }
    }


    //  <operator-mul> ::= "*" | "/" | "%"
//
//  <binary-add>   ::= <binary-mul> | <binary-add> <operator-add> <binary-mul>

//    Aus der Grammatik ergibt sich die gleiche Weise die beiden Expressions zu parsen,
//    wobei Subexpression und Operator andere Methoden/Tokens benötigen
//
//                          	              parseBinaryAdditive
//    Subexpression	             	  multiplikative binäre Expression
//    Operator	                               Plus, Minus

//    Das Parsen funktioniert dann wie gefolgt:
//    1.  Subexpression wird geparst und als left gespeichert
//    2.  Ist das derzeitige Symbol ein passender Operator, geht es weiter mit Schritt 3, ansonsten wird left zurückgegeben
//    3.  Subexpression wird geparst und als right gespeichert
//    4.  Erstelle neue Expression aus left, right und dem Operator und speichere diese in left.
//        Hierfür müssen left und right dem AST hinzugefügt werden.

//    5.  Wiederhole ab Schritt 2
//    Sofern bereits bei Schritt 1 keine Expression geparst werden kann,
//    soll direkt ein leeres Optional zurückgegeben werden.
//    Kann bei Schritt 3 keine Expression geparst werden, soll direkt left ausgegeben werden.
//    Das primäre Token einer binären Expression ist der Operator.


    public Optional<Expression> parseBinaryAdditive() {
        Optional<Expression> leftExp = parseBinaryMultiplicative();
//        if (!leftExp.isPresent() ) {
//            leftExp = parseBinaryAdditive();
//            if (leftExp.isPresent() == false) {
//                return Optional.empty();
//            }
//        }
        if (!leftExp.isPresent() ) {

            return Optional.empty();

        }

        while (true) {
            Token current = currentToken();
            TokenKind currentKind = current.getKind();
            if (currentKind != TokenKind.Minus && currentKind != TokenKind.Plus) {
                return leftExp;
            }
            int primaryToken = this.position;
            Token operator = consumeToken();  //operatorse tüketti
            Optional<Expression> rightExp = parseBinaryMultiplicative();
            if (rightExp.isPresent() == false) {
                return leftExp;
            }
            int leftid = ast.addExpression(leftExp.get());
            int rightid = ast.addExpression(rightExp.get());
            if (operator.kind == TokenKind.Plus) {
                Expression e = new Expression(ExpressionKind.Add, leftid, rightid, primaryToken);
                leftExp = Optional.of(e);
            } else if (operator.kind == TokenKind.Minus) {
                Expression e = new Expression(ExpressionKind.Sub, leftid, rightid, primaryToken);
                leftExp = Optional.of(e);
            }
        }


    }

    //    <let-expression> ::= "let" <identifier> "=" <expression> "in" <expression>
//    Parst eine Let-Expression, falls möglich. Es darf angenommen werden, dass das derzeitige Token ein LetKeyword,
//    da die Methode nur dann aufgerufen werden sollte.
//    Es sollten erst alle Teile der Expression geparst werden und erst danach,
//    falls die zugewiesene oder die innere Expression leer ist, ein leeres Optional ausgegeben.
//    Was das Parsen der einzelnen Teile erfolgreich, so müssen die zugewiesene und die innere Expression dem AST hinzugefügt werden.
//    Das primäre Token ist das LetKeyword.
//
    // let x = 2 in ...
    public Optional<Expression> parseLetExpression() {
        int primaryToken = position;
        Token letToken = consumeToken();
        Token identifierToken = expectToken(TokenKind.Identifier);
        expectToken(TokenKind.Equals);
        Optional<Expression> zuweisungExpression = parseExpression();



        expectToken(TokenKind.InKeyword);
        Optional<Expression> innereExpression = parseExpression();
        if(zuweisungExpression.isEmpty()==true){
            return Optional.empty();

        }
        if(innereExpression.isEmpty()==true){
            return Optional.empty();
        }
        int assigned= ast.addExpression(zuweisungExpression.get());
        int inner = ast.addExpression(innereExpression.get());
        int dataofIdtoken =identifierToken.getData();

        Expression letExpression = new Expression(ExpressionKind.Let, dataofIdtoken, assigned, primaryToken);
        return Optional.of(letExpression);



    }
    // <expression> ::= <let-expression> | <binary-add>
//    Parst jegliche Expression, falls möglich. Hierbei muss zuerst darauf geachtet werden,
//    ob das derzeitige Token ein LetKeyword ist. Ist es eines, so muss eine Let-Expression geparst werden.
//    Ist es keines, so wird eine additive binäre Expression zurückgegeben.
//
    public Optional<Expression> parseExpression() {
        Token currentToken = currentToken();
        TokenKind currentKind = currentToken.getKind();
        if(currentKind==TokenKind.LetKeyword){
            return parseLetExpression();
        }
        return parseBinaryAdditive();
    }
//    <file>       ::= <expression> <eof>
//    Parst eine Expression und erwartet danach das Ende des Codes, also ein EOF-Token.
//    Dies verifiziert, dass der gesamte Code eine einzige Expression ergibt und nicht noch Rest-Tokens enthält.
//    Sollte es eine Expression geparst werden können,
//    soll diese dem AST hinzugefügt und der daraus bekommene Expressionindex zurückgegeben werden.

    public Optional<Integer> parseAll() {
        Optional<Expression> parseExpression = parseExpression();
        if(parseExpression.isEmpty()==true){
            return Optional.empty();
        }
        int expressionIndex= ast.addExpression(parseExpression.get());
        Token eofToken = expectToken(TokenKind.EOF);
        if(eofToken.getKind()!=TokenKind.EOF){
            TokenLocation loc = new TokenLocation(eofToken.position, eofToken.position);
            Error error = new ExpectedTokenError(TokenKind.EOF, eofToken.getKind(), loc);
            errors.add(error);
//            return Optional.empty();
            return Optional.of(expressionIndex);
        }
        return Optional.of(expressionIndex);

    }
}