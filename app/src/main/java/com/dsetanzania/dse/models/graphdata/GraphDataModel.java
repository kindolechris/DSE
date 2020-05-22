package com.dsetanzania.dse.models.graphdata;

import com.dsetanzania.dse.models.BaseResponseModel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GraphDataModel  {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("boardshare_id")
    @Expose
    private Integer boardshareId;
    @SerializedName("openingPrice")
    @Expose
    private Integer openingPrice;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("full_date_data")
    @Expose
    private String fullDateData;
    @SerializedName("dayOfMonth")
    @Expose
    private Integer dayOfMonth;
    @SerializedName("dayOfYear")
    @Expose
    private Integer dayOfYear;
    @SerializedName("x")
    @Expose
    private Integer x;
    @SerializedName("y")
    @Expose
    private Integer y;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getBoardshareId() {
        return boardshareId;
    }

    public void setBoardshareId(Integer boardshareId) {
        this.boardshareId = boardshareId;
    }

    public Integer getOpeningPrice() {
        return openingPrice;
    }

    public void setOpeningPrice(Integer openingPrice) {
        this.openingPrice = openingPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFullDateData() {
        return fullDateData;
    }

    public void setFullDateData(String fullDateData) {
        this.fullDateData = fullDateData;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

}
