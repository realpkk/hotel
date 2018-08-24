package top.hotel.management.common.bean;

public class OrderInfoBean {

    private String timeRange;

    private String[] roomNumber;

    private Long[] roomId;

    public String getTimeRange() {
        return timeRange;
    }

    public void setTimeRange(String timeRange) {
        this.timeRange = timeRange;
    }

    public String[] getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String[] roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Long[] getRoomId() {
        return roomId;
    }

    public void setRoomId(Long[] roomId) {
        this.roomId = roomId;
    }
}
