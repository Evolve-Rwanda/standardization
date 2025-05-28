package com.example.springauth.logging.writers;
import java.io.*;
import java.util.Scanner;




public class LogFileWriter {


    private String directory;


    public LogFileWriter(String directory){
        this.directory = directory;
    }


    public void writeFile(String fileName, String content){
        try{
            int l = content.length();
            File file = new File(fileName);
            //if(file.isFile()){
            FileWriter fw = new FileWriter(fileName);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content, 0, l);
            bw.close();
            //}
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String createDirectory(){
        File fileDirectory = new File(this.directory);
        fileDirectory.mkdir();
        fileDirectory.setWritable(true);
        fileDirectory.setExecutable(true);
        if(fileDirectory.isDirectory()){
            return fileDirectory.toString();
        }
        return null;
    }


    public void copyFiles(String[] sourcePath, String sourceDirectoryName, String destinationDirectory){
        for(String s: sourcePath){
            File sourceDirectory = new File(s + systemSeparator());
            File[] sourcePathFiles = sourceDirectory.listFiles();

            for(File f: sourcePathFiles){
                if(!f.isDirectory()){
                    String fileName = f.getName();
                    int length = sourceDirectoryName.length();
                    String relativePath = s.substring(length, s.length());
                    String newFileName = destinationDirectory + relativePath + systemSeparator() + fileName;
                    writeFile(newFileName, parseContentsScanner(s + systemSeparator() + fileName));
                }
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
