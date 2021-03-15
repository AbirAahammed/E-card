package redbull.ecard.DataLayer;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class ProfileDBO extends Model {
    private  static ObjectMapper objectMapper = new ObjectMapper();
    private Long name;
    private Long contact;
    private Long address;
    private String uid;

    public ProfileDBO() {
    }

    public ProfileDBO(Long name, Long contact, Long address, String uid) {
        this.name = name;
        this.contact = contact;
        this.address = address;
        this.uid = uid;
    }


    public Long getName() {
        return name;
    }

    public void setName(Long name) {
        this.name = name;
    }

    public Long getContact() {
        return contact;
    }

    public void setContact(Long contact) {
        this.contact = contact;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ProfileDBO map(String json) throws IOException {
        return objectMapper.readValue(json, this.getClass());
    }
}
