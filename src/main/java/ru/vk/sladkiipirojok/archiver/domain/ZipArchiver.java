package ru.vk.sladkiipirojok.archiver.domain;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipArchiver implements Archiver {
    /**
     * Функция архивации данных
     *
     * @param archive  Файл архива для записи
     * @param fileList список файлов для записи
     * @return список сохраненных файлов
     */
    @Override
    public List<File> archive(File archive, List<File> fileList) {
        try (ZipOutputStream zipStream =
                     new ZipOutputStream(new FileOutputStream(archive))) {
            //Задаем степень компрессии файлов
            zipStream.setLevel(5);

            List<File> savedFiles = new ArrayList<>();
            //Рекурсивно сохраняем все файлы
            fileList.forEach(file -> doZip(file, zipStream, savedFiles));

            return savedFiles;
        } catch (IOException e) {
            throw new IllegalStateException("Failed to create archive try again");
        }
    }

    /**
     * Рекурсивный алгоритм для записи данных в архив
     *
     * @param file       файл или директория для записи
     * @param out        Поток архива для записи
     * @param savedFiles Список файлов сохранненых в архив
     */
    private static void doZip(File file, ZipOutputStream out, List<File> savedFiles) {
        if (file.isDirectory())
            //Пустые папки не сохраняются
            for (File f : file.listFiles()) {
                doZip(f, out, savedFiles);
            }
        else {
            try (FileInputStream fileStream = new FileInputStream(file)) {
                //Создаем запись в архиве
                out.putNextEntry(new ZipEntry(file.getPath()));
                //Записываем данные в новую запись
                write(fileStream, out);
                savedFiles.add(file);
            } catch (IOException e) {
                throw new IllegalStateException("Cannot write data to archive");
            }
        }
    }

    /**
     * @param archive  Файл архива
     * @param fileList Список файлов для разархифирования
     */
    @Override
    public void unpack(File archive, List<File> fileList) {
        try (ZipFile zip = new ZipFile(archive)) {
            for (File file : fileList) {
                ZipEntry entry = zip.getEntry(file.getPath());

                //Если заданного файла нет в архиве, то пропускаем его
                if (entry == null) {
                    continue;
                }

                //Пытаемся создать все директории, предшествующие данному файлу
                if (file.getParentFile() != null &&
                        !file.getParentFile().exists() &&
                        !file.getParentFile().mkdirs()) {
                    throw new IllegalStateException("Couldn't create dir: " + file.getParentFile());
                }

                try {
                    write(zip.getInputStream(entry),
                            new BufferedOutputStream(new FileOutputStream(
                                    file.getPath())));
                } catch (IOException e) {
                    throw new IllegalStateException("Cannot write data to file");
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Failed archive " + archive.getPath() + " not found");
        }
    }

    /**
     * Вспомогательная функция для записи данных
     *
     * @param in  входной поток файл или элемент архива
     * @param out выходной поток файл или элемент архива
     * @throws IOException Если данные невозможно записать или считать
     */
    private static void write(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) >= 0)
            out.write(buffer, 0, len);
    }
}
