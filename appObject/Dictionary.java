package appObject;

import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Dictionary {
    public static ArrayList<Vocabulary> dictList = new ArrayList<>();

    public static void load(){
        try{
            for (int i = 1; i <= 6; i++) {
                Scanner scanner = new Scanner(new FileInputStream("src/file/level_" + Integer.toString(i) + ".csv"));
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    int position_1 = line.indexOf("@"), position_2 = line.indexOf("."), position_3 = line.indexOf(")");
                    Vocabulary v = new Vocabulary(line.substring(0, position_1), line.substring(position_3 + 1), line.substring(position_1 + 2, position_2));
                    v.setLevel(i);
                    if (v.getEnglish().charAt(0) == '\"')
                        v.setEnglish(v.getEnglish().substring(1));
                    if (v.getChinese().charAt(v.getChinese().length() - 1) == '\"')
                        v.setChinese(v.getChinese().substring(0, v.getChinese().length() - 1));
                    dictList.add(v);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int[] chooseQuestion(int numOfQus){
        Random random = new Random();
        int[] returnVal = new int[4 * numOfQus];
        for (int i = 0; i < 4 * numOfQus; i++){
            returnVal[i] = random.nextInt(8795);
            for (int j = 0; j < i; j++){
                if (returnVal[j] == returnVal[i]){
                    returnVal[i] = random.nextInt(8795);
                    j = 0;
                }
            }
        }
        return returnVal;
    }

}
