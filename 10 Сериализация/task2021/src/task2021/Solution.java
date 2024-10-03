package task2021;

import java.io.*;

/* 
Сериализация под запретом
Запрети сериализацию класса SubSolution используя NotSerializableException.
Сигнатуры классов менять нельзя.


Requirements:
1. Класс Solution должен поддерживать интерфейс Serializable.
2. Класс SubSolution должен быть создан внутри класса Solution.
3. Класс SubSolution должен быть потомком класса Solution.
4. При попытке сериализовать объект типа SubSolution должно возникать исключение NotSerializableException.
5. При попытке десериализовать объект типа SubSolution должно возникать исключение NotSerializableException.*/


public class Solution implements Serializable {

    /*
Для того чтобы запретить сериализацию класса SubSolution, можно переопределить методы writeObject и readObject в этом
классе и выбросить исключение NotSerializableException внутри них. Это приведет к тому, что при любой попытке
сериализовать или десериализовать объект типа SubSolution, программа выбросит соответствующее исключение.
     */
    public static class SubSolution extends Solution {// Переопределяем метод для сериализации
        private void writeObject(ObjectOutputStream out) throws IOException {
            throw new NotSerializableException("Сериализация класса SubSolution запрещена.");
        }

        // Переопределяем метод для десериализации
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            throw new NotSerializableException("Десериализация класса SubSolution запрещена.");
        }
    }

    public static void main(String[] args) {
        SubSolution subSolution = new SubSolution();

        // Пример проверки на сериализацию
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("subSolution.ser"))) {
            oos.writeObject(subSolution);
        } catch (NotSerializableException e) {
            System.out.println("Исключение: " + e.getMessage());  // Ловим исключение NotSerializableException
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Пример проверки на десериализацию
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("subSolution.ser"))) {
            SubSolution deserializedSubSolution = (SubSolution) ois.readObject();
        } catch (NotSerializableException e) {
            System.out.println("Исключение: " + e.getMessage());  // Ловим исключение NotSerializableException
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
