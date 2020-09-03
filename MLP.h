#pragma once
#define MAXLAYER  100// 최대 레이어스

class CMLP
{
public:
	CMLP();
	~CMLP();

	int m_iNumInNodes;
	int m_iNumOutNodes;
	int m_iNumHiddenLayer;
	int m_iNumTotalLayer;
	int m_iNumNodes[MAXLAYER];

	double** m_Weight[MAXLAYER];
	double** m_NodeOut;

	double* pInValue, * pOutValue;
	double* pCorrectOutValue;

	bool Create(int InNodes, int OutNode, int hiddenLayer, int* pHiddenNode);
private:

	void InitW();

	double ActivationFunc(double u);
public:
	void Forward();
};

