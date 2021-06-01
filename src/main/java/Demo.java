import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Question;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Weiduo
 * @date 2021/5/31 - 9:18 下午
 */
public class Demo {
    private static ObjectMapper MAPPER = new ObjectMapper();

    public static void main(String[] args) {

        String jsonString1 = convertJsonFileToStr("example_1.json");
        String jsonString2 = convertJsonFileToStr("example_2.json");

        JsonNode tree1 = parseJsonToTree(jsonString1);
        JsonNode tree2 = parseJsonToTree(jsonString2);

        compareTree(tree1, tree2);

    }
    private static void compareTree(JsonNode tree1, JsonNode tree2) {
        if(isValueNode(tree1, tree2)) {
            if(tree1.toString().equals(tree2.toString())) return;
            else System.out.println("diff: " + "first: " + tree1.toString() + ", second: " + tree2.toString());
        }else if(isObject(tree1, tree2)) {
            Iterator<Map.Entry<String, JsonNode>> iterator1 = tree1.fields();
            Iterator<Map.Entry<String, JsonNode>> iterator2 = tree2.fields();

            while(iterator1.hasNext() || iterator2.hasNext()) {
                Map.Entry<String, JsonNode> entry1 = iterator1.next();
                Map.Entry<String, JsonNode> entry2 = iterator2.next();
                compareTree(entry1.getValue(), entry2.getValue());
            }

        }else if(isArray(tree1, tree2)) {
            Iterator<Map.Entry<String, JsonNode>> iterator1 = tree1.fields();
            Iterator<Map.Entry<String, JsonNode>> iterator2 = tree2.fields();

            while(iterator1.hasNext() || iterator2.hasNext()) {
                Map.Entry<String, JsonNode> entry1 = iterator1.next();
                Map.Entry<String, JsonNode> entry2 = iterator2.next();
                compareTree(entry1.getValue(), entry2.getValue());
            }
        }
    }

    private static boolean isValueNode(JsonNode tree1, JsonNode tree2) {
        return tree1.isValueNode() && tree2.isValueNode();
    }
    private static boolean isObject(JsonNode tree1, JsonNode tree2) {
        return tree1.isObject() && tree2.isObject();
    }
    private static boolean isArray(JsonNode tree1, JsonNode tree2) {
        return tree1.isArray() && tree2.isArray();
    }
    private static JsonNode parseJsonToTree(String jsonString) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonString);
            return jsonNode;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }



        private static Map<String, Map<String, Map<String, Question>>> parseJson(String jsonString, TypeReference<Map<String, Map<String, Map<String, Question>>>> typeRef) {
        try {
            // 反序列化 JSON 到对象
            Map<String, Map<String, Map<String, Question>>> obj = MAPPER.readValue(jsonString, typeRef);
            System.out.println("反序列化 JSON 到对象: " + obj);

            // 序列化对象到 JSON
            String json = MAPPER.writeValueAsString(obj);
            System.out.println("序列化对象到 JSON: " + json);
            return obj;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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
