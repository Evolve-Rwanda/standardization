package com.example.springauth.utilities;

import java.io.*;
import java.util.Scanner;


public class CustomFileWriter {


    public CustomFileWriter() {

    }


    public void writeFile(String fileName, String content){
        try{
            int l = content.length();
            File file = new File(fileName);
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content, 0, l);
            bw.close();
        }catch(IOException e){
            System.out.println("Error creating file - " + fileName + " due to - " + e.getMessage());
        }
    }

    public String makeSourceSubDirectory(String subSourceDirectoryName){
        File subDirectory = new File(subSourceDirectoryName);
        subDirectory.mkdir();
        subDirectory.setWritable(true);
        subDirectory.setExecutable(true);
        if(subDirectory.isDirectory()){
            return subSourceDirectoryName;
        }
        return null;
    }


    public void copyFiles(String[] sourcePath, String sourceDirectoryName, String destinationDirectory){
        for(String s: sourcePath){
            File sourceDirectory = new File(s + systemSeparator());
            File[] sourcePathFiles = sourceDirectory.listFiles();
            try {
                for (File f : sourcePathFiles) {
                    if (!f.isDirectory()) {
                        String fileName = f.getName();
                        int length = sourceDirectoryName.length();
                        String relativePath = s.substring(length, s.length());
                        String newFileName = destinationDirectory + relativePath + systemSeparator() + fileName;
                        writeFile(newFileName, parseContentsScanner(s + systemSeparator() + fileName));
                    }
                }
            }catch (Exception e){
                System.out.println("Error copying file - " + sourceDirectoryName + " due to - " + e.getMessage());
            }
        }
    }


    public void prependFileContent(String sourcePath, String sourceDirectoryName, String destinationDirectory, String file, String prependString){

        String fileName = file;
        int length = sourceDirectoryName.length();
        String relativePath = sourcePath.substring(length, sourcePath.length());
        String newFileName = destinationDirectory + relativePath + systemSeparator() + fileName;
        writeFile(newFileName, parseContentsScanner(sourcePath + systemSeparator() + fileName) + prependString);
    }


    public String parseContents(String fileName){

        StringBuilder contentBuilder = new StringBuilder();

        try{

            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";

            while((line = bufferedReader.readLine()) != null)
                contentBuilder.append(line);

        }catch(Exception e){
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public String parseContentsScanner(String fileName){

        Scanner fileScanner = null;
        StringBuilder contentBuilder = new StringBuilder();

        try{
            fileScanner = new Scanner(new File(fileName));
            String line = "";
            while(fileScanner.hasNextLine()){
                line = fileScanner.nextLine();
                contentBuilder.append(line + "\n");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }

    public String systemSeparator(){
        String separator = File.separator;
        return separator;
    }



}