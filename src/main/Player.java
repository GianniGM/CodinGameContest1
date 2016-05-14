package main;

import java.util.*;

import java.util.Map.Entry;
import java.awt.Point;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Player {

    public static void main(String args[]) {
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

        myPods.add(new Pod(laps, checkpointCount));
        myPods.add(new Pod(laps, checkpointCount));
        opponentPods.add(new Pod(laps, checkpointCount));
        opponentPods.add(new Pod(laps, checkpointCount));
        
        
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
            
            int index0 = myPods.get(0).getNextCheckPointId();
            int x0 = (int) checkpoints.get(index0).getPosition().getX();
            int y0 = (int) checkpoints.get(index0).getPosition().getY();
            
            int index1 = myPods.get(1).getNextCheckPointId();
            int x1 = (int) checkpoints.get(index1).getPosition().getX();
            int y1 = (int) checkpoints.get(index1).getPosition().getY();
            
            /*
             * The game is played on a map 16000 (xmax) and 9000 units high (ymax)
             * The coordinate X=0, Y=0 is the top left pixel.
             * 
             * tre scaglioni di distanze:
             * 1. distanza altissima = X in [10001, 16000] || Y in [6001, 9000]
             * 2. distanza media = X in [3001, 10700] || Y in [1001, 6000]
             * 3. distanza ravvicinata = X in [0, 1800] || Y in [0, 1000]
             * 
             * se sei a distanza molto alta:
             * 1. sparati a velocità massima
             * 2. a velocità media mettiti a 100
             * 3. a velocità ridotta sparati a 50
             * 
             * resetta la velocità a 100
             * 
             * 
             * se il nextchecpointid di uno dei nemici è maggiore del tuo e il tuo nextchecpointid
             * è minore di quello dell'altra tua navetta allora placca il nemico
             */
             
             //other my pod
            
 
            //(regolare la velocità anche in base agli angoli e alla velocità locale del pod)
            

   
            
/*            
            if(Math.abs(x1 - (int) myPods.get(1).getPosition().getX()) < 1000 && Math.abs(y1 - (int) myPods.get(1).getPosition().getY()) < 588){
                speed1 = 100;
            } else if(Math.abs(x1 - (int) myPods.get(1).getPosition().getX()) < 10 && Math.abs(y1 - (int) myPods.get(1).getPosition().getY()) < 6){
                speed1 = 10;
            } else{
                speed1 = 200;
            }
  */          

            int speed0 = myPods.get(0).calculateSpeed(x0, y0);
            int speed1 = myPods.get(1).calculateSpeed(x1, y1);            
   
            if(Pod.Advantage(myPods.get(0), myPods.get(1))){
            	//pod(0) in front of pod(1)
                if(Pod.Advantage(myPods.get(1), opponentPods.get(1)) && Pod.Advantage(myPods.get(1), opponentPods.get(0))){
                	//pod(1) is second
                	System.out.println( x0 + " " + y0 + " " + speed0);
                	System.out.println( x1 + " " + y1 + " " + speed1);
                }else{
                    //pod(0)
                	System.out.println( x0 + " " + y0 + " " + speed0);

                	//pod(1) is third or fourth
                    //se la minima distanza è il nextcheckpoint allora vai nel nextcheckpoint
                	//setting SHIELD MODE if maximum velocity
                	String s = Integer.toString(speed1);
                    
                    if((int) myPods.get(1).getVelocity().getX() >= 200 || (int) myPods.get(1).getVelocity().getY() >= 200)
                        s = "SHIELD";

                	//pods1 do tackle
                	if(Pod.Advantage(opponentPods.get(0), opponentPods.get(1))){
                        if(!CheckPoint.goToCheckPoint(myPods.get(1).getPosition(), opponentPods.get(0).getPosition(), new Point(x1,y1))){
                			System.out.println((int) opponentPods.get(0).getPosition().getX() + " " +(int) opponentPods.get(0).getPosition().getY() + " " + s);
                        }else{
                        	System.out.println( x1 + " " + y1 + " " + speed1);
                        }
                	}else{
                        if(!CheckPoint.goToCheckPoint(myPods.get(1).getPosition(), opponentPods.get(1).getPosition(), new Point(x1,y1))){
                        	System.out.println((int) opponentPods.get(1).getPosition().getX() + " " + (int) opponentPods.get(1).getPosition().getY() + " " + s); 
                        }else{
                        	System.out.println( x1 + " " + y1 + " " + speed1);
                        }
                     }

                }
            }else{
            	//pod(1) in front of pod(0)
                if(Pod.Advantage(myPods.get(0), opponentPods.get(1)) && Pod.Advantage(myPods.get(0), opponentPods.get(0))){
                	//pod(0) is second
                	System.out.println( x0 + " " + y0 + " " + speed0);
                	System.out.println( x1 + " " + y1 + " " + speed1);
                }else{
                	//pod(0) is third or fourth
                    
                	//setting SHIELD MODE if maximum velocity
                	String s = Integer.toString(speed1);
                    if((int) myPods.get(1).getVelocity().getX() == 200 || (int) myPods.get(1).getVelocity().getY() == 200)
                        s = "SHIELD";
                       
                    //pods0 do tackle
                    if(Pod.Advantage(opponentPods.get(0), opponentPods.get(1))){
                        if(!CheckPoint.goToCheckPoint(myPods.get(0).getPosition(), opponentPods.get(0).getPosition(), new Point(x0,y0))){
                			System.out.println((int) opponentPods.get(0).getPosition().getX() + " " +(int) opponentPods.get(0).getPosition().getY() + " " + s);
                        }else{
                        	System.out.println( x0 + " " + y0 + " " + speed0);
                        }
                	}else{
                        if(!CheckPoint.goToCheckPoint(myPods.get(0).getPosition(), opponentPods.get(1).getPosition(), new Point(x0,y0))){
                        	System.out.println((int) opponentPods.get(1).getPosition().getX() + " " + (int) opponentPods.get(1).getPosition().getY() + " " + s); 
                        }else{
                        	System.out.println( x0 + " " + y0 + " " + speed0);
                        }
                    }
                    
                    //pod(1) is first...
                	System.out.println( x1 + " " + y1 + " " + speed1);

                }
            }

            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

//            System.out.println("8000 4500 100");
//            System.out.println("8000 4500 100");
            in.close();
        }

    }
}

