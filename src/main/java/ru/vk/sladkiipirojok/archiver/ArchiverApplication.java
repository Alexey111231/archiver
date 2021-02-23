package ru.vk.sladkiipirojok.archiver;

import ru.vk.sladkiipirojok.archiver.domain.Archiver;
import ru.vk.sladkiipirojok.archiver.domain.ArchiverFactory;
import ru.vk.sladkiipirojok.archiver.input.Input;
import ru.vk.sladkiipirojok.archiver.input.InputFactory;
import ru.vk.sladkiipirojok.archiver.output.Output;
import ru.vk.sladkiipirojok.archiver.output.OutputFactory;

public class ArchiverApplication {
    public static void oneWay(boolean isArchive) {
        Input input = InputFactory.instance().createInput();
        Output output = OutputFactory.instance().createOutput();
        Archiver archiver = ArchiverFactory.instance().createArchiver();

        Presenter presenter = new Presenter(archiver, input, output);

        if (isArchive) {
            presenter.archive();
        } else {
            presenter.unpack();
        }
    }
}
