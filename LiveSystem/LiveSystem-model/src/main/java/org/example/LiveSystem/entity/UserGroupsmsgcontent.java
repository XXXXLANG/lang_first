package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_groupsmsgcontent
 * @author 
 */
@Data
public class UserGroupsmsgcontent implements Serializable {
    private Integer gmId;

    /**
     * 发送者ID
     */
    private Integer gmFromid;

    /**
     * 消息内容
     */
    private String gmContent;

    /**
     * 发送时间
     */
    private Date gmCreatetime;

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
        UserGroupsmsgcontent other = (UserGroupsmsgcontent) that;
        return (this.getGmId() == null ? other.getGmId() == null : this.getGmId().equals(other.getGmId()))
            && (this.getGmFromid() == null ? other.getGmFromid() == null : this.getGmFromid().equals(other.getGmFromid()))
            && (this.getGmContent() == null ? other.getGmContent() == null : this.getGmContent().equals(other.getGmContent()))
            && (this.getGmCreatetime() == null ? other.getGmCreatetime() == null : this.getGmCreatetime().equals(other.getGmCreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getGmId() == null) ? 0 : getGmId().hashCode());
        result = prime * result + ((getGmFromid() == null) ? 0 : getGmFromid().hashCode());
        result = prime * result + ((getGmContent() == null) ? 0 : getGmContent().hashCode());
        result = prime * result + ((getGmCreatetime() == null) ? 0 : getGmCreatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", gmId=").append(gmId);
        sb.append(", gmFromid=").append(gmFromid);
        sb.append(", gmContent=").append(gmContent);
        sb.append(", gmCreatetime=").append(gmCreatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}