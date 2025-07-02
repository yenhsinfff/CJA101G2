package com.lutu.nice_article.model;

import java.io.Serializable;

public class NiceArticleId implements Serializable {
    private Integer acId;
    private Integer memId;

    public NiceArticleId() {
    }

    public NiceArticleId(Integer acId, Integer memId) {
        this.acId = acId;
        this.memId = memId;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        NiceArticleId that = (NiceArticleId) obj;
        return acId.equals(that.acId) && memId.equals(that.memId);
    }

    @Override
    public int hashCode() {
        return acId.hashCode() + memId.hashCode();
    }

    @Override
    public String toString() {
        return "NiceArticleId [acId=" + acId + ", memId=" + memId + "]";
    }
}