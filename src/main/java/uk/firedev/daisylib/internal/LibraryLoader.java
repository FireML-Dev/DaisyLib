package uk.firedev.daisylib.internal;

import com.google.gson.Gson;
import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.ApiStatus;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@SuppressWarnings("UnstableApiUsage")
@ApiStatus.Internal
public class LibraryLoader implements PluginLoader {

    @Override
    public void classloader(PluginClasspathBuilder classpathBuilder) {
        MavenLibraryResolver resolver = new MavenLibraryResolver();
        PluginLibraries pluginLibraries = load();
        pluginLibraries.asDependencies().forEach(resolver::addDependency);
        pluginLibraries.asRepositories().forEach(resolver::addRepository);
        classpathBuilder.addLibrary(resolver);
    }

    private @NonNull PluginLibraries load() {
        try (InputStream in = getClass().getResourceAsStream("/paper-libraries.json")) {
            if (in == null) {
                throw new IllegalStateException("Could not find paper-libraries.json");
            }
            return new Gson().fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), PluginLibraries.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private record PluginLibraries(Map<String, String> repositories, List<String> dependencies) {
        public Stream<Dependency> asDependencies() {
            if (dependencies == null) {
                return Stream.empty();
            }
            return dependencies.stream()
                .map(d -> new Dependency(new DefaultArtifact(d), null));
        }

        public Stream<RemoteRepository> asRepositories() {
            if (repositories == null) {
                return Stream.empty();
            }
            return repositories.entrySet().stream()
                .map(e -> new RemoteRepository.Builder(e.getKey(), "default", e.getValue()).build());
        }
    }

}
