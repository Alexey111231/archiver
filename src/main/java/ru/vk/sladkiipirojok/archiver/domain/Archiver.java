package ru.vk.sladkiipirojok.archiver.domain;

import java.io.File;
import java.util.List;

public interface Archiver {
    List<File> archive(String name, List<File> fileList);

    void unpack(String name, List<File> fileList);
}
