import java.io.*;

public class Extractor {
    public static void main(String[] args) {

        String directory = "/Volumes/GoogleDrive/My Drive/PhD Resources/Shared with Dr. Heather/UaB Deployment Study/Data/iFest";

        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        for (File file: listOfFiles){
            File outDir = new File("Output");
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File outFile = new File("Output/" + file.getName());
            PrintWriter printWriter = null;
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

                Boolean duplicateStopped = false;
                String line;
                while (true) {
                    line = bufferedReader.readLine();
                    if (line == null) break;
                    System.out.println(line);

                    if (!outFile.exists()) outFile.createNewFile();

                    String newLine = line.replace(",", " ");

                    String[] parts = newLine.split(" ");

                    if (newLine.contains("Registered")) {
                        String outLine = parts[1] + ": " + "registered and logged in";
                        duplicateStopped = false;
                        printWriter.println(outLine);
                    } else if (newLine.contains("New_Posts")) {
                        String outLine = parts[1] + ": " + "created a post";
                        duplicateStopped = false;
                        printWriter.println(outLine);
                    } else if (newLine.contains("My_Posts")) {
                        String outLine = parts[1] + ": " + "checked my post";
                        duplicateStopped = false;
                        printWriter.println(outLine);
                    } else if (newLine.contains("Received_Posts")) {
                        String outLine = parts[1] + ": " + "opened notification ()";
                        duplicateStopped = false;
                        printWriter.println(outLine);
                    } else if (newLine.contains("Favorited")) {
                        String outLine = parts[1] + ": " + "Favorited a review";
                        duplicateStopped = false;
                        printWriter.println(outLine);
                    } else if (newLine.contains("App stopped")) {
                        if (duplicateStopped) {
                            continue;
                        }
                        String outLine = parts[1] + ": " + "closed app";
                        duplicateStopped = true;
                        printWriter.println(outLine);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                printWriter.close();
            }

        }
    }
}
