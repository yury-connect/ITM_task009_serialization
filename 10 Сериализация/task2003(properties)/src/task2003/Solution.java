package task2003;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/* 
Знакомство с properties
В методе main() происходит считывание пути к файлу с консоли и заполнение runtimeStorage данными из файла.
Тебе необходимо в методе save() реализовать логику записи в файл для карты runtimeStorage,
а в методе load() - логику чтения из файла для runtimeStorage.
Файл должен быть в формате .properties. Комментарии в файле игнорируй.
Про .properties прочитай в вики.
Подсказка: используй объект класса Properties.


Requirements:
1. Метод save() должен сохранять карту runtimeStorage в параметр outputStream.
2. Метод load() должен восстанавливать состояние карты runtimeStorage из параметра inputStream.*/

public class Solution {
    private static String fileName;
    private static final String PATH = System.getProperty("user.dir") + "/10 Сериализация/task2003(properties)/";
    public static Map<String, String> runtimeStorageSrc = new HashMap<>();
    public static Map<String, String> runtimeStorageDst = new HashMap<>();

    public static void save(OutputStream outputStream) throws Exception {
        //напишите тут ваш код
        Properties properties = new Properties();

        // Переносим данные из Map в Properties
        for (Map.Entry<String, String> entry : runtimeStorageSrc.entrySet()) {
            properties.setProperty(entry.getKey(), entry.getValue());
        }
        properties.store(outputStream, "Stored Properties");
    }

    public static void load(InputStream inputStream) throws IOException {
        //напишите тут ваш код
        Properties properties = new Properties();
        properties.load(inputStream);
        for (String key : properties.stringPropertyNames()) {
            runtimeStorageDst.put(key, properties.getProperty(key));
        }
    }

    public static void main(String[] args) {
        generateTestData();
        System.out.println("Введите путь и имя файла: \n\t( Подсказка: нужно ввести 1.properties )\n\t");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
             FileInputStream fis = new FileInputStream(fileName = PATH + reader.readLine());
             FileOutputStream fos = new FileOutputStream(fileName)) {

            System.out.println(" Работаю с файлом: " + fileName);
            System.out.println("\nВывожу на печать записываемые данные:");
            runtimeStorageSrc.forEach((key, value) -> System.out.println("\t" + key + " = " + value));
            save(fos);
            load(fis);
            // распечатаем прочитанное:
            System.out.println("\nВывожу на печать прочитанные данные:");
            runtimeStorageDst.forEach((key, value) -> System.out.println("\t" + key + " = " + value));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void generateTestData() {
        runtimeStorageSrc.put("username", "admin");
        runtimeStorageSrc.put("password", "12345");
        runtimeStorageSrc.put("url", "http://example.com");
        for (int i = 1; i <= 10; i++) {
            runtimeStorageSrc.put("key_" + i, "value_" + Math.round(Math.random() * 100_000));
        }
    }
}
