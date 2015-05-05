package com.samlanning.robot_simulator.util;

import java.util.ArrayList;

public class ExpandableGrid<T> {
    
    ExpandibleArrayList<ExpandibleArrayList<T>> grid = new ExpandibleArrayList<>();
    
    private int maxX = -1;
    private int minX = 0;
    private int maxY = -1;
    private int minY = 0;
    
    public void set(int x, int y, T item){
        ExpandibleArrayList<T> row = grid.get(y);
        if(row == null){
            row = new ExpandibleArrayList<>();
            grid.set(y, row);
        }
        row.set(x, item);
        maxX = Math.max(maxX, x);
        minX = Math.min(minX, x);
        maxY = Math.max(maxY, y);
        minY = Math.min(minY, y);
    }
    
    public T get(int x, int y){
        ExpandibleArrayList<T> row = grid.get(y);
        return row == null ? null : row.get(x);
    }
    
    /**
     * An array that expands in both directions (negative and positive), and
     * with constant time read access.
     *
     * @param <T>
     */
    private static class ExpandibleArrayList<T> {
        
        /**
         * [0, 1, 2, ...]
         */
        private ArrayList<T> arrA = new ArrayList<>();
        
        /**
         * [-1, -2, -3, ...]
         */
        private ArrayList<T> arrB = new ArrayList<>();
        
        private void set(int i, T item) {
            ArrayList<T> arr;
            if (i >= 0) {
                arr = arrA;
            } else {
                arr = arrB;
                i = -1 - i;
            }
            while (arr.size() <= i)
                arr.add(null);
            arr.set(i, item);
        }
        
        private T get(int i) {
            ArrayList<T> arr;
            if (i >= 0) {
                arr = arrA;
            } else {
                arr = arrB;
                i = -1 - i;
            }
            return arr.size() > i ? arr.get(i) : null;
        }
        
    }
    
}
