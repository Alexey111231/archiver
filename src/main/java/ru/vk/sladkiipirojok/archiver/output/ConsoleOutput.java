package ru.vk.sladkiipirojok.archiver.output;

import java.net.URL;

public class ConsoleOutput extends Output {
    private ConsoleOutput() {
    }

    @Override
    public void write(String data) {
        System.out.println(data);
    }

    @Override
    Output instance(URL property) {
        return new ConsoleOutput();
    }
}
