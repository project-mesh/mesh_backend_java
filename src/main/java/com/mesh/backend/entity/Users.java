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
public class Users extends Model<Users> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    private String Nickname;

    private String Email;

    private String PasswordDigest;

    private String PasswordSalt;

    private String RememberDigest;

    private String Avatar;

    private String ColorPreference;

    private String LayoutPreference;

    private String RevealedPreference;

    private LocalDateTime CreatedTime;

    private LocalDateTime UpdatedTime;

    private Integer Gender;

    private Integer Status;

    private String Description;

    private String Address;

    private LocalDateTime Birthday;


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

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getColorPreference() {
        return ColorPreference;
    }

    public void setColorPreference(String ColorPreference) {
        this.ColorPreference = ColorPreference;
    }

    public String getLayoutPreference() {
        return LayoutPreference;
    }

    public void setLayoutPreference(String LayoutPreference) {
        this.LayoutPreference = LayoutPreference;
    }

    public String getRevealedPreference() {
        return RevealedPreference;
    }

    public void setRevealedPreference(String RevealedPreference) {
        this.RevealedPreference = RevealedPreference;
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

    public Integer getGender() {
        return Gender;
    }

    public void setGender(Integer Gender) {
        this.Gender = Gender;
    }

    public Integer getStatus() {
        return Status;
    }

    public void setStatus(Integer Status) {
        this.Status = Status;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
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

    @Override
    protected Serializable pkVal() {
        return this.Id;
    }

    @Override
    public String toString() {
        return "Users{" +
        "Id=" + Id +
        ", Nickname=" + Nickname +
        ", Email=" + Email +
        ", PasswordDigest=" + PasswordDigest +
        ", PasswordSalt=" + PasswordSalt +
        ", RememberDigest=" + RememberDigest +
        ", Avatar=" + Avatar +
        ", ColorPreference=" + ColorPreference +
        ", LayoutPreference=" + LayoutPreference +
        ", RevealedPreference=" + RevealedPreference +
        ", CreatedTime=" + CreatedTime +
        ", UpdatedTime=" + UpdatedTime +
        ", Gender=" + Gender +
        ", Status=" + Status +
        ", Description=" + Description +
        ", Address=" + Address +
        ", Birthday=" + Birthday +
        "}";
    }

    public Users(){
        this.CreatedTime = LocalDateTime.now();
        this.UpdatedTime = LocalDateTime.now();
        this.Address = "";
        this.Gender = 0;
        this.Birthday = LocalDateTime.now();
        this.ColorPreference = "blue";
        this.LayoutPreference = "default";
        this.Description = "";
        this.Status = 1;
        this.RevealedPreference = "card";
        this.Avatar = "";
    }
}
