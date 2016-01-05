/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 - Alberto Marchetti
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.hydex11;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

// This script parses every .rs and .fs script we have, and extract all lines that start with //D.
// Saves extracted lines in a .property file, then zips both .bc (bytecode) and .properties file.
public class RSScriptParser {

    public static void main(String[] args) {

        // We use this package from Gradle, passing sourceDir and destinationDir as arguments.
        // sourceDir = Folder wherein .rs and .fs files are
        // destinationDir = Folder where to save .bc, .properties and .zip file
        // Note: .rs and .fs filenames are turned to lowercase, to match their bytecodes outputs.

        if (args.length < 2) {
            System.err.println("Invalid input arguments. Need sourceDir destinationDir");
            throw new RuntimeException();
        }

        // Iterates through each *.rs and *.fs file in folder and creates
        // corresponding properties file
        String sourceDir = args[0];
        String destinationDir = args[1];

        File[] files = new File(sourceDir).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                String filePath = file.getAbsolutePath();

                if (!(filePath.endsWith("rs") || filePath.endsWith("fs")))
                    continue; // Skip if file isn't RenderScript one

                parseRSFile(filePath, destinationDir);
            }
        }
    }

    // Properties are read only if line starts with this delimiter.
    private static final String DELIMITER = "//D ";

    private static void parseRSFile(String filePath, String outputDir) {

        String baseFileName = getFileNameWithoutPathAndExtension(filePath).toLowerCase();

        String bcFileName = outputDir + File.separator + baseFileName + ".bc";
        if (!Files.exists(Paths.get(bcFileName), LinkOption.NOFOLLOW_LINKS)) {
            System.err.println(String.format("Skipping file %s because of missing corresponding bytecode file", filePath));
            return;
        }

        try {
            Path path = Paths.get(filePath);

            List<String> lines = Files.readAllLines(path, Charset.defaultCharset());

            ArrayList<String> outputLines = new ArrayList<>();

            for (String line : lines) {

                if (!line.startsWith(DELIMITER))
                    continue; // Skips line if is not related to our system

                line = line.substring(DELIMITER.length()); // Clears line from delimiter

                outputLines.add(line);

            }

            String fileName = outputDir + File.separator + baseFileName + ".properties";

            FileWriter fw = new FileWriter(fileName);

            for (int i = 0; i < outputLines.size(); i++) {
                fw.write(outputLines.get(i) + "\n");
            }

            fw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now zip it!
        zipRSScript(outputDir, baseFileName);
    }

    private static void zipRSScript(String outputDir, String baseFileName) {
        try {

            String outputFileName = outputDir + File.separator + baseFileName + ".zip";
            String inputFileNameBytecode = outputDir + File.separator + baseFileName + ".bc";
            String inputFileNameProperties = outputDir + File.separator + baseFileName + ".properties";

            // out put file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputFileName));

            addZipEntry(baseFileName + ".bc", inputFileNameBytecode, out);
            addZipEntry(baseFileName + ".properties", inputFileNameProperties, out);

            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addZipEntry(String fileName, String inputFilePath, ZipOutputStream outputStream) throws IOException {

        // name the file inside the zip  file
        outputStream.putNextEntry(new ZipEntry(fileName));
        // input file
        FileInputStream in = new FileInputStream(inputFilePath);

        // buffer size
        byte[] b = new byte[1024];
        int count;
        while ((count = in.read(b)) > 0) {
            outputStream.write(b, 0, count);
        }
        in.close();
    }

    private static String getFileNameWithoutPathAndExtension(String path) {
        int idx = path.replaceAll("\\\\", "/").lastIndexOf("/");
        int withoutExtNameLength = path.lastIndexOf(".");
        return idx >= 0 ? path.substring(idx + 1, withoutExtNameLength) : path.substring(0, withoutExtNameLength);
    }
}
