package uni.myosotis.gui;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Language {
    private String language;
    private Map<String, String> map;
    public Language(String language){
        this.language = language;
        try {
            map = reader(language);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getName(String name){
        if(map.get(name) == null)
            return ("checkLanguage: " + name);
        else
            return map.get(name);
    }
    /**
     * Takes in a txt file which has the language in his first line
     * @return language
     */
    public static Map<String, String> reader(String language) throws IOException {
        Map<String, String> map = new HashMap<>();
        ClassLoader classLoader = Language.class.getClassLoader();
        File file = new File(classLoader.getResource("LanguagesFile.csv").getFile());
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        String[] value = br.readLine().split(";");
        int index = -1;
        for (int i = 0; i < value.length; i++) { //find the index of the language
            if (value[i].equals(language)) {
                index = i;
                break;
            }
        }
        while ((line = br.readLine()) != null) { //put the values in the map
            String[] values = line.split(";");
            map.put(values[0], values[index]);
        }
        br.close();
        return map;
    }
}
