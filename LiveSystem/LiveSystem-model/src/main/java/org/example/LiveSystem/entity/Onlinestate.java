package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * onlinestate
 * @author 
 */
@Data
public class Onlinestate implements Serializable {
    private Integer oId;

    /**
     * 用户ID
     */
    private Integer oUserid;

    /**
     * 客户端地址（每次登录保存）
     */
    private String oClient;

    /**
     * 在线状态（1：在线，0：离线）
     */
    private Integer oState;

    /**
     * 最近登录时间
     */
    private Date oDatatime;

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
        Onlinestate other = (Onlinestate) that;
        return (this.getOId() == null ? other.getOId() == null : this.getOId().equals(other.getOId()))
            && (this.getOUserid() == null ? other.getOUserid() == null : this.getOUserid().equals(other.getOUserid()))
            && (this.getOClient() == null ? other.getOClient() == null : this.getOClient().equals(other.getOClient()))
            && (this.getOState() == null ? other.getOState() == null : this.getOState().equals(other.getOState()))
            && (this.getODatatime() == null ? other.getODatatime() == null : this.getODatatime().equals(other.getODatatime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getOId() == null) ? 0 : getOId().hashCode());
        result = prime * result + ((getOUserid() == null) ? 0 : getOUserid().hashCode());
        result = prime * result + ((getOClient() == null) ? 0 : getOClient().hashCode());
        result = prime * result + ((getOState() == null) ? 0 : getOState().hashCode());
        result = prime * result + ((getODatatime() == null) ? 0 : getODatatime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", oId=").append(oId);
        sb.append(", oUserid=").append(oUserid);
        sb.append(", oClient=").append(oClient);
        sb.append(", oState=").append(oState);
        sb.append(", oDatatime=").append(oDatatime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}