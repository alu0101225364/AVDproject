import java.util.*;

public class funciones {

  //Funcion que devuelve un arrayList de triplets en las que estará para cada statement su tipo y su numero
  public ArrayList<triplet> slice(String str) {
    
    String s[] = str.split(";");
    
    ArrayList<triplet> l = new ArrayList<triplet>();

    for (int i = 0; i < s.length; i++){
      triplet foo = new triplet((double)i, s[i], "TIPO A");
      l.add((int)foo.number, foo);
    }
    return l;
  }

}