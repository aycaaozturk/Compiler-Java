package de.uniwue.jpp.compiler.lowering;

import de.uniwue.jpp.compiler.error.CannotFindNameError;
import de.uniwue.jpp.compiler.syntax.AST;
import de.uniwue.jpp.compiler.syntax.ASTImpl;
import de.uniwue.jpp.compiler.syntax.Expression;

import java.util.ArrayList;
import java.util.List;

import de.uniwue.jpp.compiler.error.Error;
import de.uniwue.jpp.compiler.syntax.ExpressionKind;
import de.uniwue.jpp.compiler.util.NameCacheImpl;

public class Lowerer {
//    Der Lowerer wandelt den AST in eine Reihe von Instructions um.
//    Desweiteren wird hier ermittelt welche Namen auf welche Variablen verweisen (Stichwörter: name resolution, scoping, binding).
//    Normalerweise wird das in früheren Phasen des Kompilierens gemacht.
//    Da wir hier aber nur eine Parsing-Phase zuvor haben würde es sich nicht lohnen eine zusätzliche Phase dafür einzubauen.
//    Beim Erstellen der Instructions wird auch die derzeitige und die maximale Stackgröße vermerkt.
//    Der Stack ist hierbei unser Speicher für unsere lokalen Variablen bei der Evaluierung,
//    nicht zu verwechseln mit dem Namensstack, welcher für die Name-Resolution verwendet wird.
//    Wir werden hier auch eine kleine Optimierung vornehmen:
//    Am Ende eines Scopes (Geltungsbereich einer Variable) wird der Speicher für die dementsprechende Variable wieder freigegeben.
//    Das ist möglich, da in unserer Repräsentation keine Variable länger als vorher erstelle noch geltende Variablen gelten kann.
//
    AST ast;
    List<Instruction> instructions;
    StackSize stackSize;
    List<LocalInfo> namensstack;
    List<Error> errors;


//    Erstellt einen neuen Lowerer mit den übergebenen AST. Anfangs soll der Lowerer
//    keine Instructions enthalten
//    einen leere StackSize enthalten
//    einen leeren Namensstack enthalten
//    keine Fehler enthalten
    public Lowerer(AST ast) {
       this.ast=ast;
       this.instructions= new ArrayList<>();
       this.stackSize= StackSize.create();
       this.namensstack=new ArrayList<>();
       this.errors= new ArrayList<>();

    }


 //   Erstellt einen neuen Lowerer mit den übergebenen AST und der übergebenen StackSize.
 //   Dieser Konstruktor dient nur zu Testzwecken.
    public Lowerer(AST ast, StackSize stackSize) {
       this.ast=ast;
       this.stackSize=stackSize;
        this.instructions= new ArrayList<>();
        this.namensstack=new ArrayList<>();
        this.errors= new ArrayList<>();
    }

 //   Gibt die StackSize aus.
    public StackSize getStackSize() {

       return stackSize;
    }

 //   Gibt den derzeitigen Namesstack aus.
    public List<LocalInfo> getNameStack() {

       return namensstack;
    }

    //   Gibt derzeit erstellten Instructions aus.
    public List<Instruction> getInstructions() {

      return instructions;
    }

    //   Gibt die derzeit erstellten Error aus.
    public List<Error> getErrors() {
       return errors;
    }

    //   Fügt eine LocalInfo mit den übergebenen Parametern an das Ende des Namensstacks hinzu.
    public void pushNamedLocal(int name, int offset) {
       LocalInfo push = new LocalInfo(name,offset);
       namensstack.add(push);

    }

    //   Entfernt das letzte Element des Namensstacks.
    public void popNamedLocal() {
       if(namensstack.isEmpty()==false){
           namensstack.removeLast();
       }

    }

    //   Allokiert eine unbenannte lokale Variable in der StackSize und gibt die relative Speicherposition davon aus.
    //   Eine lokale Variable benötigt einen Speicherplatz von genau eins (1).
    public int makeLocal() {
      return  stackSize.allocate(1);

    }



    //   Die folgenden Methoden dienen zur Erstellung der Instructions und geben jeweils einen Operand aus,
    //   welcher dem berechneten Wert entspricht. Um das Testen zu vereinfachen sollten immer die entsprechenden Methoden
    //   aufgerufen werden, statt manuell die Funktionalitäten zu kopieren.

    //   Gibt einen nicht-lokalen Operand aus, der den in der Expression gespeicherten Wert enthält.
    public Operand lowerNumber(Expression expression) {
      //  (ExpressionKind kind, int left, int right, int primaryToken)
     //   Operand(boolean isLocal, int value)
        int value = expression.getValue();
        Operand numberOperand= new Operand(false, value);
        return numberOperand;


    }

