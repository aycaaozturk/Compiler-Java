package de.uniwue.jpp.compiler.eval;

import java.util.List;

import de.uniwue.jpp.compiler.lowering.Instruction;
import de.uniwue.jpp.compiler.lowering.Operand;
import de.uniwue.jpp.compiler.lowering.Operation;
import de.uniwue.jpp.compiler.lowering.StackSize;

public class Evaluator {
//    Der Evaluator geht jede Instruction durch und behandelt diese.
//    Am Ende kann über getValue der endgültige Wert abgerufen werden.


    List<Instruction> instructions;
    int stackSize;
    int[] memory;

    //    Erstellt einen neuen Evaluator mit den übergebenen Parametern.
//    Hierbei soll direkt schon der Speicher mit einer Größe von stackSize Integern erstellt werden.
//    Der Inhalt des Speichers ist dabei egal, da allen Variablen vor der Nutzung ein Wert zugewiesen wird.
//
    public Evaluator(List<Instruction> instructions, int stackSize) {
        this.instructions=instructions;
        this.stackSize=stackSize;
        this.memory= new int[stackSize];

    }

 //   Erstellt einen neuen Evaluator mit den übergebenen Parametern. Dieser Konstruktor dient nur zu Testzwecken.
    public Evaluator(List<Instruction> instructions, int[] memory) {
       this.instructions=instructions;
       this.memory=memory;

    }

  //  Gibt den Speicher des Evaluators aus.
    public int[] getMemory() {

    return memory;
    }

//    Gibt den Wert des Operands aus. Ist der Operand lokal, so muss der Wert aus dem Speicher gelesen werden.
//    Ist er nicht lokal, so kann einfach der gespeicherte Wert ausgegeben werden.
//
    public int getValue(Operand operand) {
        int val=0;
        if(operand.isLocal()==true){
            val= memory[operand.getValue()];
        }
        else {
            val=operand.getValue();
        }
        return val;
    }


//    Evaluiert die übergebene Instruction. Dies erfolgt folgendermaßen:
//    Op: Kopiert den Wert des Value-Operands an die Stelle von Result.
//    Add: Berechnet left + right und speichert den Wert an die Stelle von Result.
//    Sub: Berechnet left - right und speichert den Wert an die Stelle von Result.
//    Mul: Berechnet left * right und speichert den Wert an die Stelle von Result.
//    Div: Berechnet left / right und speichert den Wert an die Stelle von Result.
//    Mod: Berechnet left % right und speichert den Wert an die Stelle von Result.

    public void evaluateInstruction(Instruction instruction) {
     //   Instruction(Operation operation, int result, Operand left, Operand right)
       int resultIndex= instruction.getResult();
       Operand left =instruction.getLeft();
       Operand right = instruction.getRight();
       Operation operationOfInstruction = instruction.getOperation();

       if(operationOfInstruction==Operation.Op){
             memory[resultIndex]= getValue(left);
       }
       else if(operationOfInstruction==Operation.Add){
           memory[resultIndex]= getValue(left)+getValue(right);
       }
       else if(operationOfInstruction==Operation.Sub){
           memory[resultIndex]= getValue(left)-getValue(right);
       }
       else if(operationOfInstruction==Operation.Mul){
           memory[resultIndex]= getValue(left)*getValue(right);
       }
       else if(operationOfInstruction==Operation.Div){
           memory[resultIndex]= getValue(left)/getValue(right);
       }
       else {
           memory[resultIndex]= getValue(left)%getValue(right);
       }




    }

//    Evaluiert die Instructions beginnend bei der ersten bis das Ende erreicht wurde.
//    Zum vereinfachten Testen soll darauf geachtet werden die evaluateInstruction zu verwenden,
//    statt Teile davon zu kopieren.
//
    public void evaluate() {
       for(Instruction i : instructions){
           evaluateInstruction(i);
       }
    }
}
