package org.paradise.controllers;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.Barcode128;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import org.paradise.Constants;
import org.paradise.itext.BarcodeQRCode;
import org.paradise.model.CustomerProfile;
import org.paradise.model.PostalAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by terrence on 9/06/2016.
 */
@RestController
public class PdfController {

    private static final Logger LOG = LoggerFactory.getLogger(PdfController.class);

    // PDF template fields
    private static final String ACCOUNT_TYPE_FIELD = "T1";
    private static final String MYPOST_NUMBER_FIELD = "T2";
    private static final String FIRST_NAME_FIELD = "T3";
    private static final String MIDDLE_NAME_FIELD = "T4";
    private static final String SURNAME_FIELD = "T5";
    private static final String RESIDENTIAL_ADDRESS_LINE1_FIELD = "T6";
    private static final String RESIDENTIAL_ADDRESS_LINE2_FIELD = "T7";
    private static final String RESIDENTIAL_SUBURB_FIELD = "T8";
    private static final String RESIDENTIAL_STATE_FIELD = "T9";
    private static final String RESIDENTIAL_POSTCODE_FIELD = "T10";
    private static final String POSTAL_ADDRESS_AS_ABOVE_FIELD = "T11";
    private static final String POSTAL_ADDRESS_LINE1_FIELD = "T12";
    private static final String POSTAL_ADDRESS_LINE2_FIELD = "T13";
    private static final String POSTAL_SUBURB_FIELD = "T14";
    private static final String POSTAL_STATE_FIELD = "T15";
    private static final String POSTAL_POSTCODE_FIELD = "T16";
    private static final String DATE_OF_BIRTH_FIELD = "T17";
    private static final String MOBILE_NUMBER_FIELD = "T18";

    private static final String EMAIL_ADDRESS_FIELD = "T19";

    private static final String MARKETING_OPT_IN_FIELD = "T20";
    private static final String CONCESSION_CARD_FIELD = "T21";

    private static final String ASTERISK = "*";
    private static final String WEBPOS_TRANSACTIION = "1502";
    // MyPost version 1.5
    private static final String MYPOST_FORM_VERSION = "A";

    private static final int BARCODE_PAGE_NUMBER = 1;
    private static final int BLANK_PAGE_NUMBER = 2;
    private static final int APPENDED_PAGE_NUMBER = 4;

    private static final int BARCODE_POSITION_X = 440;
    private static final int BARCODE_POSITION_Y = 780;
    private static final int BARCODE_SCALE_PERCENTAGE = 100;

    @Value("${pdf.template.verification}")
    private String pdfTemplate;
    @Value("${pdf.sample.a4}")
    private String pdfSample;

    @Autowired
    ServletContext servletContext;


    @RequestMapping(value = Constants.PDF_PATH + "/verification/{userId}", method = RequestMethod.POST, consumes = APPLICATION_JSON_VALUE)
    public void getVerificationPdf(HttpServletResponse response,
                                   @RequestBody @Valid CustomerProfile consumerProfile, @PathVariable("userId") String userId)
            throws IOException, DocumentException {

        LOG.debug("Generate PDF with template [" + pdfTemplate + "] with userId [" + userId+ "]");

        // Return a PDF file
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + userId + ".pdf");

        PdfReader pdfReader = new PdfReader(servletContext.getRealPath(pdfTemplate));
        PdfStamper pdfStamper = new PdfStamper(pdfReader, response.getOutputStream());

        // Disabled for production
        inspect(pdfReader, pdfStamper);

        // create barcode on the form
        generateBarcode(pdfStamper, userId);

        // fill form data
        fill(pdfStamper, consumerProfile);

        // Insert a new page after FIRST page
        pdfStamper.insertPage(BLANK_PAGE_NUMBER, PageSize.A4);

