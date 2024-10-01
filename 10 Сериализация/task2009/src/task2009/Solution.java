package task2009;

import java.io.*;

/* 
Как сериализовать static?
Сделай так, чтобы сериализация класса ClassWithStatic была возможной.


Requirements:
1. Класс ClassWithStatic должен существовать внутри класса Solution.
2. Класс ClassWithStatic должен быть статическим.
3. Класс ClassWithStatic должен быть публичным.
4. Класс ClassWithStatic должен поддерживать интерфейс Serializable.*/

public class Solution {

    public static class ClassWithStatic implements Serializable {

        private static final long serialVersionUID = 202410012322L;  // Уникальный идентификатор для версии класса

        public static String staticString = "This is a static test string";
        public int i;
        public int j;


        private void writeObject(ObjectOutputStream out) throws IOException {
            // Сериализуем обычные поля с помощью стандартного механизма сериализации
            out.defaultWriteObject();  // Сериализует все обычные (нестатические) поля класса (i и j)

            // Сериализуем статическое поле вручную
            out.writeObject(staticString);  // Явно сериализуем статическое поле staticString
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            // Десериализуем обычные поля с помощью стандартного механизма десериализации
            in.defaultReadObject();  // Десериализует обычные поля класса (i и j)

            // Восстанавливаем значение статического поля вручную
            staticString = (String) in.readObject();  // Читаем и присваиваем значение статической переменной
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        final String path = System.getProperty("user.dir") + "/10 Сериализация/task2009/";
        System.out.println("Путь расположения сериализованного файла: \t" + path); // выведет текущий путь от корня диска до корня проекта
        final String fileName = "data_task2009.ser";
        System.out.println("Имя сериализованного файла:\t " + fileName);

        // Создание объекта и задание значений для полей
        ClassWithStatic obj = new ClassWithStatic();
        obj.i = 123;  // Пример значений полей объекта
        obj.j = 456;

        // Сериализация объекта в файл
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path + fileName))) {
            oos.writeObject(obj);  // Сериализуем объект
        }

        // Изменяем значение статического поля перед десериализацией
        ClassWithStatic.staticString = "Modified static string";

        // Десериализация объекта из файла
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path + fileName))) {
            ClassWithStatic deserializedObj = (ClassWithStatic) ois.readObject();  // Десериализуем объект
            System.out.println("i: " + deserializedObj.i);  // Выводим значение поля i
            System.out.println("j: " + deserializedObj.j);  // Выводим значение поля j
            System.out.println("staticString: " + ClassWithStatic.staticString);  // Выводим значение статической переменной
        }
    }
}
