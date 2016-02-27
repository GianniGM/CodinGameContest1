package main;

import java.util.*;
import java.awt.Point;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        int laps = in.nextInt();
        int checkpointCount = in.nextInt();
        
        ArrayList<CheckPoint> checkpoints = new ArrayList<>();
        ArrayList<Pod> myPods = new ArrayList<>();
        ArrayList<Pod> opponentPods = new ArrayList<>();
        
        
        //checkpoints(0) is the started checkpoint
        for (int i = 0; i < checkpointCount; i++) {
            int checkpointX = in.nextInt();
            int checkpointY = in.nextInt();
            checkpoints.add(new CheckPoint(checkpointX, checkpointY, laps));
        }

        myPods.add(new Pod(laps));
        myPods.add(new Pod(laps));
        opponentPods.add(new Pod(laps));
        opponentPods.add(new Pod(laps));
        
        
        // game loop
        while (true) {
        	//my 2 pods
            for (int i = 0; i < 2; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int vx = in.nextInt();
                int vy = in.nextInt();
                int angle = in.nextInt();
                int nextCheckPointId = in.nextInt();
                myPods.get(i).PodStatus(x, y, vx, vy, angle, nextCheckPointId);
            }
            //the 2 opponents pods
            for (int i = 0; i < 2; i++) {
                int x = in.nextInt();
                int y = in.nextInt();
                int vx = in.nextInt();
                int vy = in.nextInt();
                int angle = in.nextInt();
                int nextCheckPointId = in.nextInt();
                opponentPods.get(i).PodStatus(x, y, vx, vy, angle, nextCheckPointId);
            }
            
            for (int i = 0; i < 2; i++) {
                int index = myPods.get(i).getNextCheckPointId();
                int x = (int) checkpoints.get(index).getPosition().getX();
                int y = (int) checkpoints.get(index).getPosition().getY();
                System.out.println( x + " " + y + " 100");
			}

            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

//            System.out.println("8000 4500 100");
//            System.out.println("8000 4500 100");
        }
    }
}

class Pod {
	private Point position;
	private Point velocity;
	private int angle;
	private int nextCheckPointId;
	private int laps;
	
	public Pod(int laps){
		this.laps = laps;
	}
	
	public void PodStatus(int x, int y, int vx, int vy, int angle, int checkId) {
		this.position = new Point(x,y);
		this.velocity = new Point(vx, vy);
		this.angle = angle;
		this.nextCheckPointId = checkId;
	}
	
	public Point getPosition(){
		return this.position;
	}
	
	public Point getVelocity(){
		return this.velocity;
	}
	
	public int getAngle(){
		return this.angle;
	}
	
	public int getNextCheckPointId(){
		return this.nextCheckPointId;
	}
	
	public boolean lapped(ArrayList<CheckPoint> checkpoints){
		boolean flag = false;
		for (CheckPoint c : checkpoints) {
			if(c.nPassed(laps)) flag = true;
			else flag = false;
		}
		return flag;
	}
}

class CheckPoint{
	private static int ID=0;
	private int passed = 0;
	private Point position;
	private int id;
	
	public CheckPoint(int x, int y, int laps){
		this.position = new Point(x, y);
		this.id = ID++;
	}
	
	public Point getPosition(){
		return position;
	}
	
	public int getId(){
		return id;
	}
	
	public void passed(){
		passed++;
	}
	
	public boolean nPassed(int totalLaps){
		return totalLaps == passed;
	}
}