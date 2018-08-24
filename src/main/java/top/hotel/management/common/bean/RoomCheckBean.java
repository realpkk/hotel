package top.hotel.management.common.bean;

public class RoomCheckBean<T> {

    private T data;

    private String phoneNumber;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
