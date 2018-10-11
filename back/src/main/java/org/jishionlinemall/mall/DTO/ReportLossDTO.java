package org.jishionlinemall.mall.DTO;

public class ReportLossDTO {
    private String openId;
    private String lossStatus;//挂失操作
    private String newCardStatus;//补办新卡操作
    private String errMsg;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getLossStatus() {
        return lossStatus;
    }

    public void setLossStatus(String lossStatus) {
        this.lossStatus = lossStatus;
    }

    public String getNewCardStatus() {
        return newCardStatus;
    }

    public void setNewCardStatus(String newCardStatus) {
        this.newCardStatus = newCardStatus;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
