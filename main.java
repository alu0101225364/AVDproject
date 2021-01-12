
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
  public static void main(String[] args) {
    String data = "";

    try {
      File myObj = new File("filename.txt");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data += myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }

    // String a = "{int a; int b; int c; read(a); read(b);b = 2+a;c = a; while
    // (100>(2+a)) {a = b;b = 2+a;}print(a);}";
    funciones func = new funciones();
    System.out.println(data);
    ArrayList<triplet> arr = slice(data);
    for (int i = 0; i < arr.size(); i++) {
      System.out.println("Statement: " + arr.get(i).getStatement() + " Number: " + arr.get(i).getNumber() + " Type: "
          + arr.get(i).getType() + "\n");
    }
    ArrayList<String> AExp = new ArrayList<String>();
    AExp = func.genAExp(arr);
    System.out.print("AEXP = { ");
    for (int i = 0; i < AExp.size(); i++){
      System.out.print(AExp.get(i) + ", ");
    }
    System.out.print("}\n");

    ArrayList<ArrayList<String>> AExpKill = new ArrayList<ArrayList<String>>();
    AExpKill = func.genAExpKill(arr);
    System.out.print("AEXPKILL = { ");
    for (int i = 0; i < AExpKill.size(); i++) {
      
      System.out.print("Number " + (i) + " : {");
      
        for (int j = 0; j < AExpKill.get(i).size(); j++) {
          System.out.print(AExpKill.get(i).get(j) + ", ");
        }

      System.out.print(" } ");

    }
    System.out.print("}\n");
    

    ArrayList<ArrayList<String>> AExpGen = new ArrayList<ArrayList<String>>();
    AExpGen = func.genAExpGen(arr);
    System.out.print("AEXPKILL = { ");
    for (int i = 0; i < AExpGen.size(); i++) {

      System.out.print("Number " + (i) + " : {");

      for (int j = 0; j < AExpGen.get(i).size(); j++) {
        System.out.print(AExpGen.get(i).get(j) + ", ");
      }

      System.out.print(" } ");

    }
    System.out.print("}\n");

  }

  // Funcion que devuelve un arrayList de triplets en las que estará para cada
  // statement su tipo y su numero
  public static ArrayList<triplet> slice(String str) {

    String s[] = str.split(";");

    ArrayList<String> strList = new ArrayList<String>();

    ArrayList<triplet> l = new ArrayList<triplet>();

    // Dividimos en dos las partes del código separadas por llaves
    for (int i = 0; i < s.length; i++) {
      if (s[i].contains("{")) {
        String foostr[] = s[i].split("\\{");
        strList.add(strList.size(), foostr[0]);
        strList.add(strList.size(), foostr[1]);
      } else if (s[i].contains("}") && i < s.length - 1) {
        String foostr[] = s[i].split("\\}");
        strList.add(strList.size(), foostr[0]);
        strList.add(strList.size(), foostr[1]);
      } else {
        strList.add(strList.size(), s[i]);
      }
    }

    for (int i = 0; i < strList.size(); i++) {
      triplet foo = new triplet((double) i, strList.get(i).trim());
      l.add((int) foo.getNumber(), foo);
    }

    return l;
  }

}
