import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.StandardSocketOptions;
import java.util.*;

public class ReadFile {
    private Scanner scanner;

    public ReadFile(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("Introduce la ubicación del fichero: ");
    }

    public String getOption() {
        String strOption = scanner.nextLine();
        return strOption;
    }
    public String getFileExtension(File file) {
        /**
         * Función que nos devuelve la extensión del archivo.
         */
        String name = file.getName();
        int lastIndexOf = name.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return ""; // extension vacia.
        }
        return name.substring(lastIndexOf);
    }
    public List<Workshop> readJsonWorkshop(String path){
        /**
         * Leemos el fichero JSON y lo guardamos en un LinkedHashMap el cual devolveremos.
         */
        List<Workshop> WorkshopConfig = new ArrayList<Workshop>();
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader( path));

            JSONObject array = (JSONObject) obj;
            JSONArray workshops = (JSONArray) array.get("workshops");
            int workshopSize = workshops.size();
            for(int i=0; i<workshopSize; i++){
                List<Date> timeList = new ArrayList<Date>();
                List<Integer> colorList = new ArrayList<Integer>();

                /*Aqui recogemos la información de cada Workshop*/
                JSONObject workshopObject = (JSONObject) workshops.get(i);
                String acronym = (String) workshopObject.get("acronym");
                float price =  Float.parseFloat(workshopObject.get("price").toString());
                int category = Integer.parseInt(workshopObject.get("category").toString());
                JSONArray timetable = (JSONArray) workshopObject.get("timetable");
                int timeSize = timetable.size();
                for(int j=0;j<timeSize; j++){
                    JSONObject time = (JSONObject) timetable.get(j);
                    Date date = new Date();
                    int day = Integer.parseInt(time.get("day").toString());
                    int hour = Integer.parseInt(time.get("hour").toString());
                    date.setDay(day);
                    date.setHour(hour);
                    timeList.add(date);
                }
                JSONArray rgb = (JSONArray) workshopObject.get("rgbColor");
                int rgbSize = rgb.size();
                for(int z=0;z<rgbSize; z++) {
                    int color = Integer.parseInt(rgb.get(z).toString());
                    colorList.add(color);
                }
                Workshop workshop = new Workshop(acronym,price,category,timeSize,timeList,colorList);
                WorkshopConfig.add(workshop);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("No encuentra la ruta");
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return WorkshopConfig;
    }
    public int[][] readJsonCompatibilities(String path){
        int[][] compatibilityArray = new int[50][50];

        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader( path));
            JSONObject array = (JSONObject) obj;

            JSONArray compatibilityMatrix = (JSONArray) array.get("compatibilityMatrix");
            int compatibilitySize = compatibilityMatrix.size();
            for(int m=0; m<compatibilitySize;m++){
                JSONArray column = (JSONArray) compatibilityMatrix.get(m);
                int columnSize = column.size();
                for(int n=0; n<columnSize;n++){
                    compatibilityArray[m][n] = Integer.parseInt(column.get(n).toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("No encuentra la ruta");
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
        return compatibilityArray;
    }
}
