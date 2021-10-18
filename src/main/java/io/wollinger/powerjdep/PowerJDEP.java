package io.wollinger.powerjdep;

import java.io.*;
import java.util.ArrayList;

public class PowerJDEP {
    public static void main(String[] args) {
        if(!(args.length < 1)) {
            File inputFile = new File(args[0]);
            if(!inputFile.exists()) {
                System.out.println("File does not exist.");
                System.exit(0);
            }
            ArrayList<String> list = load(inputFile);
            ArrayList<String> result = new ArrayList<>();
            for(String data : list) {
                if(!result.contains(data) && !data.endsWith(".jar"))
                    result.add(data);
            }
            if(args.length > 1 && args[1].equals("-jlink-pretty")) {
                for(int i = 0; i < result.size(); i++) {
                    System.out.print(result.get(i));
                    if(i != result.size() - 1)
                        System.out.print(",");
                }
            } else {
                System.out.println("Needed packages:");
                for (String resultData : result) {
                    System.out.println(resultData);
                }
            }
        } else {
            System.out.println("File missing!");
        }
    }

    public static ArrayList<String> load(File file) {
        ArrayList<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.toLowerCase().startsWith(" ")) {
                    String pckg = line.substring(108);
                    if(!pckg.contains("PowerJDEP"))
                        list.add(pckg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
