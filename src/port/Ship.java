package port;

import java.util.Random;

public class Ship implements Runnable{
	
	private String nameShip;
	private int conteinerMax;
	private int conteinerToTake;
	private int conteinerToLeave;
	private int currentConteiners;
	Port port;
	Thread thread;
	

	public Ship(String nameShip, int conteinerMax, Port port) {
		super();
		this.nameShip = nameShip;
		this.conteinerMax = conteinerMax;
		this.conteinerToTake = generateConteinerToTake();
		this.conteinerToLeave = generateConteinerToLeave();
		this.currentConteiners = conteinerToLeave;
		this.port = port;
		thread = new Thread(this, nameShip);
	}
	
	private int generateConteinerToTake() {
		Random random = new Random();
		conteinerToTake = random.nextInt(conteinerMax);
		return conteinerToTake;		
	}
	
	private int generateConteinerToLeave() {
		Random random = new Random();
		conteinerToLeave = random.nextInt(conteinerMax);
		return conteinerToLeave;		
	}

	public String getNameShip() {
		return nameShip;
	}	

	public int getConteinerToTake() {
		return conteinerToTake;
	}

	public int getConteinerToLeave() {
		return conteinerToLeave;
	}

	public int getCurrentConteiners() {
		return currentConteiners;
	}

	@Override
	public void run() {
		while(true) {
			synchronized (port) {
				if(port.askPermission()) {
					break;
				}
			}			
		}
		System.out.println("Ship " + nameShip + " is mooring");
		System.out.println("...............Port " + port.getCurrentContainersQty() + ", ship current conteiners " + currentConteiners + ", ship take conteiners " + conteinerToTake + "...............");
		try {
			Thread.currentThread();
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(conteinerToLeave != 0) {
			do {
				if(port.getContainersCapacity() != port.getCurrentContainersQty()) {
					synchronized (port) {
						port.addContainer();
					}
					conteinerToLeave--;
					currentConteiners--;
				} else {
					System.out.println("Port have MAX containers!!!");
					break;
				}
			} while (conteinerToLeave != 0);
		}
		if(conteinerToTake != 0) {
			do {
				if(port.getCurrentContainersQty() != 0) {
					synchronized (port) {
						port.takeConatiner();
					}
					conteinerToTake--;
					currentConteiners++;
				} else {
				System.out.println("Port have 0 containers!!!");
					break;
				}
			} while (conteinerToTake != 0);
		}
		System.out.println("Ship " + nameShip + " completed wokr and leaves port");
		System.out.println("...............Port " + port.getCurrentContainersQty() + "...............");
		try {
			Thread.currentThread();
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (port) {
			port.returnPermission();
		}
	}

	@Override
	public String toString() {
		return "Ship [nameShip=" + nameShip + ", conteinerMax=" + conteinerMax + ", conteinerToTake=" + conteinerToTake
				+ ", conteinerToLeave=" + conteinerToLeave + ", port=" + port + "]";
	}
}
