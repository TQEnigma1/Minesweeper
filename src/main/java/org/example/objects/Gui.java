package org.example.objects;

import javax.swing.*;
public class Gui {
    public static void makeGui() {
        JFrame f=new JFrame();//creating instance of JFrame



        f.setSize(1000,800);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }
}  