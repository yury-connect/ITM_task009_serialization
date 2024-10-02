package task2011;

import java.io.*;

/* 
Externalizable для апартаментов
Реализуй интерфейс Externalizable в классе Apartment.


Requirements:
1. Класс Solution.Apartment должен поддерживать интерфейс Externalizable.
2. В классе Solution.Apartment должен быть реализован метод writeExternal с одним параметром типа ObjectOutput.
3. В классе Solution.Apartment должен быть реализован метод readExternal с одним параметром типа ObjectInput.
4. В методе writeExternal, на полученном в качестве параметра объекте типа ObjectOutput должен быть вызван метод writeObject с параметром address.
5. В методе writeExternal, на полученном в качестве параметра объекте типа ObjectOutput должен быть вызван метод writeInt с параметром year.
6. Метод readExternal должен корректно восстанавливать из ObjectInput значение поля address.
7. Метод readExternal должен корректно восстанавливать из ObjectInput значение поля year.*/

public class Solution {
    private static final String FULL_FILE_NAME = System.getProperty("user.dir") + "/10 Сериализация/task2011/task2011_OBJ.ser";

    public static class Apartment implements Externalizable {

        private String address;
        private int year;

        /**
         * Mandatory public no-arg constructor.
         */
        public Apartment() {
            super();
        }

        public Apartment(String addr, int y) {
            address = addr;
            year = y;
        }

        /**
         * Prints out the fields used for testing!
         */
        public String toString() {
            return ("Address: " + address + "\n" + "Year: " + year);
        }

        @Override
        public void writeExternal(ObjectOutput outObj) throws IOException {
            outObj.writeObject(address);
            outObj.writeInt(year);
        }

        @Override
        public void readExternal(ObjectInput inObj) throws IOException, ClassNotFoundException {
            this.address = inObj.readObject().toString();
            this.year = inObj.readInt();
        }
    }

    public static void main(String[] args) {
        System.out.println("\n\tРаботаю с файлом: " + FULL_FILE_NAME);
        Apartment srcObj = new Apartment("London", 2011);
        System.out.println("\n\tСериализуемый объект:\n" + srcObj);
        try {
            java.io.ObjectOutputStream outObj = new java.io.ObjectOutputStream(new java.io.FileOutputStream(FULL_FILE_NAME));
            outObj.writeObject(srcObj);
            outObj.close();
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        Apartment dstObj;
        try {
            java.io.ObjectInputStream in = new java.io.ObjectInputStream(new java.io.FileInputStream(FULL_FILE_NAME));
            dstObj = (Apartment) in.readObject();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("\n\tДесериализованный объект:\n" + dstObj);
    }
}
