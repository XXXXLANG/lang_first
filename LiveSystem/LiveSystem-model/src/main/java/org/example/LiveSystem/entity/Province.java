package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * province
 * @author 
 */
@Data
public class Province implements Serializable {
    private Integer pId;

    /**
     * 省份
     */
    private String pName;

    /**
     * 国家ID
     */
    private Integer pNationid;

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
        Province other = (Province) that;
        return (this.getPId() == null ? other.getPId() == null : this.getPId().equals(other.getPId()))
            && (this.getPName() == null ? other.getPName() == null : this.getPName().equals(other.getPName()))
            && (this.getPNationid() == null ? other.getPNationid() == null : this.getPNationid().equals(other.getPNationid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPId() == null) ? 0 : getPId().hashCode());
        result = prime * result + ((getPName() == null) ? 0 : getPName().hashCode());
        result = prime * result + ((getPNationid() == null) ? 0 : getPNationid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", pId=").append(pId);
        sb.append(", pName=").append(pName);
        sb.append(", pNationid=").append(pNationid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}