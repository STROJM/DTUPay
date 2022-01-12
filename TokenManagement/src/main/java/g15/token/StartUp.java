package g15.token;

public class StartUp {
	public static void main(String[] args) throws Exception {
		new StartUp().startUp();
	}

	private void startUp() throws Exception {
		while(true){
			Thread.sleep(1000);
			System.out.println("up");
		}
	}
}
