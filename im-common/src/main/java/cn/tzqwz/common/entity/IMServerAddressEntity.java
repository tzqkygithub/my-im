package cn.tzqwz.common.entity;


public class IMServerAddressEntity {

    private String imServerUrl;

    private Integer imServerPort;

    private Integer webServerPort;

    public IMServerAddressEntity(String serverAddress){
        String[] split = serverAddress.split(":");
        this.imServerUrl = split[0];
        this.imServerPort = Integer.valueOf(split[1]);
        this.webServerPort = Integer.valueOf(split[2]);
    }

    public String getImServerUrl() {
        return imServerUrl;
    }

    public Integer getImServerPort() {
        return imServerPort;
    }

    public Integer getWebServerPort() {
        return webServerPort;
    }
}

