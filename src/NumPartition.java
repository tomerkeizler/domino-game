//package domino;

public class NumPartition {

	public static void main(String[] args) {
		
	}
	
	public static boolean isNumPartition(int n, String s) {
        int counter = 0, sum = 0, powerSum = 0;
    	for (int i = 0; i < n; i = i + 1) {
    		if (s.charAt(i) == '0')
    		{
    		counter = counter + 1;
    		sum = sum + (i + 1);
    		powerSum = powerSum + (int)(Math.pow(i + 1, 2));
    	}
    	else {
    		counter = counter - 1;
    		sum = sum - (i + 1);
    		powerSum = powerSum - (int)(Math.pow(i + 1, 2));
    	}
    	}
    	if (counter == 0 && powerSum == 0 && sum == 0)
    		return true;
    	else
    		return false;
    }

	public static void numPartition (int n) {
			numPartition(n, "");
		}
		
		public static void numPartition (int n, String s) {
			if (s.length() == n) {
				if (isNumPartition(n, s))
					System.out.println(s);
			}
			else {
				numPartition(n, s + "0");
				numPartition(n, s + "1");
			}
		}
}
