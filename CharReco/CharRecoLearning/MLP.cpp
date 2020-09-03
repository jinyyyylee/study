#include "MLP.h"
#include <malloc.h> //malloc()를 위해 추가
#include <stdlib.h> //srand()를위해추가

#include <time.h> // time()를위해 추가
#include <math.h> 
#include <stdio.h>


CMLP::CMLP()
{
	int i;

	m_NodeOut = NULL;

	for (i = 0; i < MAXLAYER; i++)
		m_Weight[i] = NULL;
	m_ErrorGrident = NULL;

}

CMLP::~CMLP()
{
	int i, j, k;

	if (m_NodeOut != NULL)
	{
		for (i = 0; i < (m_iNumTotalLayer + 1); i++)
			free(m_NodeOut[i]);
		free(m_NodeOut);
	}

	for (k = 0; k < MAXLAYER; k++)
	{
		if (m_Weight[k] != NULL)
		{
			for (j = 0; j < m_iNumNodes[k] + 1; j++)
				free(m_Weight[k][j]);
			free(m_Weight[k]);
		}
	}
	if (m_ErrorGrident != NULL)
	{
		for (i = 0; i < (m_iNumTotalLayer); i++)
			free(m_ErrorGrident[i]);
		free(m_ErrorGrident);
	}

}



bool CMLP::Create(int InNode, int OutNode, int HiddenLayer, int* pHiddenNode)
{
	int i, j;

	m_iNumInNodes = InNode;
	m_iNumOutNodes = OutNode;
	m_iNumHiddenLayer = HiddenLayer;// 입력,출력층 제외
	m_iNumTotalLayer = HiddenLayer + 2;//전체 Layer 개수에 입,출력까지 포함되었으므로 +2

	m_iNumNodes[0] = m_iNumInNodes;
	for (i = 0; i < m_iNumHiddenLayer; i++)
		m_iNumNodes[1 + i] = pHiddenNode[i];
	m_iNumNodes[m_iNumHiddenLayer + 1] = m_iNumOutNodes;

	// 각 노드별 출력 메모리 할당=pNodeOut[layerno][nodeno]
	// 입력pNodeOut[0][], 출력pNodeOut[m_iNumHiddenLayer+1][]
	m_NodeOut = (double**)malloc((m_iNumTotalLayer) * sizeof(double*));
	for (i = 0; i < m_iNumTotalLayer; i++)
		m_NodeOut[i] = (double*)malloc(m_iNumNodes[i] * sizeof(double));
	m_NodeOut[i] = (double*)malloc(m_iNumOutNodes * sizeof(double));//정답
	// 가중치 메모리 할당=m_Weight[시작layerno][시작노드][연결노드]
	for (j = 0; j < m_iNumTotalLayer - 1; j++)//가중치
	{
		m_Weight[j] = (double**)malloc((m_iNumNodes[j] + 1) * sizeof(double*));// 바이어스를위해+1
		for (i = 0; i < m_iNumNodes[j] + 1; i++)
			m_Weight[j][i] = (double*)malloc(m_iNumNodes[j + 1] * sizeof(double));//연결되어 있는 다음 레이어에 개수만큼 받아야하기 때문
	}

	// 입력레이어, 출력레이어, 정답레이어위치
	pInValue = m_NodeOut[0];//입력
	pOutValue = m_NodeOut[m_iNumTotalLayer - 1];//출력
	pCorrectOutValue = m_NodeOut[m_iNumTotalLayer];//그다음레이어

	InitW();//가중치 초기화를 위함

	return false;

}


void CMLP::InitW()
{
	int layer, snode, enode;

	srand(time(NULL));
	for (layer = 0; layer < m_iNumTotalLayer - 1; layer++)//layer
	{
		for (snode = 0; snode < m_iNumNodes[layer] + 1; snode++)    // for 바이어스를 위해 +1, snode
		{
			for (enode = 0; enode < m_iNumNodes[layer + 1]; enode++)//enode
			{
				m_Weight[layer][snode][enode] = (double)rand() / RAND_MAX - 0.5;
			}
		}
	}

}

void CMLP::Forward()
{
	int layer, snode, enode;
	double wsum;// 가중합

	for (layer = 0; layer < m_iNumTotalLayer -1; layer++)
	{
		for (enode = 0; enode < m_iNumNodes[layer + 1]; enode++)
		{
			wsum = 0.0;	// 노드별 가중합
			for (snode = 0; snode < m_iNumNodes[layer]; snode++)
			{
				wsum += (m_Weight[layer][snode][enode] * m_NodeOut[layer][snode]);
			}
			wsum += m_Weight[layer][snode][enode] * 1;// 바이어스
			m_NodeOut[layer + 1][enode] = ActivationFunc(wsum);
		}
	}

}
double CMLP::ActivationFunc(double u)
{
	// step func
// if(u>0)   return 1.0;
// else      return 1.0;


// sigmoid

	return 1 / (1 + exp(-u));
}





