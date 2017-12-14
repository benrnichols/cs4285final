#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <openssl/aes.h>
#include <sys/time.h>

void timestamp(struct timespec* var)
{
	timespec_get(&var, NULL);
}


unsigned char key[16];
AES_KEY expanded;
unsigned char zero[16];
unsigned char scrambledzero[16];

void handle(char out[40],char in[],int len)
{
	unsigned char workarea[len* 3];
	int i;
	for (i = 0;i < 40;++i)
	{
		out[i] = 0;
	}
	struct timespec start;
	timestamp(&start);

	if (len < 16) return;
	for (i = 0;i < 16;++i)
	{
		out[i] = in[i];
	}
	for (i = 16;i < len;++i)
	{
		workarea[i] = in[i];
	
	}
	for(int j = 0; j<100; j++ ) {
	AES_encrypt(in,workarea, &expanded);
	}
	/* a realserver would now check AES-based authenticator, */
	/* process legitimate packets, and generate useful output*/

	for (i = 0;i < 16;++i)
	{
		out[16+ i] = scrambledzero[i];
	}
	struct timespec stop;
	timestamp(&stop);

	*(unsigned int *) (out +32) = stop.tv_nsec - start.tv_nsec;
	printf("%ld", stop.tv_nsec - start.tv_nsec);
}


struct sockaddr_in server;
struct sockaddr_in client;
socklen_t clientlen;
int s;
char in[1537];
int r;
char out[40];

main(int argc,char **argv)
{
	if (read(0,key,sizeof key) < sizeof key)
	{
		return	111;
	}
	AES_set_encrypt_key(key,128,&expanded);
	AES_encrypt(zero,scrambledzero,&expanded);
	if (!argv[1])
		return 100;
	if (!inet_aton(argv[1],&server.sin_addr))
		return 100;
	server.sin_family= AF_INET;
	server.sin_port = htons(10000);
	
	s = socket(AF_INET,SOCK_DGRAM,0);
	if (s == -1) 
		return 111;
	if (bind(s,(struct sockaddr *) &server,sizeof server) == -1)
		return 111;

	for (;;) {
		clientlen = sizeof client;
		r = recvfrom(s,in,sizeof in,0,(struct sockaddr*) &client,&clientlen);
		if (r < 16) continue;
		if (r >= sizeof in) continue;
		handle(out,in,r);
		sendto(s,out,40,0,(struct sockaddr *) &client,clientlen);
	}	
}
