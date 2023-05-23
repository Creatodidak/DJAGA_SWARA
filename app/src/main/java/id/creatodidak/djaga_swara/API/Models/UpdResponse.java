package id.creatodidak.djaga_swara.API.Models;

public class UpdResponse {
    private String msg;

    public UpdResponse(String msg) {
        this.msg = msg;
    }

    // Getter method
    public String getMsg() {
        return msg;
    }

    // Setter method
    public void setMsg(String msg) {
        this.msg = msg;
    }
}
