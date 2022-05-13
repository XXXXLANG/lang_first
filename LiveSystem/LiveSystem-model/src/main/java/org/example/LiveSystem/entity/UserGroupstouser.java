package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * user_groupstouser
 * @author 
 */
@Data
public class UserGroupstouser implements Serializable {
    private Integer ugtId;

    /**
     * 用户ID
     */
    private Integer ugtUserid;

    /**
     * 群ID
     */
    private Integer ugtGroupid;

    /**
     * 群内用户昵称
     */
    private String ugtGroupnick;

    /**
     * 用户是否消息免打扰（1：是；0：否）
     */
    private Integer ugtReminding;

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
        UserGroupstouser other = (UserGroupstouser) that;
        return (this.getUgtId() == null ? other.getUgtId() == null : this.getUgtId().equals(other.getUgtId()))
            && (this.getUgtUserid() == null ? other.getUgtUserid() == null : this.getUgtUserid().equals(other.getUgtUserid()))
            && (this.getUgtGroupid() == null ? other.getUgtGroupid() == null : this.getUgtGroupid().equals(other.getUgtGroupid()))
            && (this.getUgtGroupnick() == null ? other.getUgtGroupnick() == null : this.getUgtGroupnick().equals(other.getUgtGroupnick()))
            && (this.getUgtReminding() == null ? other.getUgtReminding() == null : this.getUgtReminding().equals(other.getUgtReminding()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUgtId() == null) ? 0 : getUgtId().hashCode());
        result = prime * result + ((getUgtUserid() == null) ? 0 : getUgtUserid().hashCode());
        result = prime * result + ((getUgtGroupid() == null) ? 0 : getUgtGroupid().hashCode());
        result = prime * result + ((getUgtGroupnick() == null) ? 0 : getUgtGroupnick().hashCode());
        result = prime * result + ((getUgtReminding() == null) ? 0 : getUgtReminding().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ugtId=").append(ugtId);
        sb.append(", ugtUserid=").append(ugtUserid);
        sb.append(", ugtGroupid=").append(ugtGroupid);
        sb.append(", ugtGroupnick=").append(ugtGroupnick);
        sb.append(", ugtReminding=").append(ugtReminding);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}