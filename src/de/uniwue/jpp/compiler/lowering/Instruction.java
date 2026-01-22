package de.uniwue.jpp.compiler.lowering;

public class Instruction {
  //  Eine Instruction beschreibt, was der Evaluator machen und mit welchen Werten das geschehen soll.
    //  Sie hat immer einen Ergebnisort und mindestens einen, maximal zwei Operands.

       Operation operation;
       int result;
       Operand left;
       Operand right;

  //  Erstellt eine neue Instruction mit den übergebenen Parametern.
    public Instruction(Operation operation, int result, Operand left, Operand right) {
       this.operation=operation;
       this.result=result;
       this.left=left;
       this.right=right;
    }

 //   Erstellt eine neue Instruction mit den übergebenen Parametern.
 //   Es empfiehlt sich hier wieder für value das gleiche Feld wie für entweder left oder right zu verwenden.
    public Instruction(Operation operation, int result, Operand value) {
        this.operation=operation;
        this.result=result;
        this.left=value;

    }


    public Operation getOperation() {

       return operation;
    }

    public int getResult() {

     return result;
    }

    public Operand getLeft() {

        return left;
    }

    public Operand getRight() {
       return  right;

    }


  //  Gibt den einzigen Operand der Instruction zurück.
    public Operand getValue() {
        return left;

    }
}
