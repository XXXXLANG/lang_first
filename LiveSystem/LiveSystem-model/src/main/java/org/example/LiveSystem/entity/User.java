package org.example.LiveSystem.entity;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * user
 * @author 
 */
@Data
public class User implements Serializable {
    private Integer uId;

    /**
     * 账号
     */
    private String uLoginid;

    /**
     * 昵称
     */
    private String uNickname;

    /**
     * 密码
     */
    private String uPassword;

    /**
     * 个性签名
     */
    private String uSignature;

    /**
     * 性别（1：男，0：女）
     */
    private Integer uSex;

    /**
     * 生日
     */
    private Date uBirthday;

    /**
     * 学号
     */
    private String uSno;

    /**
     * 电话
     */
    private String uTelephone;

    /**
     * 邮箱
     */
    private String uEmail;

    /**
     * 头像
     */
    private String uHeadportrait;

    /**
     * 学校
     */
    private Integer uSchooltag;

    /**
     * 学校职位（也包括“学生”）
     */
    private Integer uPosition;

    /**
     * 国家
     */
    private Integer uNationid;

    /**
     * 省份
     */
    private Integer uProvinceid;

    /**
     * 城市
     */
    private Integer uCityid;

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
        User other = (User) that;
        return (this.getUId() == null ? other.getUId() == null : this.getUId().equals(other.getUId()))
            && (this.getULoginid() == null ? other.getULoginid() == null : this.getULoginid().equals(other.getULoginid()))
            && (this.getUNickname() == null ? other.getUNickname() == null : this.getUNickname().equals(other.getUNickname()))
            && (this.getUPassword() == null ? other.getUPassword() == null : this.getUPassword().equals(other.getUPassword()))
            && (this.getUSignature() == null ? other.getUSignature() == null : this.getUSignature().equals(other.getUSignature()))
            && (this.getUSex() == null ? other.getUSex() == null : this.getUSex().equals(other.getUSex()))
            && (this.getUBirthday() == null ? other.getUBirthday() == null : this.getUBirthday().equals(other.getUBirthday()))
            && (this.getUSno() == null ? other.getUSno() == null : this.getUSno().equals(other.getUSno()))
            && (this.getUTelephone() == null ? other.getUTelephone() == null : this.getUTelephone().equals(other.getUTelephone()))
            && (this.getUEmail() == null ? other.getUEmail() == null : this.getUEmail().equals(other.getUEmail()))
            && (this.getUHeadportrait() == null ? other.getUHeadportrait() == null : this.getUHeadportrait().equals(other.getUHeadportrait()))
            && (this.getUSchooltag() == null ? other.getUSchooltag() == null : this.getUSchooltag().equals(other.getUSchooltag()))
            && (this.getUPosition() == null ? other.getUPosition() == null : this.getUPosition().equals(other.getUPosition()))
            && (this.getUNationid() == null ? other.getUNationid() == null : this.getUNationid().equals(other.getUNationid()))
            && (this.getUProvinceid() == null ? other.getUProvinceid() == null : this.getUProvinceid().equals(other.getUProvinceid()))
            && (this.getUCityid() == null ? other.getUCityid() == null : this.getUCityid().equals(other.getUCityid()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getUId() == null) ? 0 : getUId().hashCode());
        result = prime * result + ((getULoginid() == null) ? 0 : getULoginid().hashCode());
        result = prime * result + ((getUNickname() == null) ? 0 : getUNickname().hashCode());
        result = prime * result + ((getUPassword() == null) ? 0 : getUPassword().hashCode());
        result = prime * result + ((getUSignature() == null) ? 0 : getUSignature().hashCode());
        result = prime * result + ((getUSex() == null) ? 0 : getUSex().hashCode());
        result = prime * result + ((getUBirthday() == null) ? 0 : getUBirthday().hashCode());
        result = prime * result + ((getUSno() == null) ? 0 : getUSno().hashCode());
        result = prime * result + ((getUTelephone() == null) ? 0 : getUTelephone().hashCode());
        result = prime * result + ((getUEmail() == null) ? 0 : getUEmail().hashCode());
        result = prime * result + ((getUHeadportrait() == null) ? 0 : getUHeadportrait().hashCode());
        result = prime * result + ((getUSchooltag() == null) ? 0 : getUSchooltag().hashCode());
        result = prime * result + ((getUPosition() == null) ? 0 : getUPosition().hashCode());
        result = prime * result + ((getUNationid() == null) ? 0 : getUNationid().hashCode());
        result = prime * result + ((getUProvinceid() == null) ? 0 : getUProvinceid().hashCode());
        result = prime * result + ((getUCityid() == null) ? 0 : getUCityid().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", uId=").append(uId);
        sb.append(", uLoginid=").append(uLoginid);
        sb.append(", uNickname=").append(uNickname);
        sb.append(", uPassword=").append(uPassword);
        sb.append(", uSignature=").append(uSignature);
        sb.append(", uSex=").append(uSex);
        sb.append(", uBirthday=").append(uBirthday);
        sb.append(", uSno=").append(uSno);
        sb.append(", uTelephone=").append(uTelephone);
        sb.append(", uEmail=").append(uEmail);
        sb.append(", uHeadportrait=").append(uHeadportrait);
        sb.append(", uSchooltag=").append(uSchooltag);
        sb.append(", uPosition=").append(uPosition);
        sb.append(", uNationid=").append(uNationid);
        sb.append(", uProvinceid=").append(uProvinceid);
        sb.append(", uCityid=").append(uCityid);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}