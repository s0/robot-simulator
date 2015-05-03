package com.samlanning.robot_simulator.simulator.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JPanel;

import com.samlanning.robot_simulator.maps.MapBlock;
import com.samlanning.robot_simulator.maps.RobotMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.gui.GUIState.GUIRobotState;

public class MapPanel extends JPanel implements GUIState.Listener {
    
    private final GUIState state;
    
    protected MapPanel(GUIState state) {
        this.state = state;
        
        state.addListener(this);
    }
    
    @Override
    public void update() {
        this.repaint();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        Rectangle clip = g.getClipBounds();
        System.out.println(clip.width);
        System.out.println(clip.height);
        
        RobotMap map = state.map();
        if (map == null) {
            FontMetrics fm = g.getFontMetrics();
            String text = "No Map Loaded";
            int x, y;
            x = (clip.width - fm.stringWidth(text)) / 2;
            y = (clip.height - fm.getHeight()) / 2;
            g.drawString(text, x, y);
            return;
        }
        
        int mapWidth;
        int mapHeight;
        int widthFromHeight = clip.height / map.getHeight() * map.getWidth();
        if (widthFromHeight > clip.width) {
            mapWidth = clip.width;
            mapHeight = clip.width / map.getWidth() * map.getHeight();
        } else {
            mapWidth = widthFromHeight;
            mapHeight = clip.height;
        }
        int mapX = (clip.width - mapWidth) / 2;
        int mapY = (clip.height - mapHeight) / 2;
        
        g.setColor(Color.black);
        g.fillRect(mapX, mapY, mapWidth, mapHeight);
        
        // Add Tiles
        int totalX = map.getWidth();
        int totalY = map.getHeight();
        for (int x = 0; x < totalX; x++) {
            for (int y = 0; y < totalY; y++) {
                Rectangle r = getRectangle(mapX, mapY, mapWidth, mapHeight, totalX, totalY, x, y);
                paintTile(g, r, map.getBlock(x, y));
            }
        }
        
        // Add Robots
        for (Map.Entry<RobotsEnum, GUIRobotState> entry : state.robots().entrySet()) {
            Rectangle r =
                getRectangle(mapX, mapY, mapWidth, mapHeight, totalX, totalY,
                    entry.getValue().state.x, entry.getValue().state.y);
            System.out.println(entry.getValue().state.x);
            paintRobot(g, r, entry.getKey());
        }
    }
    
    private Rectangle getRectangle(int mapX, int mapY, int mapWidth, int mapHeight, int totalX,
        int totalY, int x, int y) {
        int w = mapWidth / totalX;
        int h = mapHeight / totalY;
        return new Rectangle(mapX + x * w, mapY + y * h, w, h);
    }
    
    private void paintTile(Graphics g, Rectangle clip, MapBlock block) {
        
        switch(block){
            case EMPTY:
                g.setColor(Color.white);
                break;
                
            case FINISH:
                g.setColor(new Color(00, 200, 255));
                break;
                
            case WALL:
                g.setColor(new Color(30, 30, 30));
                break;
        }
        g.fillRect(clip.x + 1, clip.y + 1, clip.width - 2, clip.height - 2);
    }
    
    private void paintRobot(Graphics g, Rectangle clip, RobotsEnum robot) {

        System.out.println("Painting Robot");
        
        g.setColor(robot.color);
        g.fillRect(clip.x + 10, clip.y + 10, clip.width - 20, clip.height - 20);
    }
    
}
