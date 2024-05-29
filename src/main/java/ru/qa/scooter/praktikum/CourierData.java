package ru.qa.scooter.praktikum;

import java.util.Random;

public class CourierData { //данные для создания курьера
    private String login;
    private String password;
    private String firstName;

    public CourierData(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static CourierData courierGenerator() {
        return new CourierData(getRandomSet(), getRandomSet(), getRandomSet());
    }

    private static String getRandomSet() { //генерируем случаные данные
        String Randomset = new String("abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPRQRSTUVWXYZ".toCharArray()); // набор данных для создания логина, пароля, имени курьера
        Random random = new Random();
        StringBuilder sb = new StringBuilder(10);
        for (int i = 0; i < 10; i++) {
            int rndCharAt = random.nextInt(Randomset.length());
            char rndChar = Randomset.charAt(rndCharAt);
            sb.append(rndChar);
        }
        return sb.toString();
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
