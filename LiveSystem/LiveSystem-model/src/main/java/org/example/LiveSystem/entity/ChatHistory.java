package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * chat_history
 * @author 
 */
@Data
public class ChatHistory implements Serializable {
    private Integer chId;

    /**
     * 群ID
     */
    private Integer chGroupid;

    /**
     * 好友ID
     */
    private Integer chFriendid;

    /**
     * 用户ID
     */
    private Integer chUserid;

    /**
     * 聊天时间
     */
    private Date chCreatetime;

    /**
     * 置顶（1：是，0：否）
     */
    private Integer chTop;

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
        ChatHistory other = (ChatHistory) that;
        return (this.getChId() == null ? other.getChId() == null : this.getChId().equals(other.getChId()))
            && (this.getChGroupid() == null ? other.getChGroupid() == null : this.getChGroupid().equals(other.getChGroupid()))
            && (this.getChFriendid() == null ? other.getChFriendid() == null : this.getChFriendid().equals(other.getChFriendid()))
            && (this.getChUserid() == null ? other.getChUserid() == null : this.getChUserid().equals(other.getChUserid()))
            && (this.getChCreatetime() == null ? other.getChCreatetime() == null : this.getChCreatetime().equals(other.getChCreatetime()))
            && (this.getChTop() == null ? other.getChTop() == null : this.getChTop().equals(other.getChTop()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getChId() == null) ? 0 : getChId().hashCode());
        result = prime * result + ((getChGroupid() == null) ? 0 : getChGroupid().hashCode());
        result = prime * result + ((getChFriendid() == null) ? 0 : getChFriendid().hashCode());
        result = prime * result + ((getChUserid() == null) ? 0 : getChUserid().hashCode());
        result = prime * result + ((getChCreatetime() == null) ? 0 : getChCreatetime().hashCode());
        result = prime * result + ((getChTop() == null) ? 0 : getChTop().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", chId=").append(chId);
        sb.append(", chGroupid=").append(chGroupid);
        sb.append(", chFriendid=").append(chFriendid);
        sb.append(", chUserid=").append(chUserid);
        sb.append(", chCreatetime=").append(chCreatetime);
        sb.append(", chTop=").append(chTop);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}