package ru.vk.sladkiipirojok.archiver.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

class ZipArchiverTest {
    @TempDir
    File tempDir;

    @Test
    void Archive_AllCorrectFiles_AllFilesSave() {
    }

    @Test
    void Archive_NotAllCorrectFiles_SkipNotCorrectFiles() {
    }

    @Test
    void Archive_DuplicateName_ThrownIllegalStateException() {
    }

    @Test
    void Archive_EmptyName_ThrownIllegalStateException() {
    }

    @Test
    void Archive_FilesAndEmptyDir_SkipEmptyDir() {
    }

    @Test
    void Archive_EmptyFilesList_Nothing() {
    }

    @Test
    void Unpack_CorrectInput_AllFilesUnpack() {
    }

    @Test
    void Unpack_ErrorFilesInput_SkipErrorFiles() {
    }

    @Test
    void Unpack_ErrorArchiveName_ThrownIllegalStateException() {
    }

    @Test
    void Unpack_EmptyFilesList_Nothing() {
    }
}