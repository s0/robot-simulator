package com.samlanning.robot_simulator.simulator.gui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
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
        Map<Point, List<RobotsEnum>> overlappingRobots = new HashMap<>();
        for (Map.Entry<RobotsEnum, GUIRobotState> entry : state.robots().entrySet()) {
            Point p = new Point(entry.getValue().state.x, entry.getValue().state.y);
            Rectangle r = getRectangle(mapX, mapY, mapWidth, mapHeight, totalX, totalY, p.x, p.y);
            paintRobot(g, r, entry.getKey(), entry.getValue());
            List<RobotsEnum> robots = overlappingRobots.get(p);
            if(robots == null){
                robots = new LinkedList<>();
                overlappingRobots.put(p, robots);
            }
            robots.add(entry.getKey());
        }
        
        // Add Overlapping Robots Indicators
        for(Map.Entry<Point, List<RobotsEnum>> entry : overlappingRobots.entrySet()){
            if(entry.getValue().size() > 1){
                Rectangle r =
                    getRectangle(mapX, mapY, mapWidth, mapHeight, totalX, totalY, entry.getKey().x,
                        entry.getKey().y);
                drawOverlappingIndicators(g, r, entry.getValue());
            }
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
    
    private void paintRobot(Graphics g, Rectangle clip, RobotsEnum robot, GUIRobotState state) {

        System.out.println("Painting Robot");
        
        g.setColor(robot.color);
        g.fillOval(clip.x + 10, clip.y + 10, clip.width - 20, clip.height - 20);
        
        g.setColor(Color.white);
        
        int angle = 0;
        switch(state.state.direction){
            case UP:
                angle = 0;
                break;
            case RIGHT:
                angle = 90;
                break;
            case DOWN:
                angle = 180;
                break;
            case LEFT:
                angle = 270;
                break;
        }
        
        int centreX = clip.x + clip.width / 2;
        int centreY = clip.y + clip.height / 2;
        int length = clip.width / 2 - 15;
        int otherX = centreX + length;
        int otherY = centreY;
        
        g.drawLine(centreX, centreY, otherX, otherY);
        
        
    }
    
    private void drawOverlappingIndicators(Graphics g, Rectangle clip, List<RobotsEnum> robots){
        g.setColor(Color.white);
        g.fillRoundRect(clip.x + 5, clip.y + 5, 5 + 10 * robots.size(), 15, 5, 5);
        int x = clip.x + 10;
        for(RobotsEnum robot : robots){
            g.setColor(robot.color);
            g.fillOval(x, clip.y + 10, 5, 5);
            x += 10;
        }
    }
    
}
