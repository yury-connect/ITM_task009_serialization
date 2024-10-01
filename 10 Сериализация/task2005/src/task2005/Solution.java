package task2005;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/* 
Очень странные дела
При чтении/записи объектов типа Human возникают странные ошибки.
Разберись в чем дело и исправь их.


Requirements:
1. Логика чтения/записи реализованная в методах save/load должна работать корректно в случае, если список assets равен null.
2. Логика чтения/записи реализованная в методах save/load должна работать корректно в случае, если поле name и список assets не равны null.
3. Класс Solution.Human не должен поддерживать интерфейс Serializable.
4. Класс Solution.Human должен быть публичным.
5. Класс Solution.Human не должен поддерживать интерфейс Externalizable.
6. Метод equals должен возвращать true для двух равных объектов типа Human и false для разных.
7. Метод hashCode должен всегда возвращать одно и то же значение для одного и того же объекта типа Human.*/

public class Solution {

    private static final String FILE_NAME_PREFIX = "task2005_human_prefix_.txt";
    private static final String FILE_EXPANSION = ".txt";
    public static void main(String[] args) {

        //исправь outputStream/inputStream в соответствии с путем к твоему реальному файлу
        try {
            File file = File.createTempFile(FILE_NAME_PREFIX, FILE_EXPANSION);
            OutputStream outputStream = new FileOutputStream(file); // PЗаменив 'FILE_NAME_PREFIX' на 'file' yначали работать с директорией системы для временных файлов
            InputStream inputStream = new FileInputStream(file); // PЗаменив 'FILE_NAME_PREFIX' на 'file' yначали работать с директорией системы для временных файлов

            System.out.println("\nМесто расположения временного файла в файловой системе (Temporary file directory): "
                    + System.getProperty("java.io.tmpdir"));
            System.out.println("Полный путь созданного файла, включая все директории: " + file.getAbsolutePath());
            
            Human ivanov = new Human("Ivanov", new Asset("home"), new Asset("car"));
            ivanov.save(outputStream);
            outputStream.flush();

            Human somePerson = new Human();
            somePerson.load(inputStream);

            //check here that ivanov equals to somePerson - проверьте тут, что ivanov и somePerson равны
            System.out.println("\nСериализуемый объект:\n\t" + ivanov);
            System.out.println("Объект, полученный после десериализации:\n\t" + somePerson + "\n");
            System.out.println(ivanov.equals(somePerson) ? "Объекты равны" : "Объекты не равны");

            System.out.println(ivanov.equals(somePerson));
            inputStream.close();

        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Oops, something wrong with my file");
        } catch (Exception e) {
            //e.printStackTrace();
            System.out.println("Oops, something wrong with save/load method");
        }
    }

    public static class Human {
        public String name;
        public List<Asset> assets = new ArrayList<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return false;
            if (o == null || getClass() != o.getClass()) return false;

            Human human = (Human) o;

            if (name != null ? !name.equals(human.name) : human.name != null) return false;
            if (this.assets.size() != human.assets.size()) {
                return false;
            }
            for (Asset asset : human.assets) {
                if (!this.assets.contains(asset)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result = name != null ? name.hashCode() : 0;
            result = 31 * result + (assets != null ? assets.hashCode() : 0);
            return result;
        }

        public Human() {
        }

        public Human(String name, Asset... assets) {
            this.name = name;
            if (assets != null) {
                this.assets.addAll(Arrays.asList(assets));
            }
        }

        public void save(OutputStream outputStream) throws Exception {
            //implement this method - реализуйте этот метод
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.println(this.name);
            if (this.assets.size() > 0) {
                for (Asset current : this.assets)
                    printWriter.println(current.getName());
            }
            printWriter.close();
        }

        public void load(InputStream inputStream) throws Exception {
            //implement this method - реализуйте этот метод
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            this.name = reader.readLine();
            String assetName;
            while ((assetName = reader.readLine()) != null)
                this.assets.add(new Asset(assetName));
            reader.close();
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
