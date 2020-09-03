#include <stdio.h>//입출력 프로그램을 위함

#include "MLP.h"//만든 클래스를 쓰가 위함, 클래스 헤더 파일 추가
CMLP MultiLayer;

int main()
{
	int hlayer[1] = { 2 };//히든레이어의 배열은 1개 노드의 개수는 2개
	MultiLayer.Create(2, 1, 1, hlayer);

	double x[4][2] = { {0,0},{ 0,1 },{ 1,0 },{ 1,1 } };


	MultiLayer.m_Weight[0][0][0] = 4.7621;
	MultiLayer.m_Weight[0][1][0] = 4.7618;
	MultiLayer.m_Weight[0][2][0] = -7.3061;

	MultiLayer.m_Weight[0][0][1] = 6.3917;
	MultiLayer.m_Weight[0][1][1] = 6.3917;
	MultiLayer.m_Weight[0][2][1] = -2.8441;

	MultiLayer.m_Weight[1][0][0] = -10.3788;
	MultiLayer.m_Weight[1][1][0] = 9.7691;
	MultiLayer.m_Weight[1][2][0] = -4.5589;
	
	printf("학번 : 20171222\n");
	printf("이름 : 이진영\n");
	printf("과제 : 전방향 다층신경망\n\n");

	int n;
	for (n = 0; n < 4; n++)//x의 0부터 4까지의 
	{
		MultiLayer.pInValue[0] = x[n][0];
		MultiLayer.pInValue[1] = x[n][1];

		MultiLayer.Forward();//순방향 결과는 forward에서 계산


		printf("%lf %lf=%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pOutValue[0]);
	}

	printf("\n\n가중치 출력값\n\n");
	
	printf("%lf   %lf   bias = %lf\n", MultiLayer.m_Weight[0][0][0], MultiLayer.m_Weight[0][1][0], MultiLayer.m_Weight[0][2][0]);
	printf("%lf   %lf   bias = %lf\n", MultiLayer.m_Weight[0][0][1], MultiLayer.m_Weight[0][1][1], MultiLayer.m_Weight[0][2][1]);
	printf("%lf   %lf   bias = %lf\n", MultiLayer.m_Weight[1][0][0], MultiLayer.m_Weight[1][1][0], MultiLayer.m_Weight[1][2][0]);

	return 0;
}
