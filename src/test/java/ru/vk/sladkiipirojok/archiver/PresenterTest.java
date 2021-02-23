package ru.vk.sladkiipirojok.archiver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.vk.sladkiipirojok.archiver.domain.Archiver;
import ru.vk.sladkiipirojok.archiver.input.Input;
import ru.vk.sladkiipirojok.archiver.output.Output;

import java.io.File;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

class PresenterTest {
    private static Input input;
    private static Output output;
    private static Archiver archiver;

    private static Presenter presenter;

    @BeforeEach
    void setUp() {
        input = Mockito.mock(Input.class);
        output = Mockito.mock(Output.class);
        archiver = Mockito.mock(Archiver.class);

        ArrayList<File> value = new ArrayList<>();
        value.add(new File("test"));

        Mockito.when(input.read()).thenReturn(value);
        Mockito.when(archiver.archive(any(), any())).thenReturn(new ArrayList<>());
        presenter = new Presenter(archiver, input, output);
    }

    @Test
    void Archive_TestCallingClasses_CurrectNumberOfUnits() {
        presenter.archive();

        Mockito.verify(input, Mockito.times(1)).read();
        Mockito.verify(output, Mockito.times(1)).write(any());
        Mockito.verify(archiver, Mockito.times(1)).archive(any(), any());
        Mockito.verify(archiver, Mockito.times(0)).unpack(any(), any());
    }

    @Test
    void Unpack_TestCallingClasses_CurrectNumberOfUnits() {
        presenter.unpack();

        Mockito.verify(input, Mockito.times(1)).read();
        Mockito.verify(output, Mockito.times(0)).write(any());
        Mockito.verify(archiver, Mockito.times(0)).archive(any(), any());
        Mockito.verify(archiver, Mockito.times(1)).unpack(any(), any());
    }

}