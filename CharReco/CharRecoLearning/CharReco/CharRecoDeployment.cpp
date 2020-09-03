#include <stdio.h>

#include "MLP.h"
CMLP MultiLayer;

#define NUM_TRAINING_SET  3
#define NUM_INPUT         9
#define NUM_OUTPUT        3

int main() {

	int n, p;
	int epoch;

	//신경망 네트워크 구성
	int hlayer[1] = { 2 };
	MultiLayer.Create(NUM_INPUT, NUM_OUTPUT, 1, hlayer);

	if (MultiLayer.LoadWeight("..\\Weight\\weight.txt"))
	{
		printf("가중치를 읽었습니다.\n");
	}
	else
	{
		printf("가중치를 읽을 수 없습니다.\n");
		return 0;
	}

	int test_input[9] = { 1,1,0,
							1,1,0,
							1,1,1 };
	for (p = 0; p < NUM_INPUT; p++)
	{
		MultiLayer.pInValue[p] = test_input[p];
	}
	MultiLayer.Forward();

	for (int i = 0; i < 3; i++)
	{
		for (int j = 0; j < 3; j++)
		{
			printf("%.0f", MultiLayer.pInValue[i * 3 + j]);
		}
		printf("\n");
	}
	printf("= %lf, %lf,%lf\n", MultiLayer.pOutValue[0], MultiLayer.pOutValue[1], MultiLayer.pOutValue[2]);




	return 0;
}