package id.creatodidak.djaga_swara.API.Models;

import com.google.gson.annotations.SerializedName;

public class UpdateApp {
    @SerializedName("id")
    private int id;

    @SerializedName("app_id")
    private String appId;

    @SerializedName("versionCode")
    private int versionCode;

    @SerializedName("ver_name")
    private String versionName;

    @SerializedName("desc")
    private String description;

    @SerializedName("path")
    private String path;

    @SerializedName("link")
    private String link;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("updated_at")
    private String updatedAt;

    public UpdateApp(int id, String appId, int versionCode, String versionName, String description, String path, String link, String createdAt, String updatedAt) {
        this.id = id;
        this.appId = appId;
        this.versionCode = versionCode;
        this.versionName = versionName;
        this.description = description;
        this.path = path;
        this.link = link;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
