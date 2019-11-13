package com.safexpress.propeli.bff.dto;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Response<T> {
    private String message;
    private List<T> data;
    private ReferenceDTO refernceList;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
    public ReferenceDTO getRefernceList() {
        return refernceList;
    }
    public void setRefernceList(ReferenceDTO refernceList) {
        this.refernceList = refernceList;
    }
}
