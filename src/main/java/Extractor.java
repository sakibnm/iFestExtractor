import java.io.*;

public class Extractor {
    public static void main(String[] args) {

        String directory = "/Volumes/GoogleDrive/My Drive/PhD Resources/Shared with Dr. Heather/UaB Deployment Study/Data/iFest";

        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        File outDir = new File("Output");
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        File timeDir = new File("Output/Times");
        if (!timeDir.exists()) {
            timeDir.mkdirs();
        }

        File outFile2 = new File(timeDir + "Times.csv");
        PrintWriter printWriter2 = null;

        try {
            if (!outFile2.exists()) outFile2.createNewFile();
            printWriter2 = new PrintWriter(new BufferedWriter(new FileWriter(outFile2)));


//            printWriter2.println("Total Time Spent: "+timeSpent);
//            printWriter2.println("Number of posts: "+num_posts);
//            printWriter2.println("Number of times opened the notifications: "+num_opened);
//            printWriter2.println("Number of times used Favorites: "+num_favorite);
            printWriter2.println("User,"+"Total Time Spent,"+"Number of posts,"+"Number of times opened the notifications,"+"Number of times used Favorites");


            for (File file: listOfFiles){
                if(file.isDirectory())continue;

                File outFile = new File("Output/" + file.getName());
                PrintWriter printWriter = null;



                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

                    printWriter = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));

                    Boolean duplicateStopped = false;
                    String line;
                    int num_posts= 0;
                    int num_opened= 0;
                    int num_favorite= 0;

                    String startTime = "0:0:0";
                    String endTime = "0:0:0";
                    String[] parts = {""};


                    while (true) {
                        line = bufferedReader.readLine();
                        if (line == null) break;
                        System.out.println(line);

                        if (!outFile.exists()) outFile.createNewFile();


                        String newLine = line.replace(",", " ");

                        parts = newLine.split(" ");


                        if (newLine.contains("Time") || newLine.contains("@") || !newLine.contains("21/09/2019"))continue;

                        if (newLine.contains("Registered")) {
                            String outLine = parts[1] + ": " + "registered and logged in";
                            duplicateStopped = false;
                            printWriter.println(outLine);
                            startTime = parts[1];

                        } else if (newLine.contains("New_Posts")) {
                            String outLine = parts[1] + ": " + "created a post";
                            num_posts+=1;
                            duplicateStopped = false;
                            printWriter.println(outLine);
                        } else if (newLine.contains("My_Posts")) {
                            String outLine = parts[1] + ": " + "checked my post";
                            duplicateStopped = false;
                            printWriter.println(outLine);
                        } else if (newLine.contains("Received_Posts")) {
                            String outLine = parts[1] + ": " + "opened notification ()";
                            duplicateStopped = false;
                            num_opened+=1;
                            printWriter.println(outLine);
                        } else if (newLine.contains("Favorited")) {
                            String outLine = parts[1] + ": " + "Favorited a review";
                            duplicateStopped = false;
                            num_favorite+=1;
                            printWriter.println(outLine);
                        } else if (newLine.contains("App stopped")) {
                            if (duplicateStopped) {
                                continue;
                            }
                            String outLine = parts[1] + ": " + "closed app";
                            duplicateStopped = true;
                            printWriter.println(outLine);
                            endTime = parts[1];
                        }
                    }
//                    endTime = parts[1];

                    String secStart[] = startTime.split(":");
                    String secEnd[] = endTime.split(":");
                    System.out.println();;
                    int secondStart = Integer.parseInt(secStart[0])*3600+Integer.parseInt(secStart[1])*60+Integer.parseInt(secStart[2]);
                    int secondEnd = Integer.parseInt(secEnd[0])*3600+Integer.parseInt(secEnd[1])*60+Integer.parseInt(secEnd[2]);
                    int timeSpent = secondEnd - secondStart;
                    System.out.println(startTime);
                    System.out.println(endTime);
                    System.out.println(timeSpent);

//                    printWriter2.println(file.getName());
                    printWriter2.println(file.getName()+","+timeSpent+","+num_posts+","+num_opened+","+num_favorite);
//                    printWriter2.println("Number of posts: "+num_posts);
//                    printWriter2.println("Number of times opened the notifications: "+num_opened);
//                    printWriter2.println("Number of times used Favorites: "+num_favorite);
                    System.out.println();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    printWriter.close();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            printWriter2.close();
        }


    }
}
