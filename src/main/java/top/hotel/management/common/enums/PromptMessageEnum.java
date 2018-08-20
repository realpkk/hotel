package top.hotel.management.common.enums;

public enum PromptMessageEnum {
    PPE("手机号或密码错误"),PD("手机号已注册");

    String value;

    PromptMessageEnum(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
