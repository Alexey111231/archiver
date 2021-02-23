package ru.vk.sladkiipirojok.archiver;

import ru.vk.sladkiipirojok.archiver.domain.Archiver;
import ru.vk.sladkiipirojok.archiver.input.Input;
import ru.vk.sladkiipirojok.archiver.output.Output;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Класс, являющийся свяюзующим звеном системы
 * Отвечает за получения данных от входа,
 * Передачу их в один из видом архиватора,
 * Обрабатывает ошибки и
 * отправляет данные на вывод
 */
public class Presenter {
    private final Archiver archiver;
    private final Input input;
    private final Output output;

    public Presenter(Archiver archiver, Input input, Output output) {
        this.archiver = archiver;
        this.input = input;
        this.output = output;
    }

    /**
     * Метод получает файлы из входа,
     * затем генерирует случайное имя архива
     * отправляет данные на архивацию
     * Отображает ошибки и записанные файлы
     */
    public void archive() {
        List<File> files = input.read();
        List<File> filteredFiles = files.stream()
                .filter(File::exists)
                .collect(Collectors.toList());

        String archiveName = UUID.randomUUID().toString();

        List<File> savedFiles;
        try {
            savedFiles = archiver.archive(new File(archiveName), filteredFiles);
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
            return;
        }

        StringBuilder outputString = new StringBuilder();
        Consumer<String> append = s -> outputString.append(s).append("\n");
        append.accept(archiveName);
        savedFiles.stream().map(File::getPath).forEach(append);

        output.write(outputString.toString());
    }

    /**
     * Метод получает файлы из входа, первым файлом всегда идет архив
     * отправляет данные на деархивацию
     * Отображает ошибки
     */
    public void unpack() {
        List<File> files = input.read();

        String archiveName = files.remove(0).getName();

        try {
            archiver.unpack(new File(archiveName), files);
        } catch (IllegalStateException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
