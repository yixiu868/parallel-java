package com.ww.actual.project.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
public class CheckBillTaskDto implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6811943231456056322L;

    /** 记录编号 */
    private String taskId;

    /** 账单时间 */
    private String payTradeTime;

    /** 受理机构编号 */
    private String channelId;

    /** 支付中心编号 */
    private Integer payCenterId;

    /** 第三方商户号 */
    private String partner;

    /** 对账下载路径 */
    private String billPath;

    /** 第三方本地路径 */
    private String billLocalPath;

    /** 对账任务状态 */
    private String checkStatus;

    /** 对账开始时间 */
    private java.util.Date startTime;

    /** 对账结束时间 */
    private java.util.Date endTime;

    /** 是否失效 */
//	private Integer physicsFlagName;

    /** 交易开始时间 */
    private Date payStartTime;

    /** 交易结束时间 */
    private Date payEndTime;

    /** 对账结果 */
    private Integer checkBillResult;
    /**
     * 对账方式
     */
    private Integer checkWay;

    //以下为重构版本新增属性 20180521

    /** 接口提供方 */
    private Integer apiProvider;

    /** 下载进度状态 */
    private Integer downStatus;

    /** 下载FTP信息 */
    private String downLoadMessage;

    /** 下载进度状态集 */
    private Set<Integer> downStatusSet;

    /** 下载方式 */
    private Integer downLoadWay;

    /** 下载开始时间 */
    private Date downBeginTime;

    /** 下载结束时间 */
    private Date downEndTime;

    /** APPID */
    private String appid;

    /** 签名key */
    private String siginKey;

    /** 对账备注 */
    private String taskDesc;

    /** 下载对账单备注 */
    private String billDesc;

    private StringBuffer errorString = new StringBuffer();
    private StringBuffer tipString = new StringBuffer();

    // 公有云标记
    private boolean isPrivateCloud;

    /** 结算方 */
    private Integer accOrg;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPayTradeTime() {
        return payTradeTime;
    }

    public void setPayTradeTime(String payTradeTime) {
        this.payTradeTime = payTradeTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public Integer getPayCenterId() {
        return payCenterId;
    }

    public void setPayCenterId(Integer payCenterId) {
        this.payCenterId = payCenterId;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getBillPath() {
        return billPath;
    }

    public void setBillPath(String billPath) {
        this.billPath = billPath;
    }

    public String getBillLocalPath() {
        return billLocalPath;
    }

    public void setBillLocalPath(String billLocalPath) {
        this.billLocalPath = billLocalPath;
    }

    public String getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(String checkStatus) {
        this.checkStatus = checkStatus;
    }

    public java.util.Date getStartTime() {
        return startTime;
    }

    public void setStartTime(java.util.Date startTime) {
        this.startTime = startTime;
    }

    public java.util.Date getEndTime() {
        return endTime;
    }

    public void setEndTime(java.util.Date endTime) {
        this.endTime = endTime;
    }

    public Date getPayStartTime() {
        return payStartTime;
    }

    public void setPayStartTime(Date payStartTime) {
        this.payStartTime = payStartTime;
    }

    public Date getPayEndTime() {
        return payEndTime;
    }

    public void setPayEndTime(Date payEndTime) {
        this.payEndTime = payEndTime;
    }

    public Integer getCheckBillResult() {
        return checkBillResult;
    }

    public void setCheckBillResult(Integer checkBillResult) {
        this.checkBillResult = checkBillResult;
    }

    public Integer getCheckWay() {
        return checkWay;
    }

    public void setCheckWay(Integer checkWay) {
        this.checkWay = checkWay;
    }

    public Integer getApiProvider() {
        return apiProvider;
    }

    public void setApiProvider(Integer apiProvider) {
        this.apiProvider = apiProvider;
    }

    public Integer getDownStatus() {
        return downStatus;
    }

    public void setDownStatus(Integer downStatus) {
        this.downStatus = downStatus;
    }

    public String getDownLoadMessage() {
        return downLoadMessage;
    }

    public void setDownLoadMessage(String downLoadMessage) {
        this.downLoadMessage = downLoadMessage;
    }

    public Set<Integer> getDownStatusSet() {
        return downStatusSet;
    }

    public void setDownStatusSet(Set<Integer> downStatusSet) {
        this.downStatusSet = downStatusSet;
    }

    public Integer getDownLoadWay() {
        return downLoadWay;
    }

    public void setDownLoadWay(Integer downLoadWay) {
        this.downLoadWay = downLoadWay;
    }

    public Date getDownBeginTime() {
        return downBeginTime;
    }

    public void setDownBeginTime(Date downBeginTime) {
        this.downBeginTime = downBeginTime;
    }

    public Date getDownEndTime() {
        return downEndTime;
    }

    public void setDownEndTime(Date downEndTime) {
        this.downEndTime = downEndTime;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSiginKey() {
        return siginKey;
    }

    public void setSiginKey(String siginKey) {
        this.siginKey = siginKey;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getBillDesc() {
        return billDesc;
    }

    public void setBillDesc(String billDesc) {
        this.billDesc = billDesc;
    }

    public StringBuffer getErrorString() {
        return errorString;
    }

    public void setErrorString(StringBuffer errorString) {
        this.errorString = errorString;
    }

    public StringBuffer getTipString() {
        return tipString;
    }

    public void setTipString(StringBuffer tipString) {
        this.tipString = tipString;
    }

    public boolean isPrivateCloud() {
        return isPrivateCloud;
    }

    public void setPrivateCloud(boolean isPrivateCloud) {
        this.isPrivateCloud = isPrivateCloud;
    }

    public Integer getAccOrg() {
        return accOrg;
    }

    public void setAccOrg(Integer accOrg) {
        this.accOrg = accOrg;
    }
}
