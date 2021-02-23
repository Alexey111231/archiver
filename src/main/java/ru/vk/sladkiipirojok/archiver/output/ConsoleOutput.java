package ru.vk.sladkiipirojok.archiver.output;

public class ConsoleOutput extends Output {
    ConsoleOutput() {
    }

    @Override
    public void write(String data) {
        System.out.println(data);
    }
}
