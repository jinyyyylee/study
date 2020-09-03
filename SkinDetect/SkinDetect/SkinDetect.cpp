#include<stdio.h>
#include<conio.h>

#include "MLP.h"
CMLP MultiLayer;

#define NUM_TRAINING_SET 20
#define NUM_INPUT 3
#define NUM_OUTPUT 2

#define MAX_EPOCH 100000

void DisplayMenu();
void LearningStart();
void SaveWeight();
void LoadWeight();
void Test();

int main() {
	int hlayer[2] = { 5,3 };
	MultiLayer.Create(NUM_INPUT,NUM_OUTPUT,2,hlayer);

	int i, j;
	char ch;
	DisplayMenu();

	while ((ch = getche()) != EOF) {
		switch (ch) {
		case '1':
			LearningStart();
			break;
		case '2':
			SaveWeight();
			break;
		case '3':
			LoadWeight();
			break;
		case '4':
			Test();
			break;
		case '0':
			return 0;
			break;
		}
		DisplayMenu();
	}
	return 0;
}

void DisplayMenu() {
	char menu[5][64] = { "[1]�н�",
						"[2]Weight����",
						"[3]Weight�б�",
						"[4]test",
						"[0]����", };

	for (int i = 0; i < 5; i++) {
		printf("\n%s", menu[i]);
	}
	printf("\n���ø޴�:");
}

void LearningStart() {

	int i, j;

	int x[NUM_TRAINING_SET][NUM_INPUT];
	int d[NUM_TRAINING_SET][NUM_OUTPUT];

	//�н������� ȭ�Ͽ��� �б�
	FILE *fp = fopen("learningdata.txt", "rt");
	if (fp == NULL)
	{
		printf("\n=>�н������͸� ���� �� �����ϴ�.");
		return;
	}
	for (i = 0; i < NUM_TRAINING_SET; i++)
	{
		for (j = 0; j < NUM_INPUT; j++)
		{
			fscanf(fp, "%d", &x[i][j]);
		}
		for (j = 0; j < NUM_OUTPUT; j++)
			fscanf(fp, "%d", &d[i][j]);
	}
	fclose(fp);

	//�н�
	int epoch, n,p;
	double MSE;//��ü���� ������ ������ �����ϱ� ����,mean squared error =��� ���� ����

	printf("******** �н����� ****************\n");
	for (epoch = 0; epoch < MAX_EPOCH; epoch++)
	{
		MSE = 0.0;//MSE ���� 0���� �ʱ�ȭ

				  //�Է°� ������ ��Ʈ����ŭ �ݺ�
		//�Է°� ������ ����
		for (n = 0; n < NUM_TRAINING_SET; n++)
		{
			//�Է�����
			for (p = 0; p < NUM_INPUT; p++)
			{
				MultiLayer.pInValue[p] = x[n][p]/256.0;//0..1������ �� ����
			}
			//��������
			for (p = 0; p < NUM_OUTPUT; p++)
				MultiLayer.pCorrectOutValue[p] = d[n][p];

			//��°����
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
	printf("\n=>�н��� �Ϸ�Ǿ����ϴ�.");
}
void SaveWeight() {
	MultiLayer.SaveWeight("weight.txt");
	printf("\n=>����ġ�� �����߽��ϴ�.");
	

}
void LoadWeight() {
	if (MultiLayer.LoadWeight("weight.txt"))
		printf("\n=>����ġ ȭ���� �о����ϴ�.");
	else
		printf("\n=>����ġ ȭ���� ���� ���� �����ϴ�.");

}
void Test() {
	int i;
	int rgb[3];

	printf("\n=>rgb���� �Է�:");
	for (i = 0; i < NUM_INPUT; i++)
		scanf("%d", &rgb[i]);
	for (i = 0; i < NUM_INPUT; i++)
		MultiLayer.pInValue[i] = rgb[i] / 256.0;
	MultiLayer.Forward();

	printf("\n[���] = %lf,%lf,%lf,%lf,%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pInValue[2],
		MultiLayer.pOutValue[0], MultiLayer.pOutValue[1]);


}