    //   Sucht den Namen rückwärts im Namensstack und gibt einen lokalen Operand aus,
    //   der die relative Position der gefundenen Variable enthält. Kann keine Variable gefunden werden,
    //   so soll ein CannotFindNameError mit dem Namen und der Location der übergebenen Expression erstellt werden
    //   und ein lokaler Operand mit beliebigem Wert ausgegeben werden.
    public Operand lowerIdentifier(Expression expression) {
        if(namensstack.isEmpty()==false) {
            int val = expression.getValue();
          for(int i = namensstack.size()-1; i>=0; i--){
              if(val==namensstack.get(i).getName()){
                  Operand idOperand = new Operand(true, namensstack.get(i).getOffset());
                  return idOperand;
              }

          }
        }

            int value=expression.getValue();

            CannotFindNameError error = new CannotFindNameError(value, expression.getLocation(ast));
            errors.add(error);
            Operand lokOperand = new Operand(true, 2777497);
            return lokOperand;

    }

//    Allokiert zunächst eine lokale Variable.
//    Speichert die derzeitige Stackgröße ab.
//    Lowert die zugewiesene Expression.
//    Fügt eine Instruction der Art Op mit der allokierten Variable als Resultat und der zugewiesenen Expression als Wert hinzu.
//    Setzt die derzeitige Stackgröße auf die gespeicherte Stackgröße zurück.
//    Fügt dem Namensstack eine LocalInfo mit dem Namen der Variable und der relativen Position der allokierten Variable hinzu.
//    Lowert die innere Expression.
//    Entfernt das hinzugefügte (letzte) Element des Namensstacks.
//    Gibt den Wert der inneren Expression aus.
//

    //    Bei einer Let-Expression ist hierbei left der Namensindex
//    der deklarierten Variable. und right der Expressionindex der Zuweisungs-Expression.
//    Es wird implizit angenommen, dass right + 1 der Expressionindex der inneren Expression ist.
//
    public Operand lowerLet(Expression expression) {
        int alloacate= makeLocal();
        int currentStackSize= stackSize.getCurrentStackSize();
        int nameIndex= expression.getLeft();
        int assigned = expression.getRight();
        int inner = expression.getRight()+1;


        Operand assignedOperand = lowerExpression(assigned);
        Instruction ins = new Instruction(Operation.Op, alloacate, assignedOperand);
        instructions.add(ins);
        stackSize.truncate(currentStackSize);
        pushNamedLocal(nameIndex, alloacate);
        Operand innerOp = lowerExpression(inner);
        popNamedLocal();
        return innerOp;



    }

//    Allokiert zunächst eine lokale Variable.
//    Speichert die derzeitige Stackgröße ab.
//    Lowert die linke Expression.
//    Lowert die rechte Expression.
//    Fügt eine Instruction der entsprechenden Art mit der allokierten Variable als Resultat, der linken bzw. rechten Expression
//    als linken bzw. rechten Wert hinzu.
//    Setzt die derzeitige Stackgröße auf die gespeicherte Stackgröße zurück.
//    Gibt einen lokalen Operand mit der allokierten Variable als Wert aus.
//
    public Operand lowerBinary(Expression expression, Operation operation) {
          int allocate= makeLocal();
          int currentStackSize = stackSize.getCurrentStackSize();
          int linke = expression.getLeft();
          int rechte = expression.getRight();
          Operand linkeOperand = lowerExpression(linke);
          Operand rechteOperand = lowerExpression(rechte);
          Instruction ins = new Instruction(operation, allocate, linkeOperand, rechteOperand);
          instructions.add(ins);
          stackSize.truncate(currentStackSize);
          Operand returnOperand = new Operand(true, allocate);
          return returnOperand;

    }

//    Ruft je nach Art der Expression die entsprechende Methode auf. Die Expression muss zunächst mithilfe der expressionIndex
//    aus dem AST geholt werden.
//    Number: Ruft lowerNumber mit der übergebenen Expression auf.
//            Identifier: Ruft lowerIdentifier mit der übergebenen Expression auf.
//            Let: Ruft lowerLet mit der übergebenen Expression auf.
//            Add, Sub, Mul, Div, Mod: Ruft lowerBinary mit der übergebenen Expression und der entsprechenden Operation auf.
//
    public Operand lowerExpression(int expressionIndex) {
       Expression expression = ast.getExpression(expressionIndex);
       ExpressionKind exKind = expression.getKind();
       if(exKind==ExpressionKind.Number){
           return lowerNumber(expression);
       }
       else if(exKind==ExpressionKind.Identifier){
           return lowerIdentifier(expression);
       }
       else if(exKind==ExpressionKind.Let){
           return lowerLet(expression);
       }
       else if(exKind==ExpressionKind.Add){
           return lowerBinary(expression, Operation.Add);
       }
       else if(exKind==ExpressionKind.Sub){
           return lowerBinary(expression, Operation.Sub);

       }
       else if(exKind==ExpressionKind.Mul){
           return lowerBinary(expression, Operation.Mul);
       }
       else if(exKind==ExpressionKind.Div){
           return lowerBinary(expression, Operation.Div);
       }
       else{
           return lowerBinary(expression, Operation.Mod);
       }
    }
}
