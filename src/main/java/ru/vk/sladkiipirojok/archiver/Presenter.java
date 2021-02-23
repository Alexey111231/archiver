package ru.vk.sladkiipirojok.archiver;

import ru.vk.sladkiipirojok.archiver.domain.Archiver;
import ru.vk.sladkiipirojok.archiver.input.Input;
import ru.vk.sladkiipirojok.archiver.output.Output;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class Presenter {
    private final Archiver archiver;
    private final Input input;
    private final Output output;

    public Presenter(Archiver archiver, Input input, Output output) {
        this.archiver = archiver;
        this.input = input;
        this.output = output;
    }

    public void archive() {
        List<File> files = input.read();
        List<File> filteredFiles = files.stream()
                .filter(File::exists)
                .collect(Collectors.toList());

        String archiveName = UUID.randomUUID().toString();

        archiver.archive(archiveName, filteredFiles);

        StringBuilder outputString = new StringBuilder();
        Consumer<String> append = s -> outputString.append(s).append("\n");
        append.accept(archiveName);
        filteredFiles.stream().map(File::getName).forEach(append);

        output.write(outputString.toString());
    }

    public void unpack() {
        List<File> files = input.read();

        String archiveName = files.remove(0).getName();

        archiver.unpack(archiveName, files);
    }
}
