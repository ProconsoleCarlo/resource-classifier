package it.proconsole.resourceclassifier.file;

import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileInterpreter {
  private static final Pattern TAGS_REGEX = Pattern.compile("([a-zA-Z]\\d{1,2})");

  private final String rootPath;

  public FileInterpreter(String rootPath) {
    this.rootPath = rootPath;
  }

  private static String fileNameFrom(Path path) {
    return FilenameUtils.removeExtension(path.getFileName().toString());
  }

  public List<TaggedFile> interpret() {
    return readFiles().stream().map(it -> new TaggedFile(it, extractTags(it))).toList();
  }

  private List<Path> readFiles() {
    try (var files = Files.walk(Path.of(rootPath))) {
      return files.filter(Files::isRegularFile).toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Map<String, Integer> extractTags(Path path) {
    return TAGS_REGEX.matcher(fileNameFrom(path)).results()
            .map(MatchResult::group)
            .collect(Collectors.toMap(
                    it -> it.substring(0, 1),
                    it -> Integer.valueOf(it.substring(1))
            ));
  }
}
