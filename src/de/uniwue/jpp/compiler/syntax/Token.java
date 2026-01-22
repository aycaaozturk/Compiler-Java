package de.uniwue.jpp.compiler.syntax;

import de.uniwue.jpp.compiler.util.NameCache;

public class Token {
    //    Ein Token ist eine Abstraktion für eine Abfolge von Zeichen aus dem Ursprungstext, welche eine gewisse Bedeutung hat.
//    Die verschiedenen Arten/Bedeutungen sind in TokenKind abgespeichert.
//    Für die Ausgabe von Fehlern beim Kompilieren ist es hilfreich zu wissen, wo das Token im Text stand,
//    weshalb eine Position übergeben wird. Die Länge des Tokens muss nicht mit angegeben werden,
//    da fast alle Tokenarten nur eine Repräsentation zulassen.
//    Die einzigen zwei Arten die mehere Repräsentation zulassen sind Number und Identifier.
//    Für diese Tokenarten werden auch noch zusätzliche Daten benötigt: Der Wert der Zahl bzw. dem Namensindex des Identifiers.
//
    TokenKind kind;  //türü
    int position;    //kod icindeki pozisyonu
    int data;        //id
   //tek bir tokencik olusturuyo


    //    Erstellt ein neues Token mit den übergebenen Parametern.
    public Token(TokenKind kind, int position, int data) {
        this.kind = kind;
        this.position = position;
        this.data = data;
    }


    //    Erstellt ein neues Token mit den übergebenen Parametern. Hierbei wird kein Wert für data angegeben,
//    da nicht alle TokenKinds zusätzliche Daten benötigt. In diesem Fall soll ein Standartwert verwendet werden.
//    Hierfür eignet sich ein erkennbarer Wert wie Integer.MAX_VALUE, Integer.MIN_Value oder -1,
//    da so im Falle eines Fehlers im Code der Fehler schneller ausfindig gemacht werden kann.
//
    public Token(TokenKind kind, int position) {
        this.kind = kind;
        this.position = position;
        this.data = -1;
    }


    public TokenKind getKind() {
        return kind;
    }

    public int getPosition() {
        return position;
    }

    public int getData() {
        return data;
    }

    //    Gibt die Länge (Anzahl der Zeichen - wenn nicht anders definiert) des Tokens im übergebenen Quellcode source aus.
//    Der Quellcode source dient nur als "letzte" Möglichkeit
//    falls man nicht anderweitig die Länge des Tokens bestimmen kann und somit nochmal manuell das Token aus dem Code einlesen muss.
//    Hinweis: Nur für ein Token der Art Number muss auf source zugegriffen werden.
//
    public int getLength(NameCache names, String source) {
        if (kind == TokenKind.EOF) {
            return 0;
        } else if (kind == TokenKind.Unknown) {
            return 1;
        } else if (kind == TokenKind.Number) {
            int len = 0;
            while (position + len < source.length() && Character.isDigit(source.charAt(position + len))) {
                len++;

            }
            return len;
        }
       else if(kind==TokenKind.Identifier){
          int n =names.getName(data).length();  //DOGRUDUR INS
          return n;
        }
       else if(kind ==TokenKind.LetKeyword){
           return 3;
        }
       else if(kind==TokenKind.InKeyword){
           return 2;
        }
       else if(kind==TokenKind.Plus || kind==TokenKind.Minus || kind==TokenKind.Times || kind==TokenKind.Divide || kind==TokenKind.Modulo || kind==TokenKind.Equals || kind==TokenKind.OpenParen || kind==TokenKind.CloseParen){
          return 1;
    }
       else{
           return 0;
        }
}}
