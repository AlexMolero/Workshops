import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FileReader {
    private Scanner scanner;

    public FileReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("Introduce la ubicación del fichero: ");
    }

    public String getOption() {
        String strOption = scanner.nextLine();
        return strOption;
    }
    public Map<String, ArrayList> readJsonFile(String path){
        /**
         * Leemos el fichero JSON y lo guardamos en un LinkedHashMap el cual devolveremos.
         */
        JSONParser parser = new JSONParser();

        LinkedHashMap<String, ArrayList> data = new LinkedHashMap<>();

            /*
            Object obj = parser.parse(new FileReader(path));
            JSONArray array = (JSONArray) obj;
            Operations operation = new Operations();
            //  INICIO: Leemos el fichero JSON, y lo guardamos en el objeto
            int length = array.size();
            for(int i=0; i<length;i++){
                JSONObject jsonObject = (JSONObject) array.get(i);
                String    name      = (String)   jsonObject.get("name");
                String    direction = (String)   jsonObject.get("address");
                String    type = (String)   jsonObject.get("type");
                Double rating = Double.parseDouble(jsonObject.get("rating").toString());
                JSONObject json = (JSONObject)jsonObject.get("opening_hours");
                boolean open = (boolean) json.get("open_now");

                if(type.equals("restaurant")){
                    //*********Es restaurante
                    long price = (long) jsonObject.get("price_level");
                    Restaurant rest = new Restaurant(name, direction, rating, open, price,type);
                    dataRest.add(rest);
                }else{
                    //*********Es local
                    Local local = new Local(name, direction, rating, open,type);
                    dataLocal.add(local);
                }
            }
             //   FIN: Leemos el fichero JSON, y lo guardamos en el objeto
            data.put("restaurante",dataRest); //Insertamos el arrayList de objetos de restaurantes
            data.put("local",dataLocal); //Insertamos el arrayList de objectos de locales
    */

        return data;
    }
}
