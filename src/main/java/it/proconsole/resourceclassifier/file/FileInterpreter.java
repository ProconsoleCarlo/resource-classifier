package it.proconsole.resourceclassifier.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileInterpreter {
  private static final Pattern TAGS_REGEX = Pattern.compile("([a-zA-Z]\\d{1,2})");
  private static final String ATTRIBUTE_EXT = ".json";

  private final ObjectMapper mapper = new ObjectMapper();

  private final String rootPath;

  public FileInterpreter(String rootPath) {
    this.rootPath = rootPath;
  }

  public List<TaggedFile> interpret() {
    return readFiles().stream()
            .map(it -> new TaggedFile(it, Collections.emptySet(), extractTags(it)))
            .toList();
  }

  public void saveAttributes(List<TaggedFile> files) {
    files.forEach(
            it -> {
              try {
                mapper.writerWithDefaultPrettyPrinter().writeValue(it.path().resolveSibling(it.path().getFileName() + ATTRIBUTE_EXT).toFile(), it);
              } catch (IOException e) {
                throw new RuntimeException(e);
              }
            }
    );
  }

  private List<Path> readFiles() {
    try (var files = Files.walk(Paths.get(this.getClass().getResource(rootPath).toURI()))) {
      return files.filter(Files::isRegularFile)
              .filter(it -> !isAttributeFile(it))
              .toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }

  private static boolean isAttributeFile(Path it) {
    return it.getFileName().toString().endsWith(ATTRIBUTE_EXT);
  }

  private Map<String, Integer> extractTags(Path path) {
    return TAGS_REGEX.matcher(fileNameFrom(path)).results()
            .map(MatchResult::group)
            .collect(Collectors.toMap(
                    it -> it.substring(0, 1),
                    it -> Integer.valueOf(it.substring(1))
            ));
  }

  private static String fileNameFrom(Path path) {
    return FilenameUtils.removeExtension(path.getFileName().toString());
  }
}
