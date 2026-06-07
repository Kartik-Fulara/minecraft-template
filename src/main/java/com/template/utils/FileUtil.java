package com.template.utils;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Utility class for world and file management.
 * Imported from WorldResetPlugin core logic.
 */
public class FileUtil {

    public static void unzip(File zipFile, File destDir) throws IOException {
        String canonicalDest = destDir.getCanonicalPath();
        try (ZipInputStream zis = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipFile)))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File file = new File(destDir, entry.getName());
                String canonicalEntry = file.getCanonicalPath();
                if (!canonicalEntry.startsWith(canonicalDest + File.separator) && !canonicalEntry.equals(canonicalDest)) {
                    throw new IOException("Blocked Zip Slip attempt: entry '" + entry.getName() + "' is outside destination.");
                }
                if (entry.isDirectory()) {
                    file.mkdirs();
                } else {
                    file.getParentFile().mkdirs();
                    try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))) {
                        byte[] buffer = new byte[4096];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            bos.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        }
    }

    public static void zipDirectory(File sourceDir, File zipFile) throws IOException {
        final Path sourcePath = sourceDir.toPath().toAbsolutePath();
        try (ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile)))) {
            Files.walkFileTree(sourcePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path absDir = dir.toAbsolutePath();
                    if (!absDir.equals(sourcePath)) {
                        String entry = sourcePath.relativize(absDir).toString().replace('\\', '/') + "/";
                        zos.putNextEntry(new ZipEntry(entry));
                        zos.closeEntry();
                    }
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path absFile = file.toAbsolutePath();
                    String entry = sourcePath.relativize(absFile).toString().replace('\\', '/');
                    zos.putNextEntry(new ZipEntry(entry));
                    Files.copy(absFile, zos);
                    zos.closeEntry();
                    return FileVisitResult.CONTINUE;
                }
            });
        }
    }
}
