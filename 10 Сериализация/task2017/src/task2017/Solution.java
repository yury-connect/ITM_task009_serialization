package task2017;

import java.io.*;

/* 
Десериализация
На вход подается поток, в который записан сериализованный объект класса A либо класса B.
Десериализуй объект в методе getOriginalObject так, чтобы в случае возникновения исключения было выведено сообщение на экран и возвращен null.
Реализуй интерфейс Serializable где необходимо.


Requirements:
1. Класс B должен быть потомком класса A.
2. Класс A должен поддерживать интерфейс Serializable.
3. Класс B не должен явно поддерживать интерфейс Serializable.
4. Метод getOriginalObject должен возвращать объект типа A полученный из потока ObjectInputStream.
5. Метод getOriginalObject должен возвращать null, если при попытке десериализации не был получен объект типа A.
6. Метод getOriginalObject должен возвращать null, если при попытке десериализации возникло исключение.*/

public class Solution {
    public A getOriginalObject(ObjectInputStream objectStream) {
        try {
            Object object = objectStream.readObject();

            // Проверка, является ли объект экземпляром A
            if (object.getClass().equals(A.class)) { // Если объект является экземпляром A
                return (A) object; // вернем этот объект
            } else {
                return null;
            }

        } catch (IOException | ClassNotFoundException e) { // любые ошибки во время десериализации (например, IOException или ClassNotFoundException) приводят к выводу сообщения и возвращению null.
            System.out.println("Возникла непредвиденная ошибка.\nСообщение об ошибке: " + e.getMessage() + "\nВывожу стэктрэйс: \n" + e.getStackTrace());
        }
        return null;
    }

    public static class A implements Serializable {
    }

    public static class B extends A {
        public B() {
            System.out.println("inside B");
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();

        // Тестирование сериализации и десериализации класса A
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            // Создаем объект класса A и сериализуем его
            A objectA = new A(); // изменим создание объектов A и B так, чтобы они не ссылались на объект внешнего класса Solution // По условию задачи Solution НЕ является Serializable
            objectOutputStream.writeObject(objectA);

            // Десериализуем объект класса A
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                A deserializedObjectA = solution.getOriginalObject(objectInputStream);
                System.out.println("Вывожу на печать десериализованный объект A: " + deserializedObjectA);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Тестирование сериализации и десериализации класса B
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {

            // Создаем объект класса B и сериализуем его
            B objectB = new B(); // изменим создание объектов A и B так, чтобы они не ссылались на объект внешнего класса Solution // По условию задачи Solution НЕ является Serializable
            objectOutputStream.writeObject(objectB);

            // Десериализуем объект класса B
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
                 ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream)) {

                A deserializedObjectB = solution.getOriginalObject(objectInputStream);
                System.out.println("Вывожу на печать десериализованный объект B: " + deserializedObjectB);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
