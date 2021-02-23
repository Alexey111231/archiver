package ru.vk.sladkiipirojok.archiver.domain;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipFile;

public class ZipArchiver implements Archiver {
    @Override
    public void archive(String name, List<File> fileList) {
        try {
            ZipFile zip = new ZipFile(name);

        } catch (IOException e) {
            throw new IllegalStateException("Zip not exists");
        }
    }

    @Override
    public void unpack(String name, List<File> fileList) {

    }
}
