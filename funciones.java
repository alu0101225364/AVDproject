import java.util.*;

//TODO Terminar Kill & gen con otro tipo de statements

public class funciones {

  private ArrayList<String> AExp;
  private ArrayList<ArrayList<String>> AExpKill;
  private ArrayList<ArrayList<String>> AExpGen;


  public ArrayList<String> getAExp(){
    return AExp;
  }
  
  public ArrayList<ArrayList<String>> getAExpKill() {
    return AExpKill;
  }

  public ArrayList<ArrayList<String>> getAExpGen() {
    return AExpGen;
  }


  public ArrayList<String> genAExp(ArrayList<triplet> arr){
    Set<String> setSol = new HashSet<String>();
    for(int i = 0; i < arr.size(); i++){
      if (arr.get(i).getType() == "Assignment"){
        String foostr[] = arr.get(i).getStatement().split("=");
        setSol.add(foostr[1]);
      }
    }
    ArrayList<String> sol = new ArrayList<String>();
    sol.addAll(setSol);
    this.AExp = sol;
    return sol;
  }

  public ArrayList<ArrayList<String>> genAExpKill(ArrayList<triplet> arr){
    ArrayList<ArrayList<String>> sol = new ArrayList<ArrayList<String>>(20);

    for (int i = 0; i < arr.size(); i++) {
      ArrayList<String> fooArr = new ArrayList<String>();
      if (arr.get(i).getType() == "Assignment") {
        String foostr[] = arr.get(i).getStatement().split("=");
        String var = foostr[0].trim();
        for (int j = 0; j < AExp.size(); j++){
          if (AExp.get(j).contains(var)){
            fooArr.add(AExp.get(j));
          }
        }
      }
      sol.add(fooArr);
    }
    this.AExpKill = sol;
    return sol;
  }

  public ArrayList<ArrayList<String>> genAExpGen(ArrayList<triplet> arr){
    ArrayList<ArrayList<String>> sol = new ArrayList<ArrayList<String>>();
    for (int i = 0; i < arr.size(); i++) {
      ArrayList<String> fooArr = new ArrayList<String>();
      if (arr.get(i).getType() == "Assignment") {
        String exp = arr.get(i).getStatement();
        String foostr[] = arr.get(i).getStatement().split("=");
        String var = foostr[0].trim();
        if(!foostr[1].contains(var)){
          fooArr.add(foostr[1]);
        }
      } 
      sol.add(fooArr);
      }
    
    this.AExpGen = sol;
    return sol;
  }

  public void fixNextElements(ArrayList<triplet> arr){
    
    for (int i = 0; i< arr.size();i++){
      System.out.println(arr.get(i).getType());
      if (arr.get(i).getType() == "Condition_if"){

        arr.get(i).nextElement.add( (i + 1));
        arr.get(i + 1).prevElement.add((i));
        int count = 0;
        System.out.println("0000000");
        for (int j = i+1; j < arr.size(); j++){
          if (count == 0 && arr.get(j).getType()=="Else"){
            System.out.println("2222");
            arr.get(i).nextElement.add(j);
            arr.get(j).prevElement.add(i);
            System.out.println("AAAAAA");
          }
          else if(arr.get(j).getType() == "Condition_if") {
            count++;
            System.out.println("BBBBBB");

          }
          else if(arr.get(j).getType() == "Else"){
            count--;
            System.out.println("CCCCCC");
          }
        }
      }
      else if (arr.get(i).getType() == "Condition_while"){}
      else if(arr.get(i).getType() == "Else"){
        
      }
      else{
        System.out.println("12345672121121212");
        int foo = i;
        arr.get(i).nextElement.add(foo+1);
        if(i != arr.size()-1){
          arr.get(i+1).prevElement.add((i));
        }
      }
    }
    
  }

}