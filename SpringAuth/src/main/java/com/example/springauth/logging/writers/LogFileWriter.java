package com.example.springauth.logging.writers;
import java.io.*;
import java.nio.file.FileSystems;
import java.util.Scanner;



public class LogFileWriter {



    private String directory;
    private static final String SEPARATOR = FileSystems.getDefault().getSeparator();


    public LogFileWriter(){

    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    // Create and write to the file if it does not exist, otherwise,
    // append to the file if it already exists
    public void write(String fileName, String content){
        try{
            boolean dirAvailable = (this.createDirectory() != null);
            if (!dirAvailable)
                throw new NullPointerException("Exception trying to create directory - " + this.directory);

            int length = content.length();
            String absolutePath = (directory != null) ? (directory + SEPARATOR + fileName) : fileName;
            File file = new File(absolutePath);
            if(!file.isFile()) {
                FileWriter fileWriter = new FileWriter(absolutePath);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.write(content, 0, length);
                bufferedWriter.close();
            }else {
                FileWriter fileWriter = new FileWriter(absolutePath, true);
                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                bufferedWriter.newLine();
                bufferedWriter.write(content, 0, length);
                bufferedWriter.close();
            }
        }catch(IOException e){
            System.out.println("Error writing to the file - " + e.getMessage());
        }
    }

    private String createDirectory(){
        File fileDirectory = new File(this.directory);
        if(!fileDirectory.isDirectory()){
            boolean isCreated = fileDirectory.mkdirs();
            boolean isSetWritable = fileDirectory.setWritable(true);
            boolean isExecutable = fileDirectory.setExecutable(true);
            boolean isSet = isCreated & isSetWritable & isExecutable;
            return isSet ? fileDirectory.toString() : null;
        }
        return this.directory;
    }


    public void copyFiles(String[] sourcePath, String sourceDirectoryName, String destinationDirectory){
        for(String s: sourcePath){
            File sourceDirectory = new File(s + SEPARATOR);
            File[] sourcePathFiles = sourceDirectory.listFiles();
            if (sourcePathFiles == null)
                throw new NullPointerException("Cannot copy files from null source");
            for(File f: sourcePathFiles){
                if(!f.isDirectory()){
                    String fileName = f.getName();
                    int length = sourceDirectoryName.length();
                    String relativePath = s.substring(length); // , s.length()
                    String newFileName = destinationDirectory + relativePath + SEPARATOR + fileName;
                    this.write(newFileName, parseContentsScanner(s + SEPARATOR + fileName));
                }
            }
        }
    }


    public void prependFileContent(String sourcePath, String sourceDirectoryName, String destinationDirectory, String fileName, String prependString){
        int length = sourceDirectoryName.length();
        String relativePath = sourcePath.substring(length); //, sourcePath.length()
        String newFileName = destinationDirectory + relativePath + SEPARATOR + fileName;
        this.write(
                newFileName,
                this.parseContentsScanner(sourcePath + SEPARATOR + fileName) + prependString
        );
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
            System.out.println("Error parsing contents - " + e.getMessage());
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
                contentBuilder.append(line).append("\n");
            }
        }catch(Exception e){
            System.out.println("Error parsing file contents - " + e.getMessage());
        }
        return contentBuilder.toString();
    }

}
