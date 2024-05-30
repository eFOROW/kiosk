package kr.ac.wsu.kiosk;

public class Data {
    String name;
    String temperature;
    int price;
    String option;
    int imageResourceId;
    int count;

    // 데이터 생성자 (count 포함)
    public Data(String name, String temperature, int price, String option, int imageResourceId, int count) {
        this.name = name;
        this.temperature = temperature;
        this.price = price;
        this.option = option == null ? "없음" : option;
        this.imageResourceId = imageResourceId;
        this.count = count;
    }

    // 데이터 생성자 (count 기본값 1)
    public Data(String name, String temperature, int price, String option, int imageResourceId) {
        this(name, temperature, price, option, imageResourceId, 1);
    }
}
