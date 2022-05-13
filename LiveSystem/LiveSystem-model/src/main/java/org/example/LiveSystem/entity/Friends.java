package org.example.LiveSystem.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * friends
 * @author 
 */
@Data
public class Friends implements Serializable {
    private Integer fId;

    /**
     * 自己的ID
     */
    private Integer fUserid;

    /**
     * 朋友的ID
     */
    private Integer fFriendid;

    /**
     * 好友备注(USER_ID对FRIEND_ID的备注)
     */
    private String fAliasuser;

    /**
     * 好友备注(FRIEND_ID对USER_ID的备注)
     */
    private String fAliasfriend;

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
        Friends other = (Friends) that;
        return (this.getFId() == null ? other.getFId() == null : this.getFId().equals(other.getFId()))
            && (this.getFUserid() == null ? other.getFUserid() == null : this.getFUserid().equals(other.getFUserid()))
            && (this.getFFriendid() == null ? other.getFFriendid() == null : this.getFFriendid().equals(other.getFFriendid()))
            && (this.getFAliasuser() == null ? other.getFAliasuser() == null : this.getFAliasuser().equals(other.getFAliasuser()))
            && (this.getFAliasfriend() == null ? other.getFAliasfriend() == null : this.getFAliasfriend().equals(other.getFAliasfriend()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFId() == null) ? 0 : getFId().hashCode());
        result = prime * result + ((getFUserid() == null) ? 0 : getFUserid().hashCode());
        result = prime * result + ((getFFriendid() == null) ? 0 : getFFriendid().hashCode());
        result = prime * result + ((getFAliasuser() == null) ? 0 : getFAliasuser().hashCode());
        result = prime * result + ((getFAliasfriend() == null) ? 0 : getFAliasfriend().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", fId=").append(fId);
        sb.append(", fUserid=").append(fUserid);
        sb.append(", fFriendid=").append(fFriendid);
        sb.append(", fAliasuser=").append(fAliasuser);
        sb.append(", fAliasfriend=").append(fAliasfriend);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}