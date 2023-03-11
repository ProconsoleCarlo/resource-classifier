package it.proconsole.resourceclassifier.file;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;

public record TaggedFile(Path path, Set<String> tags, Map<String, Integer> attributes) {
}
