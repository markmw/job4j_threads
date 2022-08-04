package ru.job4j.io;

import java.io.*;
import java.util.function.Predicate;

public class ContentLoad {
    private final File file;

    public ContentLoad(File file) {
        this.file = file;
    }

    private String getContent(Predicate<Character> filter) {
        StringBuilder output = new StringBuilder();
        synchronized (this) {
            try (BufferedInputStream i = new BufferedInputStream(new FileInputStream(file))) {
                int data;
                char dataF;
                while ((data = i.read()) > 0) {
                    dataF = (char) data;
                    if (filter.test(dataF)) {
                        output.append(dataF);
                    }
                }
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            return output.toString();
        }
    }

    public String getContent() {
        return getContent(i -> true);
    }

    public String getContentWithoutUnicode() {
        return getContent(i -> i < 0x80);
    }
}
