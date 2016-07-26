package org.paradise.model;

/**
 * Created by terrence on 26/07/2016.
 */
public final class CustomerProfileBuilder {

    private String title;
    private String mobile;
    private String bpn;
    private String legalFirstName;
    private String firstName;
    private String lastName;
    private String dateBirth;
    private PostalAddress postalAddress;
    private boolean sms;
    private boolean reachAboveOfSpecialReq;
    private boolean reachBelowOfSpecialReq;
    private boolean notificationTimeSlot;
    private boolean deliveryNotifications;
    private boolean registeredCustomer;
    private boolean marketingPermissions;


    public static CustomerProfileBuilder aCustomerProfile() {
        return new CustomerProfileBuilder();
    }

    public CustomerProfileBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomerProfileBuilder withMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public CustomerProfileBuilder withBpn(String bpn) {
        this.bpn = bpn;
        return this;
    }

    public CustomerProfileBuilder withLegalFirstName(String legalFirstName) {
        this.legalFirstName = legalFirstName;
        return this;
    }

    public CustomerProfileBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public CustomerProfileBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public CustomerProfileBuilder withDateBirth(String dateBirth) {
        this.dateBirth = dateBirth;
        return this;
    }

    public CustomerProfileBuilder withPostalAddress(PostalAddress postalAddress) {
        this.postalAddress = postalAddress;
        return this;
    }

    public CustomerProfileBuilder withSms(boolean sms) {
        this.sms = sms;
        return this;
    }

    public CustomerProfileBuilder withReachAboveOfSpecialReq(boolean reachAboveOfSpecialReq) {
        this.reachAboveOfSpecialReq = reachAboveOfSpecialReq;
        return this;
    }

    public CustomerProfileBuilder withReachBelowOfSpecialReq(boolean reachBelowOfSpecialReq) {
        this.reachBelowOfSpecialReq = reachBelowOfSpecialReq;
        return this;
    }

    public CustomerProfileBuilder withNotificationTimeSlot(boolean notificationTimeSlot) {
        this.notificationTimeSlot = notificationTimeSlot;
        return this;
    }

    public CustomerProfileBuilder withDeliveryNotifications(boolean deliveryNotifications) {
        this.deliveryNotifications = deliveryNotifications;
        return this;
    }

    public CustomerProfileBuilder withRegisteredCustomer(boolean registeredCustomer) {
        this.registeredCustomer = registeredCustomer;
        return this;
    }

    public CustomerProfileBuilder withMarketingPermissions(boolean marketingPermissions) {
        this.marketingPermissions = marketingPermissions;
        return this;
    }

    public CustomerProfile build() {

        CustomerProfile customerProfile = new CustomerProfile();

        customerProfile.setTitle(title);
        customerProfile.setMobile(mobile);
        customerProfile.setBpn(bpn);
        customerProfile.setLegalFirstName(legalFirstName);
        customerProfile.setFirstName(firstName);
        customerProfile.setLastName(lastName);
        customerProfile.setDateBirth(dateBirth);
        customerProfile.setPostalAddress(postalAddress);
        customerProfile.setSms(sms);
        customerProfile.setReachAboveOfSpecialReq(reachAboveOfSpecialReq);
        customerProfile.setReachBelowOfSpecialReq(reachBelowOfSpecialReq);
        customerProfile.setNotificationTimeSlot(notificationTimeSlot);
        customerProfile.setDeliveryNotifications(deliveryNotifications);
        customerProfile.setRegisteredCustomer(registeredCustomer);
        customerProfile.setMarketingPermissions(marketingPermissions);

        return customerProfile;
    }

}
