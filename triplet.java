import java.util.*;

public class triplet {
  private double number;
  private String statement;
  private String type;
  public ArrayList<Integer> nextElement;
  public ArrayList<Integer> prevElement;


  public triplet() {
  }

  public triplet(double number, String statement) {
    this.number = number;
    this.statement = statement;
    nextElement = new ArrayList<Integer>();
    prevElement = new ArrayList<Integer>();
    setType();
  }

  public double getNumber() {
    return number;
  }

  public void setNumber(double n) {
    this.number = n;
  }

  public String getStatement() {
    return statement;
  }

  public String getType() {
    return this.type;
  }

  private void setType() {
    // TODO Se me ocurrió implementar una función que evalue el statement y le
    // asigne el tipo
    // TODO contemplar else
    if (statement.contains("==") || statement.contains("!=") || statement.contains("<") || statement.contains("<=")
        || statement.contains(">") || statement.contains(">=")) {
      if (statement.contains("while")) {
        this.type = "Condition_while";
      } else {
        this.type = "Condition_if";
      }
      fixConditionStatement();
    } else if (statement.contains("=")) {
      this.type = "Assignment";
    } else if (statement.contains("int ") || statement.contains("bool")) {
      this.type = "Declaration";
    } else if (statement.contains("else")){
      this.type = "Else";
    }
    else 
    {
      this.type = "Function";
    }
  }

  private void fixConditionStatement() {
    // eliminar ifs y esas cosas de los statements
    if (statement.contains("if") || statement.contains("while")) {
      int first = statement.indexOf("(");
      int last = statement.lastIndexOf(")");
      statement = statement.substring(first + 1, last);
    }
  }



}