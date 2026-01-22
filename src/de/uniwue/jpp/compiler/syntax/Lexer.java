package de.uniwue.jpp.compiler.syntax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.uniwue.jpp.compiler.util.NameCache;

public class Lexer {
//    Der Lexer geht durch den gesamten Text und erstellt währenddessen die erkannten Tokens.
//    In unserem Fall wird der gesamte Text auf einmal durch gegangen und alle Tokens erstellt,
//    bevor die Tokens dem Parser übergeben werden.
//    In manchen Compilern und speziell in Interpretern wird stattdessen ein sogenannter Token-Stream erstellt.
//    Dieser hat den Vorteil, dass nicht der gesamte Text auf einmal eingelesen werden muss
//    und die Tokens auch nicht abgespeichert werden müssen,
//    da der Parser dann immer erst das nächste Token anfrägt und dann direkt verwendet.
//
//    Für unsere Implementierung soll bei den Lex-Methoden (letName, letNumber, lexToken) darauf geachtet werden,
//    dass getCurrent() die Position direkt nach dem gelexten Token ausgeben soll.
//    Startet eine solche Methode also bei einer Position i und lext ein Token der Länge l,
//    so sollte der Lexer danach an Position i + l sein. 0. pos 5 harfli 5. pozda durucak
//
     public String text;    //okudugu text bu
     public int position;   //textte okumaya basladigi pozisyon
     public NameCache names;     //name deposu, map icinde isim-id donüsümleri var cift yönlü





//    Erstellt einen neuen Lexer mit Startposition 0.
    public Lexer(String text, NameCache names) {
        this.text=text;
        this.position=0;
        this.names=names;
    }

//    Erstellt einen neuen Lexer mit Startposition start.
//    Dieser Konstruktor wird nicht in der normalen Pipeline verwendet, sondern dient nur zu Testzwecken.
    public Lexer(String text, NameCache names, int start) {
       this.text=text;
       this.names=names;
       this.position=start;

    }


    public static List<Token> lex(String text, NameCache names) {
       Lexer newLex = new Lexer(text, names);
       return newLex.lexAll();
    }






//    Gibt die derzeitige Position des Lexers zurück.
    public int getCurrent() {

       return position;
    }


//    Überspringt jegliche Form von Leerzeichen, insbesondere
//
//    Horizontales Tabulatorzeichen (Horizontal Tabulation (HT), '\t', U+0009)
//    Zeilenvorschub (Line Feed (LF), '\n', U+000A)
//    Zeilenumbruch (Carriage Return (CR), \r, U+000D) zur Kompatibilität für Windows
//    das eigentliche Leerzeichen (Space, ' ', U+0020)
//
//    Um zu überprüfen, ob ein Zeichen eine Art von Leerzeichen ist kann Character.isWhitespace verwendet werden.
//    Die Position des Lexers sollte also am Ende hinter der (möglicherweise leeren) Reihe von Leerzeichen sein.
//
    public void skipWhitespace() {
        while(position<text.length() && Character.isWhitespace(text.charAt(position))){
            position++;
        }
    }




//    Lext einen Namen und erstellt ein dazu passendes Token. Ein Name fängt mit einem alphabetischen Zeichen (A-Z, a-z) an,
//    gefolgt von weiteren alphabetischen Zeichen, Ziffern (0-9) und/oder Unterstrichen (_).
//
//    Entspricht der Name einem Keyword ("let", "in") so soll ein Token mit dementsprechender Art ausgegeben werden.
//    Ansonsten ist es ein Identifier: Der Name soll dem NameCache hinzugefügt werden
//    und ein Token der Art Identifier ausgegeben werden, wobei hierbei das Token den Namensindex aus dem NameCache
//    als zusätzliche Daten enthalten soll.
//
    public Token lexName(){
       int start = position;
//       if(!Character.isLetter(text.charAt(position))){
//           throw new UnsupportedOperationException("cant find name lexer");
//       }   //ilk karakter harf degilse hata ver
//
//

       position++;  //ilk harf zaten harf ins o yüden devamina bak

      while(position<text.length() &&(Character.isLetter(text.charAt(position)) ||
                Character.isDigit(text.charAt(position)) ||
                text.charAt(position) == '_') ){
          position++;  //kelime uzunlugunu bulduk

      }
      String name = text.substring(start, position);
      if(name.equals("let")){
          Token letToken = new Token(TokenKind.LetKeyword, start);
          return letToken;
      }
      else if(name.equals("in")){
          Token inToken = new Token(TokenKind.InKeyword, start);
          return inToken;
      }
      else{
          int identID = names.cacheName(name);
          Token idToken = new Token(TokenKind.Identifier, start, identID);
          return idToken;
      }


    }



//    Lext eine Zahl und erstellt ein dazu passendes Token. Eine Zahl fängt mit einer Ziffer (0-9) and,
//    gefolgt von weiteren Ziffern oder Unterstrichen (_). Am Ende soll ein Token der Art Number ausgegeben werden,
//    wobei des Token den Wert der dargestellten Zahl als zusätzliche Daten enthalten soll.
//
    public Token lexNumber() {
     int start = position;
//     if(!Character.isDigit(text.charAt(start))){
//         throw new UnsupportedOperationException("not a number (lexer");
//     }
       //  position++;
     while(position<text.length() &&( ( text.charAt(position)>='0' && text.charAt(position)<='9') || text.charAt(position)=='_') ){
         position++;
     }

     String numberStringwithLine = text.substring(start, position);
     String numberString = numberStringwithLine.replace("_", "");
     int number = Integer.parseInt(numberString);
     Token numberToken = new Token(TokenKind.Number, start, number);
    // position++;
     return numberToken;


    }


//    Lext und erstellt jegliches Token. Ist der Lexer bereits am Ende des Texts angekommen,
//    soll ein Token der Art EOF ausgegeben werden. Ist das derzeitige Zeichen alphabetisch oder
//    eine Ziffer soll ein Name bzw. eine Zahl gelext werden. Desweiteren gibt es folgende zu lexende Tokens
//    ohne zusätzliche Daten:
//...
//Ist das derzeitige Zeichen keines der oben genannten, soll es trotzdem konsumiert werden und ein Token der Art Unknown ausgegeben werden.
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
//

