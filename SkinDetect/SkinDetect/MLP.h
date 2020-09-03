#pragma once
#define MAXLAYER  100// �ִ� ���̾��

#define LEARNING_RATE 0.1

class CMLP
{
public:
	CMLP();
	~CMLP();

	//�Ű�� ���������� ���� ����
	int m_iNumInNodes;
	int m_iNumOutNodes;
	int m_iNumHiddenLayer;//hidden only
	int m_iNumTotalLayer;// input layer + hiddenlayer+outputlayer
	int m_iNumNodes[MAXLAYER];

	double** m_Weight[MAXLAYER];//[����layer][���۳��][������]
	double** m_NodeOut;         //[layer][node]

	double** m_ErrorGrident;

	double* pInValue, * pOutValue;
	double* pCorrectOutValue;

	bool Create(int InNodes, int OutNode, int hiddenLayer, int* pHiddenNode);
private:

	void InitW();

	double ActivationFunc(double u);

public:
	void Forward();

	void BackPropagationLearning();

	bool SaveWeight(const char* fname);
	bool LoadWeight(const char* fname);
};

