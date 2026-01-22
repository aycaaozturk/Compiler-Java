package de.uniwue.jpp.compiler.lowering;

public class StackSizeImpl implements StackSize{
    // Ein Interface zur Berechnung des derzeitig benötigten Speicherplatzes und des maximal benötigten Speicherplatzes.
      int currentStackSize;  //ne kadar kullandik
      int maxStackSize;      //tüm depolama alani

   public StackSizeImpl(){
         this.currentStackSize=0;
         this.maxStackSize=0;
   }


 //   Gibt die derzeitige Stackgröße aus.
    @Override
    public int getCurrentStackSize() {
        return currentStackSize;
    }

 //   Gibt die derzeit höchste Stackgröße aus.
    @Override
    public int getMaxStackSize() {
        return maxStackSize;
    }

//    Allokiert amount Speicher auf dem Stack und gibt die vorherigen Stackgröße aus.
//    Die derzeitige Stackgröße soll also um amount erhöht werden.
//    Es muss auch darauf geachtet werden, dass die höchste Stackgröße gegebenenfalls angepasst werden muss.
    @Override
    public int allocate(int amount) {
       int vorher = currentStackSize;
        currentStackSize+=amount;
        if(currentStackSize>maxStackSize){
            maxStackSize=currentStackSize;
        }
        return vorher;  //önceki kullanilmis bellek miktarini veriyor

    }

 //   Reduziert die derzeitige Stackgröße auf size. Die höchste Stackgröße soll nicht verändert werden.
 //   Es darf die Annahme gemacht werden, dass size maximal so groß wie die derzeitige Stackgröße ist;
 //   wird ein höherer Wert als der derzeitige übergeben, ist der resultierende Zustand undefiniert.
    @Override
    public void truncate(int size) {
       if(size>maxStackSize){
           return;
       }
       else{
           currentStackSize=size;
       }
    }
}
