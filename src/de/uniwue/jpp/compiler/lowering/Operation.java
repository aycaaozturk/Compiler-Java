package de.uniwue.jpp.compiler.lowering;

public enum Operation {
//    Der Enumationstyp Operation beschreibt die Art von Anweisung die der Evaluator machen soll.
//    Häufig es auch OpCode genannt. Jede Operation hat implizit eine sogenannte Arität,
//    welche die Anzahl an verwendeten Operanden beschreibt.
//
    Op, //    1	  Schreibt den Wert des Operanden an die Zielstelle

    Add, //   2	  Schreibt die Summe der beiden Operanden an die Zielstelle
    Sub, //   2   Schreibt den Wert des ersten Operanden minus dem Wert des zweiten Operanden an die Zielstelle
    Mul, //   2	  Schreibt das Produkt der beiden Operanden an die Zielstelle
    Div, //   2   Schreibt den Wert des ersten Operanden geteilt durch den Wert des zweiten Operanden an die Zielstelle
    Mod, //   2	  Schreibt den Wert des ersten Operanden modulo dem Wert des zweiten Operanden an die Zielstelle
}
