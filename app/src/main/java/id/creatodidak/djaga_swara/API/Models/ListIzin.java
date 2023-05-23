package id.creatodidak.djaga_swara.API.Models;

public class ListIzin {
    private final String number;
    private final String text;

    public ListIzin(String number, String text) {
        this.number = number;
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public String getText() {
        return text;
    }
}