#pragma comment(lib,"ws2_32")
#include <WS2tcpip.h>
#include <WinSock2.h>
#include <stdlib.h>
#include <stdio.h>

#define MULTICASTIP "235.7.8.9"
#define LOCALPORT 9000 //���� ��Ʈ ��ȣ
#define REMOTEPORT 9000
#define BUFSIZE 512 //����� ���� ����

//���� �Լ� ���� ��� �� ����

void err_quit(char* msg) {
	exit(1);
}

//���� �Լ� ���� ���
void err_display(char* msg) {
	printf("����");
}

DWORD WINAPI SenderThread(LPVOID arg)
{
	int retval;
	SOCKET sock = socket(AF_INET, SOCK_DGRAM, 0);
	if (sock == INVALID_SOCKET) err_quit("socket()");

	int ttl = 2;
	retval = setsockopt(sock, IPPROTO_IP, IP_MULTICAST_TTL,
		(char*)&ttl, sizeof(ttl));
	if (retval == SOCKET_ERROR)err_quit("setsockopt()");

	SOCKADDR_IN remoteaddr;//���� �ּ� ����ü ���� ����
	ZeroMemory(&remoteaddr, sizeof(remoteaddr));//(�ּҰ���, ũ��)

	remoteaddr.sin_family = AF_INET;
	remoteaddr.sin_addr.s_addr = inet_addr(MULTICASTIP);//:������ ���� IP�ּ� ����
	remoteaddr.sin_port = htons(REMOTEPORT);

	int len;
	char buf[BUFSIZE + 1];

	while (1) {
		printf("\n[���� ������]");
		if (fgets(buf, BUFSIZE + 1, stdin) == NULL)//fgets: �Լ��� ���� ����ڷκ��� ���ڿ��� �Է� ����
			break;
		len = strlen(buf);
		if (buf[len - 1] == '\n')
			buf[len - 1] = '\0';
		if (strlen(buf) == 0)
			break;
		retval = sendto(sock, buf, strlen(buf), 0,
			(SOCKADDR*)&remoteaddr, sizeof(remoteaddr));
		if (retval == SOCKET_ERROR) {
			err_display("sendto()");
			continue;
		}
	}
	closesocket(sock);
	return 0;
}

DWORD WINAPI ReceiverThread(LPVOID arg)
{
	int retval;
	SOCKET sock = socket(AF_INET, SOCK_DGRAM, 0);
	if (sock == INVALID_SOCKET) err_quit("socket()");

	BOOL optval = TRUE;
	retval = setsockopt(sock, SOL_SOCKET,
		SO_REUSEADDR, (char*)&optval, sizeof(optval));
	if (retval == SOCKET_ERROR)err_quit("setsockopt()");

	SOCKADDR_IN localaddr;//���� �ּ� ����ü ���� ����
	ZeroMemory(&localaddr, sizeof(localaddr));//(�ּҰ���, ũ��)

	localaddr.sin_family = AF_INET;
	localaddr.sin_addr.s_addr = htonl(INADDR_ANY);//:������ ���� IP�ּ� ����
	localaddr.sin_port = htons(LOCALPORT);
	retval = bind(sock, (SOCKADDR*)&localaddr, sizeof(localaddr));
	if (retval == SOCKET_ERROR)err_quit("bind()");

	struct ip_mreq mreq;
	mreq.imr_multiaddr.s_addr = inet_addr(MULTICASTIP);
	mreq.imr_interface.s_addr = htonl(INADDR_ANY);
	retval = setsockopt(sock, IPPROTO_IP, IP_ADD_MEMBERSHIP,
		(char*)&mreq, sizeof(mreq));
	if (retval == SOCKET_ERROR)err_quit("setsockopt()");

	SOCKADDR_IN peeraddr;//������ Ŭ���̾�Ʈ�� IP�ּҿ� ��Ʈ ��ȣ�� ����
	int addrlen;//������ ũ��
	char buf[BUFSIZE + 1];

	while (1) {
		//������ �ޱ�
		addrlen = sizeof(peeraddr);
		retval = recvfrom(sock, buf, BUFSIZE, 0,
			(SOCKADDR*)&peeraddr, &addrlen);
		if (retval == SOCKET_ERROR) {
			err_display("recvfrom()");
			continue;
		}

		buf[retval] = '\0';
		printf("%s\n", buf);
	}

	retval = setsockopt(sock, IPPROTO_IP, IP_DROP_MEMBERSHIP,
		(char*)&mreq, sizeof(mreq));
	if (retval == SOCKET_ERROR)
		err_quit("setsockopt()");
	closesocket(sock);
	return 0;
}

int main(int argc, char* argv[]) {


	//���� �ʱ�ȭ
	WSADATA wsa;
	if (WSAStartup(MAKEWORD(2, 2), &wsa) != 0)
		return 1;

	HANDLE hThreads[2];
	hThreads[0] = CreateThread(NULL, 0, SenderThread, NULL, 0, NULL);
	hThreads[1] = CreateThread(NULL, 0, ReceiverThread, NULL, 0, NULL);
	
	WaitForMultipleObjects(2, hThreads, TRUE, INFINITE);



	WSACleanup();
	return 0;
}