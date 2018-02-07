package riyadh;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.text.Position;

public class  Objet {
    // Attributs 
    int x,y;                
    int h,l;                
    float direction;        
    float vitesse;          
    BufferedImage image;    
    Rectangle BoxObjet;     
    int temps_explosion;    
    boolean actif;          
   
    // Constructeur
    public Objet(String NomImage, int ax, int ay, float ad, float av)    {
    super();
         try {
             image= ImageIO.read(new File(NomImage));
             } 
         catch(Exception err) 
             {
            System.out.println(NomImage+" introuvable !");            
            System.exit(0);    
            }
        
        
        h= image.getHeight(null);   
        l= image.getWidth(null);        
        x=ax;   
        y=ay;
        BoxObjet = new Rectangle(x,y,l,h);
        direction=ad;
        vitesse=av; 
        temps_explosion=0;
        actif=true;
        
    }
    
    boolean Collision(Objet O) {
        return BoxObjet.intersects(O.BoxObjet); 
    }
    
    public void move(Rectangle Ecran) {
        x=x+(int)(vitesse*Math.cos(direction)); 
        y=y+(int)(vitesse*Math.sin(direction));
        
        BoxObjet.setLocation(x,y);
        
        if (( x + l<0)||( y + h<0)||( x > Ecran.width) ||( y > Ecran.height)) actif=false;
    }
    
}
