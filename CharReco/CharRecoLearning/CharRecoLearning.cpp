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

	//신경망 네트워크 구성
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
											//ㄱ,ㄴ,ㄷ
	double d[NUM_TRAINING_SET][NUM_OUTPUT] = { {1,0,0},{0,1,0},{0,0,1} };

	if (MultiLayer.LoadWeight("..\\Weight\\weight.txt"))
	{
		printf("기존의 가중치로부터 학습을 시작합니다.\n");
	}
	else
	{
		printf("랜덤 가중치로부터 처음으로 시작합니다.\n");
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
	getchar();//처음의 초기상태를 확인할 수 있도록

	//학습
	double MSE;//전체적인 에러의 분포를 저장하기 위함,mean squared error =평균 제곱 오차

	printf("******** 학습시작 ****************\n");
	for (epoch = 0; epoch < MAX_EPOCH; epoch++)
	{
		MSE = 0.0;//MSE 값을 0으로 초기화

				  //입력과 전달을 세트수만큼 반복
		for (n = 0; n < NUM_TRAINING_SET; n++)
		{

			//입력전달
			for (p = 0; p < NUM_INPUT; p++)
			{
				MultiLayer.pInValue[p] = x[n][p];
			}
			//정답 전달
			for (p = 0; p < NUM_OUTPUT; p++)
				MultiLayer.pCorrectOutValue[p] = d[n][p];
			//출력값 계산
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
	MultiLayer.SaveWeight("..\\Weight\\weight.txt");//저장

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