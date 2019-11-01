package com.safexpress.propeli.bff.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

public class LookUpMDMDTO {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long Id;
    private String lookupVal;
    private int lookupTypeId;

    private String desc;
    private int status;

    private String attr1;
    private String attr2;

    //commented for now
    //private int attr3;
    //private int attr4;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp crtdDt;
    private String crtdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp updDt;
    private String updBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp effectiveDt;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private Timestamp expDt;


    public LookUpMDMDTO(long id2, String lookup_val2, int i) {
        this.Id =id2;
        this.lookupVal = lookup_val2;
        this.lookupTypeId = i;

    }
    public LookUpMDMDTO() {
        // TODO Auto-generated constructor stub
    }
    /**
     * @return the id
     */
    public long getId() {
        return Id;
    }
    /**
     * @param id the id to set
     */
    public void setId(long id) {
        Id = id;
    }
    /**
     * @return the lookupVal
     */
    public String getLookupVal() {
        return lookupVal;
    }
    /**
     * @param lookupVal the lookupVal to set
     */
    public void setLookupVal(String lookupVal) {
        this.lookupVal = lookupVal;
    }
    /**
     * @return the lookupTypeId
     */
    public int getLookupTypeId() {
        return lookupTypeId;
    }
    /**
     * @param lookupTypeId the lookupTypeId to set
     */
    public void setLookupTypeId(int lookupTypeId) {
        this.lookupTypeId = lookupTypeId;
    }
    /**
     * @return the desc
     */
    public String getDesc() {
        return desc;
    }
    /**
     * @param desc the desc to set
     */
    public void setDesc(String desc) {
        this.desc = desc;
    }
    /**
     * @return the status
     */
    public int getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(int status) {
        this.status = status;
    }
    /**
     * @return the attr1
     */
    public String getAttr1() {
        return attr1;
    }
    /**
     * @param attr1 the attr1 to set
     */
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }
    /**
     * @return the attr2
     */
    public String getAttr2() {
        return attr2;
    }
    /**
     * @param attr2 the attr2 to set
     */
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }
    /**
     * @return the crtdDt
     */
    public Timestamp getCrtdDt() {
        return crtdDt;
    }
    /**
     * @param crtdDt the crtdDt to set
     */
    public void setCrtdDt(Timestamp crtdDt) {
        this.crtdDt = crtdDt;
    }
    /**
     * @return the crtdBy
     */
    public String getCrtdBy() {
        return crtdBy;
    }
    /**
     * @param crtdBy the crtdBy to set
     */
    public void setCrtdBy(String crtdBy) {
        this.crtdBy = crtdBy;
    }
    /**
     * @return the updDt
     */
    public Timestamp getUpdDt() {
        return updDt;
    }
    /**
     * @param updDt the updDt to set
     */
    public void setUpdDt(Timestamp updDt) {
        this.updDt = updDt;
    }
    /**
     * @return the updBy
     */
    public String getUpdBy() {
        return updBy;
    }
    /**
     * @param updBy the updBy to set
     */
    public void setUpdBy(String updBy) {
        this.updBy = updBy;
    }
    /**
     * @return the effectiveDt
     */
    public Timestamp getEffectiveDt() {
        return effectiveDt;
    }
    /**
     * @param effectiveDt the effectiveDt to set
     */
    public void setEffectiveDt(Timestamp effectiveDt) {
        this.effectiveDt = effectiveDt;
    }
    /**
     * @return the expDt
     */
    public Timestamp getExpDt() {
        return expDt;
    }
    /**
     * @param expDt the expDt to set
     */
    public void setExpDt(Timestamp expDt) {
        this.expDt = expDt;
    }
}
