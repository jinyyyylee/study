#include <stdio.h>//����� ���α׷��� ����

#include "MLP.h"//���� Ŭ������ ���� ����, Ŭ���� ��� ���� �߰�
CMLP MultiLayer;

int main()
{
	int hlayer[1] = { 2 };//���緹�̾��� �迭�� 1�� ����� ������ 2��
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
	
	printf("�й� : 20171222\n");
	printf("�̸� : ������\n");
	printf("���� : ������ �����Ű��\n\n");

	int n;
	for (n = 0; n < 4; n++)//x�� 0���� 4������ 
	{
		MultiLayer.pInValue[0] = x[n][0];
		MultiLayer.pInValue[1] = x[n][1];

		MultiLayer.Forward();//������ ����� forward���� ���


		printf("%lf %lf=%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pOutValue[0]);
	}

	printf("\n\n����ġ ��°�\n\n");
	
	printf("%lf   %lf   bias = %lf\n", MultiLayer.m_Weight[0][0][0], MultiLayer.m_Weight[0][1][0], MultiLayer.m_Weight[0][2][0]);
	printf("%lf   %lf   bias = %lf\n", MultiLayer.m_Weight[0][0][1], MultiLayer.m_Weight[0][1][1], MultiLayer.m_Weight[0][2][1]);
	printf("%lf   %lf   bias = %lf\n", MultiLayer.m_Weight[1][0][0], MultiLayer.m_Weight[1][1][0], MultiLayer.m_Weight[1][2][0]);

	return 0;
}
