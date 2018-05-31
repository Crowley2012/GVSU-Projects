import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.net.*;
import javax.imageio.*;
import java.io.*;

/****************************************************************
 * Class Card - Business Card with Photo, Info, and Logo.
 * 
 * @author Sean Crowley 
 * @version January 2014
 ****************************************************************/

public class Card extends JApplet {

	public void paint(Graphics g) {

		//Variables
		//Change the whole card position
		int cardPos = 25;

		//Change the position of Name and Info
		int infoX = 270;
		int infoY = 170;

		//Change the position of Logo
		int logoX = 350;
		int logoY = 35;
		
		//Change the position of Photo
		int photoX = 75;
		int photoY = 75;
		
		//Change the size of the Photo
		int photoSizeX = 150;
		int photoSizeY = 200;
		
		//Declare BufferedImage
		BufferedImage photo = null;

		//Set Canvas Size
		setSize(550 + (cardPos *2), 350 + (cardPos *2));

		//Card Border and Background
		g.setColor(new Color(230, 230, 230));
		g.fillRect(cardPos, cardPos, 500, 300);
		g.setColor(Color.black);
		g.drawRect(cardPos, cardPos, 500, 300);

		//Name and Info
		g.setColor(Color.blue);
		g.fillRect(cardPos + infoX - 5, cardPos + infoY - 30, 235, 40);

		g.setColor(Color.white);
		g.setFont(new Font("TimesRoman", Font.BOLD, 30));
		g.drawString("Sean Crowley", cardPos + infoX, cardPos + infoY);

		g.setColor(Color.blue);
		g.setFont(new Font("Dialog", Font.PLAIN, 18));
		g.drawString("Synergy Inc.", cardPos + infoX, cardPos + infoY + 45);
		g.drawString("(815) 953 - 6030", cardPos + infoX, cardPos + infoY + 70);
		g.drawString("CrowleyS@GVSU.edu", cardPos + infoX, cardPos + infoY + 95);

		//Logo
		g.setColor(Color.orange);
		g.drawLine(cardPos + logoX + 38, cardPos + logoY + 76, cardPos + logoX + 38, cardPos + logoY - 25);
		g.drawLine(cardPos + logoX + 39, cardPos + logoY + 76, cardPos + logoX + 39, cardPos + logoY - 25);
		g.drawLine(cardPos + logoX + 40, cardPos + logoY + 76, cardPos + logoX + 40, cardPos + logoY - 25);

		g.setColor(Color.red);
		g.drawOval(cardPos + logoX, cardPos + logoY, 75, 75);
		g.drawOval(cardPos + logoX + 1, cardPos + logoY, 75, 75);
		g.drawOval(cardPos + logoX, cardPos + logoY + 1, 75, 75);

		g.setColor(Color.blue);
		g.drawLine(cardPos + logoX - 37, cardPos + logoY + 76, cardPos + logoX + 112, cardPos + logoY + 76);
		g.drawLine(cardPos + logoX - 37, cardPos + logoY + 77, cardPos + logoX + 112, cardPos + logoY + 77);
		g.drawLine(cardPos + logoX - 38, cardPos + logoY + 76, cardPos + logoX + 39, cardPos + logoY - 27);
		g.drawLine(cardPos + logoX - 37, cardPos + logoY + 76, cardPos + logoX + 40, cardPos + logoY - 27);
		g.drawLine(cardPos + logoX + 40, cardPos + logoY - 27, cardPos + logoX + 112, cardPos + logoY + 76);
		g.drawLine(cardPos + logoX + 39, cardPos + logoY - 27, cardPos + logoX + 111, cardPos + logoY + 76);
		
		//Photo
        try {
        	URL u = new URL(getCodeBase(), "photo.jpg");
        	photo = ImageIO.read(u);
        } catch (IOException e){
         	g.drawString("Problem reading the file", photoX, photoY);
        }
        
		g.setColor(Color.blue);
		g.fillRect(photoX - 3, photoY - 3, photoSizeX + 6, photoSizeY + 6);
        g.drawImage(photo, photoX, photoY, photoSizeX, photoSizeY, null);
        
        while(true){
            g.fillRect(10, 10, 50, 50);
            int x = 100001;
            if(x>10000){
                g.setColor(Color.red);
                x = 1;
            }else{
                g.setColor(Color.blue);
                x = 0;
            }
        }
	}
}
