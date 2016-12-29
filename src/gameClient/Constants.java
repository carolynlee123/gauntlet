package gameClient;

public class Constants {
	public static String imageFolder = "images/";
	public static String spaceInvadersImages = "images/space_invaders/";
	public static String alienImage = "images/space_invaders/alien.png";
	public static String shipImage = "images/space_invaders/ship.png";
	public static String blastImage = "images/space_invaders/blast.png";
	
//	public static int alienDrawScale = 12;
	public static double alienWidthScale = 1.0/12;
	public static double alienHeightScale = 1.0/12;
	public static double shipWidthScale = 1.0/12;
	public static double shipHeightScale = 1.0/12;
	public static double shieldWidthScale = 1.0/8;
	public static double shieldHeightScale = 1.0/8;
	public static double blastWidthScale = 1.0/64;
	public static double blastHeightScale = 1.0/16;
	
	public static double alienStartX = 1.0/8;
	public static double alienStartY = 1.0/8;
	
	public static double alienWaveDownDist = 1.0/64;
	
	public static double shipStartX = 1.0/2;
	public static double shipStartY = 14.5/16;
	
	public static double shieldStartY = 2.5 / 4;
	
	public static double bufferScale = 1.0/16;
	public static int shipImageScale = 12;
	
	public static Vector2D zeroVector = new Vector2D(0,0);
	public static Vector2D i = new Vector2D(new Point2D(1,0));
	public static Vector2D ni = new Vector2D(new Point2D(-1,0));
	public static Vector2D j = new Vector2D(new Point2D(0,1));
	
	public static Point2D startPoint = new Point2D(0,0);
	
	public static String right = "right";
	public static String left = "left";
}
