import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Question;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Weiduo
 * @date 2021/5/31 - 9:18 下午
 */
public class Demo {
    private static ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {

        String jsonString = convertJsonFileToStr("example_2.json");

        Map<String, Map<String, Map<String, Question>>> map = new HashMap<>();

        TypeReference<Map<String, Map<String, Map<String, Question>>>> typeRef
                = new TypeReference<Map<String, Map<String, Map<String, Question>>>>() {};

        parseJson(jsonString, typeRef);

    }

    private static void parseJson(String jsonString, TypeReference<Map<String, Map<String, Map<String, Question>>>> typeRef) {
        try {
            // 反序列化 JSON 到对象
            Map<String, Map<String, Map<String, Question>>> obj = MAPPER.readValue(jsonString, typeRef);
            System.out.println("反序列化 JSON 到对象: " + obj);

            // 序列化对象到 JSON
            String json = MAPPER.writeValueAsString(obj);
            System.out.println("序列化对象到 JSON: " + json);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String convertJsonFileToStr(String fileName) {
        String path = Demo.class.getClassLoader().getResource(fileName).getPath();
        String jsonString = "";
        try {
            File jsonFile = new File(path);
            FileReader fileReader = new FileReader(jsonFile);
            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
            int ch = 0;
            StringBuffer sb = new StringBuffer();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            fileReader.close();
            reader.close();
            jsonString = sb.toString();
            return jsonString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
