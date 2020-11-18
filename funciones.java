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

  public static void main(String[] args) {
    String a = "AAA;BBB;CCCC";
    funciones f = new funciones();
    String aaa = f.slice(a).get(2).statement;
    System.out.println(aaa);
  }
}