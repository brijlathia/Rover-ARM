package ARM ;

//make it a thread
public class Rover_Arm {

	public static final long POWER = 110;
	//define variables
	boolean ARM_ON = false;
	boolean CAMERA_ON = false;
	boolean ROVER_MOVE = false;
	
	//instrument
	//private String instrument;
	
	//angles
	private int shoulder_arm_angle_theta1;
	private int arm_wrist_angle_theta2;
	
	//degrees of freedom

	private int DRT;
	private int MAHLI;	
	private int DRILL;
	private int APXS;
	private int CHIMRA;
	
	//power
	private long arm_power;
	//time
	private long arm_time;
	
	//temperature
	//private String instrument_temperature;
	//time
	//private String instrument_time_dependency;
	
	//constructor
	
	public Rover_Arm(){
		
	}
	
	//robotic arm constructor
	public Rover_Arm(int angle1,int angle2, int degree_1, int degree_2, int degree_3, 
			int degree_4 , int degree_5, long time, long power){
		

		this.shoulder_arm_angle_theta1 = angle1;
		this.arm_wrist_angle_theta2 = angle2;
		this.DRT = degree_1;
		this.MAHLI = degree_2;
		this.DRILL = degree_3;
		this.APXS = degree_4;
		this.CHIMRA = degree_5;
		this.arm_time = time;
		this.arm_power = power;

	}
	
	

	
	//methods
		
	@Override
	public String toString() {
		return "Rover_Arm [ARM_ON=" + ARM_ON + ", CAMERA_ON=" + CAMERA_ON + ", ROVER_MOVE=" + ROVER_MOVE
				+ ", shoulder_arm_angle_theta1=" + shoulder_arm_angle_theta1 + ", arm_wrist_angle_theta2="
				+ arm_wrist_angle_theta2 + ", DRT=" + DRT + ", MAHLI=" + MAHLI + ", DRILL=" + DRILL + ", APXS=" + APXS
				+ ", CHIMRA=" + CHIMRA + ", arm_power=" + arm_power + ", arm_time=" + arm_time + "]";
	}

	//method to move the arm
	public Rover_Arm move(int theta1, int theta2, int deg_1,int deg_2,int deg_3, int deg_4, int deg_5){
		
		
		//check theta1
		int angle1 = theta1;
		//check theta2
		int angle2 = theta2;
		
		
		//checking if angles are valid
		if(theta1 <= 45){
			angle1 = 20;
			angle2 = 10;
		}else if (theta1 >= 70){
			angle1 = 20;
			angle2 = 10;
		}else if (theta2 > 290){
			angle1= 20;
			angle2= 10;
		}
		
		
		//assign the instrument  0 for off 1 for on
		int degree_1 = deg_1;
		int degree_2 = deg_2;
		int degree_3 = deg_3;
		int degree_4 = deg_4;
		int degree_5 = deg_5;
		
		//power
		long power_watts = POWER;
		
		//time
		long move_time;

		

		//if default angle values then no instrument
		if (angle1 == 20 && angle2 == 10){

			degree_1 = 0;
			degree_2 = 0;
			degree_3 = 0;
			degree_4 = 0;
			degree_5 = 0;
			move_time= 0;
			power_watts = 0;
		}
		else{

		if(degree_1 == 1){
			move_time = ARM_MOVEMENT_TIME(1000);
		}else if (degree_2 == 1){
			move_time = ARM_MOVEMENT_TIME(2000);
		}else if (degree_3 == 1){
			move_time = ARM_MOVEMENT_TIME(3000);
		}else if (degree_4 == 1){
			move_time = ARM_MOVEMENT_TIME(4000);
		}else{
			move_time = ARM_MOVEMENT_TIME(5000);
		}
		}		
		//set constructor
		Rover_Arm d = new Rover_Arm(angle1,angle2,degree_1,degree_2,degree_3,degree_4,degree_5,
				move_time,power_watts);
		
		d.setARM_ON(true);
		
		return d;
	}
	
