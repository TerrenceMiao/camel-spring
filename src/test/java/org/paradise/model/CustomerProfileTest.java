package org.paradise.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by terrence on 26/07/2016.
 */
public class CustomerProfileTest {

    private String title = "Mr.";
    private String mobile = "0412 123 456";
    private String bpn = "1000012345";
    private String legalFirstName = "T T";
    private String firstName = "Terrence";
    private String lastName = "Miao";
    private String dateBirth = "1980/10/01";

    private PostalAddress postalAddress = createPostalAddress();

    private boolean sms = Boolean.FALSE;
    private boolean reachAboveOfSpecialReq = Boolean.FALSE;
    private boolean reachBelowOfSpecialReq = Boolean.FALSE;
    private boolean notificationTimeSlot = Boolean.TRUE;
    private boolean deliveryNotifications = Boolean.TRUE;
    private boolean registeredCustomer = Boolean.TRUE;
    private boolean marketingPermissions = Boolean.FALSE;

    @Test
    public void testCustomerProfileBuilder() throws Exception {

        CustomerProfile customerProfile = CustomerProfileBuilder.aCustomerProfile()
                .withTitle(title)
                .withMobile(mobile)
                .withBpn(bpn)
                .withLegalFirstName(legalFirstName)
                .withFirstName(firstName)
                .withLastName(lastName)
                .withDateBirth(dateBirth)
                .withPostalAddress(postalAddress)
                .withSms(sms)
                .withReachAboveOfSpecialReq(reachAboveOfSpecialReq)
                .withReachBelowOfSpecialReq(reachBelowOfSpecialReq)
                .withNotificationTimeSlot(notificationTimeSlot)
                .withDeliveryNotifications(deliveryNotifications)
                .withRegisteredCustomer(registeredCustomer)
                .withMarketingPermissions(marketingPermissions)
                .build();

        assertEquals(title, customerProfile.getTitle());
        assertEquals(mobile, customerProfile.getMobile());
        assertEquals(bpn, customerProfile.getBpn());
        assertEquals(legalFirstName, customerProfile.getLegalFirstName());
        assertEquals(firstName, customerProfile.getFirstName());
        assertEquals(lastName, customerProfile.getLastName());
        assertEquals(dateBirth, customerProfile.getDateBirth());
        assertEquals(postalAddress, customerProfile.getPostalAddress());
        assertEquals(sms, customerProfile.isSms());
        assertEquals(reachAboveOfSpecialReq, customerProfile.isReachAboveOfSpecialReq());
        assertEquals(reachBelowOfSpecialReq, customerProfile.isReachBelowOfSpecialReq());
        assertEquals(notificationTimeSlot, customerProfile.isDeliveryNotifications());
        assertEquals(deliveryNotifications, customerProfile.isDeliveryNotifications());
        assertEquals(registeredCustomer, customerProfile.isRegisteredCustomer());
        assertEquals(marketingPermissions, customerProfile.isMarketingPermissions());
    }

    private PostalAddress createPostalAddress() {

        PostalAddress postalAddress = new PostalAddress();

        return postalAddress;
    }

}