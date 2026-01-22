package de.uniwue.jpp.compiler.lowering;

public class Operand {
//    Bellek	Integer’lardan oluşan bir liste (byte seviyesi erişim yok).
//    Register Kullanımı	Yok (tüm işlemler doğrudan bellekte saklanıyor).
//    Instruction (Komut)	Tür + Bellek Adresi + 1-2 Operand içeriyor.
//    Operand (İşlenen Değer)	Sabit sayı veya bellek adresi olabilir.
//    Kod Çalıştırma	Tüm komutlar sırayla (lineer) çalıştırılır.

//    Bir işlemde (instruction) kullanılan değerleri temsil eder.
//    Bir işlem (örneğin x = 5 + y) iki tür operand içerebilir:
//
//    Sabit Değer (Constant Operand) → Örn: 5
//    Lokal Değişken (Local Variable Operand) → Örn: y (bellekte tutuluyor)
//    Kod yürütme sırasında değişkenlerin gerçek değerlerini bilmediğimiz için, onların bellekteki adreslerini saklarız.


//    Ein Operand beschreibt einen Wert mit dem eine Instruction zu arbeiten hat.
//    Dieser kann eine Konstante oder eine lokale Variable sein.
//    Da wir zum Zeitpunkt der Codegeneration den Wert einer Variable noch nicht kennen,
//    wird stattdessen ihr Speicherort vermerkt.
//

        boolean isLocal;
        int value;


 //   Erstellt einen neuen Operand mit den übergebenen Parametern.
    public Operand(boolean isLocal, int value) {
       this.isLocal=isLocal;
       this.value=value;   // Eğer lokal değişkense bellekteki konumu  (VARIABLE ISE),
                          // lokal değilse sabit değer
    }


   // Gibt aus, ob der Operand eine lokale Variable ist.
    public boolean isLocal() {

       return isLocal;
    }


    // Gibt den Wert des Operands aus.
    public int getValue() {

       return value;
    }
}
