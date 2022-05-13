package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user_groupsmsgtouser
 * @author 
 */
@Data
public class UserGroupsmsgtouser implements Serializable {
    private Integer gmId;

    /**
     * 接收者ID
     */
    private Integer gmUserid;

    /**
     * 消息ID
     */
    private Integer gmGroupmessageid;

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
        UserGroupsmsgtouser other = (UserGroupsmsgtouser) that;
        return (this.getGmId() == null ? other.getGmId() == null : this.getGmId().equals(other.getGmId()))
            && (this.getGmUserid() == null ? other.getGmUserid() == null : this.getGmUserid().equals(other.getGmUserid()))
            && (this.getGmGroupmessageid() == null ? other.getGmGroupmessageid() == null : this.getGmGroupmessageid().equals(other.getGmGroupmessageid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGmId() == null) ? 0 : getGmId().hashCode());
        result = prime * result + ((getGmUserid() == null) ? 0 : getGmUserid().hashCode());
        result = prime * result + ((getGmGroupmessageid() == null) ? 0 : getGmGroupmessageid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gmId=").append(gmId);
        sb.append(", gmUserid=").append(gmUserid);
        sb.append(", gmGroupmessageid=").append(gmGroupmessageid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}