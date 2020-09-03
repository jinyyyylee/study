#include <stdio.h>

#include "MLP.h"
CMLP MultiLayer;

#define NUM_TRAINING_SET  3
#define NUM_INPUT         9
#define NUM_OUTPUT        3

#define MAX_EPOCH   10000000

int main() {

	int n, p;
	int epoch;

	//�Ű�� ��Ʈ��ũ ����
	int hlayer[1] = { 2 };
	MultiLayer.Create(NUM_INPUT, NUM_OUTPUT, 1, hlayer);


	double x[NUM_TRAINING_SET][NUM_INPUT] = { {	1,1,1,
												0,0,1,
												0,0,1},
											{	1,0,0,
												1,0,0,
												1,1,1},
											{	1,1,1,
												1,0,0,
												1,1,1} };
											//��,��,��
	double d[NUM_TRAINING_SET][NUM_OUTPUT] = { {1,0,0},{0,1,0},{0,0,1} };

	if (MultiLayer.LoadWeight("..\\Weight\\weight.txt"))
	{
		printf("������ ����ġ�κ��� �н��� �����մϴ�.\n");
	}
	else
	{
		printf("���� ����ġ�κ��� ó������ �����մϴ�.\n");
	}
	for (n = 0; n < NUM_TRAINING_SET; n++)
	{
		for (p = 0; p < NUM_INPUT; p++)
		{
			MultiLayer.pInValue[p] = x[n][p];
		}
		MultiLayer.Forward();
		for (p = 0; p < NUM_INPUT; p++)
			printf("%.0f", MultiLayer.pInValue[p]);
		printf("= %lf, %lf,%lf\n", MultiLayer.pOutValue[0], MultiLayer.pOutValue[1], MultiLayer.pOutValue[2]);

	}
	getchar();//ó���� �ʱ���¸� Ȯ���� �� �ֵ���

	//�н�
	double MSE;//��ü���� ������ ������ �����ϱ� ����,mean squared error =��� ���� ����

	printf("******** �н����� ****************\n");
	for (epoch = 0; epoch < MAX_EPOCH; epoch++)
	{
		MSE = 0.0;//MSE ���� 0���� �ʱ�ȭ

				  //�Է°� ������ ��Ʈ����ŭ �ݺ�
		for (n = 0; n < NUM_TRAINING_SET; n++)
		{

			//�Է�����
			for (p = 0; p < NUM_INPUT; p++)
			{
				MultiLayer.pInValue[p] = x[n][p];
			}
			//���� ����
			for (p = 0; p < NUM_OUTPUT; p++)
				MultiLayer.pCorrectOutValue[p] = d[n][p];
			//��°� ���
			MultiLayer.Forward();
			//������ �˰��� ���� ����ġ ����
			MultiLayer.BackPropagationLearning();
			//�����Ŀ� ��°� �������� ���
			MultiLayer.Forward();

			for (p = 0; p < NUM_OUTPUT; p++)
				MSE += (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]) * (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]);
		}
		MSE /= NUM_TRAINING_SET;
		//������ ���
		printf("Epoch%d(MSE)=%.15f\n", epoch, MSE);
		if (MSE < 0.0001)
			break;
	}
	MultiLayer.SaveWeight("..\\Weight\\weight.txt");//����

	for (n = 0; n < NUM_TRAINING_SET; n++)
	{
		for (p = 0; p < NUM_INPUT; p++)
		{
			MultiLayer.pInValue[p] = x[n][p];
		}
		MultiLayer.Forward();
		for (p = 0; p < NUM_INPUT; p++)
			printf("%.0f", MultiLayer.pInValue[p]);
		printf("= %lf, %lf,%lf\n", MultiLayer.pOutValue[0], MultiLayer.pOutValue[1], MultiLayer.pOutValue[2]);

	}



	return 0;
}