        // Add some text into new page
        PdfContentByte pdfContentByte;
        BaseFont baseFont = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);

        pdfContentByte = pdfStamper.getOverContent(BLANK_PAGE_NUMBER);
        pdfContentByte.beginText();
        pdfContentByte.setFontAndSize(baseFont, 18);
        pdfContentByte.showTextAligned(Element.ALIGN_LEFT, "This Page Intentionally Left Blank", 170, 500, 0);
        pdfContentByte.endText();

        // Append a PDF file to existing PDF
        LOG.debug("Append PDF [" + pdfSample + "] to existing template [" + pdfTemplate + "] with userId [" + userId+ "]");

        PdfReader pdfSampleReader = new PdfReader(servletContext.getRealPath(pdfSample));

        // Move to page no. 4
        pdfStamper.insertPage(APPENDED_PAGE_NUMBER, PageSize.A4);
        pdfContentByte = pdfStamper.getOverContent(APPENDED_PAGE_NUMBER);

        // Import only ONE page
        PdfImportedPage pdfImportedPage = pdfStamper.getImportedPage(pdfSampleReader, 1);
        pdfContentByte.addTemplate(pdfImportedPage, 0, 0);

        // create a QR Code on the page
        generateQRCode(pdfStamper);

        pdfStamper.close();
        pdfReader.close();

        // Flushes output stream and forces buffered output bytes to be written out
        response.getOutputStream().flush();
    }

    /**
     * Inspect PDF, find input / editable fields in the interactive form
     *
     * @param pdfReader
     * @param pdfStamper
     */
    private void inspect(PdfReader pdfReader, PdfStamper pdfStamper) {

        // Get the fields from the reader (read-only!!!)
        AcroFields form = pdfReader.getAcroFields();

        // Loop over the fields and get info about them
        Set<String> fields = form.getFields().keySet();

        for (String field : fields) {
            switch (form.getFieldType(field)) {
                case AcroFields.FIELD_TYPE_CHECKBOX:
                    LOG.debug("[" + field + "] : Check box");
                    break;
                case AcroFields.FIELD_TYPE_COMBO:
                    LOG.debug("[" + field + "] : Combo box");
                    break;
                case AcroFields.FIELD_TYPE_LIST:
                    LOG.debug("[" + field + "] : List");
                    break;
                case AcroFields.FIELD_TYPE_NONE:
                    LOG.debug("[" + field + "] : None");
                    break;
                case AcroFields.FIELD_TYPE_PUSHBUTTON:
                    LOG.debug("[" + field + "] : Push button");
                    break;
                case AcroFields.FIELD_TYPE_RADIOBUTTON:
                    LOG.debug("[" + field + "] : Radio button");
                    break;
                case AcroFields.FIELD_TYPE_SIGNATURE:
                    LOG.debug("[" + field + "] : Signature");
                    break;
                case AcroFields.FIELD_TYPE_TEXT:
                    LOG.debug("[" + field + "] : Text");
                    break;
                default:
                    LOG.debug("[" + field + "] : ?");
            }

            findPossibleValues(pdfStamper, field);
        }
    }

    /**
     * find out input field name in the interactive form
     *
     * @param pdfStamper
     * @param field
     */
    private void findPossibleValues(PdfStamper pdfStamper, String field) {

        String[] states;
        String values = "";

        AcroFields form = pdfStamper.getAcroFields();

        states = form.getAppearanceStates(field);

        for (int i = 0; i < states.length; i++) {
            values += states[i] + ", ";
        }

        LOG.debug("Possible values for [" + field + "] : " + values);
    }

    /**
     * Generate barcode in the form
     *
     * @param pdfStamper
     * @throws DocumentException
     */
    private void generateBarcode(PdfStamper pdfStamper, String userId) throws DocumentException {

        // add barcode on the first page
        PdfContentByte cb = pdfStamper.getOverContent(BARCODE_PAGE_NUMBER);

        // barcode format 128C
        Barcode128 code128 = new Barcode128();

        // barcode format e.g. *1502A1234567890
        //  asterisk - * [constant]
        //  WebPOS Transaction - 1502 [constant]
        //  Form Version - A [constant for MyPost 1.5]
        //  10-digit APCN
        code128.setCode(ASTERISK + WEBPOS_TRANSACTIION + MYPOST_FORM_VERSION + userId);

        code128.setCodeType(Barcode128.CODE128);

        // convert barcode into image
        Image code128Image = code128.createImageWithBarcode(cb, null, null);

        // set barcode position x pixel, y pixel
        code128Image.setAbsolutePosition(BARCODE_POSITION_X, BARCODE_POSITION_Y);
        code128Image.scalePercent(BARCODE_SCALE_PERCENTAGE);

        // add barcode image into PDF template
        cb.addImage(code128Image);
    }

    /**
     * Generate a QR code including URL on the page
     * 
     * @param pdfStamper
     * @throws DocumentException
     */
    private void generateQRCode(PdfStamper pdfStamper) throws DocumentException {

        // add barcode on the first page
        PdfContentByte pdfContentByte = pdfStamper.getOverContent(APPENDED_PAGE_NUMBER);

        BarcodeQRCode qrcode = new BarcodeQRCode("http://www.vendian.org/mncharity/dir3/paper_rulers/", 1, 1, null);
        Image qrcodeImage = qrcode.getImage();
        qrcodeImage.setAbsolutePosition(400,600);
        qrcodeImage.scalePercent(400);
        pdfContentByte.addImage(qrcodeImage);
    }


    /**
     * Fill data into interactive form input fields
     *
     * @param pdfStamper
     * @throws IOException
     * @throws DocumentException
     */
    private void fill(PdfStamper pdfStamper, CustomerProfile customerProfile) throws IOException, DocumentException {

        AcroFields form = pdfStamper.getAcroFields();

        // It's always a New Account
        // Valid values "1", "2", "3", "Off"
        form.setField(ACCOUNT_TYPE_FIELD, "1");
        // lock Account Type as READ ONLY
        form.setFieldProperty(ACCOUNT_TYPE_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setField(MYPOST_NUMBER_FIELD, customerProfile.getBpn());
        // lock APCN as READ ONLY
        form.setFieldProperty(MYPOST_NUMBER_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);


        form.setField(FIRST_NAME_FIELD, StringUtils.capitalize(customerProfile.getFirstName()));
        form.setFieldProperty(FIRST_NAME_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setField(SURNAME_FIELD, StringUtils.capitalize(customerProfile.getLastName()));
        form.setFieldProperty(SURNAME_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        // Get customer registered residential address
        PostalAddress postalAddress = customerProfile.getPostalAddress();

        form.setField(RESIDENTIAL_ADDRESS_LINE1_FIELD, StringUtils.capitalize(postalAddress.getAddressLine1()));
        form.setFieldProperty(RESIDENTIAL_ADDRESS_LINE1_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        String addressLine2 = StringUtils.isEmpty(postalAddress.getAddressLine2()) ? "" : postalAddress.getAddressLine2();
        addressLine2 += StringUtils.isEmpty(postalAddress.getAddressLine3()) ? "" : " " + postalAddress.getAddressLine3();
        form.setField(RESIDENTIAL_ADDRESS_LINE2_FIELD, StringUtils.capitalize(addressLine2));
        form.setFieldProperty(RESIDENTIAL_ADDRESS_LINE2_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setField(RESIDENTIAL_SUBURB_FIELD, StringUtils.capitalize(postalAddress.getSuburb()));
        form.setFieldProperty(RESIDENTIAL_SUBURB_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setField(RESIDENTIAL_STATE_FIELD, StringUtils.capitalize(postalAddress.getState()));
        form.setFieldProperty(RESIDENTIAL_STATE_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setField(RESIDENTIAL_POSTCODE_FIELD, postalAddress.getPostCode());
        form.setFieldProperty(RESIDENTIAL_POSTCODE_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        // Postal address always as residential address
        // Valid values "1", "Off"
        form.setField(POSTAL_ADDRESS_AS_ABOVE_FIELD, "1");
        form.setFieldProperty(POSTAL_ADDRESS_AS_ABOVE_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        // Leave postal address blank
        form.setField(POSTAL_ADDRESS_LINE1_FIELD, postalAddress.getAddressLine1());
        form.setField(POSTAL_ADDRESS_LINE2_FIELD, addressLine2);
        form.setField(POSTAL_SUBURB_FIELD, postalAddress.getSuburb());
        form.setField(POSTAL_STATE_FIELD, postalAddress.getState());
        form.setField(POSTAL_POSTCODE_FIELD, postalAddress.getPostCode());

        form.setField(DATE_OF_BIRTH_FIELD, customerProfile.getDateBirth());
        form.setFieldProperty(DATE_OF_BIRTH_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        if (null != customerProfile.getMobile()) {
            form.setField(MOBILE_NUMBER_FIELD, customerProfile.getMobile());
        }
        form.setFieldProperty(MOBILE_NUMBER_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setField(EMAIL_ADDRESS_FIELD, StringUtils.capitalize(customerProfile.getPostalAddress().getEmail()));
        form.setFieldProperty(EMAIL_ADDRESS_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        form.setFieldProperty(MIDDLE_NAME_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty(POSTAL_ADDRESS_LINE1_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty(POSTAL_ADDRESS_LINE2_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty(POSTAL_SUBURB_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty(POSTAL_STATE_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty(POSTAL_POSTCODE_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);

        // Set marketing permissions if true
        // Valid values "5", "Off"
        if (customerProfile.isMarketingPermissions()) {
            form.setField(MARKETING_OPT_IN_FIELD, "5");
        }
        form.setFieldProperty(MARKETING_OPT_IN_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty(CONCESSION_CARD_FIELD, "fflags", PdfFormField.FF_READ_ONLY, null);
    }

}
