package ru.vk.sladkiipirojok.archiver.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

class ZipArchiverTest {
    @TempDir
    File tempDir;

    private static Archiver archiver;

    @BeforeAll
    static void setUp() {
        try {
            System.getProperties().load(ZipArchiverTest.class.getResourceAsStream("../archiver-test.properties"));
        } catch (IOException | IllegalArgumentException e) {
            throw new IllegalStateException("Error in property file: \"archiver.properties\"");
        }
        archiver = ArchiverFactory.instance().createArchiver();
    }

    @Test
    void Archive_AllCorrectFiles_AllFilesSave() throws IOException {
        File dir = new File(tempDir, "dir");
        dir.mkdir();
        File file = new File(tempDir, "file");
        file.createNewFile();
        File fileInDir = new File(dir, "file");
        fileInDir.createNewFile();
        List<File> files = List.of(dir, file);

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));
        List<File> archivedFiles = archiver.archive(zip, files);

        Assertions.assertEquals(2, archivedFiles.size());
    }

    @Test
    void Archive_NotAllCorrectFiles_ThrownIllegalStateException() throws IOException {
        File notCreateFile = new File("notCreateFile");
        List<File> files = List.of(notCreateFile);

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));

        Assertions.assertThrows(IllegalStateException.class, () -> archiver.archive(zip, files));
    }

    @Test
    void Archive_DuplicateName_ThrownIllegalStateException() throws IOException {
        File file = new File("file");
        file.createNewFile();
        File duplicateFile = new File("file");
        duplicateFile.createNewFile();
        List<File> files = List.of(file, duplicateFile);

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));

        Assertions.assertThrows(IllegalStateException.class, () -> archiver.archive(zip, files));
    }

    @Test
    void Archive_EmptyName_ThrownIllegalStateException() {
        File fileWithEmptyName = new File("");
        List<File> files = List.of(fileWithEmptyName);

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));

        Assertions.assertThrows(IllegalStateException.class, () -> archiver.archive(zip, files));
    }

    @Test
    void Archive_FilesAndEmptyDir_SkipEmptyDir() throws IOException {
        File dir = new File(tempDir, "dir");
        dir.mkdir();
        File file = new File(tempDir, "file");
        file.createNewFile();
        List<File> files = List.of(dir, file);

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));
        List<File> archivedFiles = archiver.archive(zip, files);

        Assertions.assertEquals(1, archivedFiles.size());
    }

    @Test
    void Archive_EmptyFilesList_Nothing() {
        List<File> files = new ArrayList<>();

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));
        List<File> archivedFiles = archiver.archive(zip, files);

        Assertions.assertEquals(0, archivedFiles.size());
    }

    @Test
    void Unpack_CorrectInput_AllFilesUnpack() throws IOException {
        List<File> archive = archive();

        File zip = archive.remove(0);

        archiver.unpack(zip, archive);

        archive.forEach(file -> Assertions.assertTrue(file.exists()));
    }

    @Test
    void Unpack_ErrorFilesInput_SkipErrorFiles() throws IOException {
        List<File> archive = archive();

        File zip = archive.remove(0);

        List<File> errorsFile = List.of(new File("afassfa"), new File("sadadszx"));

        List<File> allFiles = new ArrayList<>();

        allFiles.addAll(errorsFile);
        allFiles.addAll(archive);

        archiver.unpack(zip, allFiles);

        archive.forEach(file -> Assertions.assertTrue(file.exists()));
        errorsFile.forEach(file -> Assertions.assertFalse(file.exists()));
    }

    @Test
    void Unpack_ErrorArchiveName_ThrownIllegalStateException() throws IOException {
        List<File> archive = archive();

        archive.remove(0);

        Assertions.assertThrows(IllegalStateException.class, () -> archiver.unpack(new File("qrwqrwwqr"), archive));
    }

    @Test
    void Unpack_EmptyFilesList_Nothing() throws IOException {
        List<File> archive = archive();

        File zip = archive.remove(0);

        archiver.unpack(zip, new ArrayList<>());

        Assertions.assertTrue(tempDir.listFiles().length == 1 && tempDir.listFiles()[0].equals(zip));
    }

    private List<File> archive() throws IOException {
        File dir = new File(tempDir, "dir");
        dir.mkdir();
        File file = new File(tempDir, "file");
        file.createNewFile();
        File fileInDir = new File(dir, "file");
        fileInDir.createNewFile();
        List<File> files = List.of(dir, file);

        File zip = new File(tempDir, String.valueOf(UUID.randomUUID()));
        List<File> archivedFiles = archiver.archive(zip, files);

        List<File> archivedFilesStartWithArchiveFile = new ArrayList<>();
        archivedFilesStartWithArchiveFile.add(zip);
        archivedFilesStartWithArchiveFile.addAll(archivedFiles);
        file.delete();
        fileInDir.delete();
        dir.delete();
        return archivedFilesStartWithArchiveFile;
    }
}