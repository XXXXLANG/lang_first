package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * interaction
 * @author 
 */
@Data
public class Interaction implements Serializable {
    private Integer iId;

    /**
     * 互动方式
     */
    private String iPattern;

    /**
     * 发送者
     */
    private Integer iUserid;

    /**
     * 发送时间
     */
    private Date iCreatetime;

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
        Interaction other = (Interaction) that;
        return (this.getIId() == null ? other.getIId() == null : this.getIId().equals(other.getIId()))
            && (this.getIPattern() == null ? other.getIPattern() == null : this.getIPattern().equals(other.getIPattern()))
            && (this.getIUserid() == null ? other.getIUserid() == null : this.getIUserid().equals(other.getIUserid()))
            && (this.getICreatetime() == null ? other.getICreatetime() == null : this.getICreatetime().equals(other.getICreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getIId() == null) ? 0 : getIId().hashCode());
        result = prime * result + ((getIPattern() == null) ? 0 : getIPattern().hashCode());
        result = prime * result + ((getIUserid() == null) ? 0 : getIUserid().hashCode());
        result = prime * result + ((getICreatetime() == null) ? 0 : getICreatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", iId=").append(iId);
        sb.append(", iPattern=").append(iPattern);
        sb.append(", iUserid=").append(iUserid);
        sb.append(", iCreatetime=").append(iCreatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}