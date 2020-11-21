public class pruebas {
  public static void main(String[] args) {
    String a = "while(a < 5 (2)) {";
    String aaa = "";
    if (a.contains("if") || a.contains("while")) {
      int first = a.indexOf("(");
      int last = a.lastIndexOf(")");
      aaa = a.substring(first + 1, last);
      // String str2[] = str[1].split("\\)");
      // aaa= str[0];
    }
    System.out.println(aaa);
  }
}
