package port;

import java.util.ArrayList;
import java.util.List;

public class Main {
	
	public static void main(String[] args) {
		Port port = new Port(1, 40, 20);
		
		System.out.println("Build List Ships");
		List<Ship> ships = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			ships.add(new Ship("Ship " + i, 10, port));
		}

		try {
			System.out.println("Sleep main 2s");
			Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
				}		
		
		for (Ship ship: ships) {
			ship.thread.start();
		}
		
		for (Ship ship : ships){
			try {
				ship.thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
		}
		
		System.out.println("All ships have finished their task");
	}
}
