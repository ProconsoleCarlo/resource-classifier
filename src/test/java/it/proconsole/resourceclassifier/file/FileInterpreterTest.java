package it.proconsole.resourceclassifier.file;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileInterpreterTest {
  private final FileInterpreter interpreter = new FileInterpreter("/file");

  @BeforeEach
  void setUp() {
  }

  @Test
  void interpret() {
    var current = interpreter.interpret();

    assertEquals(1, current.size());
    assertEquals(Map.of("R", 2, "D", 1, "V", 0), current.get(0).attributes());
  }

  @Test
  void saveAttributes() {
    var file = new TaggedFile(
            Path.of("C:\\Users\\carlo\\IdeaProjects\\resource-classifier\\target\\test-classes\\file\\First file name D1 R2 V0.html"),
            Set.of("firstTag", "secondTag"),
            Map.of("R", 2, "D", 1, "V", 0)
    );
    interpreter.saveAttributes(List.of(file));

  }
}