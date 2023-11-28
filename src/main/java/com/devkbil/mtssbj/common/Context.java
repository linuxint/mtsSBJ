package com.devkbil.mtssbj.common;

import java.io.Serializable;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;

public class Context implements Serializable {
    private static final long serialVersionUID = 3941329442614276449L;
    public static String ACTIVITY_DB_READ = "DB_READ";
    public static String ACTIVITY_DB_WRITE = "DB_WRITE";
    public static String ACTIVITY_MESSAGE = "MESSAGE";
    public static String ACTIVITY_LOOKUP = "LOOKUP";
    public static String ACTIVITY_LOGIC = "LOGIC";
    public static String MONITOR_DB = "MONITOR_DB";
    //private HashMap           contextMap;
    //private HashMap           contextMap = new HashMap();
    @Value("${INSTANCE_ID}")
    protected String instanceId;
    protected String transactionDate;
    protected String transactionTime;
    protected String uid;
    @Getter
    protected String uri;
    @Getter
    protected String userId;
    @Getter
    protected String trxSerNo;
    protected long startTime;
    protected String ssnCrn;
    protected String userType;
    @Getter
    protected String userIp;
    boolean isLogEnabledTF = false;
    @Getter
    private boolean forceShutdown = false;

    public Context() {
        this.transactionDate = getToday("yyyyMMdd");
        this.transactionTime = getToday("HHmmss");
        this.uid = String.valueOf(UUID.randomUUID());

        setTrxSerNo(this.transactionDate + this.transactionTime + this.instanceId + this.uid);
        this.startTime = System.currentTimeMillis();
        //this.contextMap = new HashMap();
    }
    
    /*
    public HashMap getContextMap()
    {
        return this.contextMap;
    }
    */

    private static String getToday(String pOutformat) {
        return DateFormatUtils.format(System.currentTimeMillis(), pOutformat, Locale.KOREA);
    }

    private void setTrxSerNo(String trxSerNo) {
        this.trxSerNo = trxSerNo;
    }
    
    /*
    public String getInstanceId()
    {
        return this.instanceId;
    }
    */
    
    /*
    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }
    */
    
    /*
    public String getTransactionDate()
    {
        return this.transactionDate;
    }
    */

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }
    
    /*
    public String getTransactionTime()
    {
        return this.transactionTime;
    }
    */

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }
    
    /*
    public String getUid()
    {
        return this.uid;
    }
    */
    
    /*
    public void setUid(String uid)
    {
        this.uid = uid;
    }
    */
    
    /*
    public String getTransactionDateTime()
    {
        return this.transactionDate + this.transactionTime;
    }
    */

    /*
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    */
    
    /*
    public String getName()
    {
        if (this.uri != null) {
            return this.uri;
        }
        return this.transactionTime;
    }
    */

    /*
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    */
    
    /*
    public long getStartTime()
    {
        return this.startTime;
    }
    */

    public String toString() {
        long now = System.currentTimeMillis();
        return getUserId() + "|" + getTrxSerNo() + "|" + getUri() + "|" + (now - this.startTime) / 1000.0D + "second past";
    }
    
    /*
    public void setLogEnabled()
    {
        this.isLogEnabled = true;
    }
    */

    public boolean isLogEnabled() {
        return this.isLogEnabledTF;
    }
    
    /*
    public boolean isLogDisabled()
    {
        return !this.isLogEnabled;
    }
    */
    
    /*
    public void setLogEnabled(boolean logEnabled)
    {
        this.isLogEnabled = logEnabled;
    }
    */
    
    /*
    public void setLogDisabled()
    {
        this.isLogEnabled = false;
    }
    */

    /*
    public void setForceShutdown()
    {
        this.forceShutdown = true;
    }
    */

    public void setForceShutdown(boolean forceShutdown) {
        this.forceShutdown = forceShutdown;
    }
    
    /*
    public long getElapsedTime()
    {
        return System.currentTimeMillis() - getStartTime();
    }
    */
    
    /*
    public String getSsnCrn()
    {
        return this.ssnCrn;
    }
    */

    /*
    public void setSsnCrn(String ssnCrn)
    {
        this.ssnCrn = ssnCrn;
    }
    */
    
    /*
    public String getUserType()
    {
        return this.userType;
    }
    */
    
    /*
    public void setUserType(String userType)
    {
        this.userType = userType;
    }
    */

    public void setUserIp(String userIp) {
        this.userIp = userIp;
    }
}
