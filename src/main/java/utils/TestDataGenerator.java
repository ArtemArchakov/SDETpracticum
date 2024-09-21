package utils;

public class TestDataGenerator {

    public static String generatePostCode() {
        return String.valueOf((long) (Math.random() * 1_000_000_0000L));
    }

    public static String generateFirstName(String postCode) {
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < postCode.length(); i += 2) {
            // Проверяем, что есть хотя бы 2 символа для обработки
            if (i + 1 < postCode.length()) {
                int num = Integer.parseInt(postCode.substring(i, i + 2));
                char letter = (char) ('a' + num % 26);
                name.append(letter);
            } else {
                // Обрабатываем последний одиночный символ, если длина строки нечетная
                int num = Integer.parseInt(postCode.substring(i, i + 1));
                char letter = (char) ('a' + num % 26);
                name.append(letter);
            }
        }
        return name.toString();
    }
}
