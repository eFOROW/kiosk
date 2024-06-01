package kr.ac.wsu.kiosk;

public class Data {
    String name;
    String temperature;
    int price;
    String option;
    int imageResourceId;
    int count;
    public Data(String name, String temperature, int price, String option, int imageResourceId, int count) {
        this.name = name;
        this.temperature = temperature;
        this.price = price;
        this.option = option;
        this.imageResourceId = imageResourceId;
        this.count = count;
    }
}