class CheckPoint{
	private static int ID=0;
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
	
	/*
	 * decided if pod1 must go to checkpoint or do tackle on pod2
	 * return true: pod1 must go to checkpoint
	 * return false: pod1 must do tackle to pod2
	 */
	public static boolean goToCheckPoint(Point pod1, Point pod2, Point checkpoint){
	    return Point.distance(pod1.getX(), pod1.getY(), pod2.getX(), pod2.getY()) >= Point.distance(pod1.getX(), pod2.getY(), checkpoint.getX(), checkpoint.getY());
	}
}

class Pod {
	private Point position;
	private Point velocity;
	private int angle;
	private int nextCheckPointId=0;
	private HashMap<Integer, Integer> checks;
	private int laps;
	public Pod(int laps, int ncp){
		this.laps = laps;
		this.checks = new HashMap<>();
	}
	
	public void PodStatus(int x, int y, int vx, int vy, int angle, int checkId) {
		this.position = new Point(x,y);
		this.velocity = new Point(vx, vy);
		this.angle = angle;
		
		if(this.nextCheckPointId != checkId){
			int val = 1;
			if(checks.containsKey(checkId))
				val = checks.get(checkId) + 1;
			checks.put(checkId, val);
		}
		
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
	
	public int getNextCheckPoint(){
	    if(this.checks.containsKey(this.nextCheckPointId))
		    return this.checks.get(this.nextCheckPointId);
		else
		    return 0;
	}
	
	public int calculateSpeed(int x, int y){
            double sin, cos, tan;
            double velocity;
            
            sin = Math.abs(this.getPosition().getY() - y);
            cos = Math.abs(this.getPosition().getX() - x);
           
            if(cos == 0){
            }else{
            	tan = sin/cos;
            } 

            sin = Math.abs(this.getPosition().getY() -  this.getVelocity().getY() );
            cos = Math.abs(this.getPosition().getX() -  this.getVelocity().getX() );
           
            if(cos == 0){
            	velocity = 90;
            }else{
            	tan = sin/cos;
            	velocity = Math.toDegrees(Math.atan(tan));
            } 

            System.err.println("local angle: " + this.getAngle());
            System.err.println("local Vangle: " + velocity);
            
            double valor = Math.abs((velocity - this.getAngle()) % 360);
            System.err.println("difference: " + valor);
            
            int X = 6;
            if((valor < X || valor > 180-X) && (Point.distance(this.getPosition().getX(), this.getPosition().getY(), x, y) > 5000))
                return 200;
                
            if(valor > 90 && valor < 180)
                return 10;
                
            if((valor > 30 && valor <= 90) || (valor > 180 && valor <= 180+60))
                return 20;
                
            return 100;


/*                
            if(valor > 90 || valor < 90){
                return 10;
            } else if(valor < 6 || valor > 180-6 ){
                return 200;
            } 
 */          
	}
	
	public int getElapsedLaps(){
		int temp = 0;
		
		//iterate over a HashMap find max value
		for (Iterator<Entry<Integer, Integer>> it = checks.entrySet().iterator(); it.hasNext();) {
			Map.Entry<Integer, Integer> i = (Map.Entry<Integer, Integer>) it.next();
			if(i.getValue() > temp){
				temp = i.getValue();
			}
			
		}
	
		return laps - temp;
	}
	
	
	/*
	 * return true if first Pod is in front of second Pod
	 */
	public static boolean Advantage (Pod first, Pod second){
		//if i pass in checkpoint 0 elapsedLaps is decremented
		if(first.getElapsedLaps() == second.getElapsedLaps() && first.getNextCheckPointId() != 0 && second.getNextCheckPointId() != 0){
			return first.getNextCheckPoint() >= second.getNextCheckPoint();
		} else if(first.getElapsedLaps() < second.getElapsedLaps()){
			return true;
		}else{
			return false;
		}
	}
}

