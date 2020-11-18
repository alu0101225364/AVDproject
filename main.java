public class main {
  public static void main (String [ ] args) {
    String a = "AA<A;BBB;CCCC";
    funciones f = new funciones();
    String aaa = f.slice(a).get(0).type;
    System.out.println(aaa);
  }
}
