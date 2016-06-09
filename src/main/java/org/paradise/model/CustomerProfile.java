package org.paradise.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by terrence on 9/06/2016.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerProfile {

    @JsonProperty("title")
    private String title;

    @JsonProperty("mobile")
    private String mobile;

    @JsonProperty("bpn")
    private String bpn;

    @JsonProperty("legalFirstName")
    private String legalFirstName;
    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("dateBirth")
    private String dateBirth;

    @JsonProperty("postalAddress")
    private PostalAddress postalAddress;

    @JsonProperty("sms")
    private boolean sms;
    @JsonProperty("reachAboveOfSpecialReq")
    private boolean reachAboveOfSpecialReq;
    @JsonProperty("reachBelowOfSpecialReq")
    private boolean reachBelowOfSpecialReq;
    @JsonProperty("notificationTimeSlot")
    private boolean notificationTimeSlot;
    @JsonProperty("deliveryNotifications")
    private boolean deliveryNotifications;
    @JsonProperty("registeredCustomer")
    private boolean registeredCustomer;
    @JsonProperty("marketingPermissions")
    private boolean marketingPermissions;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBpn() {
        return bpn;
    }

    public void setBpn(String bpn) {
        this.bpn = bpn;
    }

    public String getLegalFirstName() {
        return legalFirstName;
    }

    public void setLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
    }

    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    public void setPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isReachAboveOfSpecialReq() {
        return reachAboveOfSpecialReq;
    }

    public void setReachAboveOfSpecialReq(boolean reachAboveOfSpecialReq) {
        this.reachAboveOfSpecialReq = reachAboveOfSpecialReq;
    }

    public boolean isReachBelowOfSpecialReq() {
        return reachBelowOfSpecialReq;
    }

    public void setReachBelowOfSpecialReq(boolean reachBelowOfSpecialReq) {
        this.reachBelowOfSpecialReq = reachBelowOfSpecialReq;
    }

    public boolean isNotificationTimeSlot() {
        return notificationTimeSlot;
    }

    public void setNotificationTimeSlot(boolean notificationTimeSlot) {
        this.notificationTimeSlot = notificationTimeSlot;
    }

    public boolean isDeliveryNotifications() {
        return deliveryNotifications;
    }

    public void setDeliveryNotifications(boolean deliveryNotifications) {
        this.deliveryNotifications = deliveryNotifications;
    }

    public boolean isRegisteredCustomer() {
        return registeredCustomer;
    }

    public void setRegisteredCustomer(boolean registeredCustomer) {
        this.registeredCustomer = registeredCustomer;
    }

    public boolean isMarketingPermissions() {
        return marketingPermissions;
    }

    public void setMarketingPermissions(boolean marketingPermissions) {
        this.marketingPermissions = marketingPermissions;
    }

}
