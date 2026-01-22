package de.uniwue.jpp.compiler.util;

public interface NameCache {

  //  Erstellt einen neuen NameCache. Dieser muss noch keine Strings gecacht haben.
  //  Hierfür muss das Interface NameCache implementiert werden.
    public static NameCache create() {
       return new NameCacheImpl();
    }

//    Cacht einen String und gibt den dazugehörigen Namensindex aus.
//    Der zurückgegebene Namensindex muss eindeutig und einzigartig sein. In anderen Worten:
//    Der zurückgegebene Namensindex für einen String muss stets der Gleiche sein:
//    Für einen gegebenen Cache cache und zwei Strings a und b mit a.equals(b) muss cache.cacheName(a) == cache.cacheName(b).
//    Die zurückgegebenen Namensindizes für zwei unterschiedliche Strings müssen stets ungleich sein:
//    Für einen gegebenen Cache cache und zwei Strings a und b mit !a.equals(b) muss cache.cacheName(a) != cache.cacheName(b).
//
    public int cacheName(String name);


  //  Gibt den zum Namensindex zugehörigen gecachten String wieder zurück.
    public String getName(int id);
}
