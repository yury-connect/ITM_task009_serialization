package task2011;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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

    public static class Apartment {

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
    }

    public static void main(String[] args) {

    }
}
