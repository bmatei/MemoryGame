package memory.game;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class ImageUtilities {
	//resize an image with the width: scaledWidth and the height: scaledHeight
    public static BufferedImage resizeImage(Image originalImage,
    			int scaledWidth, 
                int scaledHeight, 
                boolean preserveAlpha){
         
    	int imageType = preserveAlpha ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;
    	BufferedImage scaledBI = new BufferedImage(scaledWidth, scaledHeight, imageType);
    	Graphics2D g = scaledBI.createGraphics();
    	if (preserveAlpha) {
    		g.setComposite(AlphaComposite.Src);
    	}
        
    	g.drawImage(originalImage, 0, 0, scaledWidth, scaledHeight, null); 
    	g.dispose();
    	return scaledBI;
    
        
         
    }
    
    
    //read an image from url: 
    public static Image readImageFromURL(String adress){
        Image img=null;
        try{
            File file = new File(adress);
            img=ImageIO.read(file);
        } catch(IOException ex){
            ex.printStackTrace();
            System.err.println(adress);
        }finally{
            return img;
        }
    }
}