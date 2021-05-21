package com.mesh.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author aodethri
 * @since 2020-12-05
 */
public class Admins extends Model<Admins> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Nickname;

    private String Email;

    private String PasswordDigest;

    private String PasswordSalt;

    private String RememberDigest;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;

    private String Description;

    private Integer Status;

    private String Address;

    private LocalDateTime Birthday;

    private Integer Gender;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getNickname() {
        return Nickname;
    }

    public void setNickname(String Nickname) {
        this.Nickname = Nickname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPasswordDigest() {
        return PasswordDigest;
    }

    public void setPasswordDigest(String PasswordDigest) {
        this.PasswordDigest = PasswordDigest;
    }

    public String getPasswordSalt() {
        return PasswordSalt;
    }

    public void setPasswordSalt(String PasswordSalt) {
        this.PasswordSalt = PasswordSalt;
    }

    public String getRememberDigest() {
        return RememberDigest;
    }

    public void setRememberDigest(String RememberDigest) {
        this.RememberDigest = RememberDigest;
    }

    public LocalDateTime getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(LocalDateTime CreatedTime) {
        this.CreatedTime = CreatedTime;
    }

    public LocalDateTime getUpdatedTime() {
        return UpdatedTime;
    }

    public void setUpdatedTime(LocalDateTime UpdatedTime) {
        this.UpdatedTime = UpdatedTime;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public LocalDateTime getBirthday() {
        return Birthday;
    }

    public void setBirthday(LocalDateTime Birthday) {
        this.Birthday = Birthday;
    }

    public Integer getGender() {
        return Gender;
    }

    public void setGender(Integer Gender) {
        this.Gender = Gender;
    }

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "Admins{" +
        "Id=" + Id +
        ", Nickname=" + Nickname +
        ", Email=" + Email +
        ", PasswordDigest=" + PasswordDigest +
        ", PasswordSalt=" + PasswordSalt +
        ", RememberDigest=" + RememberDigest +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        ", Description=" + Description +
        ", Status=" + Status +
        ", Address=" + Address +
        ", Birthday=" + Birthday +
        ", Gender=" + Gender +
        "}";
    }
}
