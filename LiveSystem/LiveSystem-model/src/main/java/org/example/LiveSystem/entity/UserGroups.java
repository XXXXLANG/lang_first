package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user_groups
 * @author 
 */
@Data
public class UserGroups implements Serializable {
    private Integer ugId;

    /**
     * 群名称
     */
    private String ugName;

    /**
     * 创建时间
     */
    private Date ugCreatetime;

    /**
     * 群主
     */
    private Integer ugAdminid;

    /**
     * 群图标
     */
    private String ugIcon;

    /**
     * 班级账号（自动生成）
     */
    private String ugGroupNum;

    /**
     * 公告
     */
    private String ugNotice;

    /**
     * 简介
     */
    private String ugIntro;

    /**
     * 进群验证（1：是，0：否）
     */
    private Integer ugVerification;

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
        UserGroups other = (UserGroups) that;
        return (this.getUgId() == null ? other.getUgId() == null : this.getUgId().equals(other.getUgId()))
            && (this.getUgName() == null ? other.getUgName() == null : this.getUgName().equals(other.getUgName()))
            && (this.getUgGroupNum() == null ? other.getUgGroupNum() == null : this.getUgGroupNum().equals(other.getUgGroupNum()))
            && (this.getUgCreatetime() == null ? other.getUgCreatetime() == null : this.getUgCreatetime().equals(other.getUgCreatetime()))
            && (this.getUgAdminid() == null ? other.getUgAdminid() == null : this.getUgAdminid().equals(other.getUgAdminid()))
            && (this.getUgIcon() == null ? other.getUgIcon() == null : this.getUgIcon().equals(other.getUgIcon()))
            && (this.getUgNotice() == null ? other.getUgNotice() == null : this.getUgNotice().equals(other.getUgNotice()))
            && (this.getUgIntro() == null ? other.getUgIntro() == null : this.getUgIntro().equals(other.getUgIntro()))
            && (this.getUgVerification() == null ? other.getUgVerification() == null : this.getUgVerification().equals(other.getUgVerification()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUgId() == null) ? 0 : getUgId().hashCode());
        result = prime * result + ((getUgName() == null) ? 0 : getUgName().hashCode());
        result = prime * result + ((getUgGroupNum() == null) ? 0 : getUgGroupNum().hashCode());
        result = prime * result + ((getUgCreatetime() == null) ? 0 : getUgCreatetime().hashCode());
        result = prime * result + ((getUgAdminid() == null) ? 0 : getUgAdminid().hashCode());
        result = prime * result + ((getUgIcon() == null) ? 0 : getUgIcon().hashCode());
        result = prime * result + ((getUgNotice() == null) ? 0 : getUgNotice().hashCode());
        result = prime * result + ((getUgIntro() == null) ? 0 : getUgIntro().hashCode());
        result = prime * result + ((getUgVerification() == null) ? 0 : getUgVerification().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", ugId=").append(ugId);
        sb.append(", ugName=").append(ugName);
        sb.append(", ugGroupNum=").append(ugGroupNum);
        sb.append(", ugCreatetime=").append(ugCreatetime);
        sb.append(", ugAdminid=").append(ugAdminid);
        sb.append(", ugIcon=").append(ugIcon);
        sb.append(", ugNotice=").append(ugNotice);
        sb.append(", ugIntro=").append(ugIntro);
        sb.append(", ugVerification=").append(ugVerification);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}