    public Token lexToken() {

        Token un = new Token(TokenKind.Unknown, position);
        if(position>=text.length()){
            Token end = new Token(TokenKind.EOF, position);
            return end;
        }
      //  char currentChar = text.charAt(position);

       if(text.charAt(position) == '+'){
           position++;
            Token plusToken = new Token(TokenKind.Plus, position-1);
            return plusToken;

        }
        else if(text.charAt(position) == '-'){
           position++;
            Token minusToken = new Token(TokenKind.Minus, position-1);
            return minusToken;
        }
        else if(text.charAt(position) == '*'){
           position++;
            Token multiplyToken = new Token(TokenKind.Times, position-1);
                    return multiplyToken;
        }
         else if(text.charAt(position) == '/'){
           position++;
            Token divideToken = new Token(TokenKind.Divide, position-1);
            return divideToken;
        }
       else  if(text.charAt(position)== '%'){
           position++;
            Token moduloToken = new Token(TokenKind.Modulo, position-1);
            return moduloToken;
        }
       else if(text.charAt(position) == '('){
           position++;
           Token openPar = new Token(TokenKind.OpenParen, position-1);
           return openPar;
        }
       else if(text.charAt(position)== ')'){
           position++;
           Token closePar = new Token(TokenKind.CloseParen, position-1);
           return closePar;
        }
       else if(text.charAt(position)=='='){
           position++;
           Token equal = new Token(TokenKind.Equals, position-1);
           return equal;
       }


       else if(Character.isLetter(text.charAt(position))){
            return lexName();
        }
       else if(Character.isDigit(text.charAt(position))){
            return lexNumber();
        }
       else{
           position++;
           Token unknown = new Token(TokenKind.Unknown, position-1);
           return unknown;
        }




    }




    public List<Token> lexAll() {
        List<Token> tokenList = new ArrayList<>();
        while(position<text.length()){
            skipWhitespace();
            if(position>=text.length()){
                break;
            }
            Token token = lexToken();
            tokenList.add(token);


            }
        Token endToken = new Token(TokenKind.EOF, position);
        tokenList.add(endToken);
        return tokenList;





        }


}
