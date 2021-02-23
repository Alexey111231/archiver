package ru.vk.sladkiipirojok.archiver.domain;

import java.io.File;
import java.util.List;

public interface Archiver {
    List<File> archive(File archive, List<File> fileList);

    void unpack(File archive, List<File> fileList);
}
