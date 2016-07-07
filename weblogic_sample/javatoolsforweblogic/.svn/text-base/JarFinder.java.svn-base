package jarfinder;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;


public class JarFinder {
    /** if true, search text in file */
    boolean findtextInFile = false;
    BufferedWriter out = null;

    public JarFinder() {
        super();
    }


    public static void main(String[] args) throws Exception {
        JarFinder jarSearcher = new JarFinder();
        FileWriter fstream = new FileWriter("c:/out.txt");
        jarSearcher.out = new BufferedWriter(fstream);
        jarSearcher.findTest("C:\\Oracle1", "pipelineMonitoringLevel");
        jarSearcher.out.close();
    }

    private void findTest(String dirString, String text) throws IOException {
        File dir = new File(dirString);
        for (File f : dir.listFiles()) {
            if (f.isDirectory()) {
                findTest(f.getAbsolutePath(), text);
            } else {
                String name = f.getName();
                if (name.endsWith(".jar") || name.endsWith(".zip")) {
                    System.out.println("SEARCHING " + f.getAbsolutePath());
                    out.write("\n\nSEARCHING: " + f.getAbsolutePath());
                    out.write("\n");
                    try {
                        ZipFile zipFile = new ZipFile(f);
                        Enumeration<? extends ZipEntry> e = zipFile.entries();
                        while (e.hasMoreElements()) {
                            ZipEntry nextElement = e.nextElement();
                            out.write(nextElement.getName());
                            out.write("\n");
                            if (findtextInFile) {
                                InputStream is = zipFile.getInputStream(nextElement);
                                if (findInInputStream(is, text)) {
                                    System.out.println("FOUND ENTRY " + nextElement.getName() + " in file " + f.getAbsolutePath());
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.print("EXCEPTION: " + e.getMessage());
                    }
                } else {

                }
            }
        }
    }


    private boolean findInInputStream(InputStream is, String text) throws IOException {
        boolean result = false;
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        while ((line = br.readLine()) != null && result == false) {
            if (line.contains(text)) {
                result = true;
            }
        }
        br.close();
        is.close();
        return result;
    }
}
