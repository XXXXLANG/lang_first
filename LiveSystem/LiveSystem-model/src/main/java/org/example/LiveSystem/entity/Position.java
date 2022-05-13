package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * position
 * @author 
 */
@Data
public class Position implements Serializable {
    private Integer spId;

    /**
     * 职位名称
     */
    private String spName;

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
        Position other = (Position) that;
        return (this.getSpId() == null ? other.getSpId() == null : this.getSpId().equals(other.getSpId()))
            && (this.getSpName() == null ? other.getSpName() == null : this.getSpName().equals(other.getSpName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getSpId() == null) ? 0 : getSpId().hashCode());
        result = prime * result + ((getSpName() == null) ? 0 : getSpName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", spId=").append(spId);
        sb.append(", spName=").append(spName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}