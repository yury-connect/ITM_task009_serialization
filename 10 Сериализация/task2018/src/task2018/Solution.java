package task2018;

import java.io.*;

/* 
Найти ошибки
Почему-то при сериализации/десериализации объекта класса B возникают ошибки.

Найди проблему и исправь ее.

Класс A не должен реализовывать интерфейсы Serializable и Externalizable.

В сигнатуре класса В ошибки нет :).

В методе main ошибок нет.


Requirements:
1. Класс B должен быть потомком класса A.
2. Класс B должен поддерживать интерфейс Serializable.
3. Класс A не должен поддерживать интерфейс Serializable.
4. Класс A не должен поддерживать интерфейс Externalizable.
5. Программа должна выполняться без ошибок.
6. При десериализации должны корректно восстанавливаться значение полей nameA и nameB.*/


public class Solution {
    public static class A {

        protected String nameA = "A";

        public A(String nameA) {
            this.nameA += nameA;
        }

        public A() { // я добавил  Конструктор по умолчанию для A
        }
    }



    public static class B extends A implements Serializable { // сделал класс статическим

        private String nameB;

        public B(String nameA, String nameB) {
            super(nameA);
//            this.nameA += nameA;
            this.nameB = nameB;
        }

        public B() { // я добавил  Конструктор по умолчанию
        }

        // Метод для сериализации
        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject(); // Сериализуем поля класса B
            out.writeUTF(nameA); // Сериализуем поле класса A
        }

        // Метод для десериализации
        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject(); // Десериализуем поля класса B
            this.nameA = in.readUTF(); // Десериализуем поле класса A
        }
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(arrayOutputStream);

        Solution solution = new Solution();
        B b = new B("B2", "C33");
        System.out.println("nameA: " + b.nameA + ", nameB: " + b.nameB);

        oos.writeObject(b);

        ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(arrayInputStream);

        B b1 = (B) ois.readObject();
        System.out.println("nameA: " + b1.nameA + ", nameB: " + b1.nameB);
    }
}
