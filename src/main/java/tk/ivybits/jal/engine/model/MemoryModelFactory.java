package tk.ivybits.jal.engine.model;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.InputStream;

public final class MemoryModelFactory {
    public static MemoryModel from(InputStream... streams) throws Exception {
        return new SQLiteMemoryModel(streams);
    }

    public static MemoryModel from(File... files) throws Exception {
        InputStream[] streams = new InputStream[files.length];
        for (int i = 0; i != files.length; i++) {
            streams[i] = new FileInputStream(files[i]);
        }
        return from(streams);
    }

    public static MemoryModel fromDir(File directory) throws Exception {
        return from(directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(".hal");
            }
        }));
    }
}
