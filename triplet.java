import java.util.*;

public class triplet {
  public double number;
  public String statement;
  public String type;

  public triplet(double number, String statement, String type){
    this.number = number;
    this.statement = statement;
    setType();
  }

  private void setType(){
    //TODO Se me ocurrió implementar una función que evalue el statement y le asigne el tipo
    if (statement.contains("==") || statement.contains("!=") || statement.contains("<") || statement.contains("<=") || statement.contains(">") || statement.contains(">=") ) {
      this.type = "Condition";
    }
  }
}