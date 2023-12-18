import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class WordDB {
    // akan berisi seperti ini key -> category, value -> words
    private HashMap<String, String[]> wordList;

    // untuk pick random categories (karena kita ga bisa random nge get index pake hashmap)
    private ArrayList<String> categories;

    public WordDB(){
        try{
            wordList = new HashMap<>();
            categories = new ArrayList<>();

            // kita nge get path file nya pake fungsi ini
            String filePath = getClass().getClassLoader().getResource(CommonConstants.DATA_PATH).getPath();
            if(filePath.contains("%20")) filePath = filePath.replaceAll("%20", " ");
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // iterates semua dataline pada data.txt
            String line;
            while((line = reader.readLine()) != null){
                // pisahkan data dengan ","
                String[] parts = line.split(",");

                // kata pertama pada data data line dijadikan category
                String category = parts[0];
                categories.add(category);

                // dan kata setelah kata pertama menjadi value dari category tersebut
                String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                wordList.put(category, values);
            }
        }catch(IOException e){
            System.out.println("Error: " + e);
        }
    }

    public String[] loadChallenge(){
        Random rand = new Random();

        // generate angka random untuk memilih category / line pada data.txt
        String category = categories.get(rand.nextInt(categories.size()));

        // generate angka random untuk memilih value dari category / line tersebut (value itu semua kata setelah kata pertama)
        String[] categoryValues = wordList.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];

        // [0] -> category dan [1] -> word
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}