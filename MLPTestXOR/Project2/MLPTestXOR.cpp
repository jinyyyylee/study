#include <stdio.h>

#include "MLP.h"
CMLP MultiLayer;

#define MAX_EPOCH 10000

int main() {

	int n;
	int epoch;

	int hlayer[2] = { 3,2 };//첫번째 레이어 3, 두번째 레이어 2개
	MultiLayer.Create(2, 1, 2, hlayer);//입력노드 2, 출력노드 1, 히든레이어

	int input[4][2] = { {0,0},{0,1},{1,0},{1,1} };
	int danswer[4] =  {0,1,1,0} ;

	MultiLayer.LoadWeight("weight.txt");//가중치 파일이 있는지 확인, 없으면 새로 생성

	for (n = 0; n < 4; n++)
	{
		MultiLayer.pInValue[0] = input[n][0];//앞에 세트번호, 뒤에 입력번호
		MultiLayer.pInValue[1] = input[n][1];
		MultiLayer.Forward();//현재 상태 출력
		printf("%lf %lf =%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pOutValue[0]);

	}

	double MSE;//전체적인 에러의 분포를 저장하기 위함,mean squared error =평균 제곱 오차

	printf("******** 학습시작 ****************\n");
	for (epoch = 0; epoch < MAX_EPOCH; epoch++)
	{
		MSE = 0.0;
		for (n = 0; n < 4; n++)
		{
			MultiLayer.pInValue[0] = input[n][0];		// 입력전달
			MultiLayer.pInValue[1] = input[n][1];		// 입력전달
			MultiLayer.pCorrectOutValue[0] = danswer[n];	// 정답전달


			MultiLayer.Forward();				// 출력계산
			MultiLayer.BackPropagationLearning();	// 학습-가중치갱신

			// 갱신후에 에러값을 계산
			MultiLayer.Forward();				// 갱신이후의 출력 계산
			MSE += (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]) * (MultiLayer.pCorrectOutValue[0] - MultiLayer.pOutValue[0]);

		}
		MSE /= 4;	// 평균값 계산
		printf("Epoch%d(MSE)=%lf\n", epoch, MSE);
		if (MSE < 0.001)
			break;
	}
	printf("******** 학습종료 ****************\n");

	MultiLayer.SaveWeight("weight.txt");
	// 입력과 현재의 출력값 계산
	for (n = 0; n < 4; n++)
	{
		MultiLayer.pInValue[0] = input[n][0];
		MultiLayer.pInValue[1] = input[n][1];
		MultiLayer.Forward();
		printf("%lf %lf =%lf\n", MultiLayer.pInValue[0], MultiLayer.pInValue[1], MultiLayer.pOutValue[0]);
	}
	return 0;
}