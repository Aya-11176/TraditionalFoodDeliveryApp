package com.tradifooddelivery.view;

import com.tradifooddelivery.controller.CartController;
import com.tradifooddelivery.model.Product;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ProductPanel extends JPanel {

    private final MainWindow parent;
    private final CartController cartController;
    private final JPanel gridHolder;
    private final SearchBar searchBar;
    private final JPanel grid;
    private final List<Product> products = new ArrayList<>();

    public ProductPanel(MainWindow parent, CartController cartController){
        this.parent = parent;
        this.cartController = cartController;

        parent.setNavbarTransparent(false);
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        searchBar = new SearchBar("Search dishes...");
        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER,0,24));
        top.setOpaque(false);
        top.add(searchBar);
        add(top,BorderLayout.NORTH);

        gridHolder = new JPanel(new BorderLayout());
        gridHolder.setOpaque(false);
        gridHolder.setBorder(new EmptyBorder(0,40,40,40));

        grid = new JPanel(new GridLayout(0,4,26,26));
        grid.setOpaque(false);

        JScrollPane scroller = new JScrollPane(grid,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setBorder(null);
        scroller.getViewport().setOpaque(false);
        scroller.setOpaque(false);
        scroller.getVerticalScrollBar().setUnitIncrement(16);

        gridHolder.add(scroller,BorderLayout.CENTER);
        add(gridHolder,BorderLayout.CENTER);

        loadDemoProducts();
        buildDishCards();

        searchBar.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e){ filterProducts(); }
            @Override public void removeUpdate(DocumentEvent e){ filterProducts(); }
            @Override public void changedUpdate(DocumentEvent e){ filterProducts(); }
        });
    }

    private void loadDemoProducts(){
        products.clear();
        products.add(new Product(1,"Couscous Royal",1200,"Main","couscous.jpg"));
        products.add(new Product(2,"Chakchouka",800,"Main","Shakshuka.jpg"));
        products.add(new Product(3,"Tajine Zitoune",950,"Main","tajine.jpg"));
        products.add(new Product(4,"Chorba Frik",600,"Soup","frik.jpg"));
        products.add(new Product(5,"Makroud El Louz",450,"Dessert","makroud.jpg"));
        products.add(new Product(6,"Bourek",550,"Snack","borek.jpeg"));
    }

    private void buildDishCards(){
        grid.removeAll();
        for(Product p : products){
            DishCard card = new DishCard(p);

            // ✅ Correct wiring
            card.addActionListenerToButton(e -> {
                cartController.addToCart(p);
                JOptionPane.showMessageDialog(null, p.getName()+" added to cart!");
            });

            JPanel wrapper = new JPanel(new GridBagLayout());
            wrapper.setOpaque(false);
            wrapper.add(card);
            grid.add(wrapper);
        }
        revalidate();
        repaint();
    }

    private void filterProducts(){
        String query = searchBar.getText().trim().toLowerCase();
        grid.removeAll();
        boolean anyFound=false;

        for(Product p:products){
            if(p.getName().toLowerCase().contains(query) || p.getCategory().toLowerCase().contains(query)){
                DishCard card = new DishCard(p);
                card.addActionListenerToButton(e -> {
                    cartController.addToCart(p);
                    JOptionPane.showMessageDialog(null, p.getName()+" added to cart!");
                });
                JPanel wrapper = new JPanel(new GridBagLayout());
                wrapper.setOpaque(false);
                wrapper.add(card);
                grid.add(wrapper);
                anyFound=true;
            }
        }

        if(!anyFound && !query.isEmpty()){
            JLabel noResults = new JLabel("No dishes found 🥲",SwingConstants.CENTER);
            noResults.setFont(new Font("SansSerif",Font.ITALIC,16));
            noResults.setForeground(new Color(120,120,120));
            grid.add(noResults);
        }

        revalidate();
        repaint();
    }
}


