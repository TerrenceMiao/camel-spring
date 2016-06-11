package org.paradise.itext;

import com.lowagie.text.BadElementException;
import com.lowagie.text.ExceptionConverter;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.codec.CCITTG4Encoder;
import org.paradise.itext.qrcode.ByteMatrix;
import org.paradise.itext.qrcode.EncodeHintType;
import org.paradise.itext.qrcode.QRCodeWriter;
import org.paradise.itext.qrcode.WriterException;

import java.awt.*;
import java.util.Map;

/**
 * Created by terrence on 10/06/2016.
 */
public class BarcodeQRCode {

    ByteMatrix bm;

    /**
     * Creates the QR barcode. The barcode is always created with the smallest possible size and is then stretched
     * to the width and height given. Set the width and height to 1 to get an unscaled barcode.
     * @param content the text to be encoded
     * @param width the barcode width
     * @param height the barcode height
     * @param hints modifiers to change the way the barcode is create. They can be EncodeHintType.ERROR_CORRECTION
     * and EncodeHintType.CHARACTER_SET. For EncodeHintType.ERROR_CORRECTION the values can be ErrorCorrectionLevel.L, M, Q, H.
     * For EncodeHintType.CHARACTER_SET the values are strings and can be Cp437, Shift_JIS and ISO-8859-1 to ISO-8859-16.
     * You can also use UTF-8, but correct behaviour is not guaranteed as Unicode is not supported in QRCodes.
     * The default value is ISO-8859-1.
     * @throws WriterException
     */
    public BarcodeQRCode(String content, int width, int height, Map<EncodeHintType,Object> hints) {
        try {
            QRCodeWriter qc = new QRCodeWriter();
            bm = qc.encode(content, width, height, hints);
        }
        catch (WriterException ex) {
            throw new ExceptionConverter(ex);
        }
    }

    private byte[] getBitMatrix() {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int stride = (width + 7) / 8;
        byte[] b = new byte[stride * height];
        byte[][] mt = bm.getArray();
        for (int y = 0; y < height; ++y) {
            byte[] line = mt[y];
            for (int x = 0; x < width; ++x) {
                if (line[x] != 0) {
                    int offset = stride * y + x / 8;
                    b[offset] |= (byte)(0x80 >> (x % 8));
                }
            }
        }
        return b;
    }

    /** Gets an <CODE>Image</CODE> with the barcode.
     * @return the barcode <CODE>Image</CODE>
     * @throws BadElementException on error
     */
    public Image getImage() throws BadElementException {
        byte[] b = getBitMatrix();
        byte g4[] = CCITTG4Encoder.compress(b, bm.getWidth(), bm.getHeight());
        return Image.getInstance(bm.getWidth(), bm.getHeight(), false, Image.CCITTG4, Image.CCITT_BLACKIS1, g4, null);
    }

    // AWT related methods (remove this if you port to Android / GAE)

    /** Creates a <CODE>java.awt.Image</CODE>.
     * @param foreground the color of the bars
     * @param background the color of the background
     * @return the image
     */
    public java.awt.Image createAwtImage(java.awt.Color foreground, java.awt.Color background) {
        int f = foreground.getRGB();
        int g = background.getRGB();
        java.awt.Canvas canvas = new java.awt.Canvas();

        int width = bm.getWidth();
        int height = bm.getHeight();
        int pix[] = new int[width * height];
        byte[][] mt = bm.getArray();
        for (int y = 0; y < height; ++y) {
            byte[] line = mt[y];
            for (int x = 0; x < width; ++x) {
                pix[y * width + x] = line[x] == 0 ? f : g;
            }
        }

        java.awt.Image img = canvas.createImage(new java.awt.image.MemoryImageSource(width, height, pix, 0, width));
        return img;
    }

    public void placeBarcode(PdfContentByte cb, Color foreground, float moduleSide) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        byte[][] mt = bm.getArray();

        cb.setColorFill(foreground);

        for (int y = 0; y < height; ++y) {
            byte[] line = mt[y];
            for (int x = 0; x < width; ++x) {
                if (line[x] == 0) {
                    cb.rectangle(x * moduleSide, (height - y - 1) * moduleSide, moduleSide, moduleSide);
                }
            }
        }
        cb.fill();
    }

    /** Gets the size of the barcode grid. */
    public Rectangle getBarcodeSize() {
        return new Rectangle(0, 0, bm.getWidth(), bm.getHeight());
    }

}
