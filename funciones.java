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
    int llaveCierraWhile = -1;
    for (int i = 0; i< arr.size();i++){
      System.out.println(arr.get(i).getType());
      if (arr.get(i).getType() == "Condition_if"){

        arr.get(i).nextElement.add( (i + 1));
        arr.get(i + 1).prevElement.add((i));
        int count = 0;
        for (int j = i+1; j < arr.size(); j++){
          if (count == 0 && arr.get(j).getType() == "Else"){
            arr.get(i).nextElement.add(j);
            arr.get(j).prevElement.add(i);
            break;
          }
          else if(arr.get(j).getType() == "Condition_if") {
            count++;
          }
          else if(arr.get(j).getType() == "Else"){
            count--;
          }
        }
      }
      else if (arr.get(i).getType() == "Condition_while"){
        arr.get(i).nextElement.add((i + 1));
        arr.get(i + 1).prevElement.add((i));
        int count = 0;
        for (int j = i + 1; j < arr.size(); j++) {
          if (count == 0 && arr.get(j).getStatement().contains("}")) {
            arr.get(i).nextElement.add(j+1);
            arr.get(i).prevElement.add(j);
            arr.get(j+1).prevElement.add(i);
            arr.get(j).nextElement.add(i);
            llaveCierraWhile = j;
            break;
          } else if (arr.get(j).getType() == "Condition_if") {
            count++;
          } else if (arr.get(j).getType() == "Else") {
            count--;
          }
        }
      }
      else if(arr.get(i).getType() == "Else"){
        arr.get(i).nextElement.add(i + 1);
        arr.get(i + 1).prevElement.add((i));
        //Añadir next al nodo ELSE  y añadir next al ultimo nodo del if y previous del siguiente nodo al else
        if(arr.get(i).getStatement().contains("{")){
           int count = 0;
           System.out.println("pPPPPPPPPPPPPPPP");
           for (int j = i+1; j < arr.size(); j++){
          if (count == 0 && arr.get(j).getStatement().contains("}")){
            //Añadir ultimo nodo del if al siguiente nodo despues de finalizar else
            arr.get(i-1).nextElement.add(j+1);
            arr.get(j+1).prevElement.add(i-1);
            break;
          }
          else if(arr.get(j).getType() == "Condition_if") {
            count++;

          }
          else if(arr.get(j).getType() == "Else"){
            count--;
          }
        }
      }
        else{
          //En caso de que solo haya un statement en el else, el next del ultimo del if será el segundo despues del else
          arr.get(i - 1).nextElement.add(i + 1);
          arr.get(i+1).prevElement.add(i-1);
        }
      }
      else{
        if (i == llaveCierraWhile){
          
        }
        else if(i != arr.size()-1 && arr.get(i + 1).getType() != "Else"){
          arr.get(i).nextElement.add(i+1);
          arr.get(i+1).prevElement.add((i));
        }
      }
    }
    
  }

  public ArrayList<ArrayList<String>> initialEquationsEntry(ArrayList<triplet> arr){
    ArrayList<ArrayList<String>> AEentry = new ArrayList<ArrayList<String>>();

    ArrayList<String> foo = new ArrayList<String>();
    foo.add("");
    AEentry.add(foo); 

    for (int i = 0; i< arr.size(); i++){
      foo.clear();
      if (i==1){
        foo.add("{}");
        AEentry.add(foo);
      }
      else{
        for(int j = 0; j < arr.get(i).prevElement.size(); j++){
          foo.add(arr.get(i).prevElement.get(j).toString());
          System.out.println("AAAAAA\n Nodo: " + i + "Prev: " + arr.get(i).prevElement.get(j));
        }
        AEentry.add(foo);
      }
      //System.out.println("\nAAAADSFÑJADKFSDKFASKDSKDKFDLKDF : " + AEentry.get(i) + " : ");

      System.out.println("\nArray a meter en posicion : " + i + " : ");
      for(int z = 0; z < foo.size(); z++){
        System.out.println(foo.get(z).toString());
      }
    }
    for (int i = 0; i < AEentry.size(); i++) {
      System.out.println("Nodo: " + AEentry.get(i));
      for (int j = 0; j < AEentry.get(i).size(); j++) {
        System.out.println(AEentry.get(i).get(j).toString() + "-");
      }
    }

    return AEentry;
  }

}