	public long ARM_MOVEMENT_TIME(int deg){
		
		long currentTime = System.currentTimeMillis()/1000;
				
		//issue delay
		try {
			Thread.sleep((int)(Math.random()*(5000 - deg)) + deg);                
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
				
		long latterTime = System.currentTimeMillis()/1000;
		long arm_move_time = latterTime - currentTime;
				
		return arm_move_time;
	}
	
	//instrument status
	public String INST_STATUS(){
		
		String int_status = "OFF";
		int [] instruments =  {this.getDRT(), this.getMAHLI(),this.getDRILL(),this.getAPXS(),this.getCHIMRA()}; 
	
		for(int i = 0; i < instruments.length; i++){
			
			if(instruments[i] == 1)
				int_status = "ON";
		}
		
		if(int_status.equals("OFF")){
			
			this.setShoulder_arm_angle_theta1(20);
			this.setArm_wrist_angle_theta2(10);
			//this.setInstrument("No instrument selected yet");
			//this.setTemperature("");
			//this.setInstrument_time("");
		}
		
		return int_status;
	}
	
	//if in instrument is in use
	public String INST_IN_USE(){
		
		String instrument = "No instrument selected yet";
		int [] instruments = {this.getDRT(), this.getMAHLI(),this.getDRILL(),this.getAPXS(),this.getCHIMRA()}; 
		
		for(int i = 0; i < instruments.length; i++){
			
			if(instruments[i] == 1)
			{
				switch(i){
					case 0: instrument = "DRT";
						break;
					case 1: instrument = "MAHLI";
						break;
					case 2: instrument = "Drill";
						break;
					case 3: instrument = "APXS";
						break;
					case 4: instrument = "CHIMRA";
						break;
				}
			}
		}
		
		if(instrument.equals("No instrument selected yet")){
			
			this.setShoulder_arm_angle_theta1(20);
			this.setArm_wrist_angle_theta2(10);

		}
		
		return instrument;
	}
	
	//the status of the arm
	public String ARM_STATUS(){
		
		if (isARM_ON() == false){
			
			this.setShoulder_arm_angle_theta1(20);
			this.setArm_wrist_angle_theta2(10);
			//this.setInstrument("No instrument Arm is OFF");
			//this.setTemperature("");
			//this.setInstrument_time("");
			
			return "OFF";
		}else{
			return "ON";
		}
	}
	
	//turn off instruments
	public void turn_off_instruments(){
		
		this.setDRT(0);
		this.setMAHLI(0);
		this.setDRILL(0);
		this.setAPXS(0);
		this.setCHIMRA(0);
	}
	
	//power the arm
	public long ARM_PWR_ON(){
		
		//output it requires 10 seconds to turn on
		//long currentTime = System.currentTimeMillis()/1000;
		
		//issue delay
		try {
		    Thread.sleep(2000);                
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		
		//long latterTime = 10;
		long arm_boot_time = 10;
		//set On to true;
		setARM_ON(true);
		//this.setInstrument("No instrument selected yet");
		this.setShoulder_arm_angle_theta1(20);
		this.setArm_wrist_angle_theta2(10);
		this.setPower(POWER);
		this.setTime(10);
		
		return arm_boot_time;
	}
	

	public long ARM_PWR_OFF(){
		
		
		//set instruments off
		turn_off_instruments();
		
		//output it requires 10 seconds to turn on
		//long currentTime = System.currentTimeMillis()/1000;
				
		//issue delay
		try {
			Thread.sleep(2000);                
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
				
		//long latterTime = System.currentTimeMillis()/1000;
		long arm_off_time = 5;
		
		//int arm_off_time_int = (int) arm_off_time; 
		//set On to false;
		setARM_ON(false);
		this.setShoulder_arm_angle_theta1(20);
		this.setArm_wrist_angle_theta2(10);
		this.setPower(0);
		this.setTime(5);
		
		return arm_off_time;	
	}
	
	
	public long ARM_PWR_STOW(){
		
		//stowing time less than turn off
		long time = 2;
		//
		//issue delay
		try {
			Thread.sleep(2000);                
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		
		setArm_stow(time);

		//set to stow values
		int time_int = (int) time;
		
		this.setShoulder_arm_angle_theta1(20);
		this.setArm_wrist_angle_theta2(10);
		this.setTime(time_int);
		
		
		return time;
	}
	
	public void setArm_stow(long time){
		
		this.setShoulder_arm_angle_theta1(20);
		this.setArm_wrist_angle_theta2(10);
		this.setDRT(0);
		this.setMAHLI(0);
		this.setDRILL(0);
		this.setAPXS(0);
		this.setCHIMRA(0);
		this.setPower(ARM_POWER_VALUE());
	}
	
	public long ARM_POWER_VALUE(){
		
		long power = POWER;
		
		return power;
		
	}
	
	
	public void printObject(){
		
		System.out.println("===========================================");
		System.out.println("ARM_ON = " + this.ARM_ON);
		System.out.println("CAMERA_ON = " + this.CAMERA_ON);
		System.out.println("shoulder_arm_angle_theta1 = " + this.shoulder_arm_angle_theta1);
		System.out.println("arm_wrist_angle_theta2 = " + this.arm_wrist_angle_theta2);
		System.out.println("DRT = " + this.DRT);
		System.out.println("MAHLI = " + this.MAHLI);
		System.out.println("DRILL = " + this.DRILL);
		System.out.println("APXS = " + this.APXS);
		System.out.println("CHIMRA = " + this.CHIMRA);
		System.out.println("ARM_POWER = " + this.arm_power);
		System.out.println("ARM_TIME = " + this.arm_time);
		System.out.println("===========================================");
	}
	
	
	

	//setters and getters

	public int getShoulder_arm_angle_theta1() {
		return shoulder_arm_angle_theta1;
	}

	public void setShoulder_arm_angle_theta1(int shoulder_arm_angle_theta1) {
		this.shoulder_arm_angle_theta1 = shoulder_arm_angle_theta1;
	}

	public int getArm_wrist_angle_theta2() {
		return arm_wrist_angle_theta2;
	}

	public void setArm_wrist_angle_theta2(int arm_wrist_angle_theta2) {
		this.arm_wrist_angle_theta2 = arm_wrist_angle_theta2;
	}

	public boolean isARM_ON() {
		return ARM_ON;
	}

	public void setARM_ON(boolean aRM_ON) {
		ARM_ON = aRM_ON;
	}

	public int getDRT() {
		return DRT;
	}

	public void setDRT(int dRT) {
		DRT = dRT;
	}

	public int getMAHLI() {
		return MAHLI;
	}

	public void setMAHLI(int mAHLI) {
		MAHLI = mAHLI;
	}

	public int getDRILL() {
		return DRILL;
	}

	public void setDRILL(int dRILL) {
		DRILL = dRILL;
	}

	public int getAPXS() {
		return APXS;
	}

	public void setAPXS(int aPXS) {
		APXS = aPXS;
	}

	public int getCHIMRA() {
		return CHIMRA;
	}

	public void setCHIMRA(int cHIMRA) {
		CHIMRA = cHIMRA;
	}

	public long getPower() {
		return arm_power;
	}

	public void setPower(long power) {
		this.arm_power = power;
	}

	public long getTime() {
		return arm_time;
	}

	public void setTime(long time) {
		this.arm_time = time;
	}

	public boolean isCAMERA_ON() {
		return CAMERA_ON;
	}

	public void setCAMERA_ON(boolean cAMERA_ON) {
		CAMERA_ON = cAMERA_ON;
	}

	public boolean isROVER_MOVE() {
		return ROVER_MOVE;
	}

	public void setROVER_MOVE(boolean rOVER_MOVE) {
		ROVER_MOVE = rOVER_MOVE;
	}


}
