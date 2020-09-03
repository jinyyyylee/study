#include <stdio.h>

#include "MLP.h"
CMLP MultiLayer;

#define MAX_EPOCH 10000

int main() {

	int n;
	int epoch;

	int hlayer[2] = { 3,2 };//ù��° ���̾� 3, �ι�° ���̾� 2��
	MultiLayer.Create(2, 1, 2, hlayer);//�Է³�� 2, ��³�� 1, ���緹�̾�

	int input[4][2] = { {0,0},{0,1},{1,0},{1,1} };
	int danswer[4] =  {0,1,1,0} ;

	MultiLayer.LoadWeight("weight.txt");//����ġ ������ �ִ��� Ȯ��, ������ ���� ����

	for (n = 0; n < 4; n++)
	{
		MultiLayer.pInValue[0] = input[n][0];//�տ� ��Ʈ��ȣ, �ڿ� �Է¹�ȣ
		MultiLayer.pInValue[1] = input[n][1];
		MultiLayer.Forward();//���� ���� ���
		printf("%lf %lf =%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pOutValue[0]);

	}

	double MSE;//��ü���� ������ ������ �����ϱ� ����,mean squared error =��� ���� ����

	printf("******** �н����� ****************\n");
	for (epoch = 0; epoch < MAX_EPOCH; epoch++)
	{
		MSE = 0.0;
		for (n = 0; n < 4; n++)
		{
			MultiLayer.pInValue[0] = input[n][0];		// �Է�����
			MultiLayer.pInValue[1] = input[n][1];		// �Է�����
			MultiLayer.pCorrectOutValue[0] = danswer[n];	// ��������


			MultiLayer.Forward();				// ��°��
			MultiLayer.BackPropagationLearning();	// �н�-����ġ����

			// �����Ŀ� �������� ���
			MultiLayer.Forward();				// ���������� ��� ���
			MSE += (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]) * (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]);

		}
		MSE /= 4;	// ��հ� ���
		printf("Epoch%d(MSE)=%lf\n", epoch, MSE);
		if (MSE < 0.001)
			break;
	}
	printf("******** �н����� ****************\n");

	MultiLayer.SaveWeight("weight.txt");
	// �Է°� ������ ��°� ���
	for (n = 0; n < 4; n++)
	{
		MultiLayer.pInValue[0] = input[n][0];
		MultiLayer.pInValue[1] = input[n][1];
		MultiLayer.Forward();
		printf("%lf %lf =%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pOutValue[0]);
	}
	return 0;
}