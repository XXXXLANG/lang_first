package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * nation
 * @author 
 */
@Data
public class Nation implements Serializable {
    private Integer nId;

    /**
     * 国家全名
     */
    private String nName;

    /**
     * 简称
     */
    private String nShort;

    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Nation other = (Nation) that;
        return (this.getNId() == null ? other.getNId() == null : this.getNId().equals(other.getNId()))
            && (this.getNName() == null ? other.getNName() == null : this.getNName().equals(other.getNName()))
            && (this.getNShort() == null ? other.getNShort() == null : this.getNShort().equals(other.getNShort()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getNId() == null) ? 0 : getNId().hashCode());
        result = prime * result + ((getNName() == null) ? 0 : getNName().hashCode());
        result = prime * result + ((getNShort() == null) ? 0 : getNShort().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", nId=").append(nId);
        sb.append(", nName=").append(nName);
        sb.append(", nShort=").append(nShort);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}