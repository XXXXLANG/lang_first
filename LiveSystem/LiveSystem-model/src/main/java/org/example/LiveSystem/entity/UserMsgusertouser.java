package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_msgusertouser
 * @author 
 */
@Data
public class UserMsgusertouser implements Serializable {
    private Integer mId;

    /**
     * 发送者
     */
    private Integer mFromuserid;

    /**
     * 接收者
     */
    private Integer mTouserid;

    /**
     * 内容
     */
    private String mMsgcontent;

    /**
     * 接收状态
     */
    private Integer mState;

    /**
     * 发送时间
     */
    private Date mCreatetime;

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
        UserMsgusertouser other = (UserMsgusertouser) that;
        return (this.getMId() == null ? other.getMId() == null : this.getMId().equals(other.getMId()))
            && (this.getMFromuserid() == null ? other.getMFromuserid() == null : this.getMFromuserid().equals(other.getMFromuserid()))
            && (this.getMTouserid() == null ? other.getMTouserid() == null : this.getMTouserid().equals(other.getMTouserid()))
            && (this.getMMsgcontent() == null ? other.getMMsgcontent() == null : this.getMMsgcontent().equals(other.getMMsgcontent()))
            && (this.getMState() == null ? other.getMState() == null : this.getMState().equals(other.getMState()))
            && (this.getMCreatetime() == null ? other.getMCreatetime() == null : this.getMCreatetime().equals(other.getMCreatetime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getMId() == null) ? 0 : getMId().hashCode());
        result = prime * result + ((getMFromuserid() == null) ? 0 : getMFromuserid().hashCode());
        result = prime * result + ((getMTouserid() == null) ? 0 : getMTouserid().hashCode());
        result = prime * result + ((getMMsgcontent() == null) ? 0 : getMMsgcontent().hashCode());
        result = prime * result + ((getMState() == null) ? 0 : getMState().hashCode());
        result = prime * result + ((getMCreatetime() == null) ? 0 : getMCreatetime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", mId=").append(mId);
        sb.append(", mFromuserid=").append(mFromuserid);
        sb.append(", mTouserid=").append(mTouserid);
        sb.append(", mMsgcontent=").append(mMsgcontent);
        sb.append(", mState=").append(mState);
        sb.append(", mCreatetime=").append(mCreatetime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}