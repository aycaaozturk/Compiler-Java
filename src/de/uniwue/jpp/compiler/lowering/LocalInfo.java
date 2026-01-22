package de.uniwue.jpp.compiler.lowering;

public class LocalInfo {
 //   Die LocalInfo repräsentiert eine lokale Variable. In ihr ist der Name der Variable und ihr Ort im Speicher gespeichert.

//    Bir yerel değişkenin (local variable) bilgilerini saklar.
//    Bir değişkenin adı ve bellekteki yeri (offset) bu sınıf içinde tutulur.

//    Bir fonksiyon veya blok içinde tanımlanan ve sadece o blok içinde erişilebilen değişkenlere "lokal değişken" denir.
//
    int name;
    int offset;

 //   Erstellt eine neue LocalInfo mit den übergebenen Parametern.
    public LocalInfo(int name, int offset) {
      this.name=name;
      this.offset=offset;  //Ort im Speicher

    }


  //  Gibt den Namen der lokalen Variable aus.
    public int getName() {

      return name;
    }

  //  Gibt die relative Position im Speicher an.
    public int getOffset() {

       return offset;
    }
}
