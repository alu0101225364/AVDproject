import java.util.*;

public class triplet {
  private double number;
  private String statement;
  private String type;

  public triplet(double number, String statement, String type){
    this.number = number;
    this.statement = statement;
    setType();
  }

  public double getNumber(){
    return number;
  }
  
  public String getStatement(){
    return statement;
  }

  public String getType(){
    return type;
  }

  private void setType(){
    //TODO Se me ocurrió implementar una función que evalue el statement y le asigne el tipo
    if (statement.contains("==") || statement.contains("!=") || statement.contains("<") || statement.contains("<=") || statement.contains(">") || statement.contains(">=") ) {
      this.type = "Condition";
      fixConditionStatement();
    }
    else if(statement.contains("=")){
      this.type = "Assignment";
    }
    else if(statement.contains("int") || statement.contains("bool")){
      this.type = "Declaration";
    }
  }

  private void fixConditionStatement(){
    //eliminar ifs y esas cosas de los statements
    if (statement.contains("if") || statement.contains("while")) {
      int first = statement.indexOf("(");
      int last = statement.lastIndexOf(")");
      statement = statement.substring(first + 1, last);
    }
  }

}