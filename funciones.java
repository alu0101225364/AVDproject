import java.util.*;

public class funciones {

  //Funcion que devuelve un arrayList de triplets en las que estará para cada statement su tipo y su numero
  public ArrayList<triplet> slice(String str) {
    
    String s[] = str.split(";");

    ArrayList<String> strList = new ArrayList<String>();
    
    ArrayList<triplet> l = new ArrayList<triplet>();


    //Dividimos en dos las partes del código separadas por llaves 
    for (int i = 0; i < s.length; i++){
      if (s[i].contains("{")) {
        String foostr[] = s[i].split("\\{");
        strList.add(strList.size(),foostr[0]);
        strList.add(strList.size(),foostr[1]);
      }
      else if(s[i].contains("}") && i < s.length-1){
        String foostr[] = s[i].split("\\}");
        strList.add(strList.size(), foostr[0]);
        strList.add(strList.size(), foostr[1]);
      }
      else{
        strList.add(strList.size(), s[i]);
      }
    }

    for (int i = 0; i < strList.size(); i++){
        triplet foo = new triplet((double)i, strList.get(i), "TIPO A");
        l.add((int)foo.number, foo);
      }
    
    return l;
  }

}