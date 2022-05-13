package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * friend_request
 * @author 
 */
@Data
public class FriendRequest implements Serializable {
    private Integer frId;

    /**
     * 对象用户
     */
    private Integer frUser;

    /**
     * 对象班群
     */
    private Integer frGroup;

    /**
     * 申请人
     */
    private Integer frApplicant;

    /**
     * 申请简介
     */
    private String frIntro;

    /**
     * 好友昵称
     */
    private String frAliasfriend;

    /**
     * 发送时间
     */
    private Date frCreatetime;

    /**
     * 是否接受（1：接受，0：未读，2：拒绝）
     */
    private Integer frState;

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
        FriendRequest other = (FriendRequest) that;
        return (this.getFrId() == null ? other.getFrId() == null : this.getFrId().equals(other.getFrId()))
            && (this.getFrUser() == null ? other.getFrUser() == null : this.getFrUser().equals(other.getFrUser()))
            && (this.getFrGroup() == null ? other.getFrGroup() == null : this.getFrGroup().equals(other.getFrGroup()))
            && (this.getFrApplicant() == null ? other.getFrApplicant() == null : this.getFrApplicant().equals(other.getFrApplicant()))
            && (this.getFrIntro() == null ? other.getFrIntro() == null : this.getFrIntro().equals(other.getFrIntro()))
            && (this.getFrAliasfriend() == null ? other.getFrAliasfriend() == null : this.getFrAliasfriend().equals(other.getFrAliasfriend()))
            && (this.getFrCreatetime() == null ? other.getFrCreatetime() == null : this.getFrCreatetime().equals(other.getFrCreatetime()))
            && (this.getFrState() == null ? other.getFrState() == null : this.getFrState().equals(other.getFrState()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getFrId() == null) ? 0 : getFrId().hashCode());
        result = prime * result + ((getFrUser() == null) ? 0 : getFrUser().hashCode());
        result = prime * result + ((getFrGroup() == null) ? 0 : getFrGroup().hashCode());
        result = prime * result + ((getFrApplicant() == null) ? 0 : getFrApplicant().hashCode());
        result = prime * result + ((getFrIntro() == null) ? 0 : getFrIntro().hashCode());
        result = prime * result + ((getFrAliasfriend() == null) ? 0 : getFrAliasfriend().hashCode());
        result = prime * result + ((getFrCreatetime() == null) ? 0 : getFrCreatetime().hashCode());
        result = prime * result + ((getFrState() == null) ? 0 : getFrState().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", frId=").append(frId);
        sb.append(", frUser=").append(frUser);
        sb.append(", frGroup=").append(frGroup);
        sb.append(", frApplicant=").append(frApplicant);
        sb.append(", frIntro=").append(frIntro);
        sb.append(", frAliasfriend=").append(frAliasfriend);
        sb.append(", frCreatetime=").append(frCreatetime);
        sb.append(", frState=").append(frState);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}