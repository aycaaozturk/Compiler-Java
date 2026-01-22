package de.uniwue.jpp.compiler.util;

import java.util.HashMap;
import java.util.Map;

public class NameCacheImpl implements NameCache {
    public HashMap<Integer, String> idToName = new HashMap<>();
    public HashMap<String, Integer> NameToId = new HashMap<>();
    public int Idcounter=0;



    //    Cacht einen String und gibt den dazugehörigen Namensindex aus.
//    Der zurückgegebene Namensindex muss eindeutig und einzigartig sein. In anderen Worten:
//    Der zurückgegebene Namensindex für einen String muss stets der Gleiche sein:
//    Für einen gegebenen Cache cache und zwei Strings a und b mit a.equals(b) muss cache.cacheName(a) == cache.cacheName(b).
//    Die zurückgegebenen Namensindizes für zwei unterschiedliche Strings müssen stets ungleich sein:
//    Für einen gegebenen Cache cache und zwei Strings a und b mit !a.equals(b) muss cache.cacheName(a) != cache.cacheName(b).
//


    @Override
    public int cacheName(String name) {
       if(NameToId.containsKey(name)){
           return NameToId.get(name);
       }
       else{
           int id =Idcounter++;  //arttirmadan önceki degerini al, sonra arttir
           NameToId.put(name, id);
           idToName.put(id, name);
           return id;
       }
    }

    @Override
    public String getName(int id) {
         return idToName.get(id);
    }
}