void CMLP::BackPropagationLearning()
{
	int i, j;

	if (m_ErrorGrident == NULL)
	{
		// 각노드별 출력메모리할당=m_ErrorGrident[layerno][nodeno]
		// 입력:m_ErrorGrident[0][],출력m_ErrorGrident[m_iNumTotalLayer-1][]
		m_ErrorGrident = (double**)malloc((m_iNumTotalLayer) * sizeof(double*));	//
		for (i = 0; i < m_iNumTotalLayer; i++)
			m_ErrorGrident[i] = (double*)malloc(m_iNumNodes[i] * sizeof(double));
	}
	int layer, snode, enode;

	// 출력층error경사계산
	for (snode = 0; snode < m_iNumNodes[m_iNumHiddenLayer + 1]; snode++)
	{
		m_ErrorGrident[m_iNumHiddenLayer + 1][snode] =
			(m_NodeOut[m_iNumHiddenLayer + 2][snode] - m_NodeOut[m_iNumHiddenLayer + 1][snode])
			* m_NodeOut[m_iNumHiddenLayer + 1][snode] * (1 - m_NodeOut[m_iNumHiddenLayer + 1][snode]);
	}

	// error경사계산
	for (layer = m_iNumHiddenLayer; layer >= 0; layer--)
	{
		for (snode = 0; snode < m_iNumNodes[layer]; snode++)
		{
			m_ErrorGrident[layer][snode] = 0.0;
			for (enode = 0; enode < m_iNumNodes[layer + 1]; enode++)
			{
				m_ErrorGrident[layer][snode] += (m_ErrorGrident[layer + 1][enode] * m_Weight[layer][snode][enode]);
			}
			m_ErrorGrident[layer][snode] *= m_NodeOut[layer][snode] * (1 - m_NodeOut[layer][snode]);
		}
	}

	// 가중치갱신
	for (layer = m_iNumHiddenLayer; layer >= 0; layer--)
	{
		for (enode = 0; enode < m_iNumNodes[layer + 1]; enode++)
		{
			for (snode = 0; snode < m_iNumNodes[layer]; snode++)
			{
				m_Weight[layer][snode][enode] += (LEARNING_RATE * m_ErrorGrident[layer + 1][enode] * m_NodeOut[layer][snode]);
			}
			m_Weight[layer][snode][enode] += (LEARNING_RATE * m_ErrorGrident[layer + 1][enode] * 1);// 바이어스
		}
	}

}




bool CMLP::SaveWeight(const char* fname)
{
	int layer, snode, enode;

	FILE* fp;

	if ((fp = fopen(fname, "wt")) == NULL)
		return false;

	// innode hlayer outnode
	fprintf(fp, "%d %d %d\n", m_iNumInNodes, m_iNumHiddenLayer, m_iNumOutNodes);
	// node_layer0 node_layer1 node_layer2......
	for (layer = 0; layer < m_iNumTotalLayer; layer++)
	{
		fprintf(fp, "%d ", m_iNumNodes[layer]);
	}
	fprintf(fp, "\n");

	// save weight
	for (layer = 0; layer < m_iNumTotalLayer - 1; layer++)
	{
		for (enode = 0; enode < m_iNumNodes[layer + 1]; enode++)
		{
			for (snode = 0; snode < m_iNumNodes[layer]; snode++)
			{
				fprintf(fp, "%.9lf ", m_Weight[layer][snode][enode]);
			}
			fprintf(fp, "%.9lf ", m_Weight[layer][snode][enode]);	// 바이어스
		}
		fprintf(fp, "\n");
	}
	fclose(fp);

	return true;
}


bool CMLP::LoadWeight(const char* fname)
{
	int layer, snode, enode;

	FILE* fp;

	if ((fp = fopen(fname, "rt")) == NULL)
		return false;

	// innode hlayer outnode 
	fscanf(fp, "%d %d %d", &m_iNumInNodes, &m_iNumHiddenLayer, &m_iNumOutNodes);
	// node_layer0 node_layer1 node_layer2......
	for (layer = 0; layer < m_iNumTotalLayer; layer++)
	{
		fscanf(fp, "%d ", &m_iNumNodes[layer]);
	}

	// load weight,가중치 저장
	for (layer = 0; layer < m_iNumTotalLayer - 1; layer++)
	{
		for (enode = 0; enode < m_iNumNodes[layer + 1]; enode++)
		{
			for (snode = 0; snode < m_iNumNodes[layer]; snode++)
			{
				fscanf(fp, "%lf ", &m_Weight[layer][snode][enode]);
			}
			fscanf(fp, "%lf ", &m_Weight[layer][snode][enode]);	// 바이어스
		}
	}
	fclose(fp);

	return true;

}
