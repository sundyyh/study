package com.sundy.test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandle {
    public static void main(String[] args) throws Exception {
        deleteContent("", "@Optional");

    }

    public static void deleteContent(String path, String deleteLineContent) throws Exception {
        List<File> files = getFileList(path);
        for (File file : files) {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String line = bufferedReader.readLine();
            while (line != null) {
                if (!line.trim().equalsIgnoreCase(deleteLineContent)) {
                    sb.append(line + "\r\n");
                }
                line = bufferedReader.readLine();
            }

            bufferedReader.close();
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file));
            fileWriter.write(sb.subSequence(0, sb.length() - 2).toString());
            fileWriter.flush();
            fileWriter.close();
        }
    }

    public static List<File> getFileList(String path) {
        List<File> fileList = new ArrayList<>();
        File file = new File(path);
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                fileList.addAll(getFileList(subFile.getPath()));
            }
        } else {
            fileList.add(file);
        }
        return fileList;
    }
}
