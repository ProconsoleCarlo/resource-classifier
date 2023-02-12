package it.proconsole.resourceclassifier;

import it.proconsole.resourceclassifier.file.FileInterpreter;

public class ResourceClassifierApplication {
  public static void main(String[] args) {
    var fileInterpreter = new FileInterpreter("E://RV");

    System.err.println(fileInterpreter.interpret());
  }
}
