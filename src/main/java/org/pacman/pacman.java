package org.pacman;

import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.random.*;
import javax.swing.*;

public class pacman extends JPanel{
    private int rowCount = 21;
    private int columnCount =  19;
    private int titleSize = 32;
    private int boardWeight = columnCount * titleSize;
    private int boardHeight = rowCount * titleSize;

    pacman() {
        setPreferredSize(new Dimension(boardWeight, boardHeight));
        setBackground(Color.BLACK);
    }
}
