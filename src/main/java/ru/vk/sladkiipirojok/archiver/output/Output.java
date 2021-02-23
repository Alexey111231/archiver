package ru.vk.sladkiipirojok.archiver.output;

import java.net.URL;

public abstract class Output {
    abstract public void write(String data);

    abstract Output instance(URL property);
}
