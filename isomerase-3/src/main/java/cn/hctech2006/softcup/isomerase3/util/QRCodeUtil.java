package cn.hctech2006.softcup.isomerase3.util;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * QRCodeUtil 生成二维码工具类
 */
public class QRCodeUtil {

    private static final String CHARSET = "UTF-8";
    private static final String FORMAT_NAME = "JPG";
    // 二维码尺寸
    private static final int QRCODE_SIZE = 300;
    // LOGO宽度
    private static final int WIDTH = 60;
    // LOGO高度
    private static final int HEIGHT = 60;

    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        //设置二维码容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置UTF-8，防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 1);
        //画二维码，记得调用multiFormatWriter。encode()时最后要加上hints参数，否则上面的设置无效
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        System.out.println("imagePath:"+imgPath);
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        image = QRCodeUtil.insertText(image,"lidengyin",true);
        return image;
    }
    private static BufferedImage createImageAndFont(String content, String imgPath, String text, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        //设置二维码容错性
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        //设置UTF-8，防止中文乱码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        //设置二维码四周白色区域的大小
        hints.put(EncodeHintType.MARGIN, 1);
        //画二维码，记得调用multiFormatWriter。encode()时最后要加上hints参数，否则上面的设置无效
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE,
                hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        System.out.println("imagePath:"+imgPath);
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        QRCodeUtil.insertImage(image, imgPath, needCompress);
        image = QRCodeUtil.insertText(image,text,true);
        return image;
    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 一行字符串拆分成多行
     */
    private static String[] splitStrLines(String str, int len){
        int blocks = str.length() /len + 1;
        String [] strArray = new String[blocks];
        for(int i = 0; i < blocks; i ++){
            if((i+1)*len > str.length()){
                strArray[i] = str.substring(i*len);
            }else{
                strArray[i] = str.substring(i*len, (i+1)*len);
            }
        }
        return strArray;
    }
    private static BufferedImage insertText(BufferedImage source, String text, boolean needCompress) throws Exception {
        String[] splitStrLines;
        int wordSize = QRCODE_SIZE / 20;
        splitStrLines = splitStrLines(text,wordSize);
        // 文字图片的高度 = 文字行数 * 每行高度(文字高度) + 10px;
        // 为了防止 文字图片 下面部分显示不全， 特意在这里额外加10像素的高度。
        int fontsImageHeight = splitStrLines.length* wordSize+10;
        // 创建顶部文字的图片
        BufferedImage imageWithFontsBufferedImage = new
                BufferedImage(QRCODE_SIZE, fontsImageHeight,
                BufferedImage.TYPE_4BYTE_ABGR);
        Graphics fontsImageGraphics = imageWithFontsBufferedImage.getGraphics();
        fontsImageGraphics.fillRect(0,0,QRCODE_SIZE, fontsImageHeight);
        fontsImageGraphics.setColor(Color.black);
        fontsImageGraphics.setFont(new Font("宋体",Font.PLAIN, wordSize));
        // 文字长度大于一行的长度，进行分行
        // 每行限制的文字个数
        if(text.length()>QRCODE_SIZE/wordSize){
            for(int i = 0; i < splitStrLines.length; i++){
                fontsImageGraphics.drawString(splitStrLines[i], 0, wordSize*(i+1));
            }
        }else{
            fontsImageGraphics.drawString(text,
            // 总长度减去字长度除以2为向右偏移长度
                    ((QRCODE_SIZE/wordSize-text.length())/2)*wordSize,wordSize
                    );
        }
        // 从图片中读取RGB
        int []imageArrayFonts = new int[QRCODE_SIZE * fontsImageHeight];
        imageArrayFonts = imageWithFontsBufferedImage.getRGB
                (0,0,QRCODE_SIZE,fontsImageHeight,imageArrayFonts,0,QRCODE_SIZE);
        int[] imageArrayQr = new int [QRCODE_SIZE*QRCODE_SIZE];
        imageArrayQr = source.getRGB(0,0,QRCODE_SIZE,QRCODE_SIZE,imageArrayQr,0,QRCODE_SIZE);
        // 生成新图片(在setsetRGB时，通过控制图像的startX与startY, 可达到不同的拼接效果)
        BufferedImage newBufferedImage = new BufferedImage(QRCODE_SIZE, QRCODE_SIZE+fontsImageHeight,BufferedImage.TYPE_INT_RGB);
        // 图片在上， 文字在下
        // 设置上半部分的RGB
        newBufferedImage.setRGB
                (0,0,QRCODE_SIZE,QRCODE_SIZE,imageArrayQr,
                        0,QRCODE_SIZE);
        // 设置下半部分的RGB
        newBufferedImage.setRGB
                (0,QRCODE_SIZE,QRCODE_SIZE,fontsImageHeight,imageArrayFonts,
                        0,QRCODE_SIZE);

        return newBufferedImage;
    }

    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        return image;
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode(String content, String imgPath, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtil.createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    public static void encode(String content, OutputStream output) throws Exception {
        QRCodeUtil.encode(content, null, output, false);
    }
    public static InputStream encode(String content, String imgPath, String text, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = QRCodeUtil.createImageAndFont(content, imgPath, text,needCompress);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        //ImageIO.write(image, FORMAT_NAME, output);
        ImageIO.write(image, FORMAT_NAME, bs);
        InputStream inputStream = new ByteArrayInputStream(bs.toByteArray());
        return inputStream;
    }

    public static void encode(String content, String text, OutputStream output) throws Exception {
        QRCodeUtil.encode(content, null, text,output, false);
    }
}