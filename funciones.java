import java.util.*;

public class funciones {

  private ArrayList<String> AExp;
  private ArrayList<ArrayList<String>> AExpKill;
  private ArrayList<ArrayList<String>> AExpGen;

  public ArrayList<String> getAExp() {
    return AExp;
  }

  public ArrayList<ArrayList<String>> getAExpKill() {
    return AExpKill;
  }

  public ArrayList<ArrayList<String>> getAExpGen() {
    return AExpGen;
  }

  private String[] separateCondition(String condition) {
    String foostr[] = { "" };
    if (condition.contains("<=")) {
      foostr = condition.split("<=");
    } else if (condition.contains(">=")) {
      foostr = condition.split(">=");
    } else if (condition.contains(">")) {
      foostr = condition.split(">");
    } else if (condition.contains("<")) {
      foostr = condition.split("<");
    } else if (condition.contains("==")) {
      foostr = condition.split("==");
    }
    return foostr;
  }

  public ArrayList<String> genAExp(ArrayList<triplet> arr) {
    Set<String> setSol = new HashSet<String>();
    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i).getType() == "Assignment") {
        String foostr[] = arr.get(i).getStatement().split("=");
        setSol.add(foostr[1].trim());
      }
      if (arr.get(i).getType().contains("Condition")) {
        String foostr[] = separateCondition(arr.get(i).getStatement());
        if (foostr[0].matches(".*[a-z].*")) {
          setSol.add(foostr[0].trim());
        }
        if (foostr[1].matches(".*[a-z].*")) {
          setSol.add(foostr[1].trim());
        }
      }
    }
    ArrayList<String> sol = new ArrayList<String>();
    sol.addAll(setSol);
    this.AExp = sol;
    return sol;

  }

  public ArrayList<ArrayList<String>> genAExpKill(ArrayList<triplet> arr) {
    ArrayList<ArrayList<String>> sol = new ArrayList<ArrayList<String>>(20);

    for (int i = 0; i < arr.size(); i++) {
      ArrayList<String> fooArr = new ArrayList<String>();
      if (arr.get(i).getType() == "Assignment") {
        String foostr[] = arr.get(i).getStatement().split("=");
        String var = foostr[0].trim();
        for (int j = 0; j < AExp.size(); j++) {
          if (AExp.get(j).contains(var)) {
            fooArr.add(AExp.get(j).trim());
          }
        }
      }
      sol.add(fooArr);
    }
    this.AExpKill = sol;
    return sol;
  }

  public ArrayList<ArrayList<String>> genAExpGen(ArrayList<triplet> arr) {
    ArrayList<ArrayList<String>> sol = new ArrayList<ArrayList<String>>();
    for (int i = 0; i < arr.size(); i++) {
      ArrayList<String> fooArr = new ArrayList<String>();
      if (arr.get(i).getType() == "Assignment") {
        String exp = arr.get(i).getStatement();
        String foostr[] = arr.get(i).getStatement().split("=");
        String var = foostr[0].trim();
        if (!foostr[1].contains(var)) {
          fooArr.add(foostr[1].trim());
        }
      }
      if (arr.get(i).getType().contains("Condition")) {
        String foostr[] = separateCondition(arr.get(i).getStatement());
        if (foostr[0].matches(".*[a-z].*")) {
          fooArr.add(foostr[0].trim());
        }
        if (foostr[1].matches(".*[a-z].*")) {
          fooArr.add(foostr[1].trim());
        }

      }
      sol.add(fooArr);
    }

    this.AExpGen = sol;
    return sol;

  }

  public void fixNextElements(ArrayList<triplet> arr) {
    int llaveCierraWhile = -1;
    for (int i = 0; i < arr.size(); i++) {
      if (arr.get(i).getType() == "Condition_if") {

        arr.get(i).nextElement.add((i + 1));
        arr.get(i + 1).prevElement.add((i));
        int count = 0;
        for (int j = i + 1; j < arr.size(); j++) {
          if (count == 0 && arr.get(j).getType() == "Else") {
            arr.get(i).nextElement.add(j);
            arr.get(j).prevElement.add(i);
            break;
          } else if (arr.get(j).getType() == "Condition_if") {
            count++;
          } else if (arr.get(j).getType() == "Else") {
            count--;
          }
        }
      } else if (arr.get(i).getType() == "Condition_while") {
        arr.get(i).nextElement.add((i + 1));
        arr.get(i + 1).prevElement.add((i));
        int count = 0;
        for (int j = i + 1; j < arr.size(); j++) {
          if (count == 0 && arr.get(j).getStatement().contains("}")) {
            arr.get(i).nextElement.add(j + 1);
            arr.get(i).prevElement.add(j);
            arr.get(j + 1).prevElement.add(i);
            arr.get(j).nextElement.add(i);
            llaveCierraWhile = j;
            break;
          } else if (arr.get(j).getType() == "Condition_if") {
            count++;
          } else if (arr.get(j).getType() == "Else") {
            count--;
          }
        }
      } else if (arr.get(i).getType() == "Else") {
        arr.get(i).nextElement.add(i + 1);
        arr.get(i + 1).prevElement.add((i));
        // Añadir next al nodo ELSE y añadir next al ultimo nodo del if y previous del
        // siguiente nodo al else
        if (arr.get(i).getStatement().contains("{")) {
          int count = 0;

          for (int j = i + 1; j < arr.size(); j++) {
            if (count == 0 && arr.get(j).getStatement().contains("}")) {
              // Añadir ultimo nodo del if al siguiente nodo despues de finalizar else
              arr.get(i - 1).nextElement.add(j + 1);
              arr.get(j + 1).prevElement.add(i - 1);
              break;
            } else if (arr.get(j).getType() == "Condition_if") {
              count++;

            } else if (arr.get(j).getType() == "Else") {
              count--;
            }
          }
        } else {
          // En caso de que solo haya un statement en el else, el next del ultimo del if
          // será el segundo despues del else
          arr.get(i - 1).nextElement.add(i + 1);
          arr.get(i + 1).prevElement.add(i - 1);
        }
      } else {
        if (i == llaveCierraWhile) {

        } else if (i != arr.size() - 1 && arr.get(i + 1).getType() != "Else") {
          arr.get(i).nextElement.add(i + 1);
          arr.get(i + 1).prevElement.add((i));
        }
      }
    }

  }

  public ArrayList<ArrayList<String>> initialExits(ArrayList<triplet> arr, ArrayList<ArrayList<String>> entries,
      ArrayList<ArrayList<String>> kill, ArrayList<ArrayList<String>> gen) {
    ArrayList<ArrayList<String>> AExit = new ArrayList<ArrayList<String>>();
    ArrayList<String> exit_subst = new ArrayList<String>();
    ArrayList<String> exit_union = new ArrayList<String>();

    for (int i = 0; i < arr.size(); i++) {

      if (i == 0) {
        AExit.add(exit_union);
        continue;
      }

      if (arr.get(i).prevElement.size() == 1) {
        exit_union = new ArrayList<String>();
        exit_subst = new ArrayList<String>(entries.get(i));
        // exit_subst = entries.get(i);
        exit_subst.removeAll(kill.get(i));

        exit_union = exit_subst;
        exit_union.addAll(gen.get(i));
      }

      if (i < arr.size() - 1)
        entries.set(i + 1, exit_union);
      AExit.add(exit_union);
    }

    return AExit;
  }

  public ArrayList<ArrayList<String>> initialEquationsEntry(ArrayList<triplet> arr) {
    ArrayList<ArrayList<String>> AEentry = new ArrayList<ArrayList<String>>();

    ArrayList<String> foo = new ArrayList<String>();
    foo.add("");
    AEentry.add(foo);

    for (int i = 1; i < arr.size(); i++) {
      foo = new ArrayList<String>();
      if (i == 1) {
        foo.add("{}");
        AEentry.add(foo);
      } else {
        for (int j = 0; j < arr.get(i).prevElement.size(); j++) {
          foo.add(arr.get(i).prevElement.get(j).toString());
        }
        AEentry.add(foo);
      }
    }
    return AEentry;
  }

  public void print(ArrayList<ArrayList<String>> kill, ArrayList<ArrayList<String>> gen, int tipo) {
    final Object[][] table = new String[kill.size()][];
    String str_kill[] = new String[kill.size()];
    String str_gen[] = new String[kill.size()];

    for (int j = 0; j < kill.size(); j++) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < kill.get(j).size(); i++) {
        sb.append("[" + kill.get(j).get(i) + "]");
      }
      str_kill[j] = sb.toString();
      if (sb.toString().length() == 0) {
        str_kill[j] = "{}";
      }
    }

    for (int j = 0; j < gen.size(); j++) {
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < gen.get(j).size(); i++) {
        sb.append("[" + gen.get(j).get(i) + "]");
      }
      str_gen[j] = sb.toString();
      if (sb.toString().length() == 0) {
        str_gen[j] = "{}";
      }
    }

    for (int i = 0; i < kill.size(); i++) {
      if (str_kill[i].length() == 0) {
        table[i] = new String[] { "{}", str_gen[i] };
      }
      if (str_gen[i].length() == 0) {
        table[i] = new String[] { str_kill[i], "{}" };
      }
      if (str_gen[i].length() == 0 && str_kill[i].length() == 0) {
        table[i] = new String[] { "{}", "{}" };
      }
      table[i] = new String[] { str_kill[i], str_gen[i] };
    }
    System.out.println("____________________");
    if (tipo == 1) {
      String header[] = new String[] { "Entry", "Exit" };
      System.out.format("%-40s%-40s\n", header);
    }
    if (tipo == 2) {
      String header[] = new String[] { "Kill", "Gen" };
      System.out.format("%-40s%-40s\n", header);
    }
    System.out.println("____________________");

    table[0] = new String[] { "", "" };

    for (final Object[] row : table) {
      System.out.format("%-40s%-40s\n", row);
    }
  }

  public void conclusiones(ArrayList<triplet> arr, ArrayList<ArrayList<String>> entry) {
    for (int i = 0; i < entry.size(); i++) {

      if (arr.get(i).getType().contains("Assignment")) {
        String foostr[] = arr.get(i).getStatement().split("=");
        if (entry.get(i).contains(foostr[1].trim())) {
          System.out.println("We can save the computation of " + foostr[1] + " because " + foostr[1]
              + " is contained in entry(" + i + ")");
        }
      }

      if (arr.get(i).getType().contains("Condition")) {
        String foostr[] = separateCondition(arr.get(i).getStatement());
        if (entry.get(i).contains(foostr[0].trim())) {
          System.out.println("We can save the computation of " + foostr[0] + " because " + foostr[0]
              + " is contained in entry(" + i + ")");
        }
        if (entry.get(i).contains(foostr[1].trim())) {
          System.out.println("We can save the computation of " + foostr[1] + " because " + foostr[1]
              + " is contained in entry(" + i + ")");
        }

      }

    }
  }

  public void printCFG(ArrayList<triplet> arr) {
    String CFG = "";
    for (int i = 0; i < arr.size(); i++) {
      CFG += "Statement: " + arr.get(i).getStatement() + " Number: " + arr.get(i).getNumber() + " Type: "
          + arr.get(i).getType() + "\n NEXT: {";

      for (int j = 0; j < arr.get(i).nextElement.size(); j++) {

        CFG += arr.get(i).nextElement.get(j) + "-";

      }

      CFG += "} \n PREVIOUS: {";

      for (int j = 0; j < arr.get(i).prevElement.size(); j++) {
        CFG += arr.get(i).prevElement.get(j) + "-";
      }
      CFG += "}\n";
    }

    System.out.println("\nControl Flow Graph: \n " + CFG);
  }
}
