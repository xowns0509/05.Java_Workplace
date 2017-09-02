
public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// - 소스 코드와 수행결과를 Paste해 주세요. (작성한 코드를 인터넷 등 외부에 공개하지 마시오.)
		// - Java, C, C++ 중 택1.
		//
		// * 읽기 좋은 코드가 좋은 코드 입니다. 읽기 좋은 코드로 작성해 주세요.
		// ** 시간 복잡도와 메모리 이슈, 최적의 성능에 중점을 두세요.
		// *** 요구 사항이 모호하다면, 그 부분을 재정의하여 서술하고 구현하시오.
		//
		//
		// 문제 ) A는 해외 여행을 가려하며, 경비로 한화 100만원을 예상한다.
		// 방문지의 화폐단위는 N이며, 100N, 50N, 20N, 10N, 5N, 1N 의 지폐가 존재한다.
		//
		// 환전하려는 A의 요구사항은 다음과 같다.
		// * 지폐 종류별 1장 이상씩 환전 하었으면 좋겠다.
		// * 100N 은 4장, 50N 은 4장만 환전하고 싶다.
		// * 1N 은 20장 이상 환전하고 싶다.
		// * 20N부터 1N까지 라운드로빈(화폐단위별 순차적으로 1장씩)으로 환전하며, 우리돈 2,000원 이하는 잔돈으로 받고
		// 싶다.
		// (특정단위 추가하여 100만원이 넘으면 그 단위 제외하고 다시 라운드로빈)
		//
		// 100만원으로 환전할 수 있는 화폐의 장수와 환전 후 남은 잔돈을 출력하시오.
		// ( 환율은 1N 에 한화 1,322원으로 한다. )
		//
		//
		// 출력 예)
		// 100N : 4장
		// 50N : 4장
		// 20N : 4장
		// 10N : 3장
		// 5N : 4장
		// 1N : 25장
		int tot = 1000000;
		int balance = 0; // 잔액

		int n = 1322; // 환율
		int n100 = n * 100;
		int n50 = n * 50;
		int n20 = n * 20;
		int n10 = n * 10;
		int n5 = n * 5;
		int n1 = n * 1;

		int cnt100 = 4;
		int cnt50 = 4;
		int cnt20 = 1;
		int cnt10 = 1;
		int cnt5 = 1;
		int cnt1 = 1;

		balance = tot - n100 * cnt100 + n50 * cnt50;

		System.out.println("RR처리 전: " + balance);

		// 잔돈 2000원 이상
		while (balance > 2000) {
			// * 20N부터 1N까지 라운드로빈(화폐단위별 순차적으로 1장씩)으로 환전
			balance = balance - n20;
			cnt20++;
			balance = balance - n10;
			cnt10++;
			balance = balance - n5;
			cnt5++;
			balance = balance - n1;
			cnt1++;
			System.out.println("WHILE balance: " + balance);

		}
		// 잔돈 2000원 이하일 때

		System.out.println("100N: " + cnt100);
		System.out.println("50N " + cnt50);
		System.out.println("20N " + cnt20);
		System.out.println("10N " + cnt10);
		System.out.println("5N " + cnt5);
		System.out.println("1N " + cnt1);

		System.out.println("잔돈: " + balance);

		// 100N : 4장
		// 50N : 4장
		// 20N : 1장 이상
		// 10N : 1장 이상
		// 5N : 1장 이상
		// 1N : 20장 이상

		// 잔돈으로 인출
		// 잔돈 : 1890원

	}

}
