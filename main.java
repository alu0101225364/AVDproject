
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main {
  public static void main(String[] args) {
    String data = "";
    try {
      File myObj = new File("filename.c");
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        data += myReader.nextLine();
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
    
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
    for (int i = 0; i < AExp.size(); i++) {
      System.out.print(AExp.get(i) + ", ");
    }
    System.out.print("}\n");

    ArrayList<ArrayList<String>> AExpKill = new ArrayList<ArrayList<String>>();
    AExpKill = func.genAExpKill(arr);

    ArrayList<ArrayList<String>> AExpGen = new ArrayList<ArrayList<String>>();
    AExpGen = func.genAExpGen(arr);

    func.fixNextElements(arr);

    func.printCFG(arr);

    func.print(AExpKill, AExpGen, 2);

    ArrayList<ArrayList<String>> Entry = func.initialEquationsEntry(arr);
    func.print(Entry, func.initialExits(arr, Entry, AExpKill, AExpGen),1);

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
        strList.add(strList.size(), foostr[0] + "{");
        strList.add(strList.size(), foostr[1]);
      } else if (s[i].contains("}")) {
        if (s[i].contains("}}")) {

          strList.add(strList.size(), "}");
          strList.add(strList.size(), "}");

        } else if (i < s.length - 1) {
          String foostr[] = s[i].split("\\}");
          strList.add(strList.size(), foostr[0] + "}");
          strList.add(strList.size(), foostr[1]);

        } else {

          strList.add(strList.size(), "}");
        }
      } else {
        strList.add(strList.size(), s[i]);
      }

    }
    int fooNmbr = 1;
    triplet foo = new triplet(0, strList.get(0).trim());
    l.add((int) foo.getNumber(), foo);
    for (int i = 1; i < strList.size(); i++) {

      foo = new triplet(fooNmbr++, strList.get(i).trim());
      l.add(i, foo);

    }

    return l;
  }

}
