package task2001;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/* 
Читаем и пишем в файл: Human
Часто необходимо сохранять состояние работы программы. До появления сериализации каждый делал это своим способом.
В этой задаче нужно сохранить в файл состояние работы программы и вычитать состояние из файла без использования сериализации.

Реализуй логику записи в файл и чтения из файла для класса Human.
Поле name в классе Human не может быть пустым.
Метод main реализован только для тебя и не участвует в тестировании.


Requirements:
1. Логика чтения/записи реализованная в методах save/load должна работать корректно в случае, если список assets пустой.
2. Логика чтения/записи реализованная в методах save/load должна работать корректно в случае, если поле name и список assets не пустые.
3. Класс Solution.Human не должен поддерживать интерфейс Serializable.
4. Класс Solution.Human должен быть публичным.
5. Класс Solution.Human не должен поддерживать интерфейс Externalizable.*/

public class Solution {

    private static final String FILE_NAME_PREFIX = "task2001_human_prefix_.txt";
    private static final String FILE_EXPANSION = ".txt";

    public static void main(String[] args) {
        //исправьте outputStream/inputStream в соответствии с путем к вашему реальному файлу

        try {
            File file = File.createTempFile(FILE_NAME_PREFIX, FILE_EXPANSION);
            System.out.println("\nМесто расположения временного файла в файловой системе (Temporary file directory): "
                    + System.getProperty("java.io.tmpdir"));
            System.out.println("Полный путь созданного файла, включая все директории: " + file.getAbsolutePath());
            OutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = new FileInputStream(file);

            Human ivanov = new Human("Ivanov", new Asset("home", 999_999.99), new Asset("car", 2999.99));
            ivanov.save(outputStream);
            outputStream.flush();

            Human somePerson = new Human();
            somePerson.load(inputStream);
            inputStream.close();

            //check here that ivanov equals to somePerson - проверьте тут, что ivanov и somePerson равны
            System.out.println("\nСериализуемый объект:\n\t" + ivanov);
            System.out.println("Объект, полученный после десериализации:\n\t" + somePerson + "\n");
            System.out.println(ivanov.equals(somePerson) ? "Объекты равны" : "Объекты не равны");

        } catch (IOException e) {
            System.out.println("Oops, something wrong with my file");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Oops, something wrong with save/load method");
            e.printStackTrace();
        }
    }

    public static class Human {
        public String name;
        public List<Asset> assets = new ArrayList<>();

        public Human() {
        }

        public Human(String name, Asset... assets) {
            this.name = name;
            if (assets != null) {
                this.assets.addAll(Arrays.asList(assets));
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Human human = (Human) o;

            if (name != null ? !name.equals(human.name) : human.name != null) return false;
            return assets != null ? assets.equals(human.assets) : human.assets == null;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (assets != null ? assets.hashCode() : 0);
            return result;
        }

        public void save(OutputStream outputStream) throws Exception {
            //implement this method - реализуйте этот метод // РЕШЕНИЕ РЕАЛИЗОВАНО В ДАННОМ МЕТОДЕ
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalArgumentException("\n\tПоле 'name' = '" + name + "'. \n\tПоле name не может быть пустым!");
            }
            PrintWriter writer = new PrintWriter(outputStream);
            writer.println(name); // Сначала записываем имя
            writer.println(assets.size()); // Записываем количество активов
            for (Asset asset : assets) {
                writer.println(asset.getName());
                writer.println(asset.getPrice());
            }
            writer.flush();
        }

        public void load(InputStream inputStream) throws Exception {
            //implement this method - реализуйте этот метод // РЕШЕНИЕ РЕАЛИЗОВАНО В ДАННОМ МЕТОДЕ
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            this.name = reader.readLine(); // Считываем имя
            int assetCount = Integer.parseInt(reader.readLine()); // Считываем количество активов
            assets.clear();
            for (int i = 0; i < assetCount; i++) {
                String assetName = reader.readLine();
                double assetValue = Double.parseDouble(reader.readLine());
                assets.add(new Asset(assetName, assetValue)); // Создаем активы и добавляем в список
            }
        }

        @Override
        public String toString() {
            return "Human{" +
                    "name='" + name + '\'' +
                    ", assets=" + assets.stream().map(Object::toString).collect(Collectors.joining(", ")) +
                    '}';
        }
    }
}



/*
Когда вы используете File.createTempFile("your_file_name", null); в Java, временный файл будет создан в директории, указанной в системной переменной java.io.tmpdir.
В IntelliJ IDEA эта переменная указывает на стандартное временное хранилище операционной системы. Обычно это:

Windows: C:\Users\<Ваше_имя>\AppData\Local\Temp\
Linux: /tmp/
macOS: /tmp/

Вы можете проверить путь, выводя значение System.getProperty("java.io.tmpdir") в коде:

System.out.println("Temporary file directory: " + System.getProperty("java.io.tmpdir"));

Таким образом, временный файл будет создан в соответствующей временной директории вашей операционной системы.
 */