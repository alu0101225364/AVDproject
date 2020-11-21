import java.util.ArrayList;

public class main {
  public static void main (String[ ] args) {
    String a = "{int a;  int b;  int c;  read(a);  read(b);b = 2+a;c = a;  while (100>(2+a)) {a = b;b = 2+a;}print(a);}";
    System.out.println(a);
    funciones f = new funciones();
    ArrayList<triplet> s = f.slice(a);
    for (int i = 0; i < s.size(); i++){
      System.out.println(s.get(i).statement + s.get(i).number);
    }
    
  }
}
