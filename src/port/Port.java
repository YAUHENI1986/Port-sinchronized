package port;

public class Port {
	
	private int dockQtyMax;
	private int currentDockQty;
	private int containersCapacity;
	private int currentContainersQty;

	public Port(int dockQtyMax, int containersCapacity, int currentContainersQty) {
		super();
		this.dockQtyMax = dockQtyMax;
		this.currentDockQty = dockQtyMax;
		this.currentContainersQty = currentContainersQty;
		this.containersCapacity = containersCapacity;
	}	

	public int getDockQtyMax() {
		return dockQtyMax;
	}

	public int getContainersCapacity() {
		return containersCapacity;
	}
	
	public int getCurrentDockQty() {
		return currentDockQty;
	}

	public void setCurrentDockQty(int dockQty) {
		this.currentDockQty = dockQty;
	}

	public int getCurrentContainersQty() {
		return currentContainersQty;
	}
	
	public void addContainer() {
		currentContainersQty++;
	}

	public void takeConatiner() {
		currentContainersQty--;
	}
	
	public boolean askPermission() {
		if(getCurrentDockQty() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return false;
		} else {
			System.out.println(Thread.currentThread().getName() + " has received permission");
			setCurrentDockQty(getCurrentDockQty() - 1);
			return true;
		}		
	}
	
	public void returnPermission() {
		setCurrentDockQty(getCurrentDockQty() + 1);
		notifyAll();
	}

}
