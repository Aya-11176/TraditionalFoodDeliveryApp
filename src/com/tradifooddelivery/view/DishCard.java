package com.tradifooddelivery.view;

import com.tradifooddelivery.model.Product;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class DishCard extends JComponent {

    private static final Color CARD_BG = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(222, 229, 214);
    private static final Color PRICE_ORANGE = new Color(221, 83, 28);
    private static final Color ADD_BTN_GREEN = new Color(169, 208, 153);
    private static final Color BADGE_ORANGE = new Color(243, 115, 61);

    private final Product product;
    private boolean hovered = false;
    private final int width = 260;
    private final int height = 360;
    private BufferedImage dishImage;

    private JButton addToCartButton;

    public DishCard(Product product) {
        this.product = product;
        setPreferredSize(new Dimension(width, height));
        setOpaque(false);

        dishImage = loadImage(product.getImagePath(), width, 170);

        addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hovered = true; repaint(); }
            @Override public void mouseExited(MouseEvent e) { hovered = false; repaint(); }
        });

        setLayout(new BorderLayout());
        add(buildInfoPanel(), BorderLayout.SOUTH);
    }

    private JPanel buildInfoPanel() {
        JPanel info = new JPanel(new BorderLayout());
        info.setOpaque(false);
        info.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

        // Name + price
        JLabel name = new JLabel(product.getName());
        name.setFont(new Font("Serif", Font.BOLD, 20));
        name.setForeground(new Color(27,44,63));
        name.setBorder(BorderFactory.createEmptyBorder(0,0,8,0));

        JLabel price = new JLabel(product.getPrice() + " DA");
        price.setFont(new Font("SansSerif", Font.BOLD, 17));
        price.setForeground(PRICE_ORANGE);
        price.setBorder(BorderFactory.createEmptyBorder(4,0,12,0));

        JPanel top = new JPanel();
        top.setOpaque(false);
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.add(name);
        top.add(price);

        // Add to Cart button
        addToCartButton = new JButton("🛒 Add to Cart");
        addToCartButton.setFocusPainted(false);
        addToCartButton.setBorderPainted(false);
        addToCartButton.setPreferredSize(new Dimension(200, 40));
        addToCartButton.setBackground(ADD_BTN_GREEN);
        addToCartButton.setForeground(Color.BLACK);
        addToCartButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        addToCartButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addToCartButton.setOpaque(true);

        addToCartButton.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) {
                addToCartButton.setBackground(new Color(0,122,204));
                addToCartButton.setForeground(Color.WHITE);
            }
            @Override public void mouseExited(MouseEvent e) {
                addToCartButton.setBackground(ADD_BTN_GREEN);
                addToCartButton.setForeground(Color.BLACK);
            }
        });

        JPanel btnWrap = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnWrap.setOpaque(false);
        btnWrap.add(addToCartButton);

        info.add(top, BorderLayout.NORTH);
        info.add(btnWrap, BorderLayout.SOUTH);

        return info;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int arc = 18;
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int shadowGap = hovered ? 10 : 6;
        g2.setColor(new Color(0,0,0,30));
        g2.fillRoundRect(4, 4 + shadowGap/2, getWidth()-8, getHeight()-8, arc, arc);

        RoundRectangle2D.Double card = new RoundRectangle2D.Double(0,0,getWidth()-1,getHeight()-1,arc,arc);
        g2.setColor(CARD_BG);
        g2.fill(card);

        g2.setColor(BORDER_COLOR);
        g2.setStroke(new BasicStroke(1f));
        g2.draw(card);

        int imageH = 170;
        Shape topImgClip = new RoundRectangle2D.Double(0,0,getWidth()-1,imageH,arc,arc);
        g2.setClip(topImgClip);
        if(dishImage != null) g2.drawImage(dishImage,0,0,getWidth()-1,imageH,null);
        else {
            g2.setColor(new Color(240,240,240));
            g2.fillRect(0,0,getWidth()-1,imageH);
        }
        g2.setClip(null);

        if(product.getId()%2==1){
            String text = "New Arrival";
            FontMetrics fm = g2.getFontMetrics(new Font("SansSerif", Font.BOLD, 12));
            int w = fm.stringWidth(text)+18;
            int h = 22;
            int x = getWidth()-w-12;
            int y = 12;
            g2.setColor(BADGE_ORANGE);
            g2.fillRoundRect(x,y,w,h,12,12);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD,12));
            g2.drawString(text,x+9,y+15);
        }

        g2.dispose();
    }

    private BufferedImage loadImage(String path, int targetW, int targetH){
        if(path==null) return null;
        BufferedImage img = null;
        try{
            URL res = getClass().getResource("/"+path);
            if(res!=null) img = ImageIO.read(res);
            else{
                File f = new File("src/resources/"+path);
                if(!f.exists()) f = new File(path);
                if(f.exists()) img = ImageIO.read(f);
            }
            if(img!=null){
                double scale = Math.max((double) targetW/img.getWidth(), (double) targetH/img.getHeight());
                int nw = (int)(img.getWidth()*scale);
                int nh = (int)(img.getHeight()*scale);
                Image scaled = img.getScaledInstance(nw,nh,Image.SCALE_SMOOTH);
                BufferedImage buf = new BufferedImage(nw,nh,BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = buf.createGraphics();
                g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                g.drawImage(scaled,0,0,null);
                g.dispose();
                int x = Math.max(0,(nw-targetW)/2);
                int y = Math.max(0,(nh-targetH)/2);
                return buf.getSubimage(x,y,targetW,targetH);
            }
        }catch(IOException ignored){}
        return null;
    }

    // ✅ Correct method to attach listener
    public void addActionListenerToButton(ActionListener l){
        if(addToCartButton != null) addToCartButton.addActionListener(l);
    }
}


