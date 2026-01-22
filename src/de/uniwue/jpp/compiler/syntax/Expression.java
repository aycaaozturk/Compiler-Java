package de.uniwue.jpp.compiler.syntax;

public class Expression {
//    Um im Falle eines Fehlers ermitteln zu können, wo die Expression im Text stand,
//    wird das sogenannte primäre Token der Expression abgespeichert.
//    Bei Number- und Identifier-Expressions ist dieses das einzig geparste Token selbst. (PRIMARY TOKEN YANI)
//    Bei binären Operationen ist es der Operator, und bei einer Let-Expression ist es das Let-Token.
//
//    Wie vorher schon beschrieben werden die Subexpressions einer Expression
//    nicht direkt, sondern per Expressionindex in den AST abgespeichert.
//    Dadurch können die Felder der Subexpressions auch für andere Zwecke verwendet werden,
//    sofern sie nicht dafür benötigt werden:
//
//    Bei einer Number- oder Identifier-Expression kann left oder right, im Folgenden auch als value bezeichnet,
//    zum Speichern der Tokendaten verwendet werden.
//    Bei einer Let-Expression ist hierbei left der Namensindex
//    der deklarierten Variable. und right der Expressionindex der Zuweisungs-Expression.
//    Es wird implizit angenommen, dass right + 1 der Expressionindex der inneren Expression ist.
//
//
        public ExpressionKind kind;
        public int left=-1;
        public int right=-1;
        public int primaryToken;



  //  Erstellt eine neue Expression mit den übergebenen Parametern
    public Expression(ExpressionKind kind, int left, int right, int primaryToken) {
        this.kind=kind;
        this.left=left;
        this.right=right;
        this.primaryToken=primaryToken;
    }

   // Gibt die Art der Expression aus.
    public ExpressionKind getKind() {

       return kind;
    }


    //Gibt, sofern vorhanden, den Expressionindex der linken Subexpression (left) oder den Namensindex der Let-Expression aus.
    public int getLeft() {
        return left;
    }

    // Gibt, sofern vorhanden, den Expressionindex der rechten Subexpression (right) aus.
    public int getRight() {

        return right;
    }

    //Gibt, sofern vorhanden, den Wert oder Namensindex der Expression (value) aus.
    public int getValue() {

       return left;
    }

    //Gibt den Tokenindex des primären Tokens der Expression aus.
    public int getPrimaryToken() {

       return primaryToken;
    }

    //Erstellt eine Expression der Art Number mit den übergebenen Parametern.
    public static Expression makeNumber(int value, int primaryToken) {
     Expression newExp = new Expression(ExpressionKind.Number, value, 0, primaryToken);
     return newExp;

    }

    //Erstellt eine Expression der Art Identifier mit den übergebenen Parametern.
    public static Expression makeIdentifier(int index, int primaryToken) {
        Expression newExp = new Expression(ExpressionKind.Identifier, index, 0, primaryToken);
        return newExp;
    }

   // Gibt das erste Token der Expression aus. Es ist wie folgt definiert:
   // Art	                                      Token
  //  Number, Identifier	                      Das übergebene primäre Token
  //  Let	                                      Das übergebene primäre Token
  //  Add, Sub, Mul, Div, Mod         	          Das erste Token der linken Subexpression
    public int getFirst(AST ast) {
        if(kind==ExpressionKind.Number || kind==ExpressionKind.Identifier || kind==ExpressionKind.Let){
            return primaryToken;
        }
        else if(kind==ExpressionKind.Add || kind==ExpressionKind.Sub || kind==ExpressionKind.Mul || kind==ExpressionKind.Div || kind==ExpressionKind.Mod){
            int primOfLeftSub = ast.getExpression(left).getFirst(ast);
            return primOfLeftSub;
        }
        else{ return -1;


    }}


//    Gibt das letzte Token der Expression aus. Es ist wie folgt definiert:
//    Art                             	Token
//    Number, Identifier	            Das übergebene primäre Token
//    Let	                            Das letzte Token der inneren Subexpression
//    Add, Sub, Mul, Div, Mod	        Das letzte Token der rechten Subexpression
//
    public int getLast(AST ast) {
        if (kind == ExpressionKind.Number || kind == ExpressionKind.Identifier) {
            return primaryToken;
        } else if (kind == ExpressionKind.Let) {
            int let = ast.getExpression(right + 1).getLast(ast);
            return let;
        } else if (kind == ExpressionKind.Add || kind == ExpressionKind.Sub || kind == ExpressionKind.Mul || kind == ExpressionKind.Div || kind == ExpressionKind.Mod) {
            int op = ast.getExpression(right).getLast(ast);
            return op;
        }
        else{return -1;}

    }
    //Erstellt eine neue TokenLocation, die durch getFirst und getLast begrenzt ist.
    public TokenLocation getLocation(AST ast) {
        TokenLocation begrenzt = new TokenLocation(getFirst(ast), getLast(ast));
        return begrenzt;
    }
}
