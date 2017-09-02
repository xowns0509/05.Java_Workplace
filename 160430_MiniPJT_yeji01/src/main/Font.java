package main;

/**
 * 글씨 설정해주는 클래스 
 * @author yeji
 *
 */
public class Font {
	public static void changeFont ( Component component, Font font ){
		component.setFont ( font );
		if ( component instanceof Container ){
			for ( Component child : ( ( Container ) component ).getComponents () ){
				changeFont ( child, font );
			}
		}
	}
}
