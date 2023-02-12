package it.proconsole.resourceclassifier.file;

import java.nio.file.Path;
import java.util.Map;

public record TaggedFile(Path path, Map<String, Integer> tags) {
}
