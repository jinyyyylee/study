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
	char menu[5][64] = { "[1]학습",
						"[2]Weight저장",
						"[3]Weight읽기",
						"[4]test",
						"[0]종료", };

	for (int i = 0; i < 5; i++) {
		printf("\n%s", menu[i]);
	}
	printf("\n선택메뉴:");
}

void LearningStart() {

	int i, j;

	int x[NUM_TRAINING_SET][NUM_INPUT];
	int d[NUM_TRAINING_SET][NUM_OUTPUT];

	//학습데이터 화일에서 읽기
	FILE *fp = fopen("learningdata.txt", "rt");
	if (fp == NULL)
	{
		printf("\n=>학습데이터를 읽을 수 없습니다.");
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

	//학습
	int epoch, n,p;
	double MSE;//전체적인 에러의 분포를 저장하기 위함,mean squared error =평균 제곱 오차

	printf("******** 학습시작 ****************\n");
	for (epoch = 0; epoch < MAX_EPOCH; epoch++)
	{
		MSE = 0.0;//MSE 값을 0으로 초기화

				  //입력과 전달을 세트수만큼 반복
		//입력과 정답을 전달
		for (n = 0; n < NUM_TRAINING_SET; n++)
		{
			//입력전달
			for (p = 0; p < NUM_INPUT; p++)
			{
				MultiLayer.pInValue[p] = x[n][p]/256.0;//0..1사이의 값 전달
			}
			//정답전달
			for (p = 0; p < NUM_OUTPUT; p++)
				MultiLayer.pCorrectOutValue[p] = d[n][p];

			//출력값계산
			MultiLayer.Forward();
			//역전파 알고리즘에 의한 가중치 갱신
			MultiLayer.BackPropagationLearning();
			//갱신후에 출력과 에러값을 계산
			MultiLayer.Forward();

			for (p = 0; p < NUM_OUTPUT; p++)
				MSE += (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]) * (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]);
		}
		MSE /= NUM_TRAINING_SET;
		//에러값 출력
		printf("Epoch%d(MSE)=%.15f\n", epoch, MSE);
		if (MSE < 0.0001)
			break;
	}
	printf("\n=>학습이 완료되었습니다.");
}
void SaveWeight() {
	MultiLayer.SaveWeight("weight.txt");
	printf("\n=>가중치를 저장했습니다.");
	

}
void LoadWeight() {
	if (MultiLayer.LoadWeight("weight.txt"))
		printf("\n=>가중치 화일을 읽었습니다.");
	else
		printf("\n=>가중치 화일을 읽을 수가 없습니다.");

}
void Test() {
	int i;
	int rgb[3];

	printf("\n=>rgb값을 입력:");
	for (i = 0; i < NUM_INPUT; i++)
		scanf("%d", &rgb[i]);
	for (i = 0; i < NUM_INPUT; i++)
		MultiLayer.pInValue[i] = rgb[i] / 256.0;
	MultiLayer.Forward();

	printf("\n[출력] = %lf,%lf,%lf,%lf,%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pInValue[2],
		MultiLayer.pOutValue[0], MultiLayer.pOutValue[1]);


}