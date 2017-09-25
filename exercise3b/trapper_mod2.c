// trapper.c 
#include <signal.h>
#include <stdio.h>
#include <unistd.h>
void trapper(int);
int main()
{
	int i;
	for(i=1;i<=64;i++)
		signal(i, trapper);
	printf("Process identifier: %d\n", getpid() );
	pause();
	printf("Continuing...\n");
	printf("Finished.\n");
	return 0;
}
void trapper(int sig)
{
	if (sig==10)
		printf("\nHello\n");
	else
		printf("\nReceived signal: %d\n", sig);
}
