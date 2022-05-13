package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * school
 * @author 
 */
@Data
public class School implements Serializable {
    private Integer sId;

    /**
     * 学校名称
     */
    private String sName;

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
        School other = (School) that;
        return (this.getSId() == null ? other.getSId() == null : this.getSId().equals(other.getSId()))
            && (this.getSName() == null ? other.getSName() == null : this.getSName().equals(other.getSName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSId() == null) ? 0 : getSId().hashCode());
        result = prime * result + ((getSName() == null) ? 0 : getSName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", sId=").append(sId);
        sb.append(", sName=").append(sName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}