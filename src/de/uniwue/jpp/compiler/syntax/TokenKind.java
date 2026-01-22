package de.uniwue.jpp.compiler.syntax;

public enum TokenKind {
    EOF,    //"", bzw. keine Darstellung, hat somit eine Länge von 0	"End Of File", bezeichnet das Ende des Quellcodes
    Unknown,  //Jegliches unbekanntes Zeichen, hat somit eine Länge von 1	Bezeichnet ein unbekanntes Zeichen

    Number,  //Verschiedene Darstellungen möglich (siehe Grammatik)	Bezeichnet eine Zahl
    Identifier,   //Verschiedene Darstellungen möglich (siehe Grammatik)	Bezeichnet eine Variable

    LetKeyword,  //"let"
    InKeyword,  //"in"

    Plus,   // "+"
    Minus,    // "-"
    Times,   //  "*"
    Divide,   //  "/"
    Modulo,   //  "%"

    Equals,   // "="

    OpenParen,   //  "("
    CloseParen,   //  ")"
}
