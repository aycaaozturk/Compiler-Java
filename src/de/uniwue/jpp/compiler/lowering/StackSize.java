package de.uniwue.jpp.compiler.lowering;

public interface StackSize {
   // Ein Interface zur Berechnung des derzeitig benötigten Speicherplatzes und des maximal benötigten Speicherplatzes.


    //Erstellt eine neue StackSize. Hierfür muss das Interface StackSize implementiert werden.
    public static StackSize create() {
        return new StackSizeImpl();
    }

    public int getCurrentStackSize();
    public int getMaxStackSize();

    public int allocate(int amount);
    public void truncate(int size);
}
