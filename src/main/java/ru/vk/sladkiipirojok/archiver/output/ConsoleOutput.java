package ru.vk.sladkiipirojok.archiver.output;

public class ConsoleOutput extends Output {
    private ConsoleOutput() {
    }

    @Override
    public void write(String data) {
        System.out.println(data);
    }
}
