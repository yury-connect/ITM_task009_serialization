package task2006;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* 
Как сериализовать?
Сделай так, чтобы сериализация класса Human была возможной.


Requirements:
1. Класс Human должен существовать внутри класса Solution.
2. Класс Human должен быть статическим.
3. Класс Human должен быть публичным.
4. Класс Human должен поддерживать интерфейс Serializable.*/

public class Solution {
    public static class Human implements Serializable {  // Реализуем интерфейс Serializable

        private static final long serialVersionUID = 1L; // Рекомендуется добавить serialVersionUID

        public String name;
        public List<String> assets = new ArrayList<>();

        public Human() {
            // для десериализации должен быть конструктор без параметров
        }

        public Human(String name, String... assets) {
            this.name = name;
            if (assets != null) {
                this.assets.addAll(Arrays.asList(assets));
            }
        }
    }

    public static void main(String[] args) {

    }
}
