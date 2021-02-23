package ru.vk.sladkiipirojok.archiver.input;

import java.io.File;
import java.util.List;

public abstract class Input {
    abstract public List<File> read();

    abstract Input instance